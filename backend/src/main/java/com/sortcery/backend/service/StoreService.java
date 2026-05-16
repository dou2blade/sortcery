package com.isko_d.isko_d.service;

import com.isko_d.isko_d.model.Store;
import com.isko_d.isko_d.dto.log.StoreRequestDTO;
import com.isko_d.isko_d.dto.log.StoreResponseDTO;
import com.isko_d.isko_d.repository.StoreRepository;
import com.isko_d.isko_d.exception.NotFoundException;
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
            request.getStoreName()
        ));
        ));

        return new StoreResponseDTO(saved);
    }

    public StoreResponseDTO update(Long id, StoreRequestDTO request) {
        Store existing = storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Store.class, id));

        if (request.getStoreName() != null && !request.getStoreName().isBlank()) existing.setStoreName(request.getStoreName());

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

