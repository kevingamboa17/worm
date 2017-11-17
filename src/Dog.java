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

    public Dog(String name, String lastName, int age, Date birthday) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.birthday = birthday;
    }

    public Dog() {
    }

    public void setName(String name) {
        this.name = name;
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
