package com.example.FinalProject.cervice;

import com.example.FinalProject.entity.Client;
import com.example.FinalProject.entity.Operation;
import com.example.FinalProject.Enum.OperationType;
import com.example.FinalProject.repository.ClientRepository;
import com.example.FinalProject.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final OperationRepository operationRepository;

    public String getBalance(Long id){
      Optional<Client> client = clientRepository.findById(id);
      if (client.isPresent()){

          return client.get().getBalance().toString();
      } else {
          return "-1: Не найден пользователь";
      }
    }

    @Transactional
    public String putMoney(Long id, BigDecimal money){
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()){
            client.get().setBalance(client.get().getBalance().add(money));
            clientRepository.save(client.get());
            operationRepository.save(new Operation(client.get(), client.get(), OperationType.REFILL, money));
            return "1";
        } else {
            return "0: Не найден пользователь";
        }
    }

    @Transactional
    public String takeMoney(Long id, BigDecimal money){
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()){
            client.get().setBalance(client.get().getBalance().subtract(money));
            clientRepository.save(client.get());
            operationRepository.save(new Operation(client.get(), client.get(), OperationType.WITHDRAWAL, money));
            return "1";
        } else {
            return "0: Не найден пользователь";
        }
    }

    @Transactional
    public String transferMoney(Long id,Long toId, BigDecimal money){
        Optional<Client> client = clientRepository.findById(id);
        Optional<Client> toClient = clientRepository.findById(toId);
        if (client.isPresent() && toClient.isPresent()){
            client.get().setBalance(client.get().getBalance().subtract(money));
            toClient.get().setBalance(toClient.get().getBalance().add(money));
            clientRepository.save(client.get());
            clientRepository.save(toClient.get());
            operationRepository.save(new Operation(client.get(), toClient.get(), OperationType.TRANSFER, money));
            return "1";
        } else {
            return "0: Не найден один из пользователей";
        }
    }
}
