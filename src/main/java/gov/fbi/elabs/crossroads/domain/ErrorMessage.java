package gov.fbi.elabs.crossroads.domain;

public class ErrorMessage {

	private String fieldName;

	private String errorMessages;

	public ErrorMessage() {
		super();
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}

}
