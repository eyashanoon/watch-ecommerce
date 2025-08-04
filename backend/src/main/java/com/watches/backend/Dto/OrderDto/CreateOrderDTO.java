package com.watches.backend.Dto.OrderDto;

import com.watches.backend.Dto.PaymentDto.CreatePaymentDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateOrderDTO {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @Size(min = 1, message = "At least one item is required")
    private List<CreateOrderItemDTO> items;

    private CreatePaymentDTO createPaymentDTO;

    public CreateOrderDTO() {}

    public CreateOrderDTO(Long customerId, List<CreateOrderItemDTO> items, CreatePaymentDTO createPaymentDTO) {
        this.customerId = customerId;
        this.items = items;
        this.createPaymentDTO = createPaymentDTO;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<CreateOrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CreateOrderItemDTO> items) {
        this.items = items;
    }

    public CreatePaymentDTO getCreatePaymentDTO() {
        return createPaymentDTO;
    }

    public void setCreatePaymentDTO(CreatePaymentDTO createPaymentDTO) {
        this.createPaymentDTO = createPaymentDTO;
    }

    @Override
    public String toString() {
        StringBuilder itemsStr = new StringBuilder("[");
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                CreateOrderItemDTO item = items.get(i);
                itemsStr.append("{productId=").append(item.getProductId())
                        .append(", quantity=").append(item.getQuantity())
                        .append("}");
                if (i < items.size() - 1) {
                    itemsStr.append(", ");
                }
            }
        }
        itemsStr.append("]");

        String paymentStr = "null";
        if (createPaymentDTO != null) {
            paymentStr = "{paymentMethod=" + createPaymentDTO.getPaymentMethod() + "}";
        }

        return "CreateOrderDTO{" +
                "customerId=" + customerId +
                ", items=" + itemsStr.toString() +
                ", createPaymentDTO=" + paymentStr +
                '}';
    }


}
