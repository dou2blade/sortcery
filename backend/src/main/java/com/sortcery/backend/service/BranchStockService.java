package com.sortcery.backend.service;

import com.sortcery.backend.model.BranchStock;
import com.sortcery.backend.model.Branch;
import com.sortcery.backend.model.Product;
import com.sortcery.backend.dto.branchstock.BranchStockRequestDTO;
import com.sortcery.backend.dto.branchstock.BranchStockResponseDTO;

import com.sortcery.backend.repository.BranchStockRepository;
import com.sortcery.backend.repository.BranchRepository;
import com.sortcery.backend.repository.ProductRepository;

import com.sortcery.backend.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchStockService {
    private final BranchStockRepository branchStockRepository;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public BranchStockService(BranchStockRepository branchStockRepository, ProductRepository productRepository, BranchRepository branchRepository) {
        this.branchStockRepository = branchStockRepository;
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
    }

    public List<BranchStockResponseDTO> findAll() {
        return branchStockRepository.findAll()
            .stream()
            .map((branchStock) -> new BranchStockResponseDTO(branchStock))
            .toList();
    }

    public BranchStockResponseDTO findById(Long id) {
        return branchStockRepository.findById(id)
            .map((branchStock) -> new BranchStockResponseDTO(branchStock))
            .orElseThrow(() -> new NotFoundException(BranchStock.class, id));
    }

    public BranchStockResponseDTO save(BranchStockRequestDTO request) {
        Branch branch = branchRepository.findById(request.getBranchId())
            .orElseThrow(() -> new NotFoundException(Branch.class, request.getBranchId()));

        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new NotFoundException(Product.class, request.getProductId()));

        BranchStock branchStock = new BranchStock();

        branchStock.setBranch(branch);
        branchStock.setProduct(product);
        branchStock.setAmount(request.getAmount());
        
        BranchStock saved = branchStockRepository.save(branchStock);
        
        return new BranchStockResponseDTO(saved);
    }

    public BranchStockResponseDTO update(Long id, BranchStockRequestDTO request) {
        BranchStock existing = branchStockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(BranchStock.class, id));

        if (request.getAmount() != null) existing.setAmount(request.getAmount());

 if (request.getBranchId() != null) {
            Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                    new NotFoundException(Branch.class, request.getBranchId())
                );

            existing.setBranch(branch);
        }

        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                    new NotFoundException(Product.class, request.getProductId())
                );

            existing.setProduct(product);
        }
        BranchStock saved = branchStockRepository.save(existing);

        return new BranchStockResponseDTO(saved);
    }

    public BranchStockResponseDTO delete(Long id) {
        BranchStock deleted = branchStockRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(BranchStock.class, id));

        branchStockRepository.deleteById(id);

        return new BranchStockResponseDTO(deleted);
    }
}