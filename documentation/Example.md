# Example of use

![Worm-logo](./worm-logo.png)

I have Dog objects in my project and i want to save the objects in my MySQL database.

So i have my Dog Class

```
public class Dog {
    
    private String name;
    public String lastName;

    public Dog(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public Dog() {
    }
}
```

But if i want to save the Dog objects, only need to extend from WormObject class

**Note: Remember the empty constructor**

```
public class Dog extends WormObject{
    
    private String name;
    public String lastName;

    public Dog(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public Dog() {
    }
}
```

Now, in your main, set your DB configuration creating an instance of a WormConfig and pass your MySQL server information

```
public static void main(String[] args) {
    new WormConfig(DATABASENAME, HOST, PORT, USER, PASSW);

    // Continue with your code
}
```

**Note: If your db doesn't exist, it will be created**

That's it, now you can use the WormObject methods to persist your objects

```
public void saveMyDogs(Dog[] dogs) {
    for (Dog dog: dogs) {
        dog.save();
    }
}
```

```
public Dog[] getMyDogs() {
    return WormObject.getAll(Dog.class);
}
```