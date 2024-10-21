package exception;

import lombok.Getter;

@Getter
public class NoSuchFieldNameException extends Exception {
    private final String className;
    private final String fieldName;

    public NoSuchFieldNameException(String className, String fieldName) {
        super(String.format("В классе %s нет поля %s", className, fieldName));
        this.className = className;
        this.fieldName = fieldName;
    }
}
