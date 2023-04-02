package com.example.dto;

import com.example.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long commentId;

    @NotEmpty(message = "You can not leave empty")
    @Size(min=10 ,message="body content should have at least 10 characters")
    private String body;
    @Email
    @Size(min =2,message = "should not empty")
    private String email;
    @NotEmpty
    @Size(min =2,message = "should not empty")
    private String name;
   // private Post post;
   private long postId;

}
