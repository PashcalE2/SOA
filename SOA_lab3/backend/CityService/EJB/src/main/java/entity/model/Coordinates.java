package entity.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates implements Comparable<Coordinates> {
    private Float x; //Поле не может быть null
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
