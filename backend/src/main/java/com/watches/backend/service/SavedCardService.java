package com.watches.backend.service;

import com.watches.backend.Dto.PaymentDto.PaymentDTO;
import com.watches.backend.Dto.SavedCardDto.CreateSavedCardDTO;
import com.watches.backend.Dto.SavedCardDto.UpdateSavedCardDTO;
import com.watches.backend.helpers.exception.CException;
import com.watches.backend.mappers.SavedCardMapper;
import com.watches.backend.model.Customer;
import com.watches.backend.model.SavedCard;
import com.watches.backend.Repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Async
@AllArgsConstructor
public class SavedCardService {

    private final SavedCardRepository savedCardRepository;
    private final CustomerService customerService;
    private final RestTemplate restTemplate;

    public CompletableFuture<SavedCard> createCard(String username, CreateSavedCardDTO dto) {
        Customer customer = customerService.getCustomerByUsername(username).join();
        SavedCard card = SavedCardMapper.fromCreateDTO(dto,customer);
        addCard(new PaymentDTO(
                card.getCardNumber(),
                null,
                card.getExpirationDate(),
                card.getCvv(),
                card.getBillingAddress(),
                card.getPostalCode(),
                card.getCardType(),
                customer.getId(),
                "La-Royal"
        )).join();        savedCardRepository.save(card);
        customer.setSavedCard(card);
        customerService.save(customer);
        return CompletableFuture.completedFuture(card);
    }

    public CompletableFuture<ResponseEntity<String>> addCard(PaymentDTO paymentDTO) {
        String paymentServerUrl = "http://localhost:9091/api/payment/add";

        try {
            return CompletableFuture.completedFuture(restTemplate.postForEntity(
                    paymentServerUrl,
                    paymentDTO,
                    String.class
            ));
        } catch (Exception ex) {
            throw CException.unexpected(ex);
        }
    }

    private SavedCard findByCustomer(Customer customer) {
        return savedCardRepository.findByCustomer(customer);
    }

    public CompletableFuture<SavedCard> getCardByCustomerID(Long customerId) {
        Customer customer = customerService.getCustomerById(customerId).join();
        return CompletableFuture.completedFuture(findByCustomer(customer));
    }

    public CompletableFuture<SavedCard> updateCard(String username, UpdateSavedCardDTO dto) {
        Customer customer = customerService.getCustomerByUsername(username).join();
        SavedCard card = findByCustomer(customer);

        SavedCardMapper.updateEntityFromDTO(dto,card);
        return CompletableFuture.completedFuture(savedCardRepository.save(card));
    }

    public void deleteCard(String username) {
        Customer customer = customerService.getCustomerByUsername(username).join();
        savedCardRepository.deleteByCustomer(customer);
    }

    public CompletableFuture<SavedCard> getByUsername(String username) {
        Customer customer = customerService.getCustomerByUsername(username).join();

        return CompletableFuture.completedFuture(savedCardRepository.findByCustomer(customer));
    }
}