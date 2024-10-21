package model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
public class Human implements Comparable<Human> {
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
