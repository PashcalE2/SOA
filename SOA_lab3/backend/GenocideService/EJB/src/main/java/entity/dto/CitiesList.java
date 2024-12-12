package entity.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import entity.model.City;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement(localName = "cities")
@XmlRootElement(name = "cities")
public class CitiesList implements Serializable {
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<City> city;

    public CitiesList(List<City> city) {
        this.city = new ArrayList<>(city);
    }
}