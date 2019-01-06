package gov.fbi.elabs.crossroads.repository;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import gov.fbi.elabs.crossroads.domain.Evidence;
import gov.fbi.elabs.crossroads.exception.BaseApplicationException;

@Repository
@SuppressWarnings("unchecked")
public class EvidenceRepository extends BaseRepository<Evidence> {

	private static final Logger logger = LoggerFactory.getLogger(EvidenceRepository.class);

	public List<Evidence> getEvidenceList(Integer batchId) throws BaseApplicationException {

		StringBuilder builder = new StringBuilder();
		builder.append("select concat(b.FSLabNum, ' ', b.EvidenceType, ' ',b.EvidenceID) as evidence, ");
		builder.append("b.BatchID as batchId, ");
		builder.append("es.AgencyEvidenceID as evidence1B, ");
		builder.append("es.EvidenceSubmissionID as evidenceSubmissionId, ");
		builder.append("es.ParentId as parentId, ");
		builder.append("e.Description as description, ");
		builder.append(
				"(select StorageLocationDescription from StorageLocation where StorageLocationCode = e.CustodyStorageLocationCode) as location, ");
		builder.append(
				"(select EvidenceStatusDescription from EvidenceStatus where EvidenceStatusCode = e.EvidenceStatusCode) as status ");
		builder.append("from BatchEvidence b LEFT JOIN EvidenceSubmission es ");
		builder.append(
				"ON b.FSLabNum = es.FSLabNum and b.EvidenceType = es.EvidenceType and b.EvidenceID = es.EvidenceID ");
		builder.append("LEFT JOIN Evidence e ");
		builder.append(
				"ON b.FSLabNum = e.FSLabNum and b.EvidenceType = e.EvidenceType and b.EvidenceID = e.EvidenceID ");
		builder.append("where es.ParentId is null and BatchID = " + batchId);

		SQLQuery sqlQuery = createSQLQuery(builder.toString());

		sqlQuery.addScalar("evidence", new StringType());
		sqlQuery.addScalar("batchId", new IntegerType());
		sqlQuery.addScalar("evidence1B", new StringType());
		sqlQuery.addScalar("evidenceSubmissionId", new IntegerType());
		sqlQuery.addScalar("description", new StringType());
		sqlQuery.addScalar("location", new StringType());
		sqlQuery.addScalar("status", new StringType());
		sqlQuery.addScalar("parentId", new IntegerType());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(Evidence.class));

		List<Evidence> evidenceList = sqlQuery.list();
		int results = evidenceList != null ? evidenceList.size() : 0;
		logger.info("No of evidences for the batch " + results);
		return evidenceList;

	}

	public List<Evidence> getEvidenceHierarchy(Integer evidenceSubmissionId) throws BaseApplicationException {

		StringBuilder builder = new StringBuilder();
		builder.append("select CONCAT(es.FSLabNum, ' ', es.EvidenceType, ' ', es.EvidenceID) as evidence, ");
		builder.append("es.AgencyEvidenceID as evidence1B, ");
		builder.append("es.EvidenceSubmissionID as evidenceSubmissionId, ");
		builder.append("es.ParentId as parentId, ");
		builder.append("e.Description as description, ");
		builder.append(
				"(select StorageLocationDescription from StorageLocation where StorageLocationCode = e.CustodyStorageLocationCode) as location, ");
		builder.append(
				"(select EvidenceStatusDescription from EvidenceStatus where EvidenceStatusCode = e.EvidenceStatusCode) as status ");
		builder.append("from EvidenceSubmission es ");
		builder.append("LEFT JOIN Evidence e ");
		builder.append(
				"ON es.FSLabNum = e.FSLabNum and es.EvidenceType = e.EvidenceType and es.EvidenceID = e.EvidenceID ");
		builder.append("where ParentId = " + evidenceSubmissionId);

		SQLQuery sqlQuery = createSQLQuery(builder.toString());

		sqlQuery.addScalar("evidence", new StringType());
		sqlQuery.addScalar("evidence1B", new StringType());
		sqlQuery.addScalar("evidenceSubmissionId", new IntegerType());
		sqlQuery.addScalar("description", new StringType());
		sqlQuery.addScalar("location", new StringType());
		sqlQuery.addScalar("status", new StringType());
		sqlQuery.addScalar("parentId", new IntegerType());

		sqlQuery.setResultTransformer(Transformers.aliasToBean(Evidence.class));

		List<Evidence> evidenceList = sqlQuery.list();
		int results = evidenceList != null ? evidenceList.size() : 0;
		logger.info("No of evidences for the hierarchy " + results + " for submissionId " + evidenceSubmissionId);
		return evidenceList;

	}

	public List<Evidence> getEvidenceDetailForTransfer(Integer batchId) throws BaseApplicationException {
		StringBuilder builder = new StringBuilder();
		builder.append("select * from Evidence");
		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		List<Evidence> evidenceList = sqlQuery.list();
		return evidenceList;
	}

	public Integer getEvidenceTransferOutLocation(Integer batchId) throws BaseApplicationException {
		StringBuilder builder = new StringBuilder();
		builder.append(" select distinct CustodyLocationID  from BatchEvidence be ");
		builder.append(" left join Evidence e ");
		builder.append(
				" ON be.FSLabNum = e.FSLabNum and be.EvidenceType = e.EvidenceType and be.EvidenceID = e.EvidenceID ");
		builder.append(" left join EvidenceSubmission es ");
		builder.append(
				" ON e.FSLabNum = e.FSLabNum and e.EvidenceType = es.EvidenceType and e.EvidenceID = es.EvidenceID ");
		builder.append(" where be.BatchID = " + batchId + " and CustodyLocationID is not null ");
		SQLQuery sqlQuery = createSQLQuery(builder.toString());
		return (Integer) sqlQuery.list().get(0);
	}

}
