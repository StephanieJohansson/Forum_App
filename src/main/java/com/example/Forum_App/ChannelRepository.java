package com.example.Forum_App;

//importing JpaRepository from Spring Data JPA, provides methods from handling database operations(CRUD)
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//importing utility classes to work with lists and optional values
import java.util.List;
import java.util.Optional;

//defines an interface for the Channel entity. JpaRepositoryChannel specifies that this repository manages Channel
//entities and that the primary key is Long. Extending to gain access
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    // load the messages asap to prevent lasyInitialitionException
    @EntityGraph(attributePaths = "messages") //
    Optional<Channel> findById(Long id);

    //annotation to allow defining a custom JPQL query. Retrieves all channel entities and fetches their associated message
    // using a left join fetch. DISTINCT ensures that duplicated channels are not returned in result
    @Query("SELECT DISTINCT c FROM Channel c LEFT JOIN FETCH c.messages")
    List<Channel> findAllWithMessages();
}