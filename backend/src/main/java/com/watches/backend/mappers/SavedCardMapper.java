package com.watches.backend.mappers;

import com.watches.backend.Dto.SavedCardDto.CreateSavedCardDTO;
import com.watches.backend.Dto.SavedCardDto.SavedCardDTO;
import com.watches.backend.Dto.SavedCardDto.UpdateSavedCardDTO;
import com.watches.backend.model.Customer;
import com.watches.backend.model.SavedCard;

public class SavedCardMapper {

    public static SavedCardDTO toDTO(SavedCard card) {
        if (card == null) return null;

        return new SavedCardDTO(
                card.getId(),
                 card.getCardHolderName(),
                card.getCardNumber(),
                card.getCardType(),
                card.getCvv(),
                card.isDefaultCard(),
                card.getCustomer() != null ? card.getCustomer().getId() : null
        );
    }

    public static SavedCard fromCreateDTO(CreateSavedCardDTO dto, Customer customer) {
        if (dto == null) return null;

        return new SavedCard(
                dto.getCardHolderName(),
                dto.getCardNumber(),
                dto.getExpirationDate(),
                dto.getCvv(),
                dto.getBillingAddress(),
                dto.getPostalCode(),
                dto.getCardType(),
                dto.isDefaultCard(),
                customer
        );
    }

    public static void updateEntityFromDTO(UpdateSavedCardDTO dto, SavedCard card) {
        if (dto == null || card == null) return;

        card.setCardHolderName(dto.getCardHolderName());
        card.setCardNumber(dto.getCardNumber());
        card.setExpirationDate(dto.getExpirationDate());
        card.setCvv(dto.getCvv());
        card.setBillingAddress(dto.getBillingAddress());
        card.setPostalCode(dto.getPostalCode());
        card.setCardType(dto.getCardType());
        card.setDefaultCard(dto.isDefaultCard());
    }
}
