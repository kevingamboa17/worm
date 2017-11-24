package business;

import domain.FieldWormType;
import domain.WormObject;
import persistence.DBManager;

import java.lang.reflect.Array;

public class BusinessManager <GenericObject extends WormObject> implements business.contracts.BusinessManager {
    private DBManager mDBManager;
    private ObjectBuilder objectBuilder;
    private TypeMatcher typeMatcher;

    public BusinessManager() {
        this.mDBManager = new DBManager();
        this.objectBuilder = new ObjectBuilder();
        this.typeMatcher = new TypeMatcher();
    }

    @Override
    public void save(WormObject wormObject) {
        FieldWormType[] fieldWormTypes = this.typeMatcher.getFieldWormTypes(wormObject);
        this.mDBManager.save(wormObject.getClass().getName(), fieldWormTypes);
    }

    @Override
    public void delete(WormObject wormObject, int id) {
        this.mDBManager.delete(wormObject.getClass().getName(), id);
    }

    @Override
    public WormObject findById(Class type, int id) {
        FieldWormType[] fieldWormTypes = this.mDBManager.getObject(type, type.getName(), id);
        return this.objectBuilder.buildObject(type, fieldWormTypes);
    }

    // TODO: Refactor this method works as getAll2 below
    @Override
    public WormObject[] getAll(Class type) {
        WormObject[] wormObjects;
        FieldWormType[][] fieldWormTypes = this.mDBManager.getAll(type, type.getName());
        wormObjects = new WormObject[fieldWormTypes.length];

        for(int i = 0; i < fieldWormTypes.length; i++){
            wormObjects[i] = this.objectBuilder.buildObject(type, fieldWormTypes[i]);
        }

        return wormObjects;
    }

    public <T>T[] getAll2(Class<T> type) {
        T[] wormObjects;
        FieldWormType[][] fieldWormTypes = this.mDBManager.getAll(type, type.getName());
        wormObjects = (T[])Array.newInstance(type, fieldWormTypes.length);


        for(int i = 0; i < fieldWormTypes.length; i++){
            wormObjects[i] = (T) this.objectBuilder.buildObject(type, fieldWormTypes[i]);
        }

        return wormObjects;
    }
}
