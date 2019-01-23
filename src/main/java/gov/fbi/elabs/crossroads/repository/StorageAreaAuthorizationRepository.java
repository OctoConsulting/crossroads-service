package gov.fbi.elabs.crossroads.repository;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.EvidenceTransferReason;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;

@Repository
@SuppressWarnings("unchecked")
public class StorageAreaAuthorizationRepository extends BaseRepository<EvidenceTransferReason> {

	private static final Logger logger = LoggerFactory.getLogger(StorageAreaAuthorizationRepository.class);

	public Integer checkTransferInAndTransferOutAccessForEmployee(Integer employeeID, Integer TransferInStorageArea,
			Integer TransferOutStorageArea) throws BaseApplicationException {
		logger.info("Get Transfer Reasons ");
		StringBuilder builder = new StringBuilder();
		builder.append(
				" select case when count(StorageAreaID) = 2 then 1 else 0 end from (select StorageAreaID,case when ");
		builder.append(" (EmployeeID = " + employeeID + " and StorageAreaID = " + TransferInStorageArea
				+ " and TransferInAllowed = 1 ) ");
		builder.append(" or ");
		builder.append(" (EmployeeID = " + employeeID + " and StorageAreaID = " + TransferOutStorageArea
				+ " and TransferOutAllowed =  1) ");
		builder.append(" then 1 ");
		builder.append(" end canTransfer ");
		builder.append(" from StorageAreaAuthorization where EmployeeID = " + employeeID + " ) temp ");
		builder.append(" where canTransfer = 1 ");
		System.out.println(builder.toString());

		SQLQuery sqlQuery = createSQLQuery(builder.toString());

		// sqlQuery.addEntity(Integer.class);

		return (Integer) sqlQuery.list().get(0);
	}

}
