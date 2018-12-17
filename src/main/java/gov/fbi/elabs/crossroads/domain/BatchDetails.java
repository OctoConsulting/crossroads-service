package gov.fbi.elabs.crossroads.domain;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class BatchDetails extends ResourceSupport {

	private int totalCounts;

	private List<Batch> batchList;

	/**
	 * @return the totalCounts
	 */
	public int getTotalCounts() {
		return totalCounts;
	}

	/**
	 * @param totalCounts
	 *            the totalCounts to set
	 */
	public void setTotalCounts(int totalCounts) {
		this.totalCounts = totalCounts;
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
