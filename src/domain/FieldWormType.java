package domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class FieldWormType {
    private String fieldName;
    private Object value;
    private Annotation annotation;
    private Type originalType;
    private String databaseType;

    public FieldWormType(String fieldName, Object value, Annotation annotation, Type originalType, String databaseType) {
        this.fieldName = fieldName;
        this.value = value;
        this.annotation = annotation;
        this.originalType = originalType;
        this.databaseType = databaseType;
    }

    public FieldWormType(String fieldName, Object value, Type originalType, String databaseType) {
        this.fieldName = fieldName;
        this.value = value;
        this.originalType = originalType;
        this.databaseType = databaseType;
    }

    public Object getValue() {
        return value;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public Type getOriginalType() {
        return originalType;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public String getFieldName() {
        return fieldName;
    }
}
