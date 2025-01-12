package com.example.crypto.controller;

import com.example.crypto.model.Crypto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.crypto.service.CryptoService;

import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping
    public ResponseEntity<?> addCrypto(@RequestBody Crypto crypto) {
        try {
            return ResponseEntity.ok(cryptoService.addCrypto(crypto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Crypto>> getAllCryptos(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok(cryptoService.getAllCryptos(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crypto> getCryptoById(@PathVariable Integer id) {
        return cryptoService.getCryptoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crypto> updateCrypto(@PathVariable Integer id, @RequestBody Crypto updatedCrypto) {
        return cryptoService.updateCrypto(id, updatedCrypto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<Double> getPortfolioValue() {
        return ResponseEntity.ok(cryptoService.calculatePortfolioValue());
    }

}
