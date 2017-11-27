package business;

import domain.FieldWormType;
import domain.WormObject;

import java.lang.reflect.Field;

/**
 * The ObjectBuilder class do the introspection of a class,
 * with the fields that contains the data of a model, generate a new generic object
 * with that data
 * @param <GenericObject> receives a generic object which extends from {@code WormObject}
 */
public class ObjectBuilder<GenericObject extends WormObject>  {
    /** The name expected of the attribute that is the id of model */
    private final String ID_FIELD_NAME = "objectID";

    /**
     * Method that build a generic object with the data of a model
     * @param genericObjectClass the class from which its attributes will be obtained
     * @param values the data of the model that will set the data of the generic object returned
     * @return a generic object with a data of a model specified
     */
    public GenericObject buildObject(Class<GenericObject> genericObjectClass, FieldWormType[] values) {
        if(values != null) {
            GenericObject object = initializeObject(genericObjectClass);
            FieldWormType idValue = getFieldIDValue(values);
            Field[] classFields = genericObjectClass.getDeclaredFields();

            setObjectFieldValues(object, classFields, values);
            setObjectFieldIDValue(object, idValue);
            return object;
        }
        return null;
    }

    /**
     * Method that set the data of the model to the generic object
     * @param object the object that have the set of the model
     * @param classFields
     * @param values data that will set to the generic model
     */
    private void setObjectFieldValues(GenericObject object, Field[] classFields, FieldWormType[] values) {
        for (Field field: classFields) {
            field.setAccessible(true);
            for (FieldWormType fieldValues: values) {
                if (fieldValues.getFieldName().equals(field.getName())) {
                    try {
                        field.set(object, fieldValues.getValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Method set a number id to the generic object
     * @param object the generic model that will set its number id
     * @param idValue the number it that will set to the generic object
     */
    private void setObjectFieldIDValue(GenericObject object, FieldWormType idValue) {
        if (idValue == null){
            return;
        }

        Field idField = null;
        try {
            idField = object.getClass().getSuperclass().getDeclaredField("objectID");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        boolean fieldIsFieldID = idValue.getFieldName().equals(idField.getName());
        if (idField != null && fieldIsFieldID) {
            try {
                idField.setAccessible(true);
                idField.set(object, idValue.getValue());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that return the value of the attribute id
     * @param values array of the attributes
     * @return the value for the attribute id
     */
    private FieldWormType getFieldIDValue(FieldWormType[] values) {
        for (FieldWormType value: values) {
            if (value.getFieldName().equals(ID_FIELD_NAME)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Method that return a instance of generic object
     * @param objectClass the class that will be instance
     * @return a instance of the generic object
     */
    private GenericObject initializeObject (Class<GenericObject> objectClass) {
        try {
            return  (GenericObject) objectClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
