package main.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

@Slf4j
@Getter
public class XmlDeserializer<T> {
    private final XmlMapper mapper = new XmlMapper();
    private final Class<T> clazz;

    public XmlDeserializer(Class<T> clazz) {
        this.clazz = clazz;
        mapper.registerModule(new JavaTimeModule());
    }

    public T deserialize(HttpResponse<String> response) {
        String body = response.body();

        if (body.startsWith("<?xml")) {
            body = body.substring(body.indexOf(">") + 1);
        }

        if (response.statusCode() == HttpStatus.OK.value()) {
            return deserialize(body);
        }

        log.error("Request failed with status code: {}", response.statusCode());
        return null;
    }

    public T deserialize(String body) {
        log.info(body);

        try {
            T obj = mapper.readValue(body, clazz);
            log.info(obj.toString(), obj.getClass());
            return obj;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
