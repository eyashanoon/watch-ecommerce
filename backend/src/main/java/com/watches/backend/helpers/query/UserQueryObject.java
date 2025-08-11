package com.watches.backend.helpers.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryObject implements Query {
    private String username = null;
    private String phone = null;
    private String email = null;
    private boolean deleted = false;
    private Integer pageNumber = 1;
    private Integer pageSize = 20;
}
