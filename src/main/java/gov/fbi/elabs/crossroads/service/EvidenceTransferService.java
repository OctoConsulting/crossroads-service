package gov.fbi.elabs.crossroads.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.EmployeeAuth;
import gov.fbi.elabs.crossroads.domain.ErrorMessage;
import gov.fbi.elabs.crossroads.domain.EvidenceTransferUI;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.BatchRepository;
import gov.fbi.elabs.crossroads.repository.EvidenceRepository;
import gov.fbi.elabs.crossroads.repository.EvidenceTransferRepository;
import gov.fbi.elabs.crossroads.repository.StorageAreaAuthorizationRepository;

@Service
@Transactional
public class EvidenceTransferService {

	@Autowired
	StorageAreaAuthorizationRepository storageAreaAuthorizationRepo;

	@Autowired
	EvidenceRepository evidenceRepo;

	@Autowired
	EvidenceTransferRepository evidenceTransferRepo;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	LDAPService ldapService;

	Logger logger = LoggerFactory.getLogger(EvidenceTransferService.class);

	public void transferEvidence(EmployeeAuth employeeAuth, EvidenceTransferUI evidenceTransferUI)
			throws BaseApplicationException {

		Integer batchID = evidenceTransferUI.getBatchID();
		String loggedinUser = employeeAuth.getUserName();
		Integer employeeID = employeeAuth.getEmployeeId();
		String evidenceTransferTypeCode = evidenceTransferUI.getEvidenceTransferTypeCode();
		String comments = evidenceTransferUI.getComments();
		Integer transferReason = evidenceTransferUI.getTransferReason();
		Integer storageAreaID = evidenceTransferUI.getStorageAreaID();
		String storageLocationID = evidenceTransferUI.getStorageLocationID();
		Integer witness1ID = evidenceTransferUI.getWitness1ID();
		Integer witness2ID = evidenceTransferUI.getWitness2ID();
		Integer locationID = evidenceTransferUI.getLocationID();
		Integer organizationID = evidenceTransferUI.getOrganizationID();

		Integer newBatchId = batchRepository.getNextBatchId();
		logger.info("Batch Id " + newBatchId);

		if (newBatchId == null) {
			return;
		}

		String evidenceTransferQuery = evidenceTransferRepo.setQueryForEvidenceTransferTable(batchID,
				evidenceTransferTypeCode, employeeID, loggedinUser, comments, transferReason, storageAreaID,
				storageLocationID, locationID, organizationID, witness1ID, witness2ID, newBatchId);
		String evidenceQuery = evidenceTransferRepo.setQueryForEvidenceTable(batchID, employeeID, storageAreaID,
				storageLocationID, locationID, organizationID, newBatchId);
		evidenceTransferRepo.transferEvidence(evidenceTransferQuery, evidenceQuery);
	}

	public List<ErrorMessage> validateEvidenceTransfer(Integer batchID, EvidenceTransferUI evidenceTransferUI)
			throws BaseApplicationException {

		List<ErrorMessage> errorMessagesList = new ArrayList<ErrorMessage>();

		ErrorMessage errorMessage;

		String employeeUserName = evidenceTransferUI.getEmployeeUserName();
		String employeePwd = evidenceTransferUI.getEmployeePwd();

		String witness1UserName = evidenceTransferUI.getWitness1UserName();
		String witness1Pwd = evidenceTransferUI.getWitness1Pwd();

		String witness2UserName = evidenceTransferUI.getWitness2UserName();
		String witness2Pwd = evidenceTransferUI.getWitness2Pwd();

		if ((!StringUtils.isEmpty(employeeUserName) && StringUtils.isEmpty(employeePwd))
				|| (StringUtils.isEmpty(employeeUserName) && !StringUtils.isEmpty(employeePwd))) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("EmployeeAuthorization");
			errorMessage.setErrorMessages("Employee username and Password is required !");
			errorMessagesList.add(errorMessage);
		}

