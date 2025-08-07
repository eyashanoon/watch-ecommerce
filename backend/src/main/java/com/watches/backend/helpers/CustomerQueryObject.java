package com.watches.backend.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerQueryObject {
    private String username = null;
    private String phone = null;
    private String email = null;
    private Integer pageNumber = 1;
    private Integer pageSize = 20;
}
