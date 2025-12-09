package com.example.user_service.services.impl;

import com.example.user_service.entity.Wallet;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {

    Wallet getWalletByUserId(UUID userId);

    Wallet credit(UUID userId, BigDecimal amount);

    Wallet debit(UUID userId, BigDecimal amount);
}
