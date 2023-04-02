package com.example.service;

import com.example.dto.PostDto;
import com.example.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto save(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long postId);

    PostDto updatePostById(long postId,PostDto postDto);

    void deletePostById(long postId);
}
