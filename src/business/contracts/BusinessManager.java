package business.contracts;

import domain.WormObject;


public interface BusinessManager<GenericObject extends WormObject> {
    void save(GenericObject genericObject);
    void delete(GenericObject genericObject, int id);
    GenericObject findById(Class<GenericObject> type, int id);
    GenericObject[] getAll(Class<GenericObject> type);
}
