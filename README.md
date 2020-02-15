# How is this?
The Maker project is an abstraction to speed up the development time of RestAPI using Spring Framework.

This project makes available to you three pre-coded interfaces, and help you to make your API more fast.

You can build your API in 10 minutes, with GET/POST/PUT/PATCH/DELETE methods and Pageable search.

# Let me show how this work

Note: this project use Spring Specification to make find on your database.

On normal development RestAPI using Spring Framework, you shold be to create a domain/model, right? Well, thats 
think is perfect, you have created your entity, for example (using Lombok @Data):

    @Data
    @Entity
    class Car {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        
        private String Model; 
    }

Now, you want to create your repository, right? In this moment, Maker can help you.

    public interface CarRepository extends RepositoryMaker<Car, Long> {}
    
Awesome, you extends RepositoryMaker because RepositoryMaker implements all thinks JpaRepository implements and more, the JpaSpecificationExecutor.

Now, you need to create a Specification for this entity, thats step is required for ServiceMaker and ResourceMaker work well.

    public class CarSpecification extends SpecificationBase<Car> {
    
        public CarSpecification(Car car) {
            super(car);
        }
            
        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) { 
            List<Predicate> predicates = new ArrayList<>();
            
            if (nonNull(getObject().getModel())) {
                predicates.add(criteriaBuilder.equal(root.get("model"), getObject().getModel()));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        
    }

Alright, the RepositoryMaker calls Specification.toPredicate inside Spring Framework, and call this @Override method and you can make your conditional search here, its very easy.

Now, you want to create a service to provide access to your database object.
    
    @Service
    public class CarService extends ServiceMaker<CarRepository, Car, Long, Car, CarSpecification> {}
    
Car, Long, Car? What?

In your specification you can spec any object, defaults you need to search one Car, but you can pass here any object for spec, for example, you have a LocalDateTime comparison, you need to create a specification with
start LocalDateTime and end LocalDateTime, and more spec's, in this situation you need to create a new object to spec.

And now? What my projects do? Now your project provide a CarService, with a lot of pre-coded methods, includes one findAll/findAllPageable/insert/update/patch/delete.

Oh my God, so i need to create one resource to make my API? Yes, and Maker mades it for you!

    @RestController
    @RequestMapping("/car")
    public class CarResource extends ResourceMaker<CarService, Car> {}
    
Now you have a lot of RestAPI methods and you can call them. Open your terminal and make a call on your server:port/context/car and enjoy!

This example lives on https://github.com/gustavovitor/maker-example :)

Any question/bugs/problems? Open an issue.

