package de.uniba.dsg.jaxrs.dto;


import de.uniba.dsg.jaxrs.model.Bottle;
import de.uniba.dsg.jaxrs.model.Crate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beverages")
@XmlType(propOrder = {"bottles", "crates"})
public class BeveragesDTO {
    private List<BottleDTO> bottles;
    private List<CrateDTO> crates;

    public List<BottleDTO> getBottles() {
        return bottles;
    }

    public void setBottles(List<BottleDTO> bottles) {
        this.bottles = bottles;
    }

    public List<CrateDTO> getCrates() {
        return crates;
    }

    public void setCrates(List<CrateDTO> crates) {
        this.crates = crates;
    }

    public BeveragesDTO() {}

    public BeveragesDTO(List<Bottle> bottles, List<Crate> crates) {
        this.bottles = BottleDTO.marshall(bottles, null);
        this.crates = CrateDTO.marshall(crates, null);
    }




}
