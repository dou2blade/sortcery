package com.sortcery.backend.service;

import com.sortcery.backend.model.Store;
import com.sortcery.backend.dto.store.StoreRequestDTO;
import com.sortcery.backend.dto.store.StoreResponseDTO;
import com.sortcery.backend.repository.StoreRepository;
import com.sortcery.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreResponseDTO> findAll() {
        return storeRepository.findAll()
            .stream()
            .map((store) -> new StoreResponseDTO(store))
            .toList();
    }

    public StoreResponseDTO findById(Long id) {
        return storeRepository.findById(id)
            .map((store) -> new StoreResponseDTO(store))
            .orElseThrow(() -> new NotFoundException(Store.class, id));
    }

    public StoreResponseDTO save(StoreRequestDTO request) {
        Store saved = storeRepository.save(new Store(
            request.getName()
        ));
        return new StoreResponseDTO(saved);
    }

    public StoreResponseDTO update(Long id, StoreRequestDTO request) {
        Store existing = storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Store.class, id));

        if (request.getName() != null && !request.getName().isBlank()) existing.setName(request.getName());

        Store saved = storeRepository.save(existing);

        return new StoreResponseDTO(saved);
    }

    public StoreResponseDTO delete(Long id) {
        Store deleted = storeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Store.class, id));

        storeRepository.deleteById(id);

        return new StoreResponseDTO(deleted);
    }
}

