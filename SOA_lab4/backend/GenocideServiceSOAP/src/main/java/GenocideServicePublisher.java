import jakarta.xml.ws.Endpoint;
import lombok.extern.slf4j.Slf4j;
import service.GenocideService;


// зачем оно надо если оно не запускается?
@Slf4j
public class GenocideServicePublisher {
    public static void main(String[] args) {
        GenocideService service = new GenocideService();
        String url = "https://localhost:22601/GenocideService";
        Endpoint.publish(url, service);
        log.info("Service running at: {}?wsdl", url);
    }
}