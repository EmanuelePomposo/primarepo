package it.begear.betalent.masterdata.commons;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@FieldNameConstants
public class SearchFieldOR implements ISearchField {
	private List<SearchField> orCriteria = new ArrayList<>();

	public SearchFieldOR(SearchField... orCriteria) {
		this.orCriteria = Arrays.asList(orCriteria);
	}

	public SearchFieldOR(List<SearchField> orCriteria) {
		this.orCriteria = orCriteria;
	}

	public void add(SearchField searchField) {
		this.orCriteria.add(searchField);
	}
}
