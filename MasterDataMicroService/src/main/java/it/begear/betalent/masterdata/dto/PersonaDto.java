package it.begear.betalent.masterdata.dto;

import it.begear.betalent.masterdata.dto.searchDto.commons.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode
public class PersonaDto extends GenericDTO<PersonaDto> {

    private Integer id;
    private String nome;
    private String cognome;
    private Integer eta;


    @Temporal(TemporalType.DATE)
    private Date ddn;
}
