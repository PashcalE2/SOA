package resource;

import entity.dto.Cities;
import entity.dto.SortOrder;
import entity.model.City;
import entity.model.Climate;
import exception.AppException;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import service.CityServiceInterface;

@Path("/cities")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Slf4j
public class CityResource {
    @EJB
    private CityServiceInterface cityService;

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
        Cities citiesList = cityService.get(filterFields, filterValues, sortFields, sortOrder, page, size);
        return Response.ok().entity(citiesList).build();
    }

    @GET
    @Path("/all/{sort-fields}/{sort-order}/{page}/{size}")
    public Response get(
            @PathParam("sort-fields") String sortFields,
            @PathParam("sort-order") SortOrder sortOrder,
            @PathParam("page") Integer page,
            @PathParam("size") Integer size
    ) throws AppException {
        log.info("get cities with params: {}, {}, {}, {}", sortFields, sortOrder, page, size);
        Cities citiesList = cityService.get(sortFields, sortOrder, page, size);
        log.info("got {} cities", citiesList.getCity().size());
        return Response.ok().entity(citiesList).build();
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
        return Response.noContent().build();
    }

    @DELETE
    @Path("/delete-by-governor/{name}")
    public Response deleteByGovernor(@PathParam("name") String name) throws AppException {
        cityService.deleteByGovernor(name);
        return Response.noContent().build();
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
