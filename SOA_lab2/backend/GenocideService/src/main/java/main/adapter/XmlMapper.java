package main.adapter;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
public class XmlMapper<T> {
    private final com.fasterxml.jackson.dataformat.xml.XmlMapper mapper = new com.fasterxml.jackson.dataformat.xml.XmlMapper();
    private final Class<T> clazz;

    public XmlMapper(Class<T> clazz) {
        mapper.registerModule(new JavaTimeModule());
        this.clazz = clazz;
    }

    public T deserialize(String body) {
        log.info("ResponseBody:\n{}\n", body);

        if (body.startsWith("<?xml")) {
            body = body.substring(body.indexOf(">") + 1);
        }

        log.info(body);

        try {
            T obj = mapper.readValue(body, clazz);
            log.info(obj.toString(), obj.getClass());
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    public String serialize(T obj) {
        try {
            // log.info(result);
            return xmlHeader + mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
