package it.begear.betalent.masterdata.commons;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@FieldNameConstants
public class SearchField implements ISearchField {
	private Object fieldValue;
	private String dtoField;
	private String modelField;
	private Class<? extends Comparable<?>> modelClass = String.class;
	private Condition condition = Condition.LIKE;

	public SearchField(String dtoField) {
		this.dtoField = dtoField;
		this.modelField = dtoField;
	}

	public SearchField(String dtoField, Class<? extends Comparable<?>> modelClass) {
		this.dtoField = dtoField;
		this.modelField = dtoField;
		this.modelClass = modelClass;
	}

	public SearchField(String dtoField, String modelField) {
		this.dtoField = dtoField;
		this.modelField = modelField;
	}

	public SearchField(String dtoField, String modelField, Condition condition) {
		this.dtoField = dtoField;
		this.modelField = modelField;
		this.condition = condition;
	}

	public SearchField(String dtoField, String modelField, Class<? extends Comparable<?>> modelClass) {
		this.dtoField = dtoField;
		this.modelField = modelField;
		this.modelClass = modelClass;
	}

	public SearchField(String dtoField, String modelField, Class<? extends Comparable<?>> modelClass, Condition condition) {
		this.dtoField = dtoField;
		this.modelField = modelField;
		this.modelClass = modelClass;
		this.condition = condition;
	}

	public SearchField(String modelField, Class<? extends Comparable<?>> modelClass, Condition condition, Object fieldValue) {
		this.modelField = modelField;
		this.modelClass = modelClass;
		this.condition = condition;
		this.fieldValue = fieldValue;
	}

	public SearchField(String modelField, Condition condition, Object fieldValue) {
		this.modelField = modelField;
		this.condition = condition;
		this.fieldValue = fieldValue;
	}

	public enum Condition {
		IN,
		EQUALS,
		NOT_EQUALS,
		NOT_IN,
		LIKE,
		GREATER_THAN,
		GREATER_THAN_OR_EQUAL_TO,
		LESS_THAN,
		LESS_THAN_OR_EQUAL_TO,
		ISNULL,
		ISNOTNULL,
		NUMBER_LIKE
	}
}
