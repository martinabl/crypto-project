package service;

import model.Crypto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CryptoService {

    private final List<Crypto> cryptos = new ArrayList<>();

    public void addCrypto(Crypto crypto) {
        cryptos.add(crypto);
    }

    public List<Crypto> getAllCryptos(){
        return cryptos;
    }

    public List<Crypto> getSortedCryptos(String sortBy) {
        switch (sortBy) {
            case "name":
                cryptos.sort(Comparator.comparing(Crypto::getName));
                break;
            case "price":
                cryptos.sort(Comparator.comparing(Crypto::getPrice));
            case "quantity":
                cryptos.sort(Comparator.comparing(Crypto::getQuantity));
                break;
            default:
                throw new IllegalArgumentException("Neplatný parametr pro řazení: " + sortBy);
        }
        return cryptos;
    }

    public Crypto getCryptoById(Integer id) {
        return cryptos.stream()
                .filter(crypto -> crypto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Kryptoměna s Id" + id + " nebyla nalezena."));
    }

    public void updateCrypto(Integer id, Crypto updatedCrypto) {

        Crypto existingCrypto = getCryptoById(id);
        if (existingCrypto != null) {

            existingCrypto.setName(updatedCrypto.getName());
            existingCrypto.setSymbol(updatedCrypto.getSymbol());
            existingCrypto.setPrice(updatedCrypto.getPrice());
            existingCrypto.setQuantity(updatedCrypto.getQuantity());
        } else {

            throw new IllegalArgumentException("Kryptoměna s tímto ID neexistuje");
        }
    }

}

