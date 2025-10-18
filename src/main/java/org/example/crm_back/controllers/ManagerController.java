package org.example.crm_back.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.crm_back.dto.manager.CreateManagerRequestDto;
import org.example.crm_back.dto.manager.ManagerDto;
import org.example.crm_back.dto.pagination.PaginationResponseDto;
import org.example.crm_back.services.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/managers")
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping()
    public ResponseEntity<PaginationResponseDto<ManagerDto>> getManagers(@RequestParam @Valid Integer page) {
        return new ResponseEntity<>(managerService.getManagers(page), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createManager(
            @RequestBody @Valid CreateManagerRequestDto createManagerRequestDto,
            @RequestHeader("Authorization") String token) {
        managerService.createManager(createManagerRequestDto, token.replace("Bearer ", ""));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/ban/{id}")
    public ResponseEntity<Void> toggleBanStatus(@PathVariable Long id) {
        managerService.toggleBan(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
