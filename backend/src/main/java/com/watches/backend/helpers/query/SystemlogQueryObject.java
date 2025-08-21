package com.watches.backend.helpers.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemlogQueryObject implements Query {
    String user = null;
    String requestMethod = null;
    String responseStatus = null;
    Integer page = 1;
    Integer pageSize = 20;
}
