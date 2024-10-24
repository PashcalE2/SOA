package main.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.entity.dto.Cities;
import main.entity.dto.SortOrder;
import main.entity.model.City;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;


@Slf4j
public class CityServiceAdapter {
    private final HttpClient client = HttpClient.newHttpClient();
    private final XmlDeserializer<City> cityXmlDeserializer = new XmlDeserializer<>(City.class);
    private final XmlDeserializer<Cities> citiesListXmlDeserializer = new XmlDeserializer<>(Cities.class);

    public CityServiceAdapter() {
        // TODO помогите
        System.setProperty("javax.net.ssl.trustStore", "/home/studs/s311817/servers/jetty-home-12.0.14/base/etc/wildfly.truststore");
    }

    @AllArgsConstructor
    public enum CitiesApi {
        GET_ALL_SORTED_PAGINATED("/all/{sort-fields}/{sort-order}/{page}/{size}", "GET"),
        GET_BY_ID("/{id}", "GET"),
        PUT_BY_ID("/{id}", "PUT");

        // TODO помогите
        private final static String baseUrl = "http://localhost:22600/cities";
        private final String endpoint;
        private final String method;

        public String buildUrl(Object... args) {
            return baseUrl + String.format(endpoint.replaceAll("(\\{[^}]+})", "%s"), args);
        }
    }

    public City getById(Long id) {
        URI uri = URI.create(CitiesApi.GET_BY_ID.buildUrl(id));

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .method(CitiesApi.GET_BY_ID.method, HttpRequest.BodyPublishers.noBody())
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return futureResponse.thenApply(cityXmlDeserializer::deserialize).join();
    }

    public Cities getAllSortedPaginated(String sortFields, SortOrder sortOrder, Integer page, Integer size) {
        URI uri = URI.create(CitiesApi.GET_ALL_SORTED_PAGINATED.buildUrl(sortFields, sortOrder, page, size));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .method(CitiesApi.GET_ALL_SORTED_PAGINATED.method, HttpRequest.BodyPublishers.noBody())
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return futureResponse.thenApply(citiesListXmlDeserializer::deserialize).join();
    }

    public City putById(Long cityId, City city) throws JsonProcessingException {
        URI uri = URI.create(CitiesApi.PUT_BY_ID.buildUrl(cityId));

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .method(CitiesApi.PUT_BY_ID.method, HttpRequest.BodyPublishers.ofString(cityXmlDeserializer.getMapper().writeValueAsString(city)))
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return futureResponse.thenApply(cityXmlDeserializer::deserialize).join();
    }
}
