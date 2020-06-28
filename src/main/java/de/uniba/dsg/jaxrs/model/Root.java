package de.uniba.dsg.jaxrs.model;
import de.uniba.dsg.jaxrs.dto.BeverageDTO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "root")
public class Root {

    private List<BeverageDTO> beverage;

    public List<BeverageDTO> getBeverages() {
        return beverage;
    }

    @XmlElementWrapper(name = "beverage")
    @XmlElements({
            @XmlElement(name = "bottle", type = Bottle.class),
            @XmlElement(name = "crate", type = Crate.class)
    })
    public void setBeverages(List<BeverageDTO> beverage) {
        this.beverage = beverage;
    }
}