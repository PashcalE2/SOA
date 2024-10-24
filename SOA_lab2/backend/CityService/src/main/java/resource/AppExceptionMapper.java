package resource;

import exception.AppException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import entity.dto.Error;

@Slf4j
public class AppExceptionMapper implements ExceptionMapper<AppException> {
    @Override
    public Response toResponse(AppException e) {
        log.warn("Вызвано исключение {}:\n\tstatus={}\n\tmessage={}", e.getExtendedClass(), e.getStatus(), e.getMessage());

        return Response
                .status(e.getStatus())
                .entity(new Error(e.getMessage()))
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}
