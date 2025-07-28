package com.watches.backend.service;

import com.watches.backend.Dto.SavedCardDto.CreateSavedCardDTO;
import com.watches.backend.Dto.SavedCardDto.SavedCardDTO;
import com.watches.backend.Dto.SavedCardDto.UpdateSavedCardDTO;
import com.watches.backend.exceptions.CustomerNotFoundException;
 import com.watches.backend.exceptions.SavedCardNotFoundException;
import com.watches.backend.mappers.SavedCardMapper;
import com.watches.backend.model.Customer;
import com.watches.backend.model.SavedCard;
import com.watches.backend.Repositories.*;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedCardService {

    private final SavedCardRepository savedCardRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public SavedCardService(SavedCardRepository savedCardRepository, CustomerRepository customerRepository) {
        this.savedCardRepository = savedCardRepository;
        this.customerRepository = customerRepository;
    }

    public SavedCardDTO createCard(CreateSavedCardDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerID())
                .orElseThrow(() -> new CustomerNotFoundException(  dto.getCustomerID()));

        SavedCard card = SavedCardMapper.fromCreateDTO(dto,customer);

        SavedCard saved = savedCardRepository.save(card);
        return SavedCardMapper.toDTO(saved);
    }

    public   SavedCardDTO getCardByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(  customerId));

        SavedCard card = savedCardRepository.findByCustomer(customer);
        return SavedCardMapper.toDTO(card);
    }
    public   List<SavedCardDTO> getAllCards() {
        return savedCardRepository.findAll().stream().map(SavedCardMapper::toDTO).collect(Collectors.toList());

    }
    public    SavedCardDTO getCardByID(Long customerId) {
        SavedCard savedCard= savedCardRepository.findById(customerId).orElseThrow(() -> new SavedCardNotFoundException(customerId));
        return SavedCardMapper.toDTO(savedCard);
    }


    public SavedCardDTO updateCard(Long cardID,UpdateSavedCardDTO dto) {
        SavedCard card = savedCardRepository.findById(cardID)
                .orElseThrow(() -> new SavedCardNotFoundException( cardID));

        SavedCardMapper.updateEntityFromDTO(dto,card);
        SavedCard updated = savedCardRepository.save(card);
        return SavedCardMapper.toDTO(updated);
    }

    public void deleteCard(Long id) {
        if (!savedCardRepository.existsById(id)) {
            throw new SavedCardNotFoundException( id);
        }
        savedCardRepository.deleteById(id);
    }
}