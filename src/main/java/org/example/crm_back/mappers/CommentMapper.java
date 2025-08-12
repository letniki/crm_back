package org.example.crm_back.mappers;

import org.example.crm_back.dto.comment.CommentDto;
import org.example.crm_back.entities.Comment;
import org.example.crm_back.entities.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        if (comment == null) return null;
        return CommentDto.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yy")))
                .build();
    }

    public static Comment toEntity(CommentDto commentDto,  Order order) {
        if (commentDto == null) return null;

        Comment comment = new Comment();
        comment.setOrder(order);
        comment.setBody(commentDto.getBody());
        comment.setAuthor(commentDto.getAuthor());
        comment.setCreatedAt(LocalDateTime.now());

        return comment;
    }
}
