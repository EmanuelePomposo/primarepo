package it.begear.betalent.masterdata.dto.searchDto.commons;

import it.begear.betalent.masterdata.commons.RetrievableBusinessCode;
import it.begear.betalent.masterdata.commons.RetrievableCode;
import it.begear.betalent.masterdata.commons.RetrievableDescription;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class GenericDTO<T extends GenericDTO> {

    public GenericDTO() {
        super();
    }

    public GenericDTO(String code) {
        super();
        this.setCode(code);
    }

    public String getCode() {
        Object value = null;
        Field field = findFirstByAnnotation(this.getClass(), RetrievableCode.class);
        if (field != null) {
            try {
                value = PropertyUtils.getProperty(this, field.getName());
            } catch (Exception ignored) {
            }
        }
        return value != null ? String.valueOf(value) : null;
    }

    public void setCode(String code) {
        Field field = findFirstByAnnotation(this.getClass(), RetrievableCode.class);
        if (field != null) {
            try {
                Object value = convert(code, field.getType());
                PropertyUtils.setProperty(this, field.getName(), value);
            } catch (Exception ignored) {
            }
        }
    }

    public String getDescription() {
        Object value = null;
        Field field = findFirstByAnnotation(this.getClass(), RetrievableDescription.class);
        if (field != null) {
            try {
                value = PropertyUtils.getProperty(this, field.getName());
            } catch (Exception ignored) {
            }
        }
        return value != null ? String.valueOf(value) : null;
    }

    public void setDescription(String description) {
        Field field = findFirstByAnnotation(this.getClass(), RetrievableDescription.class);
        if (field != null) {
            try {
                Object value = convert(description, field.getType());
                PropertyUtils.setProperty(this, field.getName(), value);
            } catch (Exception ignored) {
            }
        }
    }

    public String getBusinessCode() {
        Object value = null;
        Field field = findFirstByAnnotation(this.getClass(), RetrievableBusinessCode.class);
        if (field != null) {
            try {
                value = PropertyUtils.getProperty(this, field.getName());
            } catch (Exception ignored) {
            }
        }
        return value != null ? String.valueOf(value) : null;
    }

    public void setBusinessCode(String businessCode) {
        Field field = findFirstByAnnotation(this.getClass(), RetrievableBusinessCode.class);
        if (field != null) {
            try {
                Object value = convert(businessCode, field.getType());
                PropertyUtils.setProperty(this, field.getName(), value);
            } catch (Exception ignored) {
            }
        }
    }

    public T code(String code) {
        this.setCode(code);
        return (T) this;
    }

    public T businessCode(String businessCode) {
        this.setBusinessCode(businessCode);
        return (T) this;
    }

    private Object convert(String code, Class<?> type) {
        if (String.class.isAssignableFrom(type)) {
            return code;
        }
        if (Double.class.isAssignableFrom(type)) {
            return Double.parseDouble(code);
        }
        if (Integer.class.isAssignableFrom(type)) {
            return Integer.parseInt(code);
        }
        if (Boolean.class.isAssignableFrom(type)) {
            return Boolean.parseBoolean(code);
        }
        throw new IllegalArgumentException("Unassigned");
    }

    public static Field findFirstByAnnotation(Class<?> clazz, Class<?> annotation) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation a : annotations) {
                if (annotation.isAssignableFrom(a.getClass())) {
                    return field;
                }
            }
        }
        return null;
    }
}

