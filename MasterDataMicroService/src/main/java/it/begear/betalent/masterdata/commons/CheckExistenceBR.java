package it.begear.betalent.masterdata.commons;

import it.begear.betalent.masterdata.commons.exceptions.BusinessException;
import it.begear.betalent.masterdata.commons.exceptions.DataNotFoundException;
import it.begear.betalent.masterdata.commons.exceptions.DuplicatedDataException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class CheckExistenceBR {

	public void checkExistence(JpaRepository repository, Object id, String keyField) throws BusinessException {
		if (id == null || !repository.existsById(id)) {
			throw new DataNotFoundException("Elemento non trovato con " + keyField + " [" + id + "]");
		}
	}

	public void checkNotExistence(JpaRepository repository, Object id, String keyField) throws BusinessException {
		if (id != null && repository.existsById(id)) {
			throw new DuplicatedDataException("Elemento già presente con " + keyField + " [" + id + "]");
		}
	}

	public void checkExistenceByExample(JpaRepository repository, Example example, String keyField) throws BusinessException {
		if (example == null || !repository.exists(example)) {
			throw new DataNotFoundException("Elemento non trovato con il valore di " + keyField + " specificato");
		}
	}

	public void checkNotExistenceByExample(JpaRepository repository, Example example, String keyField) throws BusinessException {
		if (example != null && repository.exists(example)) {
			throw new DuplicatedDataException("Elemento già presente con il valore di " + keyField + " specificato");
		}
	}
}