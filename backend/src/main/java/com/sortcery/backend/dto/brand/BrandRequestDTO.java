package com.sortcery.backend.dto.brand;

import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.NotBlank;

public class BrandRequestDTO {

    @NotBlank(groups=Create.class)
    private String name;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}