package business;

import domain.FieldWormType;
import domain.WormObject;
import persistence.DBManager;

import java.lang.reflect.Array;

/**
 *The {@code BusinessManager} class has the responsibility of delegate every incoming request from
 * <code>WormObject</code>. <code>BusinessManager</code> has access to database through its
 * <code>DBManager</code> object.
 */
public class BusinessManager <GenericObject extends WormObject> implements business.contracts.BusinessManager {

    /** The object that manages database requests */
    private DBManager mDBManager;

    /** It builds data of the database to generic objects */
    private ObjectBuilder objectBuilder;

    /** The object that match types of data between database and Java objects */
    private TypeMatcher typeMatcher;

    /**
     * Initializes a newly created {@code BusinessManager} object that works like a mediator
     * between the database manager and the <code>WormObject</code> requests. It has the responsibility
     * of delegate the <code>WormObject</code> requests to <code>DBManager</code> and return the response.
     */
    public BusinessManager() {
        this.mDBManager = new DBManager();
        this.objectBuilder = new ObjectBuilder();
        this.typeMatcher = new TypeMatcher();
    }


    /**
     * Saves a generic class that extends of <code>WormObject</code>.
     * @param wormObject It contents a generic class that is going to be saved.
     */
    @Override
    public void save(WormObject wormObject) {
        FieldWormType[] fieldWormTypes = this.typeMatcher.getFieldWormTypes(wormObject);
        this.mDBManager.save(wormObject.getClass().getSimpleName(), fieldWormTypes);
    }


    /**
     * Deletes a generic class that extends of <code>WormObject</code>, using
     * its ID like reference.
     * @param wormObject It contents a generic class that is going to be deleted.
     * @param id         The identifier of generic class.
     */
    @Override
    public void delete(WormObject wormObject, int id) {
        this.mDBManager.delete(wormObject.getClass().getSimpleName(), id);
    }


    /**
     * Finds and returns a specific generic object identified by its ID.
     * @param type The class type of generic object to be returned.
     * @param id   The identifier of generic class.
     * @return     A <code>WormObject</code> that contents the generic object.
     */
    @Override
    public WormObject findById(Class type, int id) {
        FieldWormType[] fieldWormTypes = this.mDBManager.getObject(type, type.getName(), id);
        return this.objectBuilder.buildObject(type, fieldWormTypes);
    }


    /**
     * Returns all generic objects of a specific type of class stored into database.
     * @param type The class type of generic objects to be returned.
     * @return      An array of <code>WormObject</code> that contents all generic objects
     *              found into database.
     */
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

    /**
     * Returns all generic objects of a specific type of class stored into database.
     * @param type The class type of generic objects to be returned.
     * @return      An array of generic objects found into database.
     */
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
