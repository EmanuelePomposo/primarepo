package it.begear.betalent.masterdata.commons;

import it.begear.betalent.masterdata.dto.searchDto.commons.PagedDTO;
import it.begear.betalent.masterdata.dto.searchDto.commons.PagedRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class DecoratePagedDTOBR {

	public void decorate(PagedDTO pagedDTO, Long totalCounts, PagedRequestDTO pagedRequestDTO) {
		pagedDTO.setTotCount(totalCounts);
		pagedDTO.setPageNum(pagedRequestDTO.getPageNum());
		pagedDTO.setPageSize(pagedRequestDTO.getPageSize());
		pagedDTO.setTotPages((int) Math.round(Math.ceil(totalCounts.floatValue() / pagedDTO.getPageSize())));
		pagedDTO.setFirst(pagedDTO.getPageNum() == 1);
		pagedDTO.setLast(pagedDTO.getPageNum() == pagedDTO.getTotPages());
	}

	public void decorateWithSimulatedTotal(PagedDTO pagedDTO, Integer effectiveSize, PagedRequestDTO pagedRequestDTO) {
		decorate(pagedDTO, estimateTotal(pagedDTO, effectiveSize), pagedRequestDTO);
	}

	private Long estimateTotal(PagedDTO pagedDTO, Integer effectiveSize) {
		Integer extimatedTotal = (effectiveSize < pagedDTO.getPageSize()) ? effectiveSize : (pagedDTO.getPageNum() + 1) * pagedDTO.getPageSize() + 1;
		return extimatedTotal.longValue();
	}
}
