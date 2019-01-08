package gov.fbi.elabs.crossroads.domain;

import java.io.Serializable;
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
@Table(name = "StorageLocation")
@JsonInclude(Include.NON_NULL)
public class CustodyLocation extends ResourceSupport implements Serializable {

	@Id
	@Column(name = "StorageAreaID")
	private Integer storageAreaId;

	@Id
	@Column(name = "StorageLocationCode")
	private String storageLocationCode;

	@Column(name = "StorageLocationDescription")
	private String storageLocationDescription;

	@Column(name = "CanBeEmpty")
	private Boolean canBeEmpty;

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

	private UUID guid;

	@Column(name = "StorageLocationId")
	private Integer storageLocationId;

	@Column(name = "ExternalBarCode")
	private String externalBarCode;

	/**
	 * @return the storageAreaId
	 */
	public Integer getStorageAreaId() {
		return storageAreaId;
	}

	/**
	 * @param storageAreaId
	 *            the storageAreaId to set
	 */
	public void setStorageAreaId(Integer storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	/**
	 * @return the storageLocationCode
	 */
	public String getStorageLocationCode() {
		return storageLocationCode;
	}

	/**
	 * @param storageLocationCode
	 *            the storageLocationCode to set
	 */
	public void setStorageLocationCode(String storageLocationCode) {
		this.storageLocationCode = storageLocationCode;
	}

	/**
	 * @return the storageLocationDescription
	 */
	public String getStorageLocationDescription() {
		return storageLocationDescription;
	}

	/**
	 * @param storageLocationDescription
	 *            the storageLocationDescription to set
	 */
	public void setStorageLocationDescription(String storageLocationDescription) {
		this.storageLocationDescription = storageLocationDescription;
	}

	/**
	 * @return the canBeEmpty
	 */
	public Boolean getCanBeEmpty() {
		return canBeEmpty;
	}

	/**
	 * @param canBeEmpty
	 *            the canBeEmpty to set
	 */
	public void setCanBeEmpty(Boolean canBeEmpty) {
		this.canBeEmpty = canBeEmpty;
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

	/**
	 * @return the storageLocationId
	 */
	public Integer getStorageLocationId() {
		return storageLocationId;
	}

	/**
	 * @param storageLocationId
	 *            the storageLocationId to set
	 */
	public void setStorageLocationId(Integer storageLocationId) {
		this.storageLocationId = storageLocationId;
	}

	/**
	 * @return the externalBarCode
	 */
	public String getExternalBarCode() {
		return externalBarCode;
	}

	/**
	 * @param externalBarCode
	 *            the externalBarCode to set
	 */
	public void setExternalBarCode(String externalBarCode) {
		this.externalBarCode = externalBarCode;
	}

}
