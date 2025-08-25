 package com.watches.backend.Dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private List<String> roles;

    private Long cartId;
    private Long wishlistId;
    private List<Long> orderIds;
    private Long savedCardId;
}
