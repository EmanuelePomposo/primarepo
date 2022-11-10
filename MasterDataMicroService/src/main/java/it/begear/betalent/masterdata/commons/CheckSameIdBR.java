package it.begear.betalent.masterdata.commons;

import it.begear.betalent.masterdata.commons.exceptions.BusinessException;
import it.begear.betalent.masterdata.commons.exceptions.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class CheckSameIdBR {

	public void checkSameId(Object id1, Object id2) throws BusinessException {
		if (id1 != null && id2 != null && !id2.equals(id1)) {
			throw new ValidationException("ID", ValidationException.ErrorType.UNEXPECTED_VALUE, id1.toString(), id2.toString());
		}
	}
}
