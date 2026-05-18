package com.sortcery.backend.dto.store;

import com.sortcery.backend.validation.Create;
import jakarta.validation.constraints.NotBlank;

public class StoreRequestDTO {

    @NotBlank(groups=Create.class)
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
