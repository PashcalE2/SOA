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
@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates implements Comparable<Coordinates> {
    @XmlElement(name = "x")
    private Float x; //Поле не может быть null
    @XmlElement(name = "y")
    private double y;

    @Override
    public int compareTo(Coordinates o) {
        if (this.x.equals(o.x) && this.y == o.y) {
            return 0;
        }

        return Double.compare(Math.sqrt(x * x + y * y), Math.sqrt(o.getX() * o.getX() + o.getY() * o.getY()));
    }

    @Override
    public String toString() {
        return "Coordinates [x=" + x + ", y=" + y + "]";
    }
}
