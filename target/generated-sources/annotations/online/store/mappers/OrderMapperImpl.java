package online.store.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import online.store.dto.OrderDto;
import online.store.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-15T07:24:52+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public OrderDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setId( order.getId() );
        orderDto.setFirstName( order.getFirstName() );
        orderDto.setLastName( order.getLastName() );
        orderDto.setEmail( order.getEmail() );
        orderDto.setShippingAddress( order.getShippingAddress() );
        orderDto.setQuantity( order.getQuantity() );
        orderDto.setProduct( productMapper.toDto( order.getProduct() ) );

        return orderDto;
    }

    @Override
    public List<OrderDto> toDtoList(List<Order> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>( orders.size() );
        for ( Order order : orders ) {
            list.add( toDto( order ) );
        }

        return list;
    }
}
