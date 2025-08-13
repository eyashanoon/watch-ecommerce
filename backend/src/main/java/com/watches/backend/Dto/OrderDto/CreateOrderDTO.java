package com.watches.backend.Dto.OrderDto;

import com.watches.backend.Dto.PaymentDto.CreatePaymentDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @Size(min = 1, message = "At least one item is required")
    private List<CreateOrderItemDTO> items;

    private CreatePaymentDTO createPaymentDTO;

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
                ", items=" + itemsStr +
                ", createPaymentDTO=" + paymentStr +
                '}';
    }


}
