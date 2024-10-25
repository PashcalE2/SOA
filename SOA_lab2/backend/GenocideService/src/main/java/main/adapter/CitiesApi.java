package main.adapter;

import main.AppConfiguration;

public enum CitiesApi {
    GET_ALL_SORTED_PAGINATED("/all/{sort-fields}/{sort-order}/{page}/{size}"),
    GET_BY_ID("/{id}"),
    PUT_BY_ID("/{id}");

    private final String endpoint;


    CitiesApi(String endpoint) {
        this.endpoint = endpoint;
    }

    public String buildUrl(Object... args) {
        return AppConfiguration.baseEndpoint + String.format(endpoint.replaceAll("(\\{[^}]+})", "%s"), args);
    }
}