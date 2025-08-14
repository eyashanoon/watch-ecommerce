package com.watches.backend.Dto.featuresDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private Long productId;
    private String imageFileName;
    private byte[] data;

}
