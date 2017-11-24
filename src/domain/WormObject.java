package domain;

import business.BusinessManager;

public class WormObject {
    private int objectID = -1;
    private BusinessManager mBusinessManager;


    public WormObject() {
        this.mBusinessManager = new BusinessManager();
    }

    public void save(){
        this.mBusinessManager.save(this);
    }

    public void delete(){
        this.mBusinessManager.delete(this, this.objectID);
    }

    public static <T> T findById(Class<T> type, int id){
        BusinessManager mBusinessManager = new BusinessManager();
        return (T)(mBusinessManager.findById(type, id));
    }

    public static <T>T[] getAll(Class<T> type){
        BusinessManager mBusinessManager = new BusinessManager();
        // TODO: this should call to mBusinessManager.getAll(type)
        return (T[]) mBusinessManager.getAll2(type);
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }
}
