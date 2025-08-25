package com.watches.backend.mappers;

import com.watches.backend.Dto.featuresDto.ImageDto;
import com.watches.backend.model.Image;

public class FeaturesMapper {

    public static ImageDto imageToDto(Image image){
        return new ImageDto(
                image.getProduct().getId(),
                image.getFilename(),
                image.getData(),
                image.getId()
        );
    }

}
