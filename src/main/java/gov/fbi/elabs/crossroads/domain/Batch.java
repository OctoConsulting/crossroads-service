package gov.fbi.elabs.crossroads.domain;

import java.sql.Timestamp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.fbi.elabs.crossroads.utilities.CustomTimestampDeserializer;
import gov.fbi.elabs.crossroads.utilities.CustomTimestampSerializer;

public class Batch {

	private Integer batchId;

	private Integer employeeId;

	private String batchName;

	private Integer evidenceCount;

	@JsonSerialize(using = CustomTimestampSerializer.class)
	@JsonDeserialize(using = CustomTimestampDeserializer.class)
	private Timestamp expires;

	/**
	 * @return the batchId
	 */
	public Integer getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId
	 *            the batchId to set
	 */
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
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
	 * @return the batchName
	 */
	public String getBatchName() {
		return batchName;
	}

	/**
	 * @param batchName
	 *            the batchName to set
	 */
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	/**
	 * @return the evidenceCount
	 */
	public Integer getEvidenceCount() {
		return evidenceCount;
	}

	/**
	 * @param evidenceCount
	 *            the evidenceCount to set
	 */
	public void setEvidenceCount(Integer evidenceCount) {
		this.evidenceCount = evidenceCount;
	}

	/**
	 * @return the expires
	 */
	public Timestamp getExpires() {
		return expires;
	}

	/**
	 * @param expires
	 *            the expires to set
	 */
	public void setExpires(Timestamp expires) {
		this.expires = expires;
	}

}
