# How is this?
The Maker project is an abstraction to speed up the development time of RestAPI using Spring Framework.

This project makes available to you three pre-coded interfaces, and help you to make your API more fast.

You can build your API in 10 minutes, with GET/POST/PUT/PATCH/DELETE methods and Pageable search.

**Now supports Mongo! :)**

What kind of technologies are used in this project?

- Spring Framework
- JPA on Maker SQL
- QueryQsl on Maker Mongo
- JpaSpecificationExecutor or QueryQsl with a easy'ly usage with SpecificationBase<T\>

You can see what this project doo on "Maker.postman_collection.json" inside the project. Download the sample (https://github.com/gustavovitor/maker-example/tree/maker-mongo), run
and call API methods from Postman. Enjoy :)

#### Maven Dependency
    <dependency>
        <groupId>com.github.gustavovitor</groupId>
        <artifactId>maker-sql</artifactId>
        <version>0.0.6</version>
    </dependency>

..or, you can use maker-mongo for MongoDB usage.

# Let me show how this work

Note: this project use Spring Specification to make find on your database and Spring Security basic dependencies, so, if you want to use this without Spring Security, you need to disable SecurityAutoConfiguration.class on your SpringBootApplication.

On normal RestAPI development using Spring Framework, you shold be to create a domain/model, right? Well, thats 
think is perfect, you have created your entity, for example (using Lombok @Data):

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
    
Awesome, you extends RepositoryMaker because RepositoryMaker implements all thinks JpaRepository implements and more, the JpaSpecificationExecutor.

Now, you need to create a Specification for this entity, thats step is required for ServiceMaker and ResourceMaker work well.

    public class CarSpecification extends SpecificationBase<Car> {
    
        public CarSpecification(Car car) throws ReflectionException {
            super(car);
        }
    
        @Override
        public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();
    
            if (nonNull(getObject().getModel())) {
                predicates.add(criteriaBuilder.like(root.get("model"), "%" + getObject().getModel() + "%"));
            }
    
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }
    }

Alright, the RepositoryMaker calls Specification.toPredicate inside Spring Framework, and call this overrided method and you can make your conditional search here, its very easy.

Now, you want to create a service to provide access to your database object.
    
    @Service
    public class CarService extends ServiceMaker<CarRepository, Car, Long, Car, CarSpecification> {}
    
Car, Long, Car? What?

In your specification you can spec any object, by default, you need to search one Car, but you can pass here any object for spec, for example, you have a LocalDateTime comparison, you need to create a specification with
start LocalDateTime and end LocalDateTime, and more spec's, in this situation you need to create a new object to spec.

And now? What my projects do? Now your project provide a CarService, with a lot of pre-coded methods, includes one findAll/findAllPageable/insert/update/patch/delete.

So i need to create one resource to make my API? Yes, and Maker mades it for you!

    @RestController
    @RequestMapping("/car")
    public class CarResource extends ResourceMaker<CarService, Car, Long, Car> {}
    
Now you have a lot of RestAPI methods and you can call them. Open your terminal and make a call on your server:port/context/car and enjoy!

One example lives on https://github.com/gustavovitor/maker-example :)

For a Maker Mongo example thats lives on https://github.com/gustavovitor/maker-example/tree/maker-mongo :)

Any question/bugs/problems/feature? Open an issue and contribute.

