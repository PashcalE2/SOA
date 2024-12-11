package main.adapter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CitiesApi {
    GET_ALL_SORTED_PAGINATED("/all/{sort-fields}/{sort-order}/{page}/{size}"),
    GET_BY_ID("/{id}"),
    PUT_BY_ID("/{id}");

    private final String endpoint;

    public String buildUrl(String baseEndpoint, Object... args) {
        return baseEndpoint + String.format(endpoint.replaceAll("(\\{[^}]+})", "%s"), args);
    }
}