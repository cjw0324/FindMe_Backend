package com.findme.FindMeBack.Controller;

import com.findme.FindMeBack.Entity.Post;
import com.findme.FindMeBack.Entity.PostType;
import com.findme.FindMeBack.Service.PostService.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/lost") //분실물 게시글 전체 조회
    public ResponseEntity<List<Post>> getAllLostPosts() {
        List<Post> posts = postService.findByPostTypeIs(PostType.LOST);
        System.out.println(posts.toString());
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/found") //습득물 게시글 전체 조회
    public ResponseEntity<List<Post>> getAllFoundPosts() {
        List<Post> posts = postService.findByPostTypeIs(PostType.FOUND);
        return ResponseEntity.ok(posts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        return postService.updatePost(id, postDetails);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
