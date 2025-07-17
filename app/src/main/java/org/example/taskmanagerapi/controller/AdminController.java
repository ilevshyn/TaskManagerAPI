package org.example.taskmanagerapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.taskmanagerapi.dto.AppUserDTO;
import org.example.taskmanagerapi.mapper.AppUserMapper;
import org.example.taskmanagerapi.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    private final AppUserService appUserService;

    public AdminController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Operation(
            summary = "Get all users",
            description = "Gets all users stored in database, if there are any",
            responses = {
                    @ApiResponse(
                            responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppUserDTO.class))
                    ),
                    @ApiResponse(responseCode = "204")
            }
    )
    @GetMapping("/admin/users")
    public ResponseEntity<List<AppUserDTO>> getAllUsers() {
        var result = appUserService.findAllAppUsers();
        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.ok(result.get().stream().map(AppUserMapper::toAppUserDTO).toList());
        }
    }
}
