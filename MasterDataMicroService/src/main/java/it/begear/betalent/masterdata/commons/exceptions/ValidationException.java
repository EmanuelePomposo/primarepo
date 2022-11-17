package it.begear.betalent.masterdata.commons.exceptions;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.DefaultedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ValidationException extends BusinessException {
	@SuppressWarnings("unchecked")
	private static Map<ErrorType, Integer> EXPECTED_PARAMETERS = new DefaultedMap(0);

	static {
		EXPECTED_PARAMETERS.put(ErrorType.INVALID, 1); // value
		EXPECTED_PARAMETERS.put(ErrorType.UNEXPECTED_VALUE, 2); // value-expected
		EXPECTED_PARAMETERS.put(ErrorType.UNEXPECTED_NEGATIVE_VALUE, 1); // value
		EXPECTED_PARAMETERS.put(ErrorType.OUT_OF_RANGE, 3); // value-min-max
	}

	private String field;
	private ErrorType errorType;
	private List<String> other = new ArrayList<>();

	public ValidationException(String field, ErrorType errorType, List<String> other) {
		super("ValidationException on field '" + field + "': " + errorType.toString());
		this.field = field;
		this.errorType = errorType;
		this.other = other;
		validate();
	}

	private void validate() {
		if (this.other.size() < EXPECTED_PARAMETERS.get(this.errorType)) {
			throw new IllegalArgumentException("Invalid argument number for " + this.errorType + ": " + this);
		}
	}

	public ValidationException(String field, ErrorType errorType, String... parameters) {
		super("ValidationException on field '" + field + "': " + errorType.toString());
		this.field = field;
		this.errorType = errorType;
		if (parameters != null && parameters.length > 0) {
			CollectionUtils.addAll(this.other, parameters);
		}
		validate();
	}

	public enum ErrorType {
		REQUIRED, INVALID, UNEXPECTED_VALUE, UNEXPECTED_NEGATIVE_VALUE, OUT_OF_RANGE;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public List<String> getOther() {
		return other;
	}

	public void setOther(List<String> other) {
		this.other = other == null ? new ArrayList<>() : null;
	}

	@Override
	public String toString() {
		return "ValidationException [field=" + field + ", errorType=" + errorType + ", other=" + other + "]";
	}

	@Override
	public String getMessage() {
		return this.toString();
	}
}
