package gov.fbi.elabs.crossroads.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.EmployeeAuth;
import gov.fbi.elabs.crossroads.domain.ErrorMessage;
import gov.fbi.elabs.crossroads.domain.EvidenceTransferUI;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
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

	public void transferEvidence(EmployeeAuth employeeAuth, EvidenceTransferUI evidenceTransferUI) {

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
		String evidenceTransferQuery = evidenceTransferRepo.setQueryForEvidenceTransferTable(batchID,
				evidenceTransferTypeCode, employeeID, loggedinUser, comments, transferReason, storageAreaID,
				storageLocationID, locationID, organizationID, witness1ID, witness2ID);
		String evidenceQuery = evidenceTransferRepo.setQueryForEvidenceTable(batchID, employeeID, storageAreaID,
				storageLocationID, locationID, organizationID);
		evidenceTransferRepo.transferEvidence(evidenceTransferQuery, evidenceQuery);
	}

	public List<ErrorMessage> validateEvidenceTransfer(Integer batchID, EvidenceTransferUI evidenceTransferUI)
			throws BaseApplicationException {

		List<ErrorMessage> errorMessagesList = new ArrayList<ErrorMessage>();
		ErrorMessage errorMessage;

		if (!StringUtils.isEmpty(evidenceTransferUI.getTransferType())) {
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
				if (!evidenceTransferUI.getWitness1Validated() && !evidenceTransferUI.getWitness2Validated()) {
					errorMessage = new ErrorMessage();
					errorMessage.setFieldName("WitnessAuthorization");
					errorMessage.setErrorMessages(
							"Atleast one witness authorization is required to initiate evidence transfer.");
					errorMessagesList.add(errorMessage);
				}
			} else {
				if (!(evidenceTransferUI.getWitness1Validated() && evidenceTransferUI.getWitness2Validated())) {
					errorMessage = new ErrorMessage();
					errorMessage.setFieldName("WitnessAuthorization");
					errorMessage
							.setErrorMessages("All witness authorizations are required to initiate evidence transfer.");
					errorMessagesList.add(errorMessage);
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
