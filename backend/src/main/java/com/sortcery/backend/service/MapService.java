package com.sortcery.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.sortcery.backend.dto.map.MapAddressSuggestionDTO;

@Service
public class MapService {
    private final RestClient restClient;

    public MapService(
        RestClient restClient
    ) {
        this.restClient = restClient;
    }

    public List<MapAddressSuggestionDTO> search(String query) {

        List<Map<String, Object>> results = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host("nominatim.openstreetmap.org")
                .path("/search")
                .queryParam("q", query)
                .queryParam("format", "jsonv2")
                .queryParam("limit", 5)
                .queryParam("countrycodes", "ph")
                .queryParam("viewbox", "116.0,21.5,127.0,4.5")
                .queryParam("bounded", 1)
                .build())
            .header("User-Agent", "Sortcery")
            .retrieve()
            .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {});

        if (results == null) {
            return List.of();
        }

        return results.stream()
            .map(result -> new MapAddressSuggestionDTO(
                (String) result.get("display_name"),
                Double.valueOf((String) result.get("lat")),
                Double.valueOf((String) result.get("lon"))
            ))
            .toList();
    }
}
