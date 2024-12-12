package main.entity.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement(localName = "human")
public class Human implements Comparable<Human>, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long age; //Значение поля должно быть больше 0
    private float height; //Значение поля должно быть больше 0

    @Override
    public int compareTo(Human o) {
        if (this.equals(o)) {
            return 0;
        }

        return name.compareTo(o.name);
    }
}
