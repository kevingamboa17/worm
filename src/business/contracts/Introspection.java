package business.contracts;

import domain.WormObject;

import java.util.HashMap;

public interface Introspection<GenericObject extends WormObject> {
    GenericObject buildObject(GenericObject genericObject, HashMap<String, String> attributes);
}
