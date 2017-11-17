import business.ObjectBuilder;
import domain.FieldWormType;

import java.util.Date;

public class DummyTests {



    public static void main(String[] args) {
        tryIntrospection();
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
}
