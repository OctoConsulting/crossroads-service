package gov.fbi.elabs.crossroads.repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unchecked")
public class EvidenceTransferRepository extends BaseRepository {

	private static Logger logger = LoggerFactory.getLogger(EvidenceTransferRepository.class);
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	Date date = new Date();
	String todaysDate = dateFormat.format(date);

	public String setQueryForEvidenceTransferTable(Integer batchID, String evidenceTransferTypeCode, Integer employeeID,
			String loggedinUser, String comments, Integer transferReason, Integer storageAreaID,
			String storageLocationID, Integer locationID, Integer organizationID, Integer witness1ID,
			Integer witness2ID) {
		System.out.println("DATE : " + todaysDate);
		StringBuilder sql = new StringBuilder("Insert into EvidenceTransfer "
				+ " (TransferDate, BatchID ,BatchCreationDate ,EvidenceTransferTypeCode ,EvidenceTransferStatusCode ,"
				+ " FromEmployeeID ,ToEmployeeID ,FromLocationID ,ToLocationID ,FromOrganizationID ,ToOrganizationID ,"
				+ " LockboxTransferID ,StorageAreaID ,StorageLocationCode ,"
				+ " VerifiedByID  ,FSLabNum  ,SubmissionNum  ,EvidenceType  ,"
				+ " EvidenceID  ,Comments ,BatchComments  ,EvidenceTransferReasonID  ,"
				+ " CreatedBy  ,CreatedDate ,LastModifiedBy  ,LastModifiedDate ,"
				+ "IsActive  ,Witness1Id  ,Witness2Id  ,AgencyLocationId  ,OfficerId    ) ");
		sql.append(" ( select " + "convert(DATETIME," + "\'" + todaysDate + "\'" + ",20)" + ", b.BatchID,"
				+ "convert(DATETIME," + "\'" + todaysDate + "\'" + ",20)" + ", " + "\'" + "RS" + "\'" + ",'C',"
				+ "null," + employeeID + "," + "e.CustodyLocationID," + "\'" + locationID + "\'"
				+ ",e.CustodyOrganizationID," + "\'" + organizationID + "\'" + "," + "null," + "null" + "," + "null"
				+ ",null," + "e.FSLabNum, e.CurrentSubmissionNum,e.EvidenceType,e.EvidenceID," + "\'" + comments + "\'"
				+ ",null," + transferReason + "," + "\'" + loggedinUser + "\'" + ", GETDATE()," + "\'" + loggedinUser
				+ "\'" + ", GETDATE(),1," + witness1ID + "," + witness2ID + ",null,null" + " from Batch b"
				+ " left join BatchEvidence be " + " ON b.BatchID = be.BatchID " + " left join Evidence e "
				+ " ON be.FSLabNum = e.FSLabNum and be.EvidenceType = e.EvidenceType and be.EvidenceID = e.EvidenceID  "
				+ " where b.BatchID = " + batchID);
		sql.append(" union ");
		sql.append(" select " + "convert(DATETIME," + "\'" + todaysDate + "\'" + ",20)" + ", b.BatchID,"
				+ "convert(DATETIME," + "\'" + todaysDate + "\'" + ",20)" + ", " + "\'" + evidenceTransferTypeCode
				+ "\'" + ",'C'," + employeeID + "," + "null," + locationID + "," + locationID + "," + organizationID
				+ "," + organizationID + "," + "null," + storageAreaID + "," + "\'" + storageLocationID + "\'"
				+ ",null," + "e.FSLabNum, e.CurrentSubmissionNum,e.EvidenceType,e.EvidenceID," + "\'" + comments + "\'"
				+ ",null," + transferReason + "," + "\'" + loggedinUser + "\'" + ", GETDATE()," + "\'" + loggedinUser
				+ "\'" + ", GETDATE(),1," + witness1ID + "," + witness2ID + ",null,null" + " from Batch b"
				+ " left join BatchEvidence be " + " ON b.BatchID = be.BatchID " + " left join Evidence e "
				+ " ON be.FSLabNum = e.FSLabNum and be.EvidenceType = e.EvidenceType and be.EvidenceID = e.EvidenceID  "
				+ " where b.BatchID = " + batchID);
		sql.append(")");
		System.out.println("EvidenceTransfer Table Query :  " + sql.toString());
		return sql.toString();
	}

	public String setQueryForEvidenceTable(Integer batchID, Integer employeeID, Integer storageAreaID,
			String storageLocationID, Integer locationID, Integer organizationID) {

		StringBuilder sql = new StringBuilder("Update Evidence" + " set " + " Evidence.EvidenceTransferID = "
				+ " (select max(EvidenceTransferID) from EvidenceTransfer et where et.BatchID = " + batchID + " and "
				+ " et.FSLabNum = FSLabNum and et.EvidenceID = EvidenceID and et.EvidenceType = EvidenceType and et.FromEmployeeID = "
				+ employeeID + " and et.ToEmployeeID is null ), " + " Evidence.CustodyEmployeeID = " + employeeID + ","
				+ " Evidence.CustodyLocationID = " + locationID + "," + " Evidence.CustodyOrganizationID = "
				+ organizationID + "," + " Evidence.CustodyStorageAreaID = (CASE "
				+ " WHEN Evidence.EvidenceStatusCode IN ('S', 'V') THEN " + storageAreaID + " ELSE NULL " + " END), "
				+ " CustodyStorageLocationCode = (CASE " + " WHEN Evidence.EvidenceStatusCode IN ('S', 'V') THEN "
				+ "\'" + storageLocationID + "\'" + " ELSE NULL " + " END), " + " LastModifiedBy = " + employeeID + ","
				+ " LastModifiedDate = GETDATE() " + " from BatchEvidence be  " + " join EvidenceTransfer et on  "
				+ " et.FSLabNum=be.FSLabNum " + " and et.EvidenceID=be.EvidenceID "
				+ " and et.EvidenceType=be.EvidenceType and et.BatchID = " + batchID + " where be.BatchID = " + batchID
				+ " and Evidence.FSLabNum=be.FSLabNum " + " and Evidence.EvidenceID=be.EvidenceID "
				+ " and Evidence.EvidenceType=be.EvidenceType ");
		System.out.println("Evidence Table Update Query :  " + sql.toString());
		return sql.toString();
	}

	public void transferEvidence(String evidenceTransferQuery, String evidenceQuery) {
		Session session = openSession();
		session.beginTransaction();
		try {
			SQLQuery sqlQueryForEvidenceTransfer = session.createSQLQuery(evidenceTransferQuery);
			sqlQueryForEvidenceTransfer.executeUpdate();
			SQLQuery sqlQueryForEvidence = session.createSQLQuery(evidenceQuery);
			sqlQueryForEvidence.executeUpdate();
		} catch (Exception e) {
			session.getTransaction().rollback();
			logger.error("Transfer Unsuccessful !! An error occured while transfering the evidence.Exception : "
					+ e.getMessage());
		}

		session.getTransaction().commit();
		session.close();
	}

}
