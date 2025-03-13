package com.example.Forum_App;

//importing JpaRepository from Spring Data JPA, provides methods from handling database operations(CRUD)
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//defines an interface for the Channel entity. JpaRepositoryChannel specifies that this repository manages Channel
//entities and that the primary key is Long. Extending to gain access
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    // load the messages asap to prevent lasyInitialitionException
    @EntityGraph(attributePaths = "messages") //
    Optional<Channel> findById(Long id);
}