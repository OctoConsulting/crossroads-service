package gov.fbi.elabs.crossroads.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.fbi.elabs.crossroads.utilities.CustomTimestampDeserializer;
import gov.fbi.elabs.crossroads.utilities.CustomTimestampSerializer;

@Entity
@Table(name = "EvidenceTransfer")
@JsonInclude(Include.NON_NULL)
public class EvidenceTransfer {

	@Id
	@Column(name = "EmployeeTransferID")
	private Integer employeeTransferID;

	@JsonSerialize(using = CustomTimestampSerializer.class)
	@JsonDeserialize(using = CustomTimestampDeserializer.class)
	@Column(name = "TransferDate")
	private Timestamp transferDate;

	@Column(name = "BatchID")
	private Integer batchID;

	@JsonSerialize(using = CustomTimestampSerializer.class)
	@JsonDeserialize(using = CustomTimestampDeserializer.class)
	@Column(name = "BatchCreationDate")
	private Timestamp batchCreationDate;

	@Column(name = "EvidenceTransferTypeCode")
	private Integer evidenceTransferTypeCode;

	@Column(name = "EvidenceTransferStatusCode")
	private String evidenceTransferStatusCode;

	@Column(name = "FromEmployeeID")
	private Integer fromEmployeeID;

	@Column(name = "ToEmployeeID")
	private Integer toEmployeeID;

	@Column(name = "FromLocationID")
	private Integer fromLocationID;

	@Column(name = "ToLocationID")
	private Integer toLocationID;

	@Column(name = "fromOrganizationID")
	private Integer FromOrganizationID;

	@Column(name = "ToOrganizationID")
	private Integer toOrganizationID;

	@Column(name = "LockboxTransferID")
	private Integer lockboxTransferID;

	@Column(name = "StorageAreaID")
	private Integer storageAreaID;

	@Column(name = "StorageLocationCode")
	private Integer storageLocationCode;

	@Column(name = "VerifiedByID")
	private Integer verifiedByID;

	@Column(name = "SubmissionNum")
	private Integer submissionNum;

	@Column(name = "FSLabNum")
	private String fsLabNum;

	@Column(name = "EvidenceType")
	private String evidenceType;

	@Column(name = "EvidenceID")
	private String evidenceID;

	@Column(name = "BatchComments")
	private String batchComments;

	@Column(name = "Comments")
	private String comments;

	@Column(name = "EvidenceTransferReasonID")
	private Integer evidenceTransferReasonID;

	@Column(name = "CreatedBy")
	private String createdBy;

	@JsonSerialize(using = CustomTimestampSerializer.class)
	@JsonDeserialize(using = CustomTimestampDeserializer.class)
	@Column(name = "CreatedDate")
	private Timestamp createdDate;

	@Column(name = "LastModifiedBy")
	private String lastModifiedBy;

	@JsonSerialize(using = CustomTimestampSerializer.class)
	@JsonDeserialize(using = CustomTimestampDeserializer.class)
	@Column(name = "LastModifiedDate")
	private Timestamp LastModifiedDate;

	@Column(name = "IsActive")
	private String IsActive;

	@Column(name = "guid")
	private String guid;

	@Column(name = "Witness1Id")
	private Integer Witness1Id;

	@Column(name = "Witness2Id")
	private Integer Witness2Id;

	@Column(name = "AgencyLocationId")
	private Integer AgencyLocationId;

	@Column(name = "OfficerId")
	private Integer OfficerId;

	@JsonSerialize(using = CustomTimestampSerializer.class)
	@JsonDeserialize(using = CustomTimestampDeserializer.class)
	@Column(name = "RowVersion")
	private Timestamp RowVersion;

	public Integer getEmployeeTransferID() {
		return employeeTransferID;
	}

	public void setEmployeeTransferID(Integer employeeTransferID) {
		this.employeeTransferID = employeeTransferID;
	}

