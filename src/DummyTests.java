import business.ObjectBuilder;
import business.TypeMatcher;
import domain.FieldWormType;
import domain.WormConfig;
import persistence.contracts.PoolConnections;
import persistence.contracts.QueryBuilder;

import java.sql.Connection;
import java.util.Date;

public class DummyTests {
    private static final String HOST = "localhost";
    private static final String PORT = "";
    private static final String DATABASE = "wormDB";
    private static final String USER = "";
    private static final String PASSW = "";


    public static void main(String[] args) {
        //tryIntrospection();
        //tryReflexion();
        //tryQueryBuilder();
        //tryWormConfig();
        trySave();
    }

    private static void trySave() {
        new WormConfig("wormDB", HOST, PORT, USER, PASSW);
        Dog dog1 = new Dog("Carlos2", "Santana2", 2, new Date());
        dog1.setObjectID(2);
        dog1.save();
    }

    private static void tryWormConfig() {
        WormConfig wormConfig1 = new WormConfig("wormDB", HOST, PORT, USER, PASSW ,new PoolConnections() {
            @Override
            public Connection getConnection() {
                return null;
            }

            @Override
            public void configurePool() {

            }

            @Override
            public void closeConnection(Connection connection) {

            }
        });
        WormConfig wormConfig = WormConfig.newInstance();
        WormConfig wormConfig2 = WormConfig.newInstance();
        String hey = "";
    }

    private static void tryIntrospection() {
        ObjectBuilder objectBuilder = new ObjectBuilder();
        FieldWormType type1 = new FieldWormType("name", "Carlos", null, String.class.getGenericSuperclass(), 0 );
        FieldWormType type2 = new FieldWormType("lastName", "Santana", null, String.class.getGenericSuperclass(), 0 );
        FieldWormType type3 = new FieldWormType("age", 2, null, Integer.class.getGenericSuperclass(), 0 );
        FieldWormType type4 = new FieldWormType("birthday", new Date(), null, Date.class.getGenericSuperclass(), 0 );
        FieldWormType type5 = new FieldWormType("objectID", 12, null, Integer.class.getGenericSuperclass(), 0 );

        FieldWormType[] fieldWormTypes = {type1, type2, type3, type4, type5};

        Dog hey = (Dog)objectBuilder.buildObject(Dog.class, fieldWormTypes);
        System.out.println(hey);



    }

    private static void tryReflexion() {
        ObjectBuilder objectBuilder = new ObjectBuilder();
        TypeMatcher typeMatcher = new TypeMatcher();
        Dog dog2 = new Dog("Carlos", "Santa", 2, new Date());
        dog2.setObjectID(5);
        Dog dog1 = (Dog)objectBuilder.buildObject(Dog.class, typeMatcher.getFieldWormTypes(dog2));
        System.out.println(dog1);
    }

    private static void tryQueryBuilder(){
        persistence.QueryBuilder query = new persistence.QueryBuilder();
        TypeMatcher matcher = new TypeMatcher();
        Dog dog2 = new Dog("Carlos", "Santa", 2, new Date());


        String query2 = query.allEntities("perrosTable");
        System.out.println(query2);
    }
}
