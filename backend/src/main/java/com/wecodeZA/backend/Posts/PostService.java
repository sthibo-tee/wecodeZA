package com.wecodeZA.backend.Posts;

import com.wecodeZA.backend.User.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //Method that retrives post by its ID, throws IllegalStateException if the post with the given ID is not found
    public Post getPostbyId(Long postId){
        return postRepository.findById(postId).orElseThrow(() -> new IllegalStateException("Post with ID " + postId + " not found"));
    }

    //Method that retrives list of all posts
    public List<Post> getAllPost(){
        return postRepository.findAll();
    }

    //Method that retrives post by user ID, throws IllegalStateException if the post with the given ID is not found
    public List<Post> getPostsByUserId(Long userId) {
        List<Post> post = postRepository.findByUserId(userId);
        if (post.isEmpty()) {
            throw new IllegalStateException("No posts found for user with ID " + userId);
        }
        return post;
    }
    public void deletePost(Long postId) {

        boolean exist = postRepository.existsById(postId);
        if(!exist){
            throw new IllegalStateException("Post with ID: " + postId + " does not exist");
        }

        postRepository.deleteById(postId);
    }

    public Post createPost(Long userId, String title, String topic, String context) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));


        Post post = new Post(user, title, topic, context);


        return postRepository.save(post);

    }


}
