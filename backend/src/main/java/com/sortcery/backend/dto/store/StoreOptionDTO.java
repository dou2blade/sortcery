package com.sortcery.backend.dto.store;

import com.sortcery.backend.model.Store;

public class StoreOptionDTO {
    private final Long id;
    private final String name;

    public StoreOptionDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}

