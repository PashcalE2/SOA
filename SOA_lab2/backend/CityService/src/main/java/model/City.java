package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "city")
public class City {
    @XmlElement(name = "id")
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XmlElement(name = "name")
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement(name = "coordinates")
    private Coordinates coordinates; //Поле не может быть null
    @XmlElement(name = "creationDate")
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @XmlElement(name = "area")
    private long area; //Значение поля должно быть больше 0
    @XmlElement(name = "population")
    private int population; //Значение поля должно быть больше 0
    @XmlElement(name = "metersAboveSeaLevel")
    private Integer metersAboveSeaLevel;
    @XmlElement(name = "establishmentDate")
    private java.time.LocalDate establishmentDate;
    @XmlElement(name = "telephoneCode")
    private long telephoneCode; //Значение поля должно быть больше 0, Максимальное значение поля: 100000
    @XmlElement(name = "climate")
    private Climate climate; //Поле может быть null
    @XmlElement(name = "governor")
    private Human governor; //Поле может быть null

    public boolean isValid() {
        return
                id != null && id > 0
                && name != null && !name.isEmpty()
                && coordinates != null
                && creationDate != null
                && area > 0
                && population > 0
                && telephoneCode > 0 && telephoneCode <= 100000
                && climate != null
                && governor != null;
    }

    public int compareBy(City o, String field) {
        switch (field) {
            case "name": return name.compareTo(o.name);
            case "coordinates": return coordinates.compareTo(o.coordinates);
            case "creationDate": return creationDate.compareTo(o.creationDate);
            case "area": return Long.compare(area, o.area);
            case "population": return Integer.compare(population, o.population);
            case "metersAboveSeaLevel": return metersAboveSeaLevel.compareTo(o.metersAboveSeaLevel);
            case "establishmentDate": return establishmentDate.compareTo(o.establishmentDate);
            case "telephoneCode": return Long.compare(telephoneCode, o.telephoneCode);
            case "climate": return climate.compareTo(o.climate);
            case "governor": return governor.compareTo(o.governor);
            default: return id.compareTo(o.id);
        }
    }

    public boolean fieldEquals(String field, String o) {
        switch (field) {
            case "name": return name.equals(o);
            // case "coordinates": return false;
            case "creationDate": return creationDate.equals(ZonedDateTime.parse(o));
            case "area": return area == Long.parseLong(o);
            case "population": return population == Integer.parseInt(o);
            case "metersAboveSeaLevel": return metersAboveSeaLevel.equals(Integer.parseInt(o));
            case "establishmentDate": return establishmentDate.equals(LocalDate.parse(o));
            case "telephoneCode": return telephoneCode == Long.parseLong(o);
            case "climate": return climate.equals(Climate.valueOf(o));
            // case "governor": return false;
            default: return id.equals(Long.parseLong(o));
        }
    }
}
