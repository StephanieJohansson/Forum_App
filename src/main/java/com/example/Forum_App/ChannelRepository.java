package com.example.Forum_App;

//importing JpaRepository from Spring Data JPA, provides methods from handling database operations(CRUD)
import org.springframework.data.jpa.repository.JpaRepository;

//defines an interface for the Channel entity. JpaRepositoryChannel specifies that this repository manages Channel
//entities and that the primary key is Long. Extending to gain access
public interface ChannelRepository extends JpaRepository<Channel, Long> {
}