package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.Batch;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;

@Repository
@SuppressWarnings("unchecked")
public class BatchRepository extends BaseRepository<Batch> {

	private static final Logger logger = LoggerFactory.getLogger(BatchRepository.class);

	public List<Batch> getBatchDetails(Integer employeeId, Integer days, String query, String orderBy, String sortBy,
			int offset, int limit) throws BaseApplicationException {
		StringBuilder builder = new StringBuilder();
		builder.append("Select batchId,employeeId,batchName,evidenceCount,expires from (");
		builder.append("Select BatchId as batchId, EmployeeId as employeeId, Name as batchName,");
		builder.append(" (Select count(*) from BatchEvidence where BatchID = b.BatchID) as evidenceCount,");
		builder.append(" (Select DATEADD(day, " + days + ",b.LastModifiedDate)) as expires,");
		builder.append(" (Select count(*) from (");
		builder.append(" Select distinct e.CustodyStorageAreaID,e.CustodyLocationID from BatchEvidence be");
		builder.append(" Left Join Evidence e");
		builder.append(
				" ON be.FSLabNum = e.FSLabNum and be.EvidenceType = e.EvidenceType and be.EvidenceID = e.EvidenceID");
		builder.append(" where BatchID = b.BatchID) x) as locationValidation");
		builder.append(" from Batch b where EmployeeID = " + employeeId + " and ");
		builder.append(" LastModifiedDate > (Select DATEADD(day," + -days + ",GETDATE()))");

		if (StringUtils.isNotEmpty(query)) {
			builder.append(" and Name like \'" + query + "\'");
		}
		builder.append(" ) x where x.locationValidation = 1");

		builder.append(" ORDER BY " + orderBy + " " + sortBy);
		builder.append(" OFFSET " + offset + " ROWS FETCH NEXT " + limit + " ROWS ONLY");

		SQLQuery sqlQuery = createSQLQuery(builder.toString());

		sqlQuery.addScalar("batchId", new IntegerType());
		sqlQuery.addScalar("employeeId", new IntegerType());
		sqlQuery.addScalar("batchName", new StringType());
		sqlQuery.addScalar("evidenceCount", new IntegerType());
		sqlQuery.addScalar("expires", org.hibernate.type.TimestampType.INSTANCE);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Batch.class));

		List<Batch> batchList = sqlQuery.list();
		int results = batchList != null ? batchList.size() : 0;
		logger.info("Total No of batches returned " + results);
		return batchList;
	}

	public int getBatchDetailsCount(Integer employeeId, Integer days, String query) throws BaseApplicationException {

		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) from ");
		builder.append(" (select b.*, ");
		builder.append(" (Select count(*) from ( ");
		builder.append(" Select distinct e.CustodyStorageAreaID,e.CustodyLocationID from BatchEvidence be ");
		builder.append(" Left Join Evidence e");
		builder.append(
				" ON be.FSLabNum = e.FSLabNum and be.EvidenceType = e.EvidenceType and be.EvidenceID = e.EvidenceID");
		builder.append(" where BatchID = b.BatchID) x) as locationTest");
		builder.append(" from Batch b where EmployeeID = " + employeeId + " and ");
		builder.append(" CreatedDate > (Select DATEADD(day," + -days + ",GETDATE()))");

		if (StringUtils.isNotEmpty(query)) {
			builder.append(" and Name like \'" + query + "\'");
		}

		builder.append(") x");
		builder.append(" where x.locationTest = 1");

		SQLQuery sqlQuery = createSQLQuery(builder.toString());

		int totalCount = (int) sqlQuery.list().get(0);
		logger.info("Total Results " + totalCount);
		return totalCount;
	}

	public Integer getNextBatchId() throws BaseApplicationException {
		Session session = openSession();
		session.beginTransaction();

		Integer batchId = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(
					"INSERT INTO Batch (EmployeeID, CreatedBy, CreatedDate, LastModifiedBy, LastModifiedDate, IsActive)");
			builder.append(" VALUES (0, 'cross_user',  GETDATE(), 'cross_user',  GETDATE(), 0)");
			SQLQuery sqlQuery = createSQLQuery(builder.toString());
			int executeUpdate = sqlQuery.executeUpdate();

			StringBuilder sBuild = new StringBuilder(
					"SELECT max(BatchID) from Batch where EmployeeID = 0 and CreatedBy = 'cross_user'");
			SQLQuery q = createSQLQuery(sBuild.toString());
			batchId = (Integer) q.list().get(0);

			StringBuilder build = new StringBuilder(
					"DELETE FROM Batch where EmployeeID = 0 and CreatedBy = 'cross_user'");
			SQLQuery query = createSQLQuery(build.toString());
			int executeUpdate2 = query.executeUpdate();

			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Error getting next Batch id ", e);
			session.getTransaction().rollback();
		}
		session.close();
		return batchId;
	}

}
