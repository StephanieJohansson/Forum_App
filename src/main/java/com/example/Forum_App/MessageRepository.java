package com.example.Forum_App;

//importing JpaRepository(provides CRUD) interface from spring data jpa
import org.springframework.data.jpa.repository.JpaRepository;

//interface that extends JpaRepository to provide database access for the Message entity
public interface MessageRepository extends JpaRepository<Message, Long> {
}