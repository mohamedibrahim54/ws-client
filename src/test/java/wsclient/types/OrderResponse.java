package wsclient.types;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "id",
        "item"
})
@XmlRootElement(name = "order")
public class OrderResponse {

    @XmlElement(required = true)
    protected String id;

    @XmlElement(required = true)
    protected List<Item> item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItem() {
        if (item == null) {
            item = new ArrayList<>();
        }
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
