package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;

    @NotEmpty(message = "You can not leave empty")
    @Size(min=10 ,message="Post content should have at least 10 characters")
    private String content;
    @NotEmpty
    @Size(min=10 ,message="Post description should have at least 10 characters")
    private String description;
    @NotEmpty
    @Size(min=2 ,message="Post title should have at least 2 characters")
    private String title;
}
