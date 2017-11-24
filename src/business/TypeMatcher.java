package business;

import domain.FieldWormType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class TypeMatcher {

    public FieldWormType[] getFieldWormTypes(Object object) {
        Field[] objectFields = object.getClass().getDeclaredFields();
        FieldWormType[] fieldWormTypes = new FieldWormType[objectFields.length + 1];

        // The first one will be the id
        fieldWormTypes[0] = bindField(object, getFieldId(object.getClass()));

        for (int i = 1; i < fieldWormTypes.length; i++) {
            fieldWormTypes[i] = bindField(object, objectFields[i-1]);
        }


        return fieldWormTypes;
    }

    public FieldWormType[] convertToArrayFieldWormType(Class type, ResultSet resultSet) throws SQLException {
        Field fieldId = getFieldId(type);
        Field[] fields = type.getDeclaredFields();
        FieldWormType[] fieldWormTypes = new FieldWormType[fields.length + 1];

        if (resultSet.next()) {
            fieldWormTypes[0] = bindField(resultSet, fieldId);
            for(int i = 1; i < fieldWormTypes.length; i++){
                try{
                    fieldWormTypes[i] = bindField(resultSet, fields[i-1]);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        } else{
            return null;
        }

        return fieldWormTypes;
    }

    private FieldWormType bindField(ResultSet resultSet, Field field) throws SQLException {
        return new FieldWormType(
                field.getName(),
                //Gets column value from ResultSet
                resultSet.getObject(field.getName()),
                getAnnotation(field),
                field.getType(),
                getAcceptedTypeCode(field.getType())
        );
    }

    public FieldWormType[][] convertToMatrixFieldWormType(Class type, ResultSet resultSet) throws SQLException {
        Field fieldId = getFieldId(type);
        Field[] fields = type.getDeclaredFields();
        int rows = getRowCount(resultSet);
        int columns = fields.length + 1;
        FieldWormType[][] fieldWormTypes = new FieldWormType[rows][columns];

        for(int i = 0; i < rows; i++){
            if (resultSet.next()) {
                fieldWormTypes[i][0] = bindField(resultSet, fieldId);
                try{
                    for(int j = 1; j < columns; j++){
                        fieldWormTypes[i][j] = bindField(resultSet, fields[j-1]);
                    }
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return fieldWormTypes;
    }

    private int getRowCount(ResultSet resultSet){
        int totalRows;
        if(resultSet != null){
            try {
                resultSet.last();
                totalRows = resultSet.getRow();
                resultSet.beforeFirst();
                return totalRows;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    private FieldWormType bindField(Object object, Field objectField) {
        objectField.setAccessible(true);
        String fieldName = objectField.getName();
        Object fieldValue = getFieldValue(object, objectField);
        Annotation fieldAnnotation = getAnnotation(objectField);
        Type fieldType = objectField.getType();
        int acceptedTypeCode = getAcceptedTypeCode(fieldType);

        return new FieldWormType(
                fieldName,
                fieldValue,
                fieldAnnotation,
                fieldType,
                acceptedTypeCode
        );
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

    private Field getFieldId(Class object) {
        Field idField = null;
        try {
            idField = object.getSuperclass().getDeclaredField("objectID");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return idField;
    }
}
