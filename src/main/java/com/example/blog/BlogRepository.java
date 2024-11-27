package com.example.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blog.Post;
import java.util.UUID;

public interface BlogRepository extends JpaRepository<Post, UUID> {
    // 追加のクエリメソッドがあればここに定義します
}

