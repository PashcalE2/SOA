package entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement(localName = "city")
public class City implements Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long area; //Значение поля должно быть больше 0
    private int population; //Значение поля должно быть больше 0
    private Integer metersAboveSeaLevel;
    private LocalDate establishmentDate;
    private long telephoneCode; //Значение поля должно быть больше 0, Максимальное значение поля: 100000
    private Climate climate; //Поле может быть null
    private Human governor; //Поле может быть null

    @JsonIgnore
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

    @JsonIgnore
    public boolean isValidRequest() {
        return
                name != null && !name.isEmpty()
                && coordinates != null
                && area > 0
                && population > 0
                && telephoneCode > 0 && telephoneCode <= 100000
                && climate != null
                && governor != null;
    }

    @JsonIgnore
    public int compareBy(City o, String field) {
        return switch (field) {
            case "id" -> id.compareTo(o.id);
            case "name" -> name.compareTo(o.name);
            case "coordinates" -> coordinates.compareTo(o.coordinates);
            case "creationDate" -> creationDate.compareTo(o.creationDate);
            case "area" -> Long.compare(area, o.area);
            case "population" -> Integer.compare(population, o.population);
            case "metersAboveSeaLevel" -> metersAboveSeaLevel.compareTo(o.metersAboveSeaLevel);
            case "establishmentDate" -> establishmentDate.compareTo(o.establishmentDate);
            case "telephoneCode" -> Long.compare(telephoneCode, o.telephoneCode);
            case "climate" -> climate.compareTo(o.climate);
            case "governor" -> governor.compareTo(o.governor);
            default ->
                    throw new IllegalArgumentException(String.format("В классе %s нет поля %s", City.class.getName(), field));
        };
    }

    @JsonIgnore
    public boolean fieldEquals(String field, String o) {
        return switch (field) {
            case "id" -> id.equals(Long.parseLong(o));
            case "name" -> name.equals(o);
            case "creationDate" -> creationDate.equals(ZonedDateTime.parse(o));
            case "area" -> area == Long.parseLong(o);
            case "population" -> population == Integer.parseInt(o);
            case "metersAboveSeaLevel" -> metersAboveSeaLevel.equals(Integer.parseInt(o));
            case "establishmentDate" -> establishmentDate.equals(LocalDate.parse(o));
            case "telephoneCode" -> telephoneCode == Long.parseLong(o);
            case "climate" -> climate.equals(Climate.valueOf(o));
            case "coordinates", "governor" ->
                    throw new IllegalArgumentException(String.format("Нельзя сравнивать по полю %s", field));
            default ->
                    throw new IllegalArgumentException(String.format("В классе %s нет поля %s", City.class.getName(), field));
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id.equals(city.id);
    }

    @Override
    public String toString() {
        return String.format("id: %s\nname: %s\ncoordinates: %s\ncreationDate: %s\narea: %s\npopulation: %s\n" +
                "metersAboveSeaLevel: %s\nestablishmentDate: %s\ntelephoneCode: %s\nclimate: %s\ngovernor: %s",
                id, name, coordinates, creationDate, area, population, metersAboveSeaLevel, establishmentDate, telephoneCode, climate, governor);
    }
}