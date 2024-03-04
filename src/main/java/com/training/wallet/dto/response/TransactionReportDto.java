package com.training.wallet.dto.response;

import com.training.wallet.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReportDto {
    BigDecimal amount;
    TransactionType type;
    LocalDateTime transactionDate;
}