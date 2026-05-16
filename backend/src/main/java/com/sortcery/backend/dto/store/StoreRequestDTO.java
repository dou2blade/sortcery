package com.sortcery.backend.dto.store;

import com.sortcery.backend.validation.Create;
import com.sortcery.backend.validation.Update;
import jakarta.validation.constraints.NotBlank;

public class StoreRequestDTO {

    @NotBlank(groups=Create.class)
    private String name;

    public String getStoreName() { return name; }

    public void setStoreName(String name) { this.name = name; }
}
