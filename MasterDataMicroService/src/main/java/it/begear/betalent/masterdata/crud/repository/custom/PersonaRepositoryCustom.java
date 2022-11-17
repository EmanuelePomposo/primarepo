package it.begear.betalent.masterdata.crud.repository.custom;

import it.begear.betalent.masterdata.dto.searchDto.PersonaSearchInformation;
import it.begear.betalent.masterdata.model.Persona;

import java.util.List;

public interface PersonaRepositoryCustom {
    Long countElements(PersonaSearchInformation searchInformation);
    List<Persona> readAllElements(PersonaSearchInformation  searchInformation);
}
