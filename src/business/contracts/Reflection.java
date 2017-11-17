package business.contracts;


import domain.FieldWormType;
import domain.WormObject;

public interface Reflection<GenericObject extends WormObject> {
    FieldWormType[] getObjectValues(GenericObject genericObject);
}
