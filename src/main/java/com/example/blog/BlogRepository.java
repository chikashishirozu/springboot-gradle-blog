package com.example.blog;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Post, UUID> {
}
