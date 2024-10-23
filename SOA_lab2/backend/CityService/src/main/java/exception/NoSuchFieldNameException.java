package exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class NoSuchFieldNameException extends AppException {
    private final String className;
    private final String fieldName;

    public NoSuchFieldNameException(String className, String fieldName) {
        super(Response.Status.BAD_REQUEST, String.format("В классе %s нет поля %s", className, fieldName), NoSuchFieldNameException.class.getName());
        this.className = className;
        this.fieldName = fieldName;
    }
}
