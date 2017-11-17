package business;

import domain.FieldWormType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Types;

public class TypeMatcher {

    public FieldWormType[] getFieldWormTypes(Object object) {
        Field[] objectFields = object.getClass().getDeclaredFields();
        FieldWormType[] fieldWormTypes = new FieldWormType[objectFields.length];

        for (int i = 0; i < objectFields.length; i++) {
            String fieldName = objectFields[i].getName();
            Object fieldValue = getFieldValue(object, objectFields[i]);
            Annotation fieldAnnotation = getAnnotation(objectFields[i]);
            Type fieldType = objectFields[i].getType();
            int acceptedTypeCode = getAcceptedTypeCode(fieldType);

            fieldWormTypes[i] = new FieldWormType(
                fieldName,
                fieldValue,
                fieldAnnotation,
                fieldType,
                acceptedTypeCode
            );
        }

        return fieldWormTypes;
    }

    private int getAcceptedTypeCode(Type fieldType) {
        switch (fieldType.getTypeName()) {
            case "java.lang.String":
                return Types.LONGNVARCHAR;
            case "boolean":
                return Types.BOOLEAN;
            case  "java.math.BigDecimal":
                return Types.NUMERIC;
            case  "byte":
                return Types.TINYINT;
            case  "short":
                return Types.SMALLINT;
            case  "int":
                return Types.INTEGER;
            case  "long":
                return Types.BIGINT;
            case  "float":
                return Types.FLOAT;
            case  "double":
                return Types.DOUBLE;
            case  "byte[ ]":
                return Types.BINARY;
            case  "java.sql.Date":
                return Types.DATE;
            case  "java.sql.Time":
                return Types.TIME;
            case  "java.sql.Timestamp":
                return Types.TIMESTAMP;
            case  "java.sql.Clob":
                return Types.CLOB;
            case  "java.sql.Blob":
                return Types.BLOB;
            case  "java.sql.Array":
                return Types.ARRAY;
            case  "java.sql.Ref":
                return Types.REF;
            case  "java.sql.Struct":
                return Types.STRUCT;
            default:
                return Types.OTHER;
        }
    }

    private Object getFieldValue(Object object, Field field) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
            return null;
        }
    }

    private Annotation getAnnotation(Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        // Check if there's only going to be one annotation
        if (annotations.length != 0) {
            // TODO: return the the defined annotation in our framework
            return annotations[0];
        } else {
            return null;
        }
    }
}