	public Timestamp getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Timestamp transferDate) {
		this.transferDate = transferDate;
	}

	public Integer getBatchID() {
		return batchID;
	}

	public void setBatchID(Integer batchID) {
		this.batchID = batchID;
	}

	public Timestamp getBatchCreationDate() {
		return batchCreationDate;
	}

	public void setBatchCreationDate(Timestamp batchCreationDate) {
		this.batchCreationDate = batchCreationDate;
	}

	public Integer getEvidenceTransferTypeCode() {
		return evidenceTransferTypeCode;
	}

	public void setEvidenceTransferTypeCode(Integer evidenceTransferTypeCode) {
		this.evidenceTransferTypeCode = evidenceTransferTypeCode;
	}

	public String getEvidenceTransferStatusCode() {
		return evidenceTransferStatusCode;
	}

	public void setEvidenceTransferStatusCode(String evidenceTransferStatusCode) {
		this.evidenceTransferStatusCode = evidenceTransferStatusCode;
	}

	public Integer getFromEmployeeID() {
		return fromEmployeeID;
	}

	public void setFromEmployeeID(Integer fromEmployeeID) {
		this.fromEmployeeID = fromEmployeeID;
	}

	public Integer getToEmployeeID() {
		return toEmployeeID;
	}

	public void setToEmployeeID(Integer toEmployeeID) {
		this.toEmployeeID = toEmployeeID;
	}

	public Integer getFromLocationID() {
		return fromLocationID;
	}

	public void setFromLocationID(Integer fromLocationID) {
		this.fromLocationID = fromLocationID;
	}

	public Integer getToLocationID() {
		return toLocationID;
	}

	public void setToLocationID(Integer toLocationID) {
		this.toLocationID = toLocationID;
	}

	public Integer getFromOrganizationID() {
		return FromOrganizationID;
	}

	public void setFromOrganizationID(Integer fromOrganizationID) {
		FromOrganizationID = fromOrganizationID;
	}

	public Integer getToOrganizationID() {
		return toOrganizationID;
	}

	public void setToOrganizationID(Integer toOrganizationID) {
		this.toOrganizationID = toOrganizationID;
	}

	public Integer getLockboxTransferID() {
		return lockboxTransferID;
	}

	public void setLockboxTransferID(Integer lockboxTransferID) {
		this.lockboxTransferID = lockboxTransferID;
	}

	public Integer getStorageAreaID() {
		return storageAreaID;
	}

	public void setStorageAreaID(Integer storageAreaID) {
		this.storageAreaID = storageAreaID;
	}

	public Integer getStorageLocationCode() {
		return storageLocationCode;
	}

	public void setStorageLocationCode(Integer storageLocationCode) {
		this.storageLocationCode = storageLocationCode;
	}

	public Integer getVerifiedByID() {
		return verifiedByID;
	}

	public void setVerifiedByID(Integer verifiedByID) {
		this.verifiedByID = verifiedByID;
	}

	public Integer getSubmissionNum() {
		return submissionNum;
	}

	public void setSubmissionNum(Integer submissionNum) {
		this.submissionNum = submissionNum;
	}

	public String getFsLabNum() {
		return fsLabNum;
	}

	public void setFsLabNum(String fsLabNum) {
		this.fsLabNum = fsLabNum;
	}

	public String getEvidenceType() {
		return evidenceType;
	}

	public void setEvidenceType(String evidenceType) {
		this.evidenceType = evidenceType;
	}

	public String getEvidenceID() {
		return evidenceID;
	}

	public void setEvidenceID(String evidenceID) {
		this.evidenceID = evidenceID;
	}

	public String getBatchComments() {
		return batchComments;
	}

	public void setBatchComments(String batchComments) {
		this.batchComments = batchComments;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getEvidenceTransferReasonID() {
		return evidenceTransferReasonID;
	}

	public void setEvidenceTransferReasonID(Integer evidenceTransferReasonID) {
		this.evidenceTransferReasonID = evidenceTransferReasonID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getLastModifiedDate() {
		return LastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		LastModifiedDate = lastModifiedDate;
	}

	public String getIsActive() {
		return IsActive;
	}

	public void setIsActive(String isActive) {
		IsActive = isActive;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Integer getWitness1Id() {
		return Witness1Id;
	}

	public void setWitness1Id(Integer witness1Id) {
		Witness1Id = witness1Id;
	}

	public Integer getWitness2Id() {
		return Witness2Id;
	}

	public void setWitness2Id(Integer witness2Id) {
		Witness2Id = witness2Id;
	}

	public Integer getAgencyLocationId() {
		return AgencyLocationId;
	}

	public void setAgencyLocationId(Integer agencyLocationId) {
		AgencyLocationId = agencyLocationId;
	}

	public Integer getOfficerId() {
		return OfficerId;
	}

	public void setOfficerId(Integer officerId) {
		OfficerId = officerId;
	}

	public Timestamp getRowVersion() {
		return RowVersion;
	}

	public void setRowVersion(Timestamp rowVersion) {
		RowVersion = rowVersion;
	}

}
