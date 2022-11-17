package it.begear.betalent.masterdata.crud.repository.custom.impl;

import it.begear.betalent.masterdata.commons.SearchField;
import it.begear.betalent.masterdata.crud.repository.custom.PersonaRepositoryCustom;
import it.begear.betalent.masterdata.dto.searchDto.PersonaSearchInformation;
import it.begear.betalent.masterdata.model.Persona;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonaRepositoryCustomImpl implements PersonaRepositoryCustom{
    private static List<SearchField> FIELDS = new ArrayList<>();

    static{
        FIELDS.add(new SearchField(PersonaSearchInformation.Fields.id, Persona.Fields.id, Integer.class, SearchField.Condition.EQUALS));
        FIELDS.add(new SearchField(PersonaSearchInformation.Fields.nome, Persona.Fields.nome, SearchField.Condition.LIKE));
        FIELDS.add(new SearchField(PersonaSearchInformation.Fields.cognome, Persona.Fields.cognome, SearchField.Condition.LIKE));
        FIELDS.add(new SearchField(PersonaSearchInformation.Fields.eta, Persona.Fields.eta, SearchField.Condition.LIKE));
        FIELDS.add(new SearchField(PersonaSearchInformation.Fields.ddn, Persona.Fields.ddn, Date.class, SearchField.Condition.LIKE));
    }

    private CRUDRepositoryCustomImpl<PersonaSearchInformation, Persona> used = new CRUDRepositoryCustomImpl<PersonaSearchInformation, Persona>();



    @Override
    public Long countElements(PersonaSearchInformation searchInformation) {
        return used.countElements(Persona.class, searchInformation, FIELDS);
    }

    @Override
    public List<Persona> readAllElements(PersonaSearchInformation searchInformation) {
        return used.readAllElements(Persona.class, searchInformation, FIELDS);
    }
}
