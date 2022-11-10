package it.begear.betalent.masterdata.crud.repository;

import it.begear.betalent.masterdata.crud.repository.custom.PersonaRepositoryCustom;
import it.begear.betalent.masterdata.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Integer>, PersonaRepositoryCustom {
}
