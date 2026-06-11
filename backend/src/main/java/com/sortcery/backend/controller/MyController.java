package com.sortcery.backend.controller;

import com.sortcery.backend.service.MyService;
import com.sortcery.backend.dto.common.ApiResponse;
import com.sortcery.backend.exception.ForbiddenException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path="/my")
public class MyController {
    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping(path="/branches")
    public ResponseEntity<ApiResponse> getToken(
        @RequestHeader("Authorization") String authHeader
    ) {
        if (!authHeader.startsWith("Bearer ")) {
            throw new ForbiddenException("Incorrect token format");
        }

        String plainToken = authHeader.substring(7);
        return ResponseEntity.ok(ApiResponse.of(myService.findBranches(plainToken)));
    }
}
