package com.kairo.lojaWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatsDTO {
    private Integer creditCardAmount;
    private Integer debitCardAmount;
    private Integer ticketAmount;
    private Integer bankTransferAmount;
}
