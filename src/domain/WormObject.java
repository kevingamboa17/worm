package domain;

import business.BusinessManager;

public class WormObject {
    private int objectID;
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

    public <T>T[] getAll(){
        return (T[]) this.mBusinessManager.getAll(this.getClass());
    }
}
