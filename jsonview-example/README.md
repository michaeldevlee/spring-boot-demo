# @JsonView Demonstration

## Overview

There are cases where you want to control the visibility of the properties of a resource entity that you are returning in a response. By default, all the properties of an entity are included in a response.

Take this `Author` entity as an example.

```
public class Author {    
    private Integer id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String nationality;
    private String imageUrl;
    private List<Book> books;
    ...
}
```

If we have an API that returns this `Author` entity as a resource, we will get the value below as JSON response.

```
[
  {
    "id": 1,
    "firstName": "John Ronald Reuel",
    "lastName": "Tolkien",
    "birthDate": "1892-01-03",
    "nationality": "British",
    "imageUrl": "image url",
    "books": [
      {
        "isbn": "9780044403371",
        "title": "The Hobbit, or There and Back Again",
        "publicationDate": "1937",
        "description": "some description"
      }
    ]
  }
]
```

What if we only want to get the ID and name fields?

```
[
  {
    "id": 1,
    "firstName": "John Ronald Reuel",
    "lastName": "Tolkien",
  }
]
```

This can easily be done using `@JsonView` annotation. The `@JsonView` annotation can be used to include or exclude a property during the serialization and deserialization process dynamically.

This tutorial will show you how it can be done using a simple REST API application.

## Sample Application

This repository contains the source code for a simple REST API application. The API exposes three (3) endpoints:

* GET /authors
* GET /authors/withBooks
* GET /authors/all

The API doesn't need a database. The data is mocked in the repository class to keep it simple. No JPA, database driver, or validation. Just a plain Java application running as a service using Spring Boot and Spring Web.

The `GET /authors` endpoint returns this JSON payload.

```
[
  {
    "id": 1,
    "firstName": "John Ronald Reuel",
    "lastName": "Tolkien"
  }
]
```

The `GET /authors/all` endpoint returns this JSON payload.

```
[
  {
    "id": 1,
    "firstName": "John Ronald Reuel",
    "lastName": "Tolkien",
    "books": [
      {
        "isbn": "9780044403371",
        "title": "The Hobbit, or There and Back Again"
      },
      {
        "isbn": "9780261103689",
        "title": "The Lord of the Rings"
      }
    ]
  }
]
```

The `GET /authors/all` endpoint returns this JSON payload.

```
[
  {
    "id": 1,
    "firstName": "John Ronald Reuel",
    "lastName": "Tolkien",
    "birthDate": "1892-01-03",
    "nationality": "British",
    "imageUrl": "image url",
    "books": [
      {
        "isbn": "9780044403371",
        "title": "The Hobbit, or There and Back Again",
        "publicationDate": "1937",
        "description": "some description."
      },
      {
        "isbn": "9780261103689",
        "title": "The Lord of the Rings",
        "publicationDate": "1954",
        "description": "some description."
      }
    ]
  }
]
```

The following section will show how this is done.

## Using @JsonView

### Create a View class

```
public class AuthorView {
    public interface IdAndName {}
    public interface IdAndNameWithBooks extends IdAndName {}
    public interface AllDetails extends IdAndNameWithBooks {}
}
```

Give the View class a meaningful name. The example is associated to the `Author` resource so it is named `AuthorView`.

We can use `class` or `interface` when defining the View. Inside the View, we can define more classes or interfaces which can be used as sub-views. This is very useful if we need to control the data being returned. Since these are classes or interfaces, they can be extended which forms a hierarchy of views.

In our example, we have three (3) sub-views:

* ID and name
* ID and name of author and books
* All details of the author and books

### Update the Author and Book Entities

#### Original

Originally, the entities are as follows:

**Author**

```
public class Author {    
    private Integer id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String nationality;
    private String imageUrl;
    private List<Book> books;
    ...
}
```

**Book**

```
public class Book {
    private String isbn;
    private String title;
    private String publicationDate;
    private String description;
}
```

#### Updated

Let's update these and apply the JsonView annotation. Each property in the entity that we want to control will add the `@JsonView()` annotation and specify what View class and what sub-view to use.

**Author**

```
public class Author {    
    @JsonView(AuthorView.IdAndName.class)
    private Integer id;

    @JsonView(AuthorView.IdAndName.class)
    private String firstName;

    @JsonView(AuthorView.IdAndName.class)
    private String lastName;

    @JsonView(AuthorView.AllDetails.class)
    private Date birthDate;

    @JsonView(AuthorView.AllDetails.class)
    private String nationality;

    @JsonView(AuthorView.AllDetails.class)
    private String imageUrl;

    @JsonView(AuthorView.IdAndNameWithBooks.class)
    private List<Book> books;
    ...
}
```

**Book**

```
public class Book {
    @JsonView(AuthorView.IdAndNameWithBooks.class)
    private String isbn;

    @JsonView(AuthorView.IdAndNameWithBooks.class)
    private String title;

    @JsonView(AuthorView.AllDetails.class)
    private String publicationDate;

    @JsonView(AuthorView.AllDetails.class)
    private String description;
}
```

Here, we have the following properties to be displayed per group:

* ID and Name only: `Author's id, firstName, lastName`
* ID and Name only for Authors and Books: `Author's id, firstName, lastName; Book's id and title`
* All Details: `All property fields of Author and Book`

### Update the Controller

To demonstrate the different views, create three (3) separate GET methods and annotate each with the `@JsonView` annotation. Use the corresponding view for each controller method.

