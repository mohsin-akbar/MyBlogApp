package com.example.service;

import com.example.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto save(Long postId, CommentDto commentDto);
   public List<CommentDto> getCommentByPostId(long postId);

   CommentDto getCommentById(long postId,long commentId);

    CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto);

    void deleteCommentById(long postId, long commentId);
}
