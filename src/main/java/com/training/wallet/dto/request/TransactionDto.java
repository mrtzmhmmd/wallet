package com.training.wallet.dto.request;

import java.math.BigDecimal;

public record TransactionDto(String userEmail, BigDecimal amount) {
}