```
@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    AuthorRepository repo;

    @JsonView(AuthorView.AllDetails.class)
    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAllDetails() {
        return new ResponseEntity<>(repo.getUsers(), HttpStatus.OK);
    }

    @JsonView(AuthorView.IdAndName.class)
    @GetMapping()
    public ResponseEntity<List<Author>> getIdAndNamesOnly() {
        return new ResponseEntity<>(repo.getUsers(), HttpStatus.OK);
    } 

    @JsonView(AuthorView.IdAndNameWithBooks.class)
    @GetMapping("/withBooks")
    public ResponseEntity<List<Author>> getIdAndNamesWithBooks() {
        return new ResponseEntity<>(repo.getUsers(), HttpStatus.OK);
    }   
}
```

## Test the Sample API

Use curl, Postman or Firefox, navigate to http://localhost:8080/authors.

Using the `/authors` endpoint should give us:

```
[
  {
    "id": 1,
    "firstName": "John Ronald Reuel",
    "lastName": "Tolkien"
  },
  {
    "id": 2,
    "firstName": "Brandon",
    "lastName": "Sanderson"
  }
]
```

Using the `/authors/withBooks` endpoint should give us:

```
[
  {
    "id": 1,
    "firstName": "John Ronald Reuel",
    "lastName": "Tolkien",
    "books": [
      {
        "isbn": "9780044403371",
        "title": "The Hobbit, or There and Back Again"
      },
      {
        "isbn": "9780261103689",
        "title": "The Lord of the Rings"
      }
    ]
  },
  {
    "id": 2,
    "firstName": "Brandon",
    "lastName": "Sanderson",
    "books": [
      {
        "isbn": "9780765383105",
        "title": "Elantris"
      },
      {
        "isbn": "9780765311788",
        "title": "Mistborn: The Final Empire"
      }
    ]
  }
]
```

Using the `/authors/all` endpoint should give us:

```
[
  {
    "id": 1,
    "firstName": "John Ronald Reuel",
    "lastName": "Tolkien",
    "birthDate": "1892-01-03",
    "nationality": "British",
    "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/J._R._R._Tolkien%2C_1940s.jpg/220px-J._R._R._Tolkien%2C_1940s.jpg",
    "books": [
      {
        "isbn": "9780044403371",
        "title": "The Hobbit, or There and Back Again",
        "publicationDate": "1937",
        "description": "The Hobbit is set within Tolkien's fictional universe and follows the quest of home-loving Bilbo Baggins, the titular hobbit, to win a share of the treasure guarded by a dragon named Smaug. Bilbo's journey takes him from his light-hearted, rural surroundings into more sinister territory."
      },
      {
        "isbn": "9780261103689",
        "title": "The Lord of the Rings",
        "publicationDate": "1954",
        "description": "The title refers to the story's main antagonist, the Dark Lord Sauron, who in an earlier age created the One Ring to rule the other Rings of Power given to Men, Dwarves, and Elves, in his campaign to conquer all of Middle-earth. From homely beginnings in the Shire, a hobbit land reminiscent of the English countryside, the story ranges across Middle-earth, following the quest to destroy the One Ring mainly through the eyes of the hobbits Frodo, Sam, Merry and Pippin."
      }
    ]
  },
  {
    "id": 2,
    "firstName": "Brandon",
    "lastName": "Sanderson",
    "birthDate": "1975-12-19",
    "nationality": "American",
    "imageUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/Brandon_Sanderson_-_Lucca_Comics_%26_Games_2016.jpg/220px-Brandon_Sanderson_-_Lucca_Comics_%26_Games_2016.jpg",
    "books": [
      {
        "isbn": "9780765383105",
        "title": "Elantris",
        "publicationDate": "2005",
        "description": "The story follows three main characters: Prince Raoden of Arelon, Princess Sarene of Teod, and the priest Hrathen of Fjordell. At the beginning of the story, Raoden is cursed by an ancient transformation known as the Shaod and secretly exiled to the city of Elantris just days before his betrothed, princess Sarene of Teod, arrives for their wedding. As Raoden tries to avoid gangs, keep his sanity, and unite the people of Elantris, Sarene must cope with the loss of her husband-to-be and try to save Arelon from Hrathen, a priest tasked with converting all of Arelon to the religion of Fjordell or dooming it to destruction."
      },
      {
        "isbn": "9780765311788",
        "title": "Mistborn: The Final Empire",
        "publicationDate": "2006",
        "description": "Mistborn: The Final Empire is set on the dystopian world of Scadrial, where ash constantly falls from the sky, all plants are brown, and supernatural mists cloak the landscape every night. One thousand years before the start of the novel, the prophesied Hero of Ages ascended to godhood at the Well of Ascension in order to repel the Deepness, a terror threatening the world whose true nature has since been lost to time. Though the Deepness was successfully repelled and mankind saved, the world was changed into its current form by the Hero, who took the title Lord Ruler and has ruled over the Final Empire for a thousand years as an immortal tyrant and god. Under his rule, society is stratified into the nobility, believed to be the descendants of the friends and allies who helped him achieve godhood, and the brutally oppressed peasantry descended from those who opposed him, known as skaa."
      }
    ]
  }
]
```

## References

* https://fasterxml.github.io/jackson-annotations/javadoc/2.6/com/fasterxml/jackson/annotation/JsonView.html
* https://www.baeldung.com/jackson-json-view-annotation
* https://medium.com/@iamitpatil1993/jackson-jsonview-and-its-meaningful-use-with-spring-boot-rest-5fb2ad58dcfe
