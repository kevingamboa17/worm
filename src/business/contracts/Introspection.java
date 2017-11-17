package business.contracts;

import domain.WormObject;

public interface Introspection<GenericObject extends WormObject> {
    GenericObject buildObject(GenericObject genericObject);
}
