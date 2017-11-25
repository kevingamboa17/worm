import domain.WormObject;
import domain.WormColumn;

import java.util.Date;

public class Dog extends WormObject {
    @WormColumn("name")
    private String name2;
    public String lastName;
    private int age;
    private Date birthday;

    @Override
    public String toString() {
        String phrase = new StringBuilder().
                append("Hola, mi nombre es ").
                append(name2).
                append(" ").
                append(lastName).
                append(" y tengo ").
                append(age).
                append(" años y nací ").
                append(birthday).
                toString();
        return phrase;
    }

    public Dog(String name, String lastName, int age, Date birthday) {
        this.name2 = name;
        this.lastName = lastName;
        this.age = age;
        this.birthday = birthday;
    }

    public Dog() {
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
