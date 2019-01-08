package gov.fbi.elabs.crossroads.domain;

import org.springframework.hateoas.ResourceSupport;

public class CustodyArea extends ResourceSupport {

	private Integer storageAreaId;

	private Integer locationId;

	private Integer organizationId;

	private String storageAreaCode;

	private String storageAreadescription;

	private Boolean requiresLocation;

	private Integer employeeId;

	private Boolean transferInAllowed;

	private Boolean transferInVerificationRequired;

	private Boolean transferInCanVerify;

	private Boolean transferOutAllowed;

	private Boolean transferOutVerificationRequired;

	private Boolean transferOutCanVerify;

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
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the organizationId
	 */
	public Integer getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId
	 *            the organizationId to set
	 */
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the storageAreaCode
	 */
	public String getStorageAreaCode() {
		return storageAreaCode;
	}

	/**
	 * @param storageAreaCode
	 *            the storageAreaCode to set
	 */
	public void setStorageAreaCode(String storageAreaCode) {
		this.storageAreaCode = storageAreaCode;
	}

	/**
	 * @return the storageAreadescription
	 */
	public String getStorageAreadescription() {
		return storageAreadescription;
	}

	/**
	 * @param storageAreadescription
	 *            the storageAreadescription to set
	 */
	public void setStorageAreadescription(String storageAreadescription) {
		this.storageAreadescription = storageAreadescription;
	}

	/**
	 * @return the requiresLocation
	 */
	public Boolean getRequiresLocation() {
		return requiresLocation;
	}

	/**
	 * @param requiresLocation
	 *            the requiresLocation to set
	 */
	public void setRequiresLocation(Boolean requiresLocation) {
		this.requiresLocation = requiresLocation;
	}

	/**
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the transferInAllowed
	 */
	public Boolean getTransferInAllowed() {
		return transferInAllowed;
	}

	/**
	 * @param transferInAllowed
	 *            the transferInAllowed to set
	 */
	public void setTransferInAllowed(Boolean transferInAllowed) {
		this.transferInAllowed = transferInAllowed;
	}

	/**
	 * @return the transferInVerificationRequired
	 */
	public Boolean getTransferInVerificationRequired() {
		return transferInVerificationRequired;
	}

	/**
	 * @param transferInVerificationRequired
	 *            the transferInVerificationRequired to set
	 */
	public void setTransferInVerificationRequired(Boolean transferInVerificationRequired) {
		this.transferInVerificationRequired = transferInVerificationRequired;
	}

	/**
	 * @return the transferInCanVerify
	 */
	public Boolean getTransferInCanVerify() {
		return transferInCanVerify;
	}

	/**
	 * @param transferInCanVerify
	 *            the transferInCanVerify to set
	 */
	public void setTransferInCanVerify(Boolean transferInCanVerify) {
		this.transferInCanVerify = transferInCanVerify;
	}

	/**
	 * @return the transferOutAllowed
	 */
	public Boolean getTransferOutAllowed() {
		return transferOutAllowed;
	}

	/**
	 * @param transferOutAllowed
	 *            the transferOutAllowed to set
	 */
	public void setTransferOutAllowed(Boolean transferOutAllowed) {
		this.transferOutAllowed = transferOutAllowed;
	}

	/**
	 * @return the transferOutVerificationRequired
	 */
	public Boolean getTransferOutVerificationRequired() {
		return transferOutVerificationRequired;
	}

	/**
	 * @param transferOutVerificationRequired
	 *            the transferOutVerificationRequired to set
	 */
	public void setTransferOutVerificationRequired(Boolean transferOutVerificationRequired) {
		this.transferOutVerificationRequired = transferOutVerificationRequired;
	}

	/**
	 * @return the transferOutCanVerify
	 */
	public Boolean getTransferOutCanVerify() {
		return transferOutCanVerify;
	}

	/**
	 * @param transferOutCanVerify
	 *            the transferOutCanVerify to set
	 */
	public void setTransferOutCanVerify(Boolean transferOutCanVerify) {
		this.transferOutCanVerify = transferOutCanVerify;
	}

}
