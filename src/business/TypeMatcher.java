package business;

import domain.FieldWormType;
import domain.WormColumn;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code TypeMatcher} class is responsible of match the returned data from
 * database and convert it to <code>FieldWormType</code> objects.
 */
public class TypeMatcher {

    /**
     * Gets <code>FieldWormType</code> array, it represents all fields of an object.
     * @param object Any <code>Object</code>.
     * @return       A <code>FieldWormType</code> array.
     */
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


    /**
     * Converts a <code>ResultSet</code> object to <code>FieldWormType</code> array,
     * it represents the fields of an entity returned from database.
     * @param type       The class type of the entity.
     * @param resultSet  <code>ResultSet</code> returned by a database query.
     * @return           A <code>FieldWormType</code> array.
     * @throws SQLException
     */
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


    /**
     * Binds a specific <code>Field</code> of the object with a <code>FieldWormType</code>
     * @param resultSet <code>ResultSet</code> returned by database query.
     * @param field     <code>Field</code> of an object.
     * @return          <code>FieldWormType</code>
     * @throws SQLException
     */
    private FieldWormType bindField(ResultSet resultSet, Field field) throws SQLException {
        return new FieldWormType(
                field.getName(),
                //Gets column value from ResultSet
                resultSet.getObject(
                        getColumnName(field)
                ),
                getAnnotation(field),
                field.getType(),
                getAcceptedTypeCode(field.getType())
        );
    }


    /**
     * Converts a <code>ResultSet</code> object to <code>FieldWormType</code> matrix,
     * it represents a set of fields entity returned from database.
     * @param type       The class type of the entity.
     * @param resultSet  <code>ResultSet</code> returned by a database query.
     * @return           A <code>FieldWormType</code> matrix.
     * @throws SQLException
     */
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


    /**
     * Gets the total of rows returned by database query.
     * @param resultSet <code>ResultSet</code>
     * @return          The total of rows.
     */
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


    /**
     * Binds the <code>Field</code> of an object with a <code>FieldWormType</code>.
     * @param object        The object where the fields belong.
     * @param objectField   The <code>Field</code> of an object.
     * @return              A <code>FieldWormType</code>
     */
    private FieldWormType bindField(Object object, Field objectField) {
        objectField.setAccessible(true);
        String fieldName = objectField.getName();
        Object fieldValue = getFieldValue(object, objectField);
        Annotation fieldAnnotation = getAnnotation(objectField);
        Type fieldType = objectField.getType();
        String acceptedTypeCode = getAcceptedTypeCode(fieldType);

        return new FieldWormType(
                fieldName,
                fieldValue,
                fieldAnnotation,
                fieldType,
                acceptedTypeCode
        );
    }


    /**
     * Set the valid data type that is going to be saved into database.
     * @param fieldType Represents the data type of a specific field.
     * @return          A valid data type.
     */
    private String getAcceptedTypeCode(Type fieldType) {
        switch (fieldType.getTypeName()) {
            case "java.lang.String":
                return "text";
            case "boolean":
                return "tinyint(1)";
            case  "int":
                return "int";
            case  "java.sql.Date":
                return "date";
            case  "java.sql.Time":
                return "time";
            case "java.util.Date":
                return "date";
            default:
                return "text";
        }
    }


    /**
     * Gets the value of a specific <code>Field</code> object.
     * @param object <code>Object</code>
     * @param field  <code>Field</code>
     * @return       <code>Object</code> that represents a value.
     */
    private Object getFieldValue(Object object, Field field) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
            return null;
        }
    }


    /**
     * Gets the annotation of a specific field.
     * @param field <code>Field</code>
     * @return      The annotation of the field.
     */
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


    /**
     * Gets the identifier of a specific field
     * @param object <code>Class</code> of the object.
     * @return       <code>Field</code> that represents the id field.
     */
    private Field getFieldId(Class object) {
        Field idField = null;
        try {
            idField = object.getSuperclass().getDeclaredField("objectID");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return idField;
    }


    /**
     * Get the name of the field that is going to represent the column name into
     * database.
     * @param field <code>Field</code>
     * @return      The name of the column.
     */
    private String getColumnName(Field field) {
        Annotation annotation = field.getAnnotation(WormColumn.class);
        boolean hasWormTableAnnotation = annotation != null;
        if (hasWormTableAnnotation) {
            return ((WormColumn) annotation).value();
        }
        return field.getName();
    }
}
