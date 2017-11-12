package business;

import domain.WormObject;


public interface BusinessManager<GenericObject extends WormObject> {
    void save(GenericObject genericObject);
    void delete(GenericObject genericObject, int id);
    GenericObject findById(GenericObject genericObject, int id);
    GenericObject[] getAll(GenericObject genericObject);
}
