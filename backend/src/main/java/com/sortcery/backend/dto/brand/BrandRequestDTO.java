package com.sortcery.backend.dto.brand;

import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.NotBlank;

public class BrandRequestDTO {

    @NotBlank(groups=Create.class)
    private String name;

    private String imageUrl;

    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }

    public void setName(String name) { this.name = name; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
