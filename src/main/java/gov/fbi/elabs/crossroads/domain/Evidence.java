package gov.fbi.elabs.crossroads.domain;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Evidence extends ResourceSupport {

	private String evidence;

	private Integer batchId;

	private String evidence1B;

	private String description;

	private String area;

	private String location;

	private String status;

	private Integer evidenceSubmissionId;

	private List<Evidence> hierarchy;

	private Integer parentId;

	private Integer hasChildren;

	private Integer custodyStorageAreaId;

	/**
	 * @return the evidence
	 */
	public String getEvidence() {
		return evidence;
	}

	/**
	 * @param evidence
	 *            the evidence to set
	 */
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	/**
	 * @return the evidence1B
	 */
	public String getEvidence1B() {
		return evidence1B;
	}

	/**
	 * @param evidence1b
	 *            the evidence1B to set
	 */
	public void setEvidence1B(String evidence1b) {
		evidence1B = evidence1b;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the evidenceSubmissionId
	 */
	public Integer getEvidenceSubmissionId() {
		return evidenceSubmissionId;
	}

	/**
	 * @param evidenceSubmissionId
	 *            the evidenceSubmissionId to set
	 */
	public void setEvidenceSubmissionId(Integer evidenceSubmissionId) {
		this.evidenceSubmissionId = evidenceSubmissionId;
	}

	/**
	 * @return the hierarchy
	 */
	public List<Evidence> getHierarchy() {
		return hierarchy;
	}

	/**
	 * @param hierarchy
	 *            the hierarchy to set
	 */
	public void setHierarchy(List<Evidence> hierarchy) {
		this.hierarchy = hierarchy;
	}

	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

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
	 * @return the hasChildren
	 */
	public Integer getHasChildren() {
		return hasChildren;
	}

	/**
	 * @param hasChildren
	 *            the hasChildren to set
	 */
	public void setHasChildren(Integer hasChildren) {
		this.hasChildren = hasChildren;
	}

	/**
	 * @return the custodyStorageAreaId
	 */
	public Integer getCustodyStorageAreaId() {
		return custodyStorageAreaId;
	}

	/**
	 * @param custodyStorageAreaId
	 *            the custodyStorageAreaId to set
	 */
	public void setCustodyStorageAreaId(Integer custodyStorageAreaId) {
		this.custodyStorageAreaId = custodyStorageAreaId;
	}

}
