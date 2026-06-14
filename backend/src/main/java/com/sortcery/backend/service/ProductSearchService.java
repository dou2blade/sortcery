package com.sortcery.backend.service;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sortcery.backend.algorithms.Haversine;
import com.sortcery.backend.algorithms.MergeSort;
import com.sortcery.backend.dto.branch.BranchPublicDTO;
import com.sortcery.backend.dto.branchproductvariant.BranchProductVariantPublicDTO;
import com.sortcery.backend.dto.product.ProductSalesDTO;
import com.sortcery.backend.repository.ProductRepository;
import com.sortcery.backend.repository.BranchProductVariantRepository;


@Service
public class ProductSearchService {
    private final ProductRepository productRepository;
    private final BranchProductVariantRepository branchProductVariantRepository;
    private final BranchService branchService;

    public ProductSearchService(
        ProductRepository productRepository,
        BranchProductVariantRepository branchProductVariantRepository,
        BranchService branchService
    ) {
        this.productRepository = productRepository;
        this.branchProductVariantRepository = branchProductVariantRepository;
        this.branchService = branchService;
    }

    public Page<BranchProductVariantPublicDTO> findPage(
        int page,
        int size,
        String sort,
        String search,
        Long category,
        Long brand,
        Long branch,
        Double latitude,
        Double longitude,
        Double radius
    ) {
        return switch (sort) {
            case "asc" -> branchProductVariantRepository.findAllWithSales(
                search,
                category,
                brand,
                branch,
                PageRequest.of(
                    page,
                    size,
                    Sort.by(Sort.Direction.ASC, "price")
                )
            ).map((item) -> new BranchProductVariantPublicDTO(
                item.bpv(),
                item.sales(),
                latitude != null && longitude != null
                    ? Haversine.distance(
                        item.bpv().getBranch().getLatitude(),
                        item.bpv().getBranch().getLongitude(),
                        latitude,
                        longitude
                    ) : null
            ));

            case "desc" -> branchProductVariantRepository.findAllWithSales(
                search,
                category,
                brand,
                branch,
                PageRequest.of(
                    page,
                    size,
                    Sort.by(Sort.Direction.DESC, "price")
                )
            ).map((item) -> new BranchProductVariantPublicDTO(
                item.bpv(),
                item.sales(),
                latitude != null && longitude != null
                    ? Haversine.distance(
                        item.bpv().getBranch().getLatitude(),
                        item.bpv().getBranch().getLongitude(),
                        latitude,
                        longitude
                    ) : null
            ));

            case "distance" -> {
                if (latitude == null || longitude == null) {
                    yield Page.empty(
                        PageRequest.of(page, size)
                    );
                }

                List<BranchProductVariantPublicDTO> products =
                    branchProductVariantRepository.findAllWithSales(
                        search,
                        category,
                        brand,
                        branch,
                        Pageable.unpaged()
                    )
                    .stream()
                    .map(item -> new BranchProductVariantPublicDTO(
                        item.bpv(),
                        item.sales(),
                        Haversine.distance(
                            item.bpv().getBranch().getLatitude(),
                            item.bpv().getBranch().getLongitude(),
                            latitude,
                            longitude
                        )
                    ))
                    .toList();

                if (radius != null) {
                    products = products.stream()
                        .filter(p -> p.getDistance() <= radius)
                        .toList();
                }

                products = MergeSort.sort(
                    products,
                    Comparator.comparingDouble(
                        BranchProductVariantPublicDTO::getDistance
                    )
                );

                int start = page * size;
                int end = Math.min(start + size, products.size());

                yield new PageImpl<>(
                    start >= products.size()
                        ? List.of()
                        : products.subList(start, end),
                    PageRequest.of(page, size),
                    products.size()
                );
            }

            case "sales" -> branchProductVariantRepository.findTopWithSales(
                search,
                category,
                brand,
                branch,
                PageRequest.of(page, size)
            ).map((item) -> new BranchProductVariantPublicDTO(
                item.bpv(),
                item.sales(),
                latitude != null && longitude != null
                    ? Haversine.distance(
                        item.bpv().getBranch().getLatitude(),
                        item.bpv().getBranch().getLongitude(),
                        latitude,
                        longitude
                    ) : null
            ));

            default -> branchProductVariantRepository.findAllWithSales(
                search,
                category,
                brand,
                branch,
                PageRequest.of(page, size)
            ).map((item) -> new BranchProductVariantPublicDTO(
                item.bpv(),
                item.sales(),
                latitude != null && longitude != null
                    ? Haversine.distance(
                        item.bpv().getBranch().getLatitude(),
                        item.bpv().getBranch().getLongitude(),
                        latitude,
                        longitude
                    ) : null
            ));
        };
    }

    public List<ProductSalesDTO> findTop(
        int size,
        Double latitude,
        Double longitude,
        Double radius
    ) {
        if (latitude == null || longitude == null) {
            return productRepository.findTop();
        }

        List<Long> branchIds = branchService.findNearby(
                99,
                latitude,
                longitude
            )
            .stream()
            .filter(branch -> radius != null ? branch.getDistance() < radius : true)
            .map(BranchPublicDTO::getId)
            .toList();

        return productRepository.findTopByBranches(branchIds);
    }
}
