package gov.fbi.elabs.crossroads.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.fbi.elabs.crossroads.utilities.CustomTimestampDeserializer;
import gov.fbi.elabs.crossroads.utilities.CustomTimestampSerializer;

public class EvidenceTransferUI {

	private Integer batchID;

	private Integer employeeID;
	private String employeeUserName;
	private String employeePwd;

	private Integer witness1ID;
	private String witness1UserName;
	private String witness1Pwd;

	private Integer witness2ID;
	private String witness2UserName;

	private String witness2Pwd;

	private Integer locationID;
	private Integer organizationID;

	private Boolean requiresLocation;

	private Integer storageAreaID;
	private String storageLocationID;

	private String evidenceTransferTypeCode;
	private Integer transferReason;
	private String comments;
	private Boolean witness1Validated;
	private Boolean witness2Validated;
	private Boolean employeeValidated;
	private Boolean isReasonRequired;
	private Integer requiredWitnessCount;

	public Integer getBatchID() {
		return batchID;
	}

	public void setBatchID(Integer batchID) {
		this.batchID = batchID;
	}

	public String getEmployeeUserName() {
		return employeeUserName;
	}

	public void setEmployeeUserName(String employeeUserName) {
		this.employeeUserName = employeeUserName;
	}

	public String getEmployeePwd() {
		return employeePwd;
	}

	public void setEmployeePwd(String employeePwd) {
		this.employeePwd = employeePwd;
	}

	public Integer getWitness1ID() {
		return witness1ID;
	}

	public void setWitness1ID(Integer witness1id) {
		witness1ID = witness1id;
	}

	public String getWitness1UserName() {
		return witness1UserName;
	}

	public void setWitness1UserName(String witness1UserName) {
		this.witness1UserName = witness1UserName;
	}

	public String getWitness1Pwd() {
		return witness1Pwd;
	}

	public void setWitness1Pwd(String witness1Pwd) {
		this.witness1Pwd = witness1Pwd;
	}

	public Integer getWitness2ID() {
		return witness2ID;
	}

	public void setWitness2ID(Integer witness2id) {
		witness2ID = witness2id;
	}

	public String getWitness2UserName() {
		return witness2UserName;
	}

	public void setWitness2UserName(String witness2UserName) {
		this.witness2UserName = witness2UserName;
	}

	public String getWitness2Pwd() {
		return witness2Pwd;
	}

	public void setWitness2Pwd(String witness2Pwd) {
		this.witness2Pwd = witness2Pwd;
	}

	public Boolean getRequiresLocation() {
		return requiresLocation;
	}

	public void setRequiresLocation(Boolean requiresLocation) {
		this.requiresLocation = requiresLocation;
	}

	public Integer getStorageAreaID() {
		return storageAreaID;
	}

	public void setStorageAreaID(Integer storageAreaID) {
		this.storageAreaID = storageAreaID;
	}

	public String getStorageLocationID() {
		return storageLocationID;
	}

	public void setStorageLocationID(String storageLocationID) {
		this.storageLocationID = storageLocationID;
	}

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

	public Integer getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
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
