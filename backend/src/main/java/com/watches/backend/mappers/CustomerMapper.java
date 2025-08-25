package com.watches.backend.mappers;

import com.watches.backend.Dto.customer.CreateCustomerDTO;
import com.watches.backend.Dto.customer.CustomerDTO;
import com.watches.backend.enums.Role;
import com.watches.backend.model.Customer;
import com.watches.backend.model.Order;

import java.util.List;
import java.util.Set;

public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) return null;

        List<Long> orderIDs = customer.getOrders() == null ? null :
                customer.getOrders().stream().map(Order::getId).toList();

        Long savedCardID = customer.getSavedCard() == null ? null :
                customer.getSavedCard().getId();

        return new CustomerDTO(
                customer.getId(),
                customer.getUsername(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getRoles().stream().map(Enum::name).toList(),
                customer.getCart() == null ? null : customer.getCart().getId(),
                customer.getWishlist() == null ? null : customer.getWishlist().getId(),
                orderIDs,
                savedCardID
        );
    }

    public static Customer fromCreateDTO(CreateCustomerDTO dto) {
        if (dto == null) return null;

        Customer customer = new Customer();
        customer.setUsername(dto.getUsername());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setPassword(dto.getPassword());
        customer.setRoles(Set.of(Role.CUSTOMER));

        return customer;
    }
}
