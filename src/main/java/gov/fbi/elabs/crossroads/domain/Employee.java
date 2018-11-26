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
@Table(name = "Employee")
@JsonInclude(Include.NON_NULL)
public class Employee extends ResourceSupport{
	
	@Id
	@Column(name = "EmployeeID")
	private Integer employeeID;
	
	@Column(name = "UserName")
	private String userName;
	
	@Column(name = "DisplayName")
	private String displayName;
	
	@Column(name = "OldEmployeeCode")
	private String oldEmployeeCode;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "MiddleName")
	private String middleName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "Suffix")
	private String suffix;
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Initials")
	private String initials;
	
	@Column(name = "JobTitle")
	private String jobTitle;
	
	@Column(name = "ReportTitle")
	private String reportTitle;
	
	@Column(name = "PositionCode")
	private String positionCode;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "SupervisorID")
	private Integer supervisorId;
	
	@Column(name = "OnLeave")
	private Boolean onLeave;
	
	@Column(name = "SortID")
	private Integer sortId;
	
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
	
	@Column(name = "CourtCoordinator")
	private Boolean courtCoordinator;
	
	@Column(name = "ShiftCode")
	private String shiftCode;
	
	@Column(name = "PhoneNumber")
	private String phoneNumber;
	
	@Column(name = "ReportName")
	private String reportName;

	/**
	 * @return the employeeID
	 */
	public Integer getEmployeeID() {
		return employeeID;
	}

	/**
	 * @param employeeID the employeeID to set
	 */
	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the oldEmployeeCode
	 */
	public String getOldEmployeeCode() {
		return oldEmployeeCode;
	}

	/**
	 * @param oldEmployeeCode the oldEmployeeCode to set
	 */
	public void setOldEmployeeCode(String oldEmployeeCode) {
		this.oldEmployeeCode = oldEmployeeCode;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the suffix
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @param suffix the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * @param initials the initials to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @return the reportTitle
	 */
	public String getReportTitle() {
		return reportTitle;
	}

	/**
	 * @param reportTitle the reportTitle to set
	 */
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	/**
	 * @return the positionCode
	 */
	public String getPositionCode() {
		return positionCode;
	}

	/**
	 * @param positionCode the positionCode to set
	 */
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the supervisorId
	 */
	public Integer getSupervisorId() {
		return supervisorId;
	}

	/**
	 * @param supervisorId the supervisorId to set
	 */
	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	/**
	 * @return the onLeave
	 */
	public Boolean getOnLeave() {
		return onLeave;
	}

	/**
	 * @param onLeave the onLeave to set
	 */
	public void setOnLeave(Boolean onLeave) {
		this.onLeave = onLeave;
	}

	/**
	 * @return the sortId
	 */
	public Integer getSortId() {
		return sortId;
	}

	/**
	 * @param sortId the sortId to set
	 */
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
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
	 * @return the courtCoordinator
	 */
	public Boolean getCourtCoordinator() {
		return courtCoordinator;
	}

	/**
	 * @param courtCoordinator the courtCoordinator to set
	 */
	public void setCourtCoordinator(Boolean courtCoordinator) {
		this.courtCoordinator = courtCoordinator;
	}

	/**
	 * @return the shiftCode
	 */
	public String getShiftCode() {
		return shiftCode;
	}

	/**
	 * @param shiftCode the shiftCode to set
	 */
	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	
	
}
