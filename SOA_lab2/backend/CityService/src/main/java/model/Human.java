package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "human")
@XmlAccessorType(XmlAccessType.FIELD)
public class Human implements Comparable<Human> {
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement
    private long age; //Значение поля должно быть больше 0
    @XmlElement
    private float height; //Значение поля должно быть больше 0

    @Override
    public int compareTo(Human o) {
        if (this.equals(o)) {
            return 0;
        }

        return name.compareTo(o.name);
    }
}
