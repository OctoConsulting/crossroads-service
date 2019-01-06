package gov.fbi.elabs.crossroads.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.fbi.elabs.crossroads.domain.ErrorMessage;
import gov.fbi.elabs.crossroads.domain.EvidenceTransfer;
import gov.fbi.elabs.crossroads.domain.EvidenceTransferUI;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.repository.EvidenceRepository;
import gov.fbi.elabs.crossroads.repository.StorageAreaAuthorizationRepository;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Service
@Transactional
public class EvidenceTransferService {

	@Autowired
	StorageAreaAuthorizationRepository storageAreaAuthorizationRepo;

	@Autowired
	EvidenceRepository evidenceRepo;

	public void transferEvidence(Integer batchID, EvidenceTransferUI evidenceTransferUI) {
		List<EvidenceTransfer> evidenceTransferList = new ArrayList<EvidenceTransfer>();
		// for(){
		EvidenceTransfer evidenceTransfer = new EvidenceTransfer();
		evidenceTransfer.setBatchID(batchID);
		evidenceTransfer.setBatchCreationDate(evidenceTransferUI.getBatchCreationDate());
		evidenceTransfer.setEvidenceTransferStatusCode(Constants.TRANSFER_STATUS_COMPLETE);
		evidenceTransfer.setComments(evidenceTransferUI.getComments());
		evidenceTransfer.setLockboxTransferID(null);
		evidenceTransfer.setVerifiedByID(null);
		evidenceTransfer.setBatchComments(null);
		evidenceTransfer.setAgencyLocationId(null);
		evidenceTransfer.setOfficerId(null);
		// evidenceTransfer.setEmployeeID(evidenceTransferUI.getEmployeeID());
		evidenceTransfer.setEvidenceTransferReasonID(evidenceTransferUI.getTransferReason());
		// evidenceTransfer.setEvidenceTransferTypeCode(evidenceTransferUI.getTransferTypeID());
		evidenceTransfer.setWitness1Id(evidenceTransferUI.getWitnessID1());
		evidenceTransfer.setWitness2Id(evidenceTransferUI.getWitnessID2());
		evidenceTransferList.add(evidenceTransfer);
		// }

		// int entityCount = 50;
		// int batchSize = 25;
		//
		// EntityManager entityManager = entityManagerFactory().createEntityManager();
		//
		// EntityTransaction entityTransaction = entityManager.getTransaction();
		//
		// try {
		// entityTransaction.begin();
		//
		// for (int i = 0; i < entityCount; i++) {
		// if (i > 0 && i % batchSize == 0) {
		// entityTransaction.commit();
		// entityTransaction.begin();
		//
		// entityManager.clear();
		// }
		//
		// Post post = new Post(String.format("Post %d", i + 1));
		//
		// entityManager.persist(post);
		// }
		//
		// entityTransaction.commit();
		// } catch (RuntimeException e) {
		// if (entityTransaction.isActive()) {
		// entityTransaction.rollback();
		// }
		// throw e;
		// } finally {
		// entityManager.close();
		// }
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
		if (evidenceTransferUI.getStorageArea() == null || evidenceTransferUI.getStorageArea() == 0) {
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
		Integer transferInStorageArea = evidenceTransferUI.getStorageArea();
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
