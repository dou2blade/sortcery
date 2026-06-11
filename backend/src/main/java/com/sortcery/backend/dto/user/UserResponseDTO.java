package com.sortcery.backend.dto.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sortcery.backend.dto.branch.BranchSummaryDTO;
import com.sortcery.backend.model.User;

public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private User.Role role;
    private List<BranchSummaryDTO> branches = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.branches = user.getBranches()
            .stream()
            .map(BranchSummaryDTO::new)
            .toList();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public User.Role getRole() { return role; }
    public List<BranchSummaryDTO> getBranches() { return branches; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
