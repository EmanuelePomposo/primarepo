package it.begear.betalent.masterdata.dto.searchDto;

import it.begear.betalent.masterdata.dto.searchDto.commons.SearchRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class PersonaSearchInformation extends SearchRequestDTO {
    private Integer id;
    private String nome;
    private String cognome;
    private Integer eta;


    @Temporal(TemporalType.DATE)
    private Date ddn;
}
