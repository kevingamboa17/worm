package business;

import domain.FieldWormType;
import domain.WormObject;

import java.lang.reflect.Field;

public class ObjectBuilder<GenericObject extends WormObject>  {
    private final String ID_FIELD_NAME = "objectID";

    public GenericObject buildObject(Class<WormObject> genericObjectClass, FieldWormType[] values) {
        GenericObject object = initializeObject(genericObjectClass);
        FieldWormType idValue = getFieldIDValue(values);
        Field[] classFields = genericObjectClass.getDeclaredFields();

        setObjectFieldValues(object, classFields, values);
        setObjectFieldIDValue(object, idValue);

        return object;
    }

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

    private void setObjectFieldIDValue(GenericObject object, FieldWormType idValue) {
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

    private FieldWormType getFieldIDValue(FieldWormType[] values) {
        for (FieldWormType value: values) {
            if (value.getFieldName().equals(ID_FIELD_NAME)) {
                return value;
            }
        }
        return null;
    }

    private GenericObject initializeObject (Class<WormObject> objectClass) {
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
