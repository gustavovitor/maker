<p align="center">
  <img src="https://i.imgur.com/GP4BOPK.png">

  [![node](https://img.shields.io/badge/Maker-1.4.0-lightgray.svg)](https://github.com/gustavovitor/maker/tree/1.4.0)
  feito por um brasileiro para desenvolvedores do mundo inteiro.
</p>

Make your API better, create your own pattern and go to the stars!

- [x] Main features.
- [ ] Unit tests.
- [ ] Complete documentation guide.

# How is this?
Boilerplate code? No more.

The Maker project is an abstraction to speed up the development time of RestAPI's using Spring Framework.

This project provides you with a series of pre-coded interfaces.

You can build your API in 10 minutes, with `GET/POST/PUT/PATCH/DELETE` methods and Pageable search.

What kind of technologies are used in this project?

- Spring Framework
- JPA on Maker SQL
- QueryDsl on Maker Mongo
- JpaSpecificationExecutor or QueryDsl with a easy'ly usage with SpecificationBase<T\>

You can see what this project doo on "Maker.postman_collection.json" inside the project. Download the sample (https://github.com/gustavovitor/maker-example/tree/maker-mongo), run
and call API methods from Postman. Enjoy :)

#### Maven Dependency
    <dependency>
        <groupId>com.github.gustavovitor</groupId>
        <artifactId>maker-sql</artifactId>
        <version>1.4.0</version>
    </dependency>

..or, you can use maker-mongo for MongoDB usage.

# Let me show how this work

**Note:** this project use Spring Specification to make find on your database and Spring Security basic dependencies, so, if you want to use this without Spring Security, you need to disable `SecurityAutoConfiguration.class` on your `@SpringBootApplication`.

On normal RestAPI development using Spring Framework, you should be to create a domain/model, right? ..for example (using Lombok `@Data`):

    @Data
    @Entity
    class Car {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        
        private String model; 
    }

Now, you want to create your repository, right? In this moment, Maker can help you.

    public interface CarRepository extends RepositoryMaker<Car, Long> {}
    
Awesome, you extends `RepositoryMaker.class` because `RepositoryMaker.class` implements all thinks JpaRepository implements and more, the `JpaSpecificationExecutor.class`.

Now, you need to create a Specification Object for this entity, thats step is required for `ServiceMaker.class` and `ResourceMaker.class` works well.

    public class CarSpecification extends SpecificationBase<Car> {
    
        public CarSpecification(Car car) throws ReflectionException {
            super(car);
        }
    
        @Override
        public List<Predicate> predicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, Car object) {
            List<Predicate> predicates = new ArrayList<>();
    
            if (nonNull(object.getModel())) {
                predicates.add(criteriaBuilder.like(root.get("model"), "%" + object.getModel() + "%"));
            }
    
            return predicates;
        }
    }

Alright, Spring Framework call `Specification.toPredicate` and call `predicate` and you can make your conditional search here, its very easy.

Now, you want to create a service to provide access to your database object.
    
    @Service
    public class CarService extends ServiceMaker<CarRepository, Car, Long, Car, CarSpecification> {}
    
Car, Long, Car? What?

In your specification object you can specify anything, as long as that thing extends the entity.

The `ServiceMaker.class` and `ResourceMaker.class` needs to know what is the **SPO** (Specification Object).

The generic parameters are these, in order: 

<ul>
  <li>1. <strong>R</strong>, the Repository.</li>  
  <li>2. <strong>T</strong>, the Entity.</li>
  <li>3. <strong>ID</strong>, the type of entity ID.</li>
  <li>4. <strong>SPO</strong>, the Specification Object.</li>  
  <li>5. <strong>SP</strong>, the Specification.</li>
</ul>

And now? What my projects do? Now your project provide a CarService, with a lot of pre-coded methods, includes `findById`, `findAll`, 
`findAllPageable`, `insert` `update`, `patch`, `delete`.

So i need to create one resource to make my API? Yes, and Maker mades it for you!

    @RestController
    @RequestMapping("/car")
    public class CarResource extends ResourceMaker<CarService, Car, Long, Car> {}
    
If your API uses Spring @PreAuthorize resource, you need to extends the `SecurityResourceMaker.class` and override and make public the role methods.

Now you have a lot of RestAPI methods and you can call them. Open your terminal and make a call on your server:port/context/car and enjoy!

One example lives on https://github.com/gustavovitor/maker-example :)

For a Maker Mongo example thats lives on https://github.com/gustavovitor/maker-example/tree/maker-mongo :)

Any question/bugs/problems/feature? Open an issue and contribute.

