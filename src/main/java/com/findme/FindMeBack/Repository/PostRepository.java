package com.findme.FindMeBack.Repository;

import com.findme.FindMeBack.Entity.Post;
import com.findme.FindMeBack.Entity.PostType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.id = :id")
    void incrementViews(Long id);

    List<Post> findByPostType(PostType postType);
}
