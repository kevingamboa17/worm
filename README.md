# Worm - Easy Java ORM

Insanely easy way to work with Java and MySQL

## 1. Download

Download the library - [WormLibrary.jar](#) 
```
Import the WormLibrary.jar to your project
```

## 2. How does it help.

It is intended to simplify the interaction with MySQL database in Java.

* It eliminates writing SQL queries to interact with MySQL db.
* It takes care of creating your database.
* It manages object relationships too.
* It provides you with clear and simple APIs for db operations

## 3. What it provides

There are two parts to it:

* database creation
* simple APIs to manipulate your business objects


# Getting started

## 1. Start your MySQL server

## 2. Configuration

It requires minimal configuration.

All you need to do is specify the information of your MySQL server to Worm creating an instance of the object WormConfig in the main of your application:

```
    public static void main(String[] args) {
        new WormConfig(DATABASENAME, HOST, PORT, USER, PASSW);
        
        // Continue with your code
    }
```

| Parameter    | Description                                               |
|--------------|-----------------------------------------------------------|
| DATABASENAME | Name of your database (If doesn't exist, will be created) |
| HOST         | Name of your host (example: localhost)                    |
| PORT         | Number of the port of your server                         |
| USER         | Username of your server                                   |
| PASSW        | Password of your server                                   |

That's it, you only need to instance it to configure it, nothing more :)

## 3. Entities
Extend `WormObject` for all the classes that you need persisted. That's it. Worm takes care of table creation for you.

**Note: Please retain the default constructor.**

```
public class Dog extends WormObject {
    private String name;
    private String alias;

    public Dog(){
    }

    public Dog(String name, String alias){
        this.name = name;
        this.alias = alias;
    }   
}
```

By default Worm will use the `name of your class as the name of your table` in the database and `the name of your attribute as the name of your column` in the database.

If you want to customize the name of your the columns in the database, you can use the `WormColumn` annotation to specify it.

```
    @WormColumn("dog_name")
    private String name;
```

## 4. Basic Usage

Performing CRUD operations are very simple. Functions like `save()`, `delete()`, `findById(..)` and `getAll(..)` are provided to make the work easy.

**Note: Record indexes start at index 1**

Save Entity:

```
Dog dog = new Dog("Maximiliano", "Maxi")
dog.save();
```

Load Entity:

```
Dog dog = Dog.findById(Dog.class, 1);
```

Update Entity:

```
Dog dog = Dog.findById(Dog.class, 1);
dog.name = "updated name here"; // modify the values
dog.alias = "updated alias here";
dog.save(); // updates the previous entry with new values.
```

Delete Entity:

```
Dog dog = Dog.findById(Dog.class, 1);
dog.delete();
```

Bulk Operations:

```
List<Dog> dogs = Dog.listAll(Dog.class);
```
# Documentation

Something

# Development

The people who put their effort and sweat into this project

| Name         | Github         |
|--------------|----------------|
| Kevin Gamboa | [@KevinGamboa17](https://github.com/kevingamboa17)|
| Adrián Leyva | [@AdrianLeyva](https://github.com/AdrianLeyva)|
| Marco Chavez | [@MarcoChavez1940](https://github.com/MarcoChavez1940)|