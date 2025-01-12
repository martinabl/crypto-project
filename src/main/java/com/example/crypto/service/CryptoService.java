package com.example.crypto.service;

import com.example.crypto.model.Crypto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class CryptoService {

    private final List<Crypto> portfolio = new ArrayList<>();

    public Crypto addCrypto(Crypto crypto) {
        if (portfolio.stream().anyMatch(existingCrypto -> existingCrypto.getId().equals(crypto.getId()))) {
            throw new IllegalArgumentException("Kryptoměna s tímto ID již existuje.");
        }
        portfolio.add(crypto);
        return crypto;
    }

    public List<Crypto> getAllCryptos(String sort) {
        List<Crypto> sortedPortfolio = new ArrayList<>(portfolio);
        if (sort != null) {
            switch (sort) {
                case "name":
                    sortedPortfolio.sort(Comparator.comparing(Crypto::getName));
                    break;
                case "price":
                    sortedPortfolio.sort(Comparator.comparing(Crypto::getPrice));
                    break;
                case "quantity":
                    sortedPortfolio.sort(Comparator.comparing(Crypto::getQuantity));
                    break;
                default:
                    break;
            }
        }
        return sortedPortfolio;
    }

    public Optional<Crypto> getCryptoById(Integer id) {
        return portfolio.stream().filter(crypto -> crypto.getId().equals(id)).findFirst();
    }

    public Optional<Crypto> updateCrypto(Integer id, Crypto updatedCrypto) {
        Optional<Crypto> cryptoOptional = getCryptoById(id);
        cryptoOptional.ifPresent(crypto -> {
            crypto.setName(updatedCrypto.getName());
            crypto.setSymbol(updatedCrypto.getSymbol());
            crypto.setPrice(updatedCrypto.getPrice());
            crypto.setQuantity(updatedCrypto.getQuantity());
        });
        return cryptoOptional;
    }

    public Double calculatePortfolioValue() {
        return portfolio.stream()
                .mapToDouble(crypto -> crypto.getPrice() * crypto.getQuantity())
                .sum();
    }
}

