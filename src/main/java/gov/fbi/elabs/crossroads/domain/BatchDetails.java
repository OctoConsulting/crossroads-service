package gov.fbi.elabs.crossroads.domain;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class BatchDetails extends ResourceSupport {

	private int totalCount;

	private List<Batch> batchList;

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the batchList
	 */
	public List<Batch> getBatchList() {
		return batchList;
	}

	/**
	 * @param batchList
	 *            the batchList to set
	 */
	public void setBatchList(List<Batch> batchList) {
		this.batchList = batchList;
	}

}
