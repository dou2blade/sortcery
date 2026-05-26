package com.sortcery.backend.service;

import com.sortcery.backend.model.UserBranch;
import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.User;

import com.sortcery.backend.repository.UserBranchRepository;
import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.repository.UserRepository;

import com.sortcery.backend.exception.NotFoundException;

import org.springframework.security.access.AccessDeniedException;
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

    public UserBranch assignUserToBranch(Long userId, Long branchId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(User.class, userId));

        if (!user.getRole().isAssignableToBranch()) {
            throw new AccessDeniedException(String.format("User with id %d does not have a branch-assignable role.", userId));
        }

        Branch branch = branchRepository.findById(branchId)
            .orElseThrow(() -> new NotFoundException(Branch.class, branchId));

        if (userBranchRepository.existsByUserIdAndBranchId(userId, branchId)) {
            throw new IllegalStateException(String.format("User %d is already assigned to branch %d", userId, branchId));
        } 

        UserBranch saved = new UserBranch(user, branch);
        userBranchRepository.save(saved);

        return saved;
    }

    public UserBranch removeUserFromBranch(Long userId, Long branchId) {
        UserBranch deleted = userBranchRepository.findByUserIdAndBranchId(userId, branchId)
            .orElseThrow(() -> new NotFoundException(String.format("User %d is not assigned to branch %d", userId, branchId)));

        userRepository.deleteById(deleted.getId());

        return deleted;
    }

    public boolean userHasAccessToBranch(Long userId, Long branchId) {
        return userBranchRepository.existsByUserIdAndBranchId(userId, branchId);
    }
}
