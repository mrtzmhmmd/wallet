package com.training.wallet.dto.request;

public record RegisterRequest(String firstName,
                              String lastName,
                              String email,
                              String password) {
}
