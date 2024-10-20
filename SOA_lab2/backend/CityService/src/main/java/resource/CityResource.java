package resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import model.City;
import model.CityRepository;
import model.Coordinates;
import model.Human;

import java.time.ZonedDateTime;
import java.util.*;

@Path("/cities")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Slf4j
public class CityResource {
    private final CityRepository cityRepository = new CityRepository();

    @GET
    @Path("{filter-fields}/{filter-values}/{sort-fields}/{sort-order}/{page}/{size}")
    public Response get(
            @PathParam("filter-fields") String filterFields,
            @PathParam("filter-values") String filterValues,
            @PathParam("sort-fields") String sortFields,
            @PathParam("sort-order") String sortOrder,
            @PathParam("page") Integer page,
            @PathParam("size") Integer size
    ) {
        List<City> citiesList = new ArrayList<>(cityRepository.getCities());

        log.info("Filter fields: {}", filterFields);

        if (filterFields != null && !filterFields.isEmpty() && sortFields != null && !sortFields.isEmpty()) {
            String[] filterFieldsArray = filterFields.split(",");
            String[] filterValuesArray = filterValues.split(",");

            if (filterFieldsArray.length != filterValuesArray.length) {
                return Response.status(Response.Status.BAD_REQUEST).build(); // TODO: status
            }

            citiesList.removeIf((city) -> {
                for (int i = 0; i < filterFieldsArray.length; i++) {
                    if (!city.fieldEquals(filterFieldsArray[i], filterValuesArray[i])) {
                        return true;
                    }
                }
                return false;
            });
        }

        log.info("Sort fields: {}", sortFields);

        if (sortFields != null && !sortFields.isEmpty()) {
            citiesList.sort((city1, city2) -> {
                for (String field : sortFields.split(",")) {
                    int compare = city1.compareBy(city2, field);
                    if (compare != 0) {
                        return sortOrder.equalsIgnoreCase("descending") ? -compare : compare;
                    }
                }
                return 0;
            });
        }

        int citiesCount = citiesList.size();
        int start = Math.min(page * size, citiesCount);
        int end = Math.min((page + 1) * size, citiesCount);
        citiesList = citiesList.subList(start, end);

        log.info("Result count: {}", citiesList.size());

        return Response.ok().entity(citiesList).build();
    }

    @GET
    @Path("/test")
    public Response getCoordinates() {
        return Response.ok().entity(new Coordinates(1f, 2)).build();
    }

    @POST
    @Path("/test/human")
    public Response getCoordinates(Human human) {
        human.setName(human.getName() + " " + human.getAge());
        return Response.ok().entity(human).build();
    }

    @POST
    @Path("/test/coordinates")
    public Response getCoordinates(Coordinates coordinates) {
        coordinates.setX(coordinates.getX() * 2);
        coordinates.setY(coordinates.getY() * 2);
        return Response.ok().entity(coordinates).build();
    }

    @POST
    public Response add(City city) {
        if (!city.isValid()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        city.setId(cityRepository.generateUniqueId());
        city.setCreationDate(ZonedDateTime.now());
        cityRepository.addCity(city);

        log.info("Added city: {}", city);

        return Response.status(Response.Status.CREATED).entity(city).build();
    }
}
