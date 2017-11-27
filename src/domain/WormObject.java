package domain;

import business.BusinessManager;

/**
 * The WormObject class have the operations of the CRUD,
 * the model that want to have this operations in the database need extends of its.
 */
public class WormObject {
    /** The attribute that represent the number id of the model */
    private int objectID = -1;

    /** The object that works like a mediator
     * between the database manager and the WormObject requests */
    private BusinessManager mBusinessManager;

    /**
     * initializes a newly created WormObject that have the processes of the CRUD
     * to the model that extends of its.
     */
    public WormObject() {
        this.mBusinessManager = new BusinessManager();
    }

    /**
     * Method that will save(insert or update) this object into the database
     */
    public void save(){
        this.mBusinessManager.save(this);
    }

    /**
     * Method that will delete this object  stored of the database
     */
    public void delete(){
        this.mBusinessManager.delete(this, this.objectID);
    }

    /**
     * Method that find and return a specific generic object identified by its id.
     * @param type the class type of generic object to be returned
     * @param id the identifier of generic class
     * @param <T> generic class
     * @return a generic that contents the generic object
     */
    public static <T> T findById(Class<T> type, int id){
        BusinessManager mBusinessManager = new BusinessManager();
        return (T)(mBusinessManager.findById(type, id));
    }

    /**
     * Method that return all generic objects of a specific type of class stored into database.
     * @param type the class type of generic object to be returned
     * @param <T> generic class
     * @return an array of generics that contains all generic objects found into database.
     */
    public static <T>T[] getAll(Class<T> type){
        BusinessManager mBusinessManager = new BusinessManager();
        // TODO: this should call to mBusinessManager.getAll(type)
        return (T[]) mBusinessManager.getAll2(type);
    }

    /**
     * Method that set the objectID of the object
     * @param objectID the number id that want to set
     */
    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }
}
