package com.watches.backend.controller;

import com.watches.backend.Dto.savedCard.CreateSavedCardDTO;
import com.watches.backend.Dto.savedCard.SavedCardDTO;
import com.watches.backend.Dto.savedCard.UpdateSavedCardDTO;
import com.watches.backend.mappers.SavedCardMapper;
import com.watches.backend.model.SavedCard;
import com.watches.backend.service.AuthService;
import com.watches.backend.service.SavedCardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@AllArgsConstructor
public class SavedCardController {
    private final SavedCardService savedCardService;
    private final AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('OWNER') || hasRole('ADMIN')")
    SavedCardDTO createCard(@Valid @RequestBody CreateSavedCardDTO dto) {
        String username = authService.getCurrentUserName();

        SavedCard card = savedCardService.createCard(username, dto).join();
        return SavedCardMapper.toDTO(card);
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasRole('OWNER') || hasRole('SEE_CARD')")
    SavedCardDTO getCardByCustomerID(@PathVariable Long id) {
        SavedCard card = savedCardService.getCardByCustomerID(id).join();
        return SavedCardMapper.toDTO(card);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('OWNER') || hasRole('ADMIN')")
    SavedCardDTO getMyCard() {
        String username = authService.getCurrentUserName();

        SavedCard card = savedCardService.getByUsername(username).join();
        return SavedCardMapper.toDTO(card);
    }

    @PutMapping
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('OWNER') || hasRole('ADMIN')")
    SavedCardDTO  updateCard(@RequestBody UpdateSavedCardDTO dto) {
        String username = authService.getCurrentUserName();

        SavedCard card = savedCardService.updateCard(username,dto).join();
        return SavedCardMapper.toDTO(card);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('OWNER') || hasRole('ADMIN')")
    void deleteCard(){
        String username = authService.getCurrentUserName();

        savedCardService.deleteCard(username);
    }
}
