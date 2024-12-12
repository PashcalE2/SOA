package resource;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import entity.dto.Count;
import exception.AppException;
import service.GenocideServiceInterface;

@Path("/genocide")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
@Slf4j
public class GenocideResource {
    @EJB
    private GenocideServiceInterface genocideService;

    @GET
    @Path("/count/{id1}/{id2}/{id3}")
    public Response countPopulation(
            @PathParam("id1") Long id1,
            @PathParam("id2") Long id2,
            @PathParam("id3") Long id3
    ) throws AppException {
        log.info("Запрос /count/{}/{}/{}", id1, id2, id3);
        return Response.ok().entity(new Count(genocideService.countPopulation(id1, id2, id3))).build();
    }

    @POST
    @Path("/move-to-poorest/{cityId}")
    public Response moveToPoorest(
            @PathParam("cityId") Long cityId
    ) throws AppException {
        log.info("Запрос /move-to-poorest/{}", cityId);
        genocideService.moveToPoorest(cityId);
        return Response.ok().build();
    }
}
