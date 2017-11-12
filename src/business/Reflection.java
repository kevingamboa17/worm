package business;


import domain.WormObject;

import java.util.HashMap;

public interface Reflection<GenericObject extends WormObject> {
    HashMap<String, String> getObjectValues(GenericObject genericObject);
}
