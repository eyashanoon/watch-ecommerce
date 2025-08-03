package com.watches.backend.mappers;

import com.watches.backend.Dto.CreateCustomerDTO;
import com.watches.backend.Dto.CustomerDTO;
import com.watches.backend.Dto.UpdateCustomerDTO;
import com.watches.backend.enums.Role;
import com.watches.backend.model.Customer;
import com.watches.backend.model.Order;
import com.watches.backend.model.SavedCard;

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
                customer.getCart() == null ? null : customer.getCart().getId(),
                customer.getWishlist() == null ? null : customer.getWishlist().getId(),
                orderIDs,
                savedCardID,
                customer.getRoles().stream().map(role -> role.name()).toList()
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

    // For updating existing customer object
    public static void updateCustomerFromDTO(UpdateCustomerDTO dto, Customer customer) {
        if (dto == null || customer == null) return;

        customer.setUsername(dto.getUsername());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
    }

    // Optional: Only needed if your frontend sends empty forms pre-filled
    public static UpdateCustomerDTO toUpdateDTO(Customer customer) {
        if (customer == null) return null;
        return new UpdateCustomerDTO(
                customer.getUsername(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

    // Optional: Not needed unless you need to send a prefilled "create" form
    public static CreateCustomerDTO toCreateDTO(Customer customer) {
        if (customer == null) return null;
        return new CreateCustomerDTO(
                customer.getUsername(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getPhone()
        );
    }
}
