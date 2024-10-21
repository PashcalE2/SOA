package resource;

import exception.NoSuchFieldNameException;
import lombok.extern.slf4j.Slf4j;
import model.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.ZonedDateTime;
import java.util.*;

@Path("/cities")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Slf4j
public class CityResource {
    private static final CityRepository cityRepository = new CityRepository();

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
        Map<Long, City> cities = cityRepository.getCities();
        log.info("cities size: " + cities.size());

        List<City> citiesList = new ArrayList<>(cities.values());

        log.info("Filter fields: {}", filterFields);

        {
            String[] filterFieldsArray = filterFields.split(",");
            String[] filterValuesArray = filterValues.split(",");

            if (filterFieldsArray.length != filterValuesArray.length) {
                return Response.status(Response.Status.BAD_REQUEST).build(); // TODO: status
            }

            citiesList.removeIf((city) -> {
                for (int i = 0; i < filterFieldsArray.length; i++) {
                    try {
                        if (!city.fieldEquals(filterFieldsArray[i], filterValuesArray[i])) {
                            log.info("Dropped city with id: {}", city.getId());
                            return true;
                        }
                    }
                    catch (Exception ignored) {}
                }
                return false;
            });

            log.info("Cities count after filter: {}", citiesList.size());
        }

        log.info("Sort fields: {}", sortFields);

        {
            citiesList.sort((city1, city2) -> {
                for (String field : sortFields.split(",")) {
                    try {
                        int compare = city1.compareBy(city2, field);
                        if (compare != 0) {
                            return sortOrder.equalsIgnoreCase("descending") ? -compare : compare;
                        }
                    }
                    catch (Exception ignored) {}
                }
                return 0;
            });
        }

        int citiesCount = citiesList.size();
        int start = Math.min(page * size, citiesCount);
        int end = Math.min((page + 1) * size, citiesCount);
        citiesList = citiesList.subList(start, end);

        log.info("Result count: {}", citiesList.size());

        return Response.ok().entity(new CitiesList(citiesList)).build();
    }

    @POST
    public Response add(City city) {
        if (!city.isValidRequest()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        city.setCreationDate(ZonedDateTime.now());
        cityRepository.addCity(city);

        log.info("Added city: {}", city);

        return Response.status(Response.Status.CREATED).entity(city).build();
    }
}
