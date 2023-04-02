package com.example.service;

import com.example.dto.CommentDto;
import com.example.entities.Comment;
import com.example.entities.Post;
import com.example.repositories.CommentRepository;
import com.example.repositories.PostRespository;
import com.example.utils.BlogApiException;
import com.example.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.stream.Collectors;


@Service
public class CommentServiceImp implements CommentService{
    @Autowired
    private PostRespository postRespository;

    @Autowired
    private CommentRepository commentRepository;

    private ModelMapper modelMapper;

    public CommentServiceImp(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto save(Long postId, CommentDto commentDto) {
        Post post = postRespository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "postId", postId)
        );

        Comment comment=new Comment();
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);

    }
    public List<CommentDto> getCommentByPostId(long postId){
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> listOfComments = comments.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        return listOfComments;
    }

    @Override
    public CommentDto getCommentById(long postId,long commentId) {
        Post post = postRespository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "commentId", commentId)
        );


        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment Does not exist on this post");
        }


        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
        Post post = postRespository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "commentId", commentId)
        );

        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment Does not exist on this post");
        }
            comment.setName(commentDto.getName());
            comment.setEmail(commentDto.getEmail());
            comment.setBody(commentDto.getBody());
            comment.setPost(post);
            Comment savedComment = commentRepository.save(comment);



        return mapToDto(savedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = postRespository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "commentId", commentId)
        );

        if(comment.getPost().getId()!=post.getId()){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment Does not exist on this post");
        }
        commentRepository.deleteById(comment.getCommentId());
    }

    private CommentDto mapToDto(Comment comment){
       CommentDto commentDto = modelMapper.map(comment, CommentDto.class); //model mapper

//        CommentDto commentDto=new CommentDto();
//   commentDto.setPost(comment.getPost());
//        commentDto.setCommentId(comment.getCommentId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
//       commentDto.setPostId(comment.getPost().getId());

        return commentDto;
    }
}
