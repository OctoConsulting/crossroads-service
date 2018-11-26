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

/**
 * 
 * @author nithinemanuel
 *
 */
@Entity
@Table(name = "EvidenceTransferReason")
@JsonInclude(Include.NON_NULL)
public class EvidenceTransferReason extends ResourceSupport {

	@Id
	@Column(name = "EvidenceTransferReasonID")
	private Integer transferReasonId;

	@Column(name = "EvidenceTransferReasonDescription")
	private String transferReason;

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

	/**
	 * @return the transferReasonId
	 */
	public Integer getTransferReasonId() {
		return transferReasonId;
	}

	/**
	 * @param transferReasonId
	 *            the transferReasonId to set
	 */
	public void setTransferReasonId(Integer transferReasonId) {
		this.transferReasonId = transferReasonId;
	}

	/**
	 * @return the transferReason
	 */
	public String getTransferReason() {
		return transferReason;
	}

	/**
	 * @param transferReason
	 *            the transferReason to set
	 */
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
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
	 * @param createdDate
	 *            the createdDate to set
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
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
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
	 * @param lastModifiedDate
	 *            the lastModifiedDate to set
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
	 * @param isActive
	 *            the isActive to set
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
	 * @param guid
	 *            the guid to set
	 */
	public void setGuid(UUID guid) {
		this.guid = guid;
	}

}
