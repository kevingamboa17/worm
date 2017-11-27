package domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * The {@code FieldWormType} class represents a field of any generic object, including its
 * attribute name and its value.
 */
public class FieldWormType {

    /** The field name of a generic object */
    private String fieldName;

    /** The value of the field */
    private Object value;

    /** The annotation of the field */
    private Annotation annotation;

    /** The field type of a generic object */
    private Type originalType;

    /** The data type saved into database*/
    private String databaseType;



    /**
     * Initialize a newly created {@code FieldWormType} object that represents a specific
     * field of any generic object, defining a particular annotation.
     * @param fieldName
     * @param value
     * @param annotation
     * @param originalType
     * @param databaseType
     */
    public FieldWormType(String fieldName, Object value, Annotation annotation, Type originalType, String databaseType) {
        this.fieldName = fieldName;
        this.value = value;
        this.annotation = annotation;
        this.originalType = originalType;
        this.databaseType = databaseType;
    }


    /**
     * Initialize a newly created {@code FieldWormType} object that represents a specific
     * field of any generic object.
     * @param fieldName
     * @param value
     * @param originalType
     * @param databaseType
     */
    public FieldWormType(String fieldName, Object value, Type originalType, String databaseType) {
        this.fieldName = fieldName;
        this.value = value;
        this.originalType = originalType;
        this.databaseType = databaseType;
    }

    /**
     * Gets the current value.
     * @return value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Gets the current annotation.
     * @return annotation.
     */
    public Annotation getAnnotation() {
        return annotation;
    }

    /**
     * Gets the current original type.
     * @return originalType.
     */
    public Type getOriginalType() {
        return originalType;
    }

    /**
     * Gets the current database type.
     * @return databaseType.
     */
    public String getDatabaseType() {
        return databaseType;
    }


    /**
     * Gets the current field name.
     * @return fieldName.
     */
    public String getFieldName() {
        return fieldName;
    }
}
