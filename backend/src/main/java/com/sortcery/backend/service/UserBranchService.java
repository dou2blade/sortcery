package com.sortcery.backend.service;

import com.sortcery.backend.model.UserBranch;
import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.User;

import com.sortcery.backend.repository.UserBranchRepository;
import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.repository.UserRepository;

import com.sortcery.backend.exception.NotFoundException;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserBranchService {
    private final UserBranchRepository userBranchRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;

    public UserBranchService(UserBranchRepository userBranchRepository, BranchRepository branchRepository, UserRepository userRepository) {
        this.userBranchRepository = userBranchRepository;
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
    }

    public List<User> getUsersByBranch(Long branchId) {
        branchRepository.findById(branchId)
            .orElseThrow(() -> new NotFoundException(Branch.class, branchId));

        return userBranchRepository.findByBranchId(branchId)
            .stream()
            .map(UserBranch::getUser)
            .toList();
    }

    public List<Branch> getBranchesByUser(Long userId) {
        userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(User.class, userId));

        return userBranchRepository.findByUserId(userId)
            .stream()
            .map(UserBranch::getBranch)
            .toList();
    }
}
