package com.example.controller;

import com.example.dto.PostDto;
import com.example.payload.PostResponse;
import com.example.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PreAuthorize("hasRole('ADMIN')")//only user can access to save Post,ap ('ADMIN','USER') bhi rakh skte hai kyuki ap user ko bhi allowed karte hai//
    //lekin apko wha pr .hasAnyRoles("USER","ADMIN") dalna hoga configuration file me jakar
    //for validation here i used @Valid and BindingResult class for giving result message of exception or error
    @PostMapping//you can give at generic CommentDto as Object because Object is supermost clss in java so you can return any type of value in it
    public ResponseEntity<Object> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = postService.save(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    //http:localhost:8080/api/posts
    @GetMapping//http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=id&sortDir=desc
    public PostResponse getAllPosts(@RequestParam(value="pageNo",defaultValue="0",required = false) int pageNo,
                                    @RequestParam(value="pageSize",defaultValue="10",required = false) int pageSize,
                                    @RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
                                    @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir) {
        PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return postResponse;
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") long postId){
        PostDto dto = postService.getPostById(postId);

        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")//only user can access to save Post
    @PutMapping("/{postId}")//Object supermost class of java so it returns any kind of java value
    public ResponseEntity<Object> updatePostById(@PathVariable("postId") long postId,@Valid @RequestBody PostDto postDto,BindingResult result){


        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


        PostDto dto = postService.updatePostById(postId, postDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable("postId") long postId){
        postService.deletePostById(postId);
            return new ResponseEntity<>(String.format("The Post of id=%s is Deleted Successfully!",postId),HttpStatus.CREATED);
    }

}
