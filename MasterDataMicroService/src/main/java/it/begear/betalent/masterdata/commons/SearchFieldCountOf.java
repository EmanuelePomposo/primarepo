package it.begear.betalent.masterdata.commons;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@NoArgsConstructor
@FieldNameConstants
public class SearchFieldCountOf implements ISearchField {
	private Class<? extends ModelBase> modelClass = ModelBase.class;
	private Number fieldValue;
	private String dtoField;
	private String subQueryJoinPath;
	private Condition condition = Condition.EQUALS;
	List<? extends ISearchField> fields;

	public SearchFieldCountOf(Class<? extends ModelBase> modelClass, String subQueryJoinPath, Condition condition, Number fieldValue) {
		this.subQueryJoinPath = subQueryJoinPath;
		this.modelClass = modelClass;
		this.condition = condition;
		this.fieldValue = fieldValue;
	}

	public SearchFieldCountOf(Class<? extends ModelBase> modelClass, String subQueryJoinPath, Condition condition, Number fieldValue, List<? extends ISearchField> fields) {
		this.subQueryJoinPath = subQueryJoinPath;
		this.modelClass = modelClass;
		this.condition = condition;
		this.fieldValue = fieldValue;
		this.fields = fields;
	}

	public SearchFieldCountOf(Class<? extends ModelBase> modelClass, String subQueryJoinPath, Condition condition, String dtoField) {
		this.subQueryJoinPath = subQueryJoinPath;
		this.modelClass = modelClass;
		this.condition = condition;
		this.dtoField = dtoField;
	}

	public SearchFieldCountOf(Class<? extends ModelBase> modelClass, String subQueryJoinPath, Condition condition, String dtoField, List<? extends ISearchField> fields) {
		this.subQueryJoinPath = subQueryJoinPath;
		this.modelClass = modelClass;
		this.condition = condition;
		this.dtoField = dtoField;
		this.fields = fields;
	}

	public enum Condition {
		EQUALS,
		NOT_EQUALS,
		GREATER_THAN,
		GREATER_THAN_OR_EQUAL_TO,
		LESS_THAN,
		LESS_THAN_OR_EQUAL_TO
	}
}
