package com.findme.FindMeBack.Service;

import com.findme.FindMeBack.Entity.Post;
import com.findme.FindMeBack.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setProductClassifyName(postDetails.getProductClassifyName());
            post.setContent(postDetails.getContent());
            post.setProductName(postDetails.getProductName());
            post.setFoundPlace(postDetails.getFoundPlace());
            post.setImgPath(postDetails.getImgPath());
            post.setPostType(postDetails.getPostType());
            //post.setShown(postDetails.isShown()); // shown 값 업데이트
            post.setViews(postDetails.getViews());
            return postRepository.save(post);
        }
        return null;
    }

    public void deletePost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setShown(false); // 논리적 삭제: shown 필드를 false로 설정
            postRepository.save(post);
        } else {
            throw new RuntimeException("게시글을 찾을 수 없습니다: " + postId);
        }
    }
}


//package com.findme.FindMeBack.Service;
//
//import com.findme.FindMeBack.Entity.Post;
//import com.findme.FindMeBack.Repository.PostRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class PostService {
//
//    private final PostRepository postRepository;
//
//    public List<Post> getAllPosts() {
//        return postRepository.findAll();
//    }
//
//    public Post getPostById(Long id) {
//        Optional<Post> post = postRepository.findById(id);
//        return post.orElse(null);
//    }
//
//    public Post createPost(Post post) {
//        return postRepository.save(post);
//    }
//
//    public Post updatePost(Long id, Post post) {
//        post.setId(id); // 설정된 ID를 갖도록 설정
//        return postRepository.save(post);
//    }
//
//    public void deletePost(Long id) {
//        postRepository.deleteById(id);
//    }
//}
