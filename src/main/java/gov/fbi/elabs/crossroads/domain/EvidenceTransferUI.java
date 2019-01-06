package gov.fbi.elabs.crossroads.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.fbi.elabs.crossroads.utilities.CustomTimestampDeserializer;
import gov.fbi.elabs.crossroads.utilities.CustomTimestampSerializer;

public class EvidenceTransferUI {

	private Integer batchID;
	private

	private Integer employeeID;
	private String employeeUserName;
	private String employeePwd;

	private String transferType;

	private String witness1ID,
	private String witness1UserName,
	private String witness1Pwd,

	private String witness2ID,
	private String witness2UserName,
	private String witness2Pwd,

	private Integer locationID;
	private Integer organizationID;

	private Boolean requiresLocation;

	private Integer storageAreaID;
	private Integer storageLocationID;

	private String evidenceTransferTypeCode;
	private Integer transferReason;
	private String comments;
	private Boolean witness1Validated;
	private Boolean witness2Validated;
	private Boolean employeeValidated;
	private Boolean isReasonRequired;
	private Integer requiredWitnessCount;

	public Integer getLocationID() {
		return locationID;
	}

	public void setLocationID(Integer locationID) {
		this.locationID = locationID;
	}

	public Integer getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(Integer organizationID) {
		this.organizationID = organizationID;
	}

	public String getEvidenceTransferTypeCode() {
		return evidenceTransferTypeCode;
	}

	public void setEvidenceTransferTypeCode(String evidenceTransferTypeCode) {
		this.evidenceTransferTypeCode = evidenceTransferTypeCode;
	}

	public Boolean getWitness1Validated() {
		return witness1Validated;
	}

	public void setWitness1Validated(Boolean witness1Validated) {
		this.witness1Validated = witness1Validated;
	}

	public Boolean getWitness2Validated() {
		return witness2Validated;
	}

	public void setWitness2Validated(Boolean witness2Validated) {
		this.witness2Validated = witness2Validated;
	}

	public Boolean getEmployeeValidated() {
		return employeeValidated;
	}

	public void setEmployeeValidated(Boolean employeeValidated) {
		this.employeeValidated = employeeValidated;
	}

	public Boolean getIsReasonRequired() {
		return isReasonRequired;
	}

	public void setIsReasonRequired(Boolean isReasonRequired) {
		this.isReasonRequired = isReasonRequired;
	}

	public Integer getRequiredWitnessCount() {
		return requiredWitnessCount;
	}

	public void setRequiredWitnessCount(Integer requiredWitnessCount) {
		this.requiredWitnessCount = requiredWitnessCount;
	}

	@JsonSerialize(using = CustomTimestampSerializer.class)
	@JsonDeserialize(using = CustomTimestampDeserializer.class)
	private Timestamp batchCreationDate;

	public Timestamp getBatchCreationDate() {
		return batchCreationDate;
	}

	public void setBatchCreationDate(Timestamp batchCreationDate) {
		this.batchCreationDate = batchCreationDate;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public Integer getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}

	public Integer getWitnessID1() {
		return witnessID1;
	}

	public void setWitnessID1(Integer witnessID1) {
		this.witnessID1 = witnessID1;
	}

	public Integer getWitnessID2() {
		return witnessID2;
	}

	public void setWitnessID2(Integer witnessID2) {
		this.witnessID2 = witnessID2;
	}

	public Integer getStorageArea() {
		return storageArea;
	}

	public void setStorageArea(Integer storageArea) {
		this.storageArea = storageArea;
	}

	public Integer getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(Integer storageLocation) {
		this.storageLocation = storageLocation;
	}

	public Integer getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(Integer transferReason) {
		this.transferReason = transferReason;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	// -- Validations :
	// 1. Use Emp username and pwd and write a validate method to return boolean if
	// user pwd is valid, use ldap method written
	// 2. Add required validation for emp un and pwd
	// 3. Add validation if either is present ; witness un or pwd based on witness
	// count
	// 4. Add validation, if requiresLocation = true and StorageLocationID is null
	// 5. Use the following lines to validate transfer permission -- task
	// String username = (String) request.getAttribute("username");
	// EmployeeAuth employeeAuth =
	// employeeAuthUtil.getEmployeeAuthDetails(username);
	// if (employeeAuth.getEmployeeId() == null
	// || !CollectionUtils.containsAny(employeeAuth.getRoleList(), Constants.ROLES)
	// || !employeeAuth.getTaskList().contains(Constants.CAN_VIEW_BATCH)) {
	// return new ResponseEntity<Resource<BatchDetails>>(HttpStatus.UNAUTHORIZED);
	// }
	// -- Write to EvidenceTransfer
	// 5. BatchCreationDate = Fetch batch creation date based on batch id from batch
	// table
	// 6. EvidenceTransferStatusCode = always C
	// 7. FSLabNum, SubmissionNum,EvidenceType,EvidenceID = Fetch from BatchEvidence
	// for every evidence lookup by batchID//
	// Refactor if possible
	// 8. Comments = FE
	// 9. BatchComment = null
	// 10 . CreatedBy = Fetch EmployeeUsername appended with ELAB from EmployeeAuth
	// object retrieved from another validation call

}
