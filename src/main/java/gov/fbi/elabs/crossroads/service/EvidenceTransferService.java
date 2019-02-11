package gov.fbi.elabs.crossroads.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.BatchTransferTracker;
import gov.fbi.elabs.crossroads.domain.EmployeeAuth;
import gov.fbi.elabs.crossroads.domain.ErrorMessage;
import gov.fbi.elabs.crossroads.domain.EvidenceTransferUI;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.BatchRepository;
import gov.fbi.elabs.crossroads.repository.EvidenceRepository;
import gov.fbi.elabs.crossroads.repository.EvidenceTransferRepository;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Service
@Transactional
public class EvidenceTransferService {

	@Autowired
	EvidenceRepository evidenceRepo;

	@Autowired
	EvidenceTransferRepository evidenceTransferRepo;

	@Autowired
	BatchRepository batchRepository;

	@Autowired
	LDAPService ldapService;

	@Autowired
	CustodyAreaService custodyAreaService;

	@Autowired
	BatchTransferTrackerService batchTransferTrackerService;

	Logger logger = LoggerFactory.getLogger(EvidenceTransferService.class);

	public boolean transferEvidence(EmployeeAuth employeeAuth, EvidenceTransferUI evidenceTransferUI)
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
			logger.info("Error in creating batch id");
			return false;
		}

		BatchTransferTracker tracker = new BatchTransferTracker();
		tracker.setBatchId(newBatchId);
		tracker.setEmployeeId(employeeAuth.getEmployeeId());
		tracker.setStartTime(new Timestamp(System.currentTimeMillis()));
		tracker.setIsActive(true);

		String evidenceTransferQuery = evidenceTransferRepo.setQueryForEvidenceTransferTable(batchID,
				evidenceTransferTypeCode, employeeID, loggedinUser, comments, transferReason, storageAreaID,
				storageLocationID, locationID, organizationID, witness1ID, witness2ID, newBatchId);
		String evidenceQuery = evidenceTransferRepo.setQueryForEvidenceTable(batchID, employeeID, storageAreaID,
				storageLocationID, locationID, organizationID, newBatchId);
		BatchTransferTracker track = batchTransferTrackerService.createTracker(tracker);
		boolean status = evidenceTransferRepo.transferEvidence(evidenceTransferQuery, evidenceQuery);
		batchTransferTrackerService.updateEndTime(track);

		return status;
	}

	public List<ErrorMessage> validateEvidenceTransfer(Integer batchID, EvidenceTransferUI evidenceTransferUI,
			EmployeeAuth employeeAuth) throws BaseApplicationException {

		List<ErrorMessage> errorMessagesList = new ArrayList<ErrorMessage>();

		ErrorMessage errorMessage;

		if (evidenceTransferUI == null) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("Evidence Transfer Body Missing");
			errorMessage.setErrorMessages("Transfer Type is required");
			errorMessagesList.add(errorMessage);
			return errorMessagesList;
		}

		String employeeUserName = evidenceTransferUI.getEmployeeUserName();
		String employeePwd = evidenceTransferUI.getEmployeePwd();

		String witness1UserName = evidenceTransferUI.getWitness1UserName();
		String witness1Pwd = evidenceTransferUI.getWitness1Pwd();

		String witness2UserName = evidenceTransferUI.getWitness2UserName();
		String witness2Pwd = evidenceTransferUI.getWitness2Pwd();

		// if ((!StringUtils.isEmpty(employeeUserName) &&
		// StringUtils.isEmpty(employeePwd))
		// || (StringUtils.isEmpty(employeeUserName) &&
		// !StringUtils.isEmpty(employeePwd))) {
		// errorMessage = new ErrorMessage();
		// errorMessage.setFieldName("EmployeeAuthorization");
		// errorMessage.setErrorMessages("Employee username and Password is
		// required !");
		// errorMessagesList.add(errorMessage);
		// }
		//
		// if (!StringUtils.isEmpty(employeeUserName) &&
		// !StringUtils.isEmpty(employeePwd)) {
		// Boolean authenticate = ldapService.authenticateUser(employeeUserName,
		// employeePwd);
		// if (!authenticate) {
		// errorMessage = new ErrorMessage();
		// errorMessage.setFieldName("employeeValidated");
		// errorMessage.setErrorMessages("Employee Authentication Failed !");
		// errorMessagesList.add(errorMessage);
		// }
		// }

		this.validateUserInfo(employeeUserName, employeePwd, "employeeValidated", "Employee Authentication Failed !",
				errorMessagesList);

		if (StringUtils.isEmpty(evidenceTransferUI.getEvidenceTransferTypeCode())) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("transferType");
			errorMessage.setErrorMessages("Transfer Type is required");
			errorMessagesList.add(errorMessage);
		}

		if (evidenceTransferUI.getEmployeeID() == null) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("employeeId");
			errorMessage.setErrorMessages("Employee Id is required");
			errorMessagesList.add(errorMessage);
		}

		if (evidenceTransferUI.getBatchID() == null) {
			errorMessage = new ErrorMessage();
			errorMessage.setFieldName("batchId");
			errorMessage.setErrorMessages("Batch Id is required");
			errorMessagesList.add(errorMessage);
		}

		// if (evidenceTransferUI.getEmployeeValidated() == null ||
		// !evidenceTransferUI.getEmployeeValidated()) {
		// errorMessage = new ErrorMessage();
		// errorMessage.setFieldName("employeeValidated");
		// errorMessage.setErrorMessages("Logged in employees's password can not
		// be validated.");
		// errorMessagesList.add(errorMessage);
		// }

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

		boolean w1Val = this.validateUserInfo(witness1UserName, witness1Pwd, "Witness1Authorization",
				"Witness authorization failed !", errorMessagesList);

		boolean w2Val = this.validateUserInfo(witness2UserName, witness2Pwd, "Witness2Authorization",
				"Witness authorization failed !", errorMessagesList);

		// if (!w1Val) {
		// //if (evidenceTransferUI.getWitness1ID() == null) {
		// errorMessage = new ErrorMessage();
		// errorMessage.setFieldName("witness1Id");
		// errorMessage.setErrorMessages("Witness 1 Id is required");
		// errorMessagesList.add(errorMessage);
		// //}
		// }
		//
		// if (!w2Val) {
		// //if (evidenceTransferUI.getWitness2ID() == null) {
		// errorMessage = new ErrorMessage();
		// errorMessage.setFieldName("witness2Id");
		// errorMessage.setErrorMessages("Witness 2 Id is required");
		// errorMessagesList.add(errorMessage);
		// //}
		// }

		if (evidenceTransferUI.getRequiresLocation()) {
			if (StringUtils.isEmpty(evidenceTransferUI.getStorageLocationID())) {
				errorMessage = new ErrorMessage();
				errorMessage.setFieldName("storageLocation");
				errorMessage.setErrorMessages("Storage Location is required for the storage area selected");
				errorMessagesList.add(errorMessage);
			}
		}

		if (evidenceTransferUI.getIsReasonRequired()) {
			if (evidenceTransferUI.getTransferReason() == null) {
				errorMessage = new ErrorMessage();
				errorMessage.setFieldName("transferReason");
				errorMessage.setErrorMessages("Transfer Reason is required for this type of transfer");
				errorMessagesList.add(errorMessage);
			}
		}

		if (witnessCount > 0) {
			int count = 0;
			if (StringUtils.isNotEmpty(witness1UserName) && StringUtils.isNotEmpty(witness1Pwd) && w1Val) {
				count = count + 1;
			}

			if (StringUtils.isNotEmpty(witness2UserName) && StringUtils.isNotEmpty(witness2Pwd) && w2Val) {
				count = count + 1;
			}

			if (count < witnessCount) {
				errorMessage = new ErrorMessage();
				errorMessage.setFieldName("witnessCount");
				errorMessage
						.setErrorMessages("This Transfer requires atleast " + witnessCount + " validated witnesses");
				errorMessagesList.add(errorMessage);
			}
		}

		Integer employeeID = employeeAuth.getEmployeeId();
		Integer transferInStorageArea = evidenceTransferUI.getStorageAreaID();
		if (employeeID != null && transferInStorageArea != null) {
			boolean auth = custodyAreaService.validateTransferAuth(employeeID, transferInStorageArea,
					Constants.TRANSFER_IN);

			if (!auth) {
				errorMessage = new ErrorMessage();
				errorMessage.setFieldName("transferIn");
				errorMessage.setErrorMessages("User not Authorized to Transfer In to this storage area");
				errorMessagesList.add(errorMessage);

			}

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

	public boolean validateUserInfo(String username, String pwd, String field, String message,
			List<ErrorMessage> errorList) {

		if ((StringUtils.isEmpty(username) && StringUtils.isNotEmpty(pwd))
				|| (StringUtils.isNotEmpty(username) && StringUtils.isEmpty(pwd))) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setFieldName(field);
			errorMessage.setErrorMessages(message);
			errorList.add(errorMessage);
			return false;
		}

		else if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(pwd)) {
			Boolean authenticate = ldapService.authenticateUser(username, pwd);
			if (!authenticate) {
				ErrorMessage errorMessage = new ErrorMessage();
				errorMessage.setFieldName(field);
				errorMessage.setErrorMessages(message);
				errorList.add(errorMessage);
				return false;
			}
		}

		return true;
	}

}
