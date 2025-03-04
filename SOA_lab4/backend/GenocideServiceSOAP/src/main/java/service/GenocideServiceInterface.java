package service;

import exception.AppException;
import jakarta.jws.*;
import jakarta.jws.soap.SOAPBinding;


@WebService(
        name = "GenocideService",
        targetNamespace = "http://genocide.lab4.soa/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface GenocideServiceInterface {
    @WebMethod(action = "http://genocide.lab4.soa/" + "GenocideService/countPopulation")
    @WebResult(name = "count", targetNamespace = "http://genocide.lab4.soa/")
    Long countPopulation(@WebParam(name = "id1") Long id1, @WebParam(name = "id2") Long id2, @WebParam(name = "id3") Long id3) throws AppException;

    @WebMethod(action = "http://genocide.lab4.soa/" + "GenocideService/moveToPoorest")
    void moveToPoorest(@WebParam(name = "cityId") Long cityId) throws AppException;
}
