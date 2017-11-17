import domain.WormObject;

import java.util.Date;

public class Dog extends WormObject {
    private String name;
    public String lastName;
    private int age;
    private Date birthday;

    @Override
    public String toString() {
        String phrase = new StringBuilder().
                append("Hola, mi nombre es ").
                append(name).
                append(" ").
                append(lastName).
                append(" y tengo ").
                append(age).
                append(" años y nací ").
                append(birthday).
                toString();
        return phrase;
    }
}
