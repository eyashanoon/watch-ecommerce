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
                card.isDefault(),
                card.getCustomer() != null ? card.getCustomer().getId() : null
        );
    }

    public static SavedCard fromCreateDTO(CreateSavedCardDTO dto, Customer customer) {
        if (dto == null) return null;

        SavedCard card = new SavedCard();
         card.setCardHolderName(dto.getCardHolderName());
        card.setCardNumber(dto.getCardNumber());
        card.setExpiryDate(dto.getExpiryDate());
        card.setCardType(dto.getCardType());
        card.setCvv(dto.getCvv());
        card.setDefault(dto.isDefault());
        card.setCustomer(customer);
        return card;
    }

    public static void updateEntityFromDTO(UpdateSavedCardDTO dto, SavedCard card) {
        if (dto == null || card == null) return;

        card.setCardHolderName(dto.getCardHolderName());
        card.setCardNumber(dto.getCardNumber());
        card.setExpiryDate(dto.getExpiryDate());
        card.setCardType(dto.getCardType());
        card.setDefault(dto.isDefault());
        // Do not change customer or ID in an update
    }
}
