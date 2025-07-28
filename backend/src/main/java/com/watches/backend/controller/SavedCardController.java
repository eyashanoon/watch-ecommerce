package com.watches.backend.controller;

import com.watches.backend.Dto.SavedCardDto.CreateSavedCardDTO;
import com.watches.backend.Dto.SavedCardDto.SavedCardDTO;
import com.watches.backend.Dto.SavedCardDto.UpdateSavedCardDTO;
import com.watches.backend.model.SavedCard;
import com.watches.backend.service.SavedCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
 @RequestMapping("/api/saves-card")
public class SavedCardController {
    private final SavedCardService savedCardService;
    public SavedCardController(SavedCardService savedCardService) {
        this.savedCardService = savedCardService;
    }
    @PostMapping
    public ResponseEntity<SavedCardDTO> createCard(@RequestBody CreateSavedCardDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCardService.createCard(dto));
    }
    @GetMapping
    public ResponseEntity<List<SavedCardDTO>> getAllCards() {
        return ResponseEntity.ok().body(savedCardService.getAllCards());
    }
    @GetMapping("/{id}")
    public ResponseEntity <SavedCardDTO> getCardByID(@PathVariable Long id) {
        return ResponseEntity.ok().body(savedCardService.getCardByID(id));
    }
    @GetMapping("/{customerID}/get-by-customer")
    public ResponseEntity <SavedCardDTO> getCardByCustomer(@PathVariable Long customerID) {
        return ResponseEntity.ok().body(savedCardService.getCardByCustomer(customerID));
    }
    @PutMapping("/{cardID}")
    public ResponseEntity<SavedCardDTO>  updateCard(@PathVariable Long cardID, @RequestBody UpdateSavedCardDTO dto) {
        return ResponseEntity.ok().body(savedCardService.updateCard(cardID,dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id ){
        savedCardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }




}
