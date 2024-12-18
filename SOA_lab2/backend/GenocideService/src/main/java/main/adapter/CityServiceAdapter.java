package main.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.AppConfiguration;
import main.entity.dto.CitiesList;
import main.entity.dto.SortOrder;
import main.entity.model.City;
import main.exception.AppException;
import main.exception.AppRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;


@Component
@RequiredArgsConstructor
@Slf4j
public class CityServiceAdapter {
    private final AppConfiguration appConfiguration;
    private final HttpClient client = HttpClient.newHttpClient();
    private final XmlMapper<City> cityMapper = new XmlMapper<>(City.class);
    private final XmlMapper<CitiesList> citiesListMapper = new XmlMapper<>(CitiesList.class);

    public City getById(Long id) throws AppException {
        URI uri = URI.create(CitiesApi.GET_BY_ID.buildUrl(appConfiguration.baseEndpoint, id));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        City city = null;

        try {
            city = futureResponse.thenApply(response -> {
                if (response.statusCode() == HttpStatus.OK.value()) {
                    return cityMapper.deserialize(response.body());
                }

                if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                    throw new AppRuntimeException(HttpStatus.NOT_FOUND, String.format("Нет города с таким ID: %s", id));
                }

                throw new AppRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Не обработан статус ответа от основного сервера");
            }).join();
        }
        catch (CompletionException e) {
            catchCompletionException(e);
        }

        return city;
    }

    public CitiesList getAllSortedPaginated(String sortFields, SortOrder sortOrder, Integer page, Integer size) {
        URI uri = URI.create(CitiesApi.GET_ALL_SORTED_PAGINATED.buildUrl(appConfiguration.baseEndpoint, sortFields, sortOrder, page, size));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return futureResponse.thenApply(response -> citiesListMapper.deserialize(response.body())).join();
    }

    public City putById(Long cityId, City city) throws AppException {
        URI uri = URI.create(CitiesApi.PUT_BY_ID.buildUrl(appConfiguration.baseEndpoint, cityId));
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .header("Content-Type", "application/xml")
                .PUT(HttpRequest.BodyPublishers.ofString(cityMapper.serialize(city)))
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        City updatedCity = null;

        try {
            updatedCity = futureResponse.thenApply(response -> {
                if (response.statusCode() == HttpStatus.OK.value()) {
                    return cityMapper.deserialize(response.body());
                }

                if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                    throw new AppRuntimeException(HttpStatus.NOT_FOUND, String.format("Нет города с таким ID: %s", cityId));
                }

                if (response.statusCode() == HttpStatus.BAD_REQUEST.value()) {
                    throw new AppRuntimeException(HttpStatus.BAD_REQUEST, "Плохой запрос");
                }

                throw new AppRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Не обработан статус ответа от основного сервера");
            }).join();
        }
        catch (CompletionException e) {
            catchCompletionException(e);
        }

        return updatedCity;
    }

    private void catchCompletionException(CompletionException e) throws AppException {
        try {
            throw e.getCause();
        }
        catch (AppRuntimeException appRuntimeException) {
            throw new AppException(appRuntimeException.getStatus(), appRuntimeException.getMessage());
        }
        catch (ConnectException connectException) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Нет соединения с основным сервисом");
        }
        catch(Throwable impossible) {
            log.error(impossible.getMessage(), impossible);
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Необработанное исключение");
        }
    }
}
