package com.example.service;

import com.example.dto.PostDto;
import com.example.entities.Post;
import com.example.payload.PostResponse;
import com.example.repositories.PostRespository;
import com.example.utils.ResourceNotFoundException;
import com.sun.prism.shader.Solid_TextureRGB_AlphaTest_Loader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRespository postRespository;

    private ModelMapper modelMapper;

    public PostServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto save(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post savedPost = postRespository.save(post);

       return mapToDto(savedPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRespository.findAll(pageable);
        List<Post> contents = posts.getContent();//and all contents should be dtos
        List<PostDto> dtos= contents.stream().map(content -> mapToDto(content)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());


        return postResponse;
    }

    @Override
    public PostDto getPostById(long postId) {
        Post post = postRespository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "postId", postId)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(long postId,PostDto postDto) {
        Post post = postRespository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "postId", postId)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post savedPost = postRespository.save(post);

        return mapToDto(savedPost);
    }

    @Override
    public void deletePostById(long postId) {
        Post post = postRespository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "postId", postId)
        );
        postRespository.deleteById(post.getId());
    }

      public Post mapToEntity(PostDto postDto){
          Post post = modelMapper.map(postDto, Post.class);
//        Post post=new Post();
//        //post.setPostId(postDto.getPostId());
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
       return post;
    }
    public PostDto mapToDto(Post post){
        PostDto postDto = modelMapper.map(post, PostDto.class);
//        PostDto postDto=new PostDto();
//        postDto.setContent(post.getContent());
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());

        return postDto;
    }


}
