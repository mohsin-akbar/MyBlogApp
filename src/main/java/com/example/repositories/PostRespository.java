package com.example.repositories;

import com.example.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRespository extends JpaRepository<Post,Long> {
}
