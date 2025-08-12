package org.example.crm_back.controllers;

import lombok.RequiredArgsConstructor;
import org.example.crm_back.dto.comment.CommentDto;
import org.example.crm_back.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/orders/{orderId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable Long orderId, @RequestHeader("Authorization") String token, @RequestBody CommentDto dto) {

        return new ResponseEntity<>(
                commentService.createComment(orderId, token.replace("Bearer ", ""), dto),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long orderId) {
        return new ResponseEntity<>(
                commentService.getComments(orderId),
                HttpStatus.OK
        );
    }
}