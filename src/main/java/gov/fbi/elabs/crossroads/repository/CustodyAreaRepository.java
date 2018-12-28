package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.CustodyArea;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;
import gov.fbi.elabs.crossroads.utilities.Constants;

@Repository
@SuppressWarnings("unchecked")
public class CustodyAreaRepository extends BaseRepository<CustodyArea> {

	private static final Logger logger = LoggerFactory.getLogger(CustodyAreaRepository.class);

	public List<CustodyArea> getCustodyAreaInfo(Integer employeeId, Integer locationId, Integer organizationId,
			String status) throws BaseApplicationException {

		StringBuilder builder = new StringBuilder();
		builder.append(
				"select s.StorageAreaID as storageAreaId,s.LocationID as locationId,s.OrganizationID as organizationId,s.StorageAreaCode as storageAreaCode,s.StorageAreaDescription as storageAreadescription,s.RequiresLocation as requiresLocation, ");
		builder.append(
				" sa.EmployeeID as employeeId,sa.TransferInAllowed as transferInAllowed,sa.TransferInCanVerify as transferInVerificationRequired,sa.TransferInVerificationRequired as transferInCanVerify,");
		builder.append(
				" sa.TransferOutAllowed as transferOutAllowed,sa.TransferOutCanVerify as transferOutCanVerify,sa.TransferOutVerificationRequired as transferOutVerificationRequired");
		builder.append(" from StorageAreaAuthorization sa, StorageArea s");
		builder.append(" where s.StorageAreaID = sa.StorageAreaID and");
		builder.append(" s.LocationID =" + locationId + " and s.OrganizationID = " + organizationId + " and");
		builder.append(" sa.EmployeeID = " + employeeId + " and sa.TransferInAllowed = 1");

		if (StringUtils.isNotEmpty(status) && Constants.ACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and s.IsActive = 1");
		} else if (StringUtils.isNotEmpty(status) && Constants.INACTIVE.equalsIgnoreCase(status)) {
			builder.append(" and s.IsActive = 0");
		}

		logger.info(builder.toString());
		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		sqlQuery.addScalar("storageAreaId", new IntegerType());
		sqlQuery.addScalar("locationId", new IntegerType());
		sqlQuery.addScalar("organizationId", new IntegerType());
		sqlQuery.addScalar("storageAreaCode", new StringType());
		sqlQuery.addScalar("storageAreadescription", new StringType());
		sqlQuery.addScalar("requiresLocation", new BooleanType());
		sqlQuery.addScalar("employeeId", new IntegerType());
		sqlQuery.addScalar("transferInAllowed", new BooleanType());
		sqlQuery.addScalar("transferInVerificationRequired", new BooleanType());
		sqlQuery.addScalar("transferInCanVerify", new BooleanType());
		sqlQuery.addScalar("transferOutAllowed", new BooleanType());
		sqlQuery.addScalar("transferOutCanVerify", new BooleanType());
		sqlQuery.addScalar("transferOutVerificationRequired", new BooleanType());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(CustodyArea.class));

		List<CustodyArea> areaList = sqlQuery.list();
		int res = areaList != null ? areaList.size() : 0;
		logger.info("No Of areas returned " + res);
		return areaList;
	}

}
