package it.begear.betalent.masterdata.crud.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public class CustomBooleanConverter {

    private static final String TRUE_STRING = "1";
    private static final String FALSE_STRING = "0";
    public static final CustomBooleanConverter instance = Mappers.getMapper(CustomBooleanConverter.class);

    public Boolean longToBoolean(Long value) {
        return (value == null) ? Boolean.FALSE : value.intValue() == 1;
    }

    public Boolean integerToBoolean(Integer value) {
        return (value == null) ? Boolean.FALSE : value.intValue() == 1;
    }

    public Boolean shortToBoolean(Short value) {
        return (value == null) ? Boolean.FALSE : value.intValue() == 1;
    }

    public Boolean stringToBoolean(String value) {
        return (value == null) ? null : TRUE_STRING.equalsIgnoreCase(value);
    }

    public Long booleanToLong(Boolean value) {
        return (value == null) ? 0L : value ? 1L : 0L;
    }

    public Integer booleanToInteger(Boolean value) {
        return (value == null) ? 0 : value ? 1 : 0;
    }

    public Short booleanToShort(Boolean value) {
        return (value == null) ? (short) 0 : value ? (short) 1 : (short) 0;
    }

    public String booleanToString(Boolean value) {
        return (value == null) ? null : value ? TRUE_STRING : FALSE_STRING;
    }
}
