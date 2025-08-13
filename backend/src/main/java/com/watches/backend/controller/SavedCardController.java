package com.watches.backend.controller;

import com.watches.backend.Dto.SavedCardDto.CreateSavedCardDTO;
import com.watches.backend.Dto.SavedCardDto.SavedCardDTO;
import com.watches.backend.Dto.SavedCardDto.UpdateSavedCardDTO;
import com.watches.backend.mappers.SavedCardMapper;
import com.watches.backend.model.SavedCard;
import com.watches.backend.service.SavedCardService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@AllArgsConstructor
public class SavedCardController {
    private final SavedCardService savedCardService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('OWNER') || hasRole('ADMIN')")
    SavedCardDTO createCard(@Valid @RequestBody CreateSavedCardDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        SavedCard card = savedCardService.getByUsername(username).join();
        return SavedCardMapper.toDTO(card);
    }

    @PutMapping
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('OWNER') || hasRole('ADMIN')")
    SavedCardDTO  updateCard(@RequestBody UpdateSavedCardDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        SavedCard card = savedCardService.updateCard(username,dto).join();
        return SavedCardMapper.toDTO(card);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('OWNER') || hasRole('ADMIN')")
    void deleteCard(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        savedCardService.deleteCard(username);
    }
}
