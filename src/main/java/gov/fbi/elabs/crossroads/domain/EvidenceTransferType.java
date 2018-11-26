package gov.fbi.elabs.crossroads.domain;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.fbi.elabs.crossroads.utilities.CustomTimestampDeserializer;
import gov.fbi.elabs.crossroads.utilities.CustomTimestampSerializer;

@Entity
@Table(name = "EvidenceTransferType")
@JsonInclude(Include.NON_NULL)
public class EvidenceTransferType extends ResourceSupport {
	
	@Id
	@Column(name = "EvidenceTransferTypeCode")
	private String transferTypeCode;
	
	@Column(name = "EvidenceTransferTypeDescription")
	private String transferType;
	
	@Column(name = "DefaultEvidenceTransferReasonID")
	private Integer transferReasonId;
	
	@Column(name = "IsReasonRequired")
	private Boolean isReasonRequired;
	
	@Column(name = "ReportApplicationFile")
	private UUID reportApplicationFile;
	
	@Column(name = "Options")
	private Boolean options;
	
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
	private Timestamp lastModifiedDate;
	
	@Column(name = "IsActive")
	private Boolean isActive;
	
	@Column(name = "guid")
	private UUID guid;
	
	@Column(name = "Sequence")
	private Integer sequence;
	
	@Column(name = "COCText")
	private String cocText;
	
	@Column(name = "RequiredWitnessCount")
	private Integer requiredWitnessCount;

	/**
	 * @return the transferTypeCode
	 */
	public String getTransferTypeCode() {
		return transferTypeCode;
	}

	/**
	 * @param transferTypeCode the transferTypeCode to set
	 */
	public void setTransferTypeCode(String transferTypeCode) {
		this.transferTypeCode = transferTypeCode;
	}

	/**
	 * @return the transferType
	 */
	public String getTransferType() {
		return transferType;
	}

	/**
	 * @param transferType the transferType to set
	 */
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	/**
	 * @return the transferReasonId
	 */
	public Integer getTransferReasonId() {
		return transferReasonId;
	}

	/**
	 * @param transferReasonId the transferReasonId to set
	 */
	public void setTransferReasonId(Integer transferReasonId) {
		this.transferReasonId = transferReasonId;
	}

	/**
	 * @return the isReasonRequired
	 */
	public Boolean getIsReasonRequired() {
		return isReasonRequired;
	}

	/**
	 * @param isReasonRequired the isReasonRequired to set
	 */
	public void setIsReasonRequired(Boolean isReasonRequired) {
		this.isReasonRequired = isReasonRequired;
	}

	/**
	 * @return the reportApplicationFile
	 */
	public UUID getReportApplicationFile() {
		return reportApplicationFile;
	}

	/**
	 * @param reportApplicationFile the reportApplicationFile to set
	 */
	public void setReportApplicationFile(UUID reportApplicationFile) {
		this.reportApplicationFile = reportApplicationFile;
	}

	/**
	 * @return the options
	 */
	public Boolean getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(Boolean options) {
		this.options = options;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the isActive
	 */
	public Boolean getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the guid
	 */
	public UUID getGuid() {
		return guid;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(UUID guid) {
		this.guid = guid;
	}

	/**
	 * @return the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the cocText
	 */
	public String getCocText() {
		return cocText;
	}

	/**
	 * @param cocText the cocText to set
	 */
	public void setCocText(String cocText) {
		this.cocText = cocText;
	}

	/**
	 * @return the requiredWitnessCount
	 */
	public Integer getRequiredWitnessCount() {
		return requiredWitnessCount;
	}

	/**
	 * @param requiredWitnessCount the requiredWitnessCount to set
	 */
	public void setRequiredWitnessCount(Integer requiredWitnessCount) {
		this.requiredWitnessCount = requiredWitnessCount;
	}
	
	
}
