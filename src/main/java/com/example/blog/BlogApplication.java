package com.example.blog;

import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PostRepository postRepository, UserRepository userRepository) {
        return (args) -> {
            // データベースに初期データを投入するコードをここに記述
            System.out.println("アプリケーションが起動しました");
        };
    }
}
