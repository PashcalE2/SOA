package entity.dto;

import entity.model.City;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "cities")
public class Cities implements Serializable {
    private List<City> city;

    public Cities(List<City> city) {
        this.city = new ArrayList<>(city);
    }
}
