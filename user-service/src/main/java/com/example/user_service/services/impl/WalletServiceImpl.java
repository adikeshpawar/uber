package com.example.user_service.services.impl;

import com.example.user_service.entity.User;
import com.example.user_service.entity.Wallet;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.repository.WalletRepository;
import com.example.user_service.services.impl.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public Wallet getWalletByUserId(UUID userId) {
        return walletRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + userId));
    }

    @Override
    @Transactional
    public Wallet credit(UUID userId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Credit amount must be greater than zero");
        }

        Wallet wallet = walletRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + userId));

        wallet.setBalance(wallet.getBalance().add(amount));
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet debit(UUID userId, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Debit amount must be greater than zero");
        }

        Wallet wallet = walletRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + userId));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        return walletRepository.save(wallet);
    }
}
