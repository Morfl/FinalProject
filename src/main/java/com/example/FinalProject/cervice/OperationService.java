package com.example.FinalProject.cervice;

import com.example.FinalProject.entity.Client;
import com.example.FinalProject.entity.Operation;
import com.example.FinalProject.repository.ClientRepository;
import com.example.FinalProject.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationService {

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final OperationRepository operationRepository;

    public List<Operation> getOperationList(Long id, String startDate, String endDate) {
        Optional<Client> client = clientRepository.findById(id);
        return operationRepository.findByClientAndDateBetween(client.get(), startDate, endDate);
    }

    public List<Operation> getOperationList(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return operationRepository.findByClient(client.get());
    }
}
