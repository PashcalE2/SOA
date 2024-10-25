package main.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.entity.dto.CitiesList;
import main.entity.dto.SortOrder;
import main.entity.model.City;
import main.exception.AppException;
import main.exception.AppRuntimeException;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;


@Slf4j
public class CityServiceAdapter {
    private final HttpClient client = HttpClient.newHttpClient();
    private final XmlMapper<City> cityMapper = new XmlMapper<>(City.class);
    private final XmlMapper<CitiesList> citiesListMapper = new XmlMapper<>(CitiesList.class);

    public CityServiceAdapter() {
        // TODO помогите
        System.setProperty("javax.net.ssl.trustStore", "/home/studs/s311817/servers/jetty-home-12.0.14/base/etc/wildfly.truststore");
    }

    @AllArgsConstructor
    public enum CitiesApi {
        GET_ALL_SORTED_PAGINATED("/all/{sort-fields}/{sort-order}/{page}/{size}"),
        GET_BY_ID("/{id}"),
        PUT_BY_ID("/{id}");

        // TODO помогите
        private final static String baseUrl = "http://localhost:22600/cities";
        private final String endpoint;

        public String buildUrl(Object... args) {
            return baseUrl + String.format(endpoint.replaceAll("(\\{[^}]+})", "%s"), args);
        }
    }

    public City getById(Long id) throws AppException {
        URI uri = URI.create(CitiesApi.GET_BY_ID.buildUrl(id));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try { // TODO помогите
            return futureResponse.thenApply(response -> {
                if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                    throw new AppRuntimeException(HttpStatus.NOT_FOUND, String.format("Нет города с таким ID: %s", id));
                }

                return cityMapper.deserialize(response.body());
            }).join();
        }
        catch (AppRuntimeException e) {
            throw new AppException(e.getStatus(), e.getMessage());
        }
        catch (RuntimeException e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public CitiesList getAllSortedPaginated(String sortFields, SortOrder sortOrder, Integer page, Integer size) {
        URI uri = URI.create(CitiesApi.GET_ALL_SORTED_PAGINATED.buildUrl(sortFields, sortOrder, page, size));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return futureResponse.thenApply(response -> citiesListMapper.deserialize(response.body())).join();
    }

    public void putById(Long cityId, City city) throws JsonProcessingException, AppException {
        URI uri = URI.create(CitiesApi.PUT_BY_ID.buildUrl(cityId));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                // TODO помогите
                .header("Content-Type", "application/xml")
                .PUT(HttpRequest.BodyPublishers.ofString(cityMapper.serialize(city)))
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            futureResponse.thenApply(response -> {
                if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                    throw new AppRuntimeException(HttpStatus.NOT_FOUND, String.format("Нет города с таким ID: %s", cityId));
                }

                if (response.statusCode() == HttpStatus.BAD_REQUEST.value()) {
                    throw new AppRuntimeException(HttpStatus.BAD_REQUEST, "Плохой запрос");
                }

                //return cityMapper.deserialize(response.body());
                return null;
            }).join();
        }
        catch (AppRuntimeException e) {
            throw new AppException(e.getStatus(), e.getMessage());
        }
        catch (RuntimeException e) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
