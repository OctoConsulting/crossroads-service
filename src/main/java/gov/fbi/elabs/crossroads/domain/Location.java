package gov.fbi.elabs.crossroads.domain;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Location")
public class Location {
	
	@Id
	@Column(name = "LocationID")
	private Integer locationId;
	
	@Column(name = "LocationCode")
	private String locationCode;
	
	@Column(name = "LocationName")
	private String locationName;
	
	@Column(name = "LocationTypeID")
	private Integer locationTypeId;
	
	@Column(name = "AddressID")
	private Integer addressId;
	
	@Column(name = "FSLabYear")
	private Integer fsLabYear;
	
	@Column(name = "FSLabNum")
	private Integer fsLabNum;
	
	@Column(name = "ServerName")
	private String serverName;
	
	@Column(name = "Url")
	private String url;
	
	@Column(name = "WebServiceUrl")
	private String webServiceUrl;
	
	@Column(name = "Options")
	private Integer options;
	
	@Column(name = "CreatedBy")
	private String createdBy;
	
	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	
	@Column(name = "LastModifiedBy")
	private String lastModifiedBy;
	
	@Column(name = "LastModifiedDate")
	private Timestamp lastModifiedDate;
	
	@Column(name = "IsActive")
	private boolean isActive;
	
	@Column(name = "guid")
	private UUID guid;
	
	@Column(name = "AgencyId")
	private Integer agencyId;

	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the locationCode
	 */
	public String getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode the locationCode to set
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the locationTypeId
	 */
	public Integer getLocationTypeId() {
		return locationTypeId;
	}

	/**
	 * @param locationTypeId the locationTypeId to set
	 */
	public void setLocationTypeId(Integer locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	/**
	 * @return the addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the fsLabYear
	 */
	public Integer getFsLabYear() {
		return fsLabYear;
	}

	/**
	 * @param fsLabYear the fsLabYear to set
	 */
	public void setFsLabYear(Integer fsLabYear) {
		this.fsLabYear = fsLabYear;
	}

	/**
	 * @return the fsLabNum
	 */
	public Integer getFsLabNum() {
		return fsLabNum;
	}

	/**
	 * @param fsLabNum the fsLabNum to set
	 */
	public void setFsLabNum(Integer fsLabNum) {
		this.fsLabNum = fsLabNum;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the webServiceUrl
	 */
	public String getWebServiceUrl() {
		return webServiceUrl;
	}

	/**
	 * @param webServiceUrl the webServiceUrl to set
	 */
	public void setWebServiceUrl(String webServiceUrl) {
		this.webServiceUrl = webServiceUrl;
	}

	/**
	 * @return the options
	 */
	public Integer getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(Integer options) {
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
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
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
	 * @return the agencyId
	 */
	public Integer getAgencyId() {
		return agencyId;
	}

	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	
	
}
