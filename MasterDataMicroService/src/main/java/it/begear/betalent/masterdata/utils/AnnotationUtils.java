package it.begear.betalent.masterdata.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotationUtils {
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
