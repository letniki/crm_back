package org.example.crm_back.controllers;

import lombok.RequiredArgsConstructor;
import org.example.crm_back.services.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/")
    public ResponseEntity<List<String>> getAllGroups() {
        return new ResponseEntity<>(groupService.getAllGroupNames(), HttpStatus.OK);
    }
}
