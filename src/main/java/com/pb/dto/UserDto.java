package com.pb.dto;

import com.pb.model.Order;
import com.pb.model.Role;
import com.pb.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class UserDto {
    public UserDto(Long id, String firstname, String lastname, String email, String password, Role role, List<OrderDto> orders){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.orders.addAll(orders);
    }
    public UserDto(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        if(user.getOrders().isEmpty()) return;
        for(Order order : user.getOrders()) {
            OrderDto orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setIsActive(order.getIsActive());
            orders.add(orderDto);
        }
    }
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private List<OrderDto> orders = new ArrayList<>();
}
