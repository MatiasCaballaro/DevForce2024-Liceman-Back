package com.liceman.application.user.infrastructure.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Operation(description = "")
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    @Hidden
    public String get () {
        return "GET:: admin controller";
    }
    @Operation(description = "")
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public String post () {
        return "POST:: admin controller";
    }
    @Operation(description = "")
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    @Hidden
    public String put () {
        return "PUT:: admin controller";
    }
    @Operation(description = "")
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public String delete () {
        return "DELETE:: admin controller";
    }
}
