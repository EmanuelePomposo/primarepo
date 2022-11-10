package it.begear.betalent.masterdata.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ModelBase {

	public String asStringLine() {
		try {
			return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).writeValueAsString(this);
		} catch (Exception e) {
			return super.toString();
		}
	}

	public String asStringIndent() {
		try {
			return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).writeValueAsString(this);
		} catch (Exception e) {
			return super.toString();
		}
	}


}
