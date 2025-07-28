package com.payment.paymentServer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/make")
    public ResponseEntity<?> makePayment(@RequestBody PaymentDTO paymentDTO) {
        boolean result = paymentService.makePayment(paymentDTO);
        if (result) {
            return ResponseEntity.ok().body("Payment successful");
        } else {
            return ResponseEntity.badRequest().body("Payment failed");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPayment(@RequestBody PaymentDTO paymentDTO) {
        boolean result = paymentService.addPayment(paymentDTO);
        if (result) {
            return ResponseEntity.ok().body("Card added successfully");
        } else {
            return ResponseEntity.badRequest().body("Card addition failed");
        }
    }
}
