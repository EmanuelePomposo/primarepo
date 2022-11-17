package it.begear.betalent.masterdata.dto.searchDto.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class PagedDTO {

	private boolean first;
	private boolean last;
	private int totPages;
	private long totCount;
	private int pageSize = 100;
	private int pageNum = 1;
}