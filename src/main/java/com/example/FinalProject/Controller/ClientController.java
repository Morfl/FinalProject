package com.example.FinalProject.Controller;

import com.example.FinalProject.Service.ClientService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
public class ClientController {

    @Autowired
    public final ClientService clientService;

    @GetMapping("/getBalance/{id}")
    public String getBalance(@PathVariable Long id) {
        return clientService.getBalance(id);
    }

    @PutMapping("/putMoney/{id}")
    public String putMoney(@PathVariable Long id, @RequestParam @Positive BigDecimal money) {
        return clientService.putMoney(id, money);
    }

    @PutMapping("/takeMoney/{id}")
    public String takeMoney(@PathVariable Long id, @RequestParam @Positive BigDecimal money) {
        return clientService.takeMoney(id, money);
    }

}
