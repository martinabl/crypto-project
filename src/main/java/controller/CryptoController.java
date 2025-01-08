package controller;


import model.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.CryptoService;

import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping
    public void addCrypto(@RequestBody Crypto crypto) {
        System.out.println("Crypto added: " + crypto);
        cryptoService.addCrypto(crypto);
    }

    @GetMapping
    public List<Crypto> getAllCryptos(@RequestParam(value = "sort", required = false) String sortBy) {
        System.out.println("Endpoint /cryptos byl zavolán");
        if (sortBy != null) {
            return cryptoService.getSortedCryptos(sortBy);
        }
        return cryptoService.getAllCryptos();
    }

    @GetMapping("/{id}")
    public Crypto getCryptoById(@PathVariable Integer id) {
        return cryptoService.getCryptoById(id);
    }

    @PutMapping("/{id}")
    public void updateCrypto(@PathVariable Integer id, @RequestBody Crypto updatedCrypto) {

        cryptoService.updateCrypto(id, updatedCrypto);
    }


    @GetMapping("/test")
    public String testEndpoint() {
        return "It works!";
    }


}