		if (!StringUtils.isEmpty(employeeUserName) && !StringUtils.isEmpty(employeePwd)) {
			Boolean authenticate = ldapService.authenticateUser(employeeUserName, employeePwd);
			if (!authenticate) {
				errorMessage = new ErrorMessage();
				errorMessage.setFieldName("EmployeeAuthorization");
				errorMessage.setErrorMessages("Employee Authentication Failed !");
				errorMessagesList.add(errorMessage);
			}
		}

		if (StringUtils.isEmpty(evidenceTransferUI.getEvidenceTransferTypeCode())) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("transferType");
			errorMessage.setErrorMessages("This transfer type is not allowed.");
			errorMessagesList.add(errorMessage);
		}

		if (evidenceTransferUI.getEmployeeValidated() == null || !evidenceTransferUI.getEmployeeValidated()) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("employeeValidated");
			errorMessage.setErrorMessages("Logged in employees's password can not be validated.");
			errorMessagesList.add(errorMessage);
		}
		if (evidenceTransferUI.getLocationID() == null || evidenceTransferUI.getLocationID() == 0) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("locationID");
			errorMessage.setErrorMessages("At Lab field can not be left blank.");
			errorMessagesList.add(errorMessage);
		}
		if (evidenceTransferUI.getOrganizationID() == null || evidenceTransferUI.getOrganizationID() == 0) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("organizationID");
			errorMessage.setErrorMessages("At Unit can not be left blank.");
			errorMessagesList.add(errorMessage);
		}
		if (evidenceTransferUI.getStorageAreaID() == null || evidenceTransferUI.getStorageAreaID() == 0) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("storageArea");
			errorMessage.setErrorMessages("Storage Area field can not be left blank.");
			errorMessagesList.add(errorMessage);
		}

		Integer witnessCount = evidenceTransferUI.getRequiredWitnessCount();
		if (witnessCount > 0) {
			if (witnessCount == 1) {
				if (!StringUtils.isEmpty(witness1UserName) && !StringUtils.isEmpty(witness1Pwd)) {
					Boolean authenticate = ldapService.authenticateUser(witness1UserName, witness1Pwd);
					if (!authenticate) {
						errorMessage = new ErrorMessage();
						errorMessage.setFieldName("Witness1Authorization");
						errorMessage.setErrorMessages("Witness authorization failed !");
						errorMessagesList.add(errorMessage);
					}
				}
			} else {
				if (!StringUtils.isEmpty(witness2UserName) && !StringUtils.isEmpty(witness2Pwd)) {
					Boolean authenticate = ldapService.authenticateUser(witness2UserName, witness2Pwd);
					if (!authenticate) {
						errorMessage = new ErrorMessage();
						errorMessage.setFieldName("Witness2Authorization");
						errorMessage.setErrorMessages("Witness authorization failed !");
						errorMessagesList.add(errorMessage);
					}
				}
			}
		}

		Integer employeeID = evidenceTransferUI.getEmployeeID();
		Integer transferInStorageArea = evidenceTransferUI.getStorageAreaID();
		Integer transferOutStorageArea = evidenceRepo.getEvidenceTransferOutLocation(batchID);
		// transferOutStorageArea = 750;
		Integer transferInAndOutAllowed = 0;
		try {
			transferInAndOutAllowed = storageAreaAuthorizationRepo.checkTransferInAndTransferOutAccessForEmployee(
					employeeID, transferInStorageArea, transferOutStorageArea);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (transferInAndOutAllowed == 0) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("transferInAndOutArea");
			errorMessage.setErrorMessages(
					"The user does not have the privilege to transfer the evidence from/to the storage area.");
			errorMessagesList.add(errorMessage);
		}
		// if (transferInAndOutAllowed == 0) {
		// errorMessage = new ErrorMessage();
		// errorMessage.setFieldName("transferInArea");
		// errorMessage.setErrorMessages(
		// "The user does not have the privilege to transfer the evidence
		// from/to the storage area.");
		// errorMessagesList.add(errorMessage);
		// }
		return errorMessagesList;

		// 1. RequiredWitnessCount,IsReasonRequired from
		// EvidenceTransferType
		// 2. Witness authorization and employee authorization validation
		// 3. If Storage Area is provided
		// 4. If locationID is not null
		// 5. If organizationID is not null
		// 5. If storageLocation is null

	}

}
