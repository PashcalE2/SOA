package resource;

import entity.dto.Cities;
import entity.dto.SortOrder;
import entity.model.City;
import entity.model.Climate;
import exception.AppException;
import lombok.extern.slf4j.Slf4j;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.CityService;

import java.util.*;

@Path("/cities")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Slf4j
public class CityResource {
    private static final CityService cityService = new CityService();

    @GET
    @Path("/{filter-fields}/{filter-values}/{sort-fields}/{sort-order}/{page}/{size}")
    public Response get(
            @PathParam("filter-fields") String filterFields,
            @PathParam("filter-values") String filterValues,
            @PathParam("sort-fields") String sortFields,
            @PathParam("sort-order") SortOrder sortOrder,
            @PathParam("page") Integer page,
            @PathParam("size") Integer size
    ) throws AppException {
        List<City> citiesList = cityService.get(filterFields, filterValues, sortFields, sortOrder, page, size);
        return Response.ok().entity(new Cities(citiesList)).build();
    }

    @GET
    @Path("/all/{sort-fields}/{sort-order}/{page}/{size}")
    public Response get(
            @PathParam("sort-fields") String sortFields,
            @PathParam("sort-order") SortOrder sortOrder,
            @PathParam("page") Integer page,
            @PathParam("size") Integer size
    ) throws AppException {
        List<City> citiesList = cityService.get(sortFields, sortOrder, page, size);
        return Response.ok().entity(new Cities(citiesList)).build();
    }

    @POST
    public Response add(City city) throws AppException {
        city = cityService.add(city);
        return Response.status(Response.Status.CREATED).entity(city).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) throws AppException {
        return Response.ok().entity(cityService.findById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, City city) throws AppException {
        city = cityService.updateCity(id, city);
        log.info("Обновленный город: {}", city);
        return Response.ok().entity(city).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) throws AppException {
        cityService.deleteCity(id);
        return Response.ok().build();
    }

    @DELETE
    @Path("/delete-by-governor/{name}")
    public Response deleteByGovernor(@PathParam("name") String name) throws AppException {
        cityService.deleteByGovernor(name);
        return Response.ok().build();
    }

    @GET
    @Path("/group-by-id")
    public Response groupById() throws AppException {
        return Response.ok().entity(cityService.groupById()).build();
    }

    @GET
    @Path("/count-by-climate/{climate}")
    public Response countByClimate(@PathParam("climate") Climate climate) throws AppException {
        return Response.ok().entity(cityService.countByClimate(climate)).build();
    }
}
