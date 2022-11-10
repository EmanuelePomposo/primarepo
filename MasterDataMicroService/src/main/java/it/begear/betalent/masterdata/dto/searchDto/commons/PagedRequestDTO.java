package it.begear.betalent.masterdata.dto.searchDto.commons;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class PagedRequestDTO extends GenericDTO<PagedRequestDTO> {
	@NotNull
	private Integer pageSize = 100;
	@NotNull
	private Integer pageNum = 1;
	private Boolean paging;
}
