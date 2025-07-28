package com.payment.paymentServer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UsedInRepository usedInRepository;

    public PaymentService(PaymentRepository paymentRepository, UsedInRepository usedInRepository) {
        this.paymentRepository = paymentRepository;
        this.usedInRepository = usedInRepository;
    }

    public boolean makePayment(PaymentDTO paymentDTO){
        PaymentInfo paymentInfo=this.paymentRepository.findByCardNumber(paymentDTO.getCardNumber()).orElseThrow(()->new paymentExp("error"));
        List<UsedIn> usedIn= paymentInfo.getUsedIn().stream().filter(usedin->usedin.getUserId().equals(paymentDTO.getUserId())
                && usedin.getCompanyName().equals(paymentDTO.getCompanyName())).toList();
        if(usedIn.size()==1
                && paymentDTO.getCardNumber().equals(paymentInfo.getCardNumber())
                && paymentDTO.getCardType().equals(paymentInfo.getCardType())
                && paymentDTO.getExpiryDate().equals(paymentInfo.getExpiryDate() )
                && paymentDTO.getCvv().equals(paymentInfo.getCvv()) ){
            if(paymentInfo.getBalance()>=paymentDTO.getAmount()){
                paymentInfo.setBalance(paymentInfo.getBalance()-paymentDTO.getAmount());
                paymentRepository.save(paymentInfo);
                return true;
            }

        }
        return false;

    }
    public boolean addPayment(PaymentDTO paymentDTO){
        PaymentInfo paymentInfo=paymentRepository.findByCardNumber(paymentDTO.getCardNumber()).orElseThrow(()->new paymentExp("error"));
        List<UsedIn> usedIn= paymentInfo.getUsedIn().stream().filter(usedin->usedin.getUserId().equals(paymentDTO.getUserId())
                && usedin.getCompanyName().equals(paymentDTO.getCompanyName())).toList();
        if(usedIn.isEmpty()
                && paymentDTO.getCardNumber().equals(paymentInfo.getCardNumber())
                && paymentDTO.getCardType().equals(paymentInfo.getCardType())
                && paymentDTO.getExpiryDate().equals(paymentInfo.getExpiryDate() )
                && paymentDTO.getCvv().equals(paymentInfo.getCvv()) ){
            UsedIn usedIn1=new UsedIn(paymentDTO.getUserId(), paymentDTO.getCompanyName(), paymentInfo);
            paymentInfo.getUsedIn().add(usedIn1);
            paymentRepository.save(paymentInfo);
            usedInRepository.save(usedIn1);
            return true;
        }
        return false;
    }
}
