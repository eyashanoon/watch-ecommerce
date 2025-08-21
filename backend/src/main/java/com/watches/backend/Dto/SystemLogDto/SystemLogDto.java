package com.watches.backend.Dto.SystemLogDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SystemLogDto {
    String User;
    String request;
    String response;
}
