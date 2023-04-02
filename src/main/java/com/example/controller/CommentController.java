package com.example.controller;

import com.example.dto.CommentDto;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")//you can give at generic CommentDto as Object because Object is supermost clss in java so you can return any type of value in it
    public ResponseEntity<Object> saveComment( @PathVariable Long postId, @Valid @RequestBody CommentDto commentDto, BindingResult result) {
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CommentDto dto = commentService.save(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> findCommentByPostId(@PathVariable("postId") long postId){
        List<CommentDto> cmntList = commentService.getCommentByPostId(postId);
        return cmntList;
    }
    @GetMapping("/posts/{postId}/comment/{commentId}")
    public CommentDto getCommentById(@PathVariable("postId") long postId,@PathVariable("commentId") long commentId){

        CommentDto dto = commentService.getCommentById(postId, commentId);
        return dto;
    }
    @PutMapping("/posts/{postId}/comment/{commentId}")//here you can change in generic CommentDto but @Validation condition should not be there
    public ResponseEntity<Object> updateCommentById( @PathVariable("postId") long postId,@PathVariable("commentId") long commentId,@Valid @RequestBody CommentDto commentDto,BindingResult result){


        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        CommentDto dto = commentService.updateCommentById(postId, commentId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteCommentById( @PathVariable("postId") long postId,@PathVariable("commentId") long commentId){


        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>(String.format("The comment of id=%s is deleted of postId=%s ",commentId,postId),HttpStatus.CREATED);
    }





}