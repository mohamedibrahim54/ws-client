package wsclient.types;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Item }
     *
     * @return the new instance of {@link Item }
     */
    public Item createResult() {
        return new Item();
    }

    /**
     * Create an instance of {@link QueryRequest }
     *
     * @return the new instance of {@link QueryRequest }
     */
    public QueryRequest createQueryRequest() {
        return new QueryRequest();
    }

    /**
     * Create an instance of {@link OrderResponse }
     *
     * @return the new instance of {@link OrderResponse }
     */
    public OrderResponse createOrderResponse() {
        return new OrderResponse();
    }

}
