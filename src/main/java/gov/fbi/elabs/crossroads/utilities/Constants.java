package gov.fbi.elabs.crossroads.utilities;

import com.google.common.collect.ImmutableList;

public class Constants {

	public static final String AUTHENTICATION_HEADER = "X-Auth-Token";

	public static final String EVERYTHING = "Everything";
	public static final String ACTIVE = "Active";
	public static final String INACTIVE = "Inactive";

	public static final String EXPIRES = "expires";
	public static final String NAME = "batchName";

	public static final String ASC = "ASC";
	public static final String DESC = "DESC";

	public static final String TRANSFER_STATUS_COMPLETE = "Complete";

	// Evidence Transfer UI Object fields

	public static final String USERNAME_PREFIX = "ELAB\\";
	public static final ImmutableList<String> ROLES = ImmutableList.of("FBI Examiner", "FBI Supervisor",
			"Administrator", "FBI Evidence Analyst");

	public static final ImmutableList<String> TASKS = ImmutableList.of("CanViewBatch", "CanTransferBatch");
	public static final String CAN_VIEW_BATCH = "CanViewBatch";
	public static final String CAN_TRANSFER_BATCH = "CanTransferBatch";
	public static final String TRANSFER = "TRANSFER";

}
