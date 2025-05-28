package com.movies.service;

public class RentalService {
    /*
    ### **Key Capabilities for Movie Rental**
1. A **user** can rent one or more movies.
2. A **movie** needs to track its availability (e.g., total stock).
3. A **rental record** should be maintained to store the details of each rental (e.g., who rented, which movie, rental date, return date, etc.).
4. Users should be able to **return movies** or track their rentals.

### **Entities Needed**
To implement the system, we need to create or update the following entities:
#### **1. User (Already Exists)**
- The `User` in your project will rent the movies. This likely already exists based on your project analysis.

#### **2. Movie (Already Exists)**
- The `Movie` entity needs modifications:
    - Add a field to track the **number of copies available for rental**.

#### **3. Rental**
- A new `Rental` entity is needed to act as a bridge between `User` and `Movie`, recording the details of the rental (e.g., rental date, due date, status, etc.).

### **Requirements for the `Rental` Entity**
#### Attributes
- **id**: Primary key for the rental record.
- **user**: A reference to the `User` who rented the movie.
- **movie**: A reference to the `Movie` being rented.
- **rentalDate**: The date the movie was rented.
- **returnDate**: The date the movie was returned (nullable while rented).
- **status**: A rental status (e.g., `RENTED`, `RETURNED`).

#### Relationships
- **User to Rental**: One user can have multiple rentals (one-to-many relationship).
- **Movie to Rental**: One movie can have multiple rental records (one-to-many relationship).

### **Steps to Implement Movie Rental**
#### **1. Create the `Rental` Entity**
The `Rental` entity will store details of each rental.
``` java
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // The user renting the movie.

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;  // The rented movie.

    @Column(nullable = false)
    private LocalDate rentalDate;  // Date when the movie was rented.

    private LocalDate returnDate;  // Date when the movie was returned.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status;  // RENTED or RETURNED

    // Constructors, Getters and Setters.
}
```
#### **2. Update the `Movie` Entity**
Modify the `Movie` class to include the stock for rentals.
``` java
@Entity
public class Movie {

    // Other existing fields...

    @Column(nullable = false)
    private int totalCopies;  // Total number of copies available for rental.

    @Column(nullable = false)
    private int availableCopies;  // Number of copies currently available.

    // Getters and Setters...
}
```
#### **3. Create `RentalStatus` Enum**
Define the status of a rental:
``` java
public enum RentalStatus {
    RENTED,
    RETURNED
}
```
#### **4. Create the `RentalRepository`**
Add a repository for interacting with the database:
``` java
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUser(User user);  // Find all rentals by a specific user.

    List<Rental> findByMovie(Movie movie);  // Find all rentals of a specific movie.

    List<Rental> findByStatus(RentalStatus status);  // Find all rentals with a specific status.
}
```
#### **5. Create `RentalService`**
Implement the business logic for renting and returning movies.
``` java
@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    // Rent a movie
    public void rentMovie(Long userId, Long movieId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        if (movie.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Movie is not available for rental");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setMovie(movie);
        rental.setRentalDate(LocalDate.now());
        rental.setStatus(RentalStatus.RENTED);

        // Update movie availability
        movie.setAvailableCopies(movie.getAvailableCopies() - 1);

        rentalRepository.save(rental);
        movieRepository.save(movie);
    }

    // Return a movie
    public void returnMovie(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found"));

        if (rental.getStatus() != RentalStatus.RENTED) {
            throw new IllegalStateException("Movie has already been returned");
        }

        rental.setReturnDate(LocalDate.now());
        rental.setStatus(RentalStatus.RETURNED);

        Movie movie = rental.getMovie();
        movie.setAvailableCopies(movie.getAvailableCopies() + 1);

        rentalRepository.save(rental);
        movieRepository.save(movie);
    }

    // Get rentals for a user
    public List<Rental> getRentalsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return rentalRepository.findByUser(user);
    }
}
```
#### **6. Create the `RentalController`**
Expose endpoints to rent, return, and view rentals.
``` java
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public ResponseEntity<Void> rentMovie(@RequestParam Long userId, @RequestParam Long movieId) {
        rentalService.rentMovie(userId, movieId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return")
    public ResponseEntity<Void> returnMovie(@RequestParam Long rentalId) {
        rentalService.returnMovie(rentalId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rental>> getRentalsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(rentalService.getRentalsForUser(userId));
    }
}
```
### **Testing the System**
Once implemented, you can test your rental feature using tools like Postman or Swagger (`/api/rentals/`):
- **POST /api/rentals/rent?userId=1&movieId=1**: Rent a movie.
- **POST /api/rentals/return?rentalId=1**: Return a movie.
- **GET /api/rentals/user/1**: View all rentals for a user.

### **Summary of What to Create**
1. **Create** the `Rental` entity for rental records.
2. **Update** the `Movie` entity to track stock and availability.
3. **Add** the `RentalStatus` enum to track rental states.
4. **Implement** a `RentalRepository` for database operations.
5. **Create** a `RentalService` for business logic like renting and returning movies.
6. **Build** a `RentalController` to expose rental-related endpoints.

If you'd like specific assistance with any part of the implementation, feel free to let me know!



    If the quantity of each movie is always **1**, and a movie can only be rented by one user at a time, the logic changes slightly as we no longer need to track `availableCopies`. Instead, we will need to:
            1. Ensure that each movie can only be rented if it is not already rented.
2. Update the `Rental` entity to include a `RENTED` status for such movies.

    Here’s how we would modify the implementation:

            ---

            ### **Updated Plan for Single-Rent Movies**
            1. **One movie can only be rented by one user at a time.**
            - Check if the movie is currently rented by querying the `Rental` table for an active `RENTED` status.
    - If the movie is rented, prevent new rentals.
2. Once the user returns the movie, the status changes to `RETURNED`, allowing others to rent it again.
            3. Remove all notions of `quantity` or `availableCopies` in the `Movie` entity as it is no longer relevant.

---

        ### **Steps for the Updated Implementation**

            ---

            #### **1. Update the `Rental` Entity**
    The `Rental` entity will remain similar, but renting should be conditional on whether the movie is already rented.

```java
import jakarta.persistence.*;
import java.time.LocalDate;

    @Entity
    public class Rental {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;  // The user renting the movie.

        @OneToOne
        @JoinColumn(name = "movie_id", nullable = false) // Enforce One-to-One relationship
        private Movie movie;  // The rented movie.

        @Column(nullable = false)
        private LocalDate rentalDate;  // Date when the movie was rented.

        private LocalDate returnDate;  // Date when the movie was returned.

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private RentalStatus status;  // RENTED or RETURNED.

        // Getters and Setters...
    }
```

    Here, the relationship between a `Movie` and a `Rental` is **one-to-one**, which ensures only one `Rental` record can exist for a given movie until it is returned.

---

        #### **2. Remove the `availableCopies` Field in `Movie`**
    Remove the `totalCopies` and `availableCopies` fields from the `Movie` entity, as these are no longer needed.

            ```java
    @Entity
    public class Movie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        private String genre;

        private String director;

        private String summary;

        // Other fields...

        // No need to track stock or availability, because each movie is rented once at a time.

        // Getters and setters...
    }
```

        ---

        #### **3. Adjust the `RentalService` Logic**

    We modify the `RentalService` to include a check: If the movie is already rented, throw an exception to prevent duplicate rentals.

            ```java
    @Service
    public class RentalService {

        private final RentalRepository rentalRepository;
        private final UserRepository userRepository;
        private final MovieRepository movieRepository;

        @Autowired
        public RentalService(RentalRepository rentalRepository, UserRepository userRepository, MovieRepository movieRepository) {
            this.rentalRepository = rentalRepository;
            this.userRepository = userRepository;
            this.movieRepository = movieRepository;
        }

        // Rent a movie
        public void rentMovie(Long userId, Long movieId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

            // Check if the movie is already rented
            Optional<Rental> existingRental = rentalRepository.findByMovieAndStatus(movie, RentalStatus.RENTED);
            if (existingRental.isPresent()) {
                throw new IllegalStateException("Movie is already rented");
            }

            // Create a new rental record
            Rental rental = new Rental();
            rental.setUser(user);
            rental.setMovie(movie);
            rental.setRentalDate(LocalDate.now());
            rental.setStatus(RentalStatus.RENTED);

            rentalRepository.save(rental);
        }

        // Return a movie
        public void returnMovie(Long rentalId) {
            Rental rental = rentalRepository.findById(rentalId)
                    .orElseThrow(() -> new IllegalArgumentException("Rental not found"));

            if (rental.getStatus() != RentalStatus.RENTED) {
                throw new IllegalStateException("Movie has already been returned");
            }

            // Update the rental record
            rental.setReturnDate(LocalDate.now());
            rental.setStatus(RentalStatus.RETURNED);

            rentalRepository.save(rental);
        }

        // Get rentals for a user
        public List<Rental> getRentalsForUser(Long userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            return rentalRepository.findByUser(user);
        }
    }
```

        ---

        #### **4. Update the `RentalRepository`**

    Add a method to fetch active rentals for a movie:

            ```java
    @Repository
    public interface RentalRepository extends JpaRepository<Rental, Long> {

        List<Rental> findByUser(User user);

        Optional<Rental> findByMovieAndStatus(Movie movie, RentalStatus status);
    }
```

    The `findByMovieAndStatus` method ensures you can check if a specific movie is currently rented.

            ---

            #### **5. Adjust the `RentalController`**

            ```java
    @RestController
    @RequestMapping("/api/rentals")
    public class RentalController {

        private final RentalService rentalService;

        @Autowired
        public RentalController(RentalService rentalService) {
            this.rentalService = rentalService;
        }

        @PostMapping("/rent")
        public ResponseEntity<Void> rentMovie(@RequestParam Long userId, @RequestParam Long movieId) {
            rentalService.rentMovie(userId, movieId);
            return ResponseEntity.ok().build();
        }

        @PostMapping("/return")
        public ResponseEntity<Void> returnMovie(@RequestParam Long rentalId) {
            rentalService.returnMovie(rentalId);
            return ResponseEntity.ok().build();
        }

        @GetMapping("/user/{userId}")
        public ResponseEntity<List<Rental>> getRentalsForUser(@PathVariable Long userId) {
            return ResponseEntity.ok(rentalService.getRentalsForUser(userId));
        }
    }
```

        ---

        ### **Summary of Changes**
            1. **Single Rental per Movie**:
            - Make `Movie` and `Rental` a `@OneToOne` relationship.
2. **Status-Based Availability**:
            - Add `RentalStatus.RENTED` and prevent renting if already rented.
            3. **Simplify Movie Logic**:
            - Remove `availableCopies` and `totalCopies` from `Movie`.
            4. **Validation in Service**:
            - `RentalService` ensures a movie is not rented if it's already `RENTED`.

            ---

    This approach will ensure that a movie can only be rented by one user at a time, and once returned, others can rent it again. Let me know if you need me to generate or refine any specific part!*/
}
