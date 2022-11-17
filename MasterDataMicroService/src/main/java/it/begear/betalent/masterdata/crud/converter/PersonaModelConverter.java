package it.begear.betalent.masterdata.crud.converter;

import it.begear.betalent.masterdata.dto.PersonaDto;
import it.begear.betalent.masterdata.model.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CustomBooleanConverter.class})
public interface PersonaModelConverter {

    PersonaModelConverter istance= Mappers.getMapper(PersonaModelConverter.class);

    PersonaDto modelToDto(Persona model);

    Persona dtoToModel(PersonaDto dto);

    void updateToModel(PersonaDto dto, @MappingTarget Persona model);
}
