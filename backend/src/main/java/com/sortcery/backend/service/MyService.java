package com.sortcery.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sortcery.backend.model.User;
import com.sortcery.backend.dto.branch.BranchResponseDTO;

@Service
public class MyService {
    private final TokenService tokenService;

    MyService (TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public List<BranchResponseDTO> findBranches(String token) {
        User user = tokenService.findUser(token);

        return user.getBranches()
            .stream()
            .map(BranchResponseDTO::new)
            .toList();
    }
}
