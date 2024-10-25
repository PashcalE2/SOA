package main.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement(localName = "city")
public class City {
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
        switch (field) {
            case "id": return id.compareTo(o.id);
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

            default: throw new IllegalArgumentException(String.format("В классе %s нет поля %s", City.class.getName(), field));
        }
    }

    @JsonIgnore
    public boolean fieldEquals(String field, String o) {
        switch (field) {
            case "id": return id.equals(Long.parseLong(o));
            case "name": return name.equals(o);
            case "creationDate": return creationDate.equals(ZonedDateTime.parse(o));
            case "area": return area == Long.parseLong(o);
            case "population": return population == Integer.parseInt(o);
            case "metersAboveSeaLevel": return metersAboveSeaLevel.equals(Integer.parseInt(o));
            case "establishmentDate": return establishmentDate.equals(LocalDate.parse(o));
            case "telephoneCode": return telephoneCode == Long.parseLong(o);
            case "climate": return climate.equals(Climate.valueOf(o));

            case "coordinates":
            case "governor":
                throw new IllegalArgumentException(String.format("Нельзя сравнивать по полю %s", field));

            default: throw new IllegalArgumentException(String.format("В классе %s нет поля %s", City.class.getName(), field));
        }
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
