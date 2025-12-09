package com.example.user_service.controller;

import com.example.user_service.dtos.WalletUpdateDto;
import com.example.user_service.entity.Wallet;
import com.example.user_service.services.impl.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/balance")
    public ResponseEntity<Wallet> getBalance(Authentication auth) {
        UUID userId = UUID.fromString(auth.getName());
        return ResponseEntity.ok(walletService.getWalletByUserId(userId));
    }

    @PostMapping("/credit")
    public ResponseEntity<Wallet> credit(
            Authentication auth,
            @RequestBody WalletUpdateDto dto
    ) {
        UUID userId = UUID.fromString(auth.getName());
        return ResponseEntity.ok(walletService.credit(userId, dto.getAmount()));
    }

    @PostMapping("/debit")
    public ResponseEntity<Wallet> debit(
            Authentication auth,
            @RequestBody WalletUpdateDto dto
    ) {
        UUID userId = UUID.fromString(auth.getName());
        return ResponseEntity.ok(walletService.debit(userId, dto.getAmount()));
    }
}
