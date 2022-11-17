package it.begear.betalent.masterdata.dto.searchDto.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class SearchRequestDTO extends PagedRequestDTO {
	private String sortField;
	private ESortDir sortDir = ESortDir.ASC;
	private String fullSearch;
}