package de.uniba.dsg.jaxrs.model.api;

import de.uniba.dsg.jaxrs.dto.OrderDTO;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"pagination", "orders", "href"})
public class PaginatedOrders {
    private Pagination pagination;
    private List<OrderDTO> orders;

    private URI href;

    public PaginatedOrders(){

    }

    public PaginatedOrders(final Pagination pagination, final List<OrderDTO> orders, final URI href) {
        this.pagination = pagination;
        this.orders = orders;
        this.href = href;
    }

    public Pagination getPagination() {
        return this.pagination;
    }

    public void setPagination(final Pagination pagination) {
        this.pagination = pagination;
    }

    public List<OrderDTO> getOrders() {
        return this.orders;
    }

    public void setOrders(final List<OrderDTO> orders) {
        this.orders = orders;
    }

    public URI getHref() {
        return this.href;
    }

    public void setHref(final URI href) {
        this.href = href;
    }
}
