package com.example.FinalProject.cervice;

import com.example.FinalProject.Enum.OperationType;
import com.example.FinalProject.entity.Client;
import com.example.FinalProject.entity.Operation;
import com.example.FinalProject.repository.ClientRepository;
import com.example.FinalProject.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    void getBalanceTest() {
        Client client = new Client();
        client.setId(1L);
        client.setBalance(BigDecimal.valueOf(1000.54));

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        String res = clientService.getBalance(client.getId());
        verify(clientRepository).findById(client.getId());
        assertEquals("1000.54", res);
    }

    @Test
    void getBalanceExceptionTest() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        String res = clientService.getBalance(1L);

        verify(clientRepository).findById(1L);
        assertEquals("-1: Не найден пользователь", res);
    }

    @Test
    void putMoneyTest() {
        Client client = new Client();
        client.setId(12L);
        client.setBalance(BigDecimal.valueOf(1250.54));

        when(clientRepository.findById(12L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        ArgumentCaptor<Operation> operationCaptor = ArgumentCaptor.forClass(Operation.class);
        when(operationRepository.save(operationCaptor.capture())).thenAnswer(inv -> inv.getArgument(0));

        String res = clientService.putMoney(client.getId(), BigDecimal.valueOf(200));

        assertEquals("1", res);
        verify(clientRepository).findById(12L);
        verify(clientRepository).save(client);

        Operation savedOperation = operationCaptor.getValue();
        assertEquals(client, savedOperation.getClient());
        assertEquals(OperationType.REFILL, savedOperation.getOperationType());
        assertEquals(BigDecimal.valueOf(200), savedOperation.getAmount());
    }

    @Test
    void putMoneyExceptionTest() {
        Client client = new Client();
        client.setId(12L);
        client.setBalance(BigDecimal.valueOf(1250.54));

        when(clientRepository.findById(12L)).thenReturn(Optional.empty());

        String res = clientService.putMoney(client.getId(), BigDecimal.valueOf(299));

        assertEquals("0: Не найден пользователь", res);
    }

    @Test
    void takeMoneyTest() {
        Client client = new Client();
        client.setId(12L);
        client.setBalance(BigDecimal.valueOf(1250.54));

        when(clientRepository.findById(12L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        ArgumentCaptor<Operation> operationCaptor = ArgumentCaptor.forClass(Operation.class);
        when(operationRepository.save(operationCaptor.capture())).thenAnswer(inv -> inv.getArgument(0));

        String res = clientService.takeMoney(client.getId(), BigDecimal.valueOf(200));

        assertEquals("1", res);
        verify(clientRepository).findById(12L);
        verify(clientRepository).save(client);

        Operation savedOperation = operationCaptor.getValue();
        assertEquals(client, savedOperation.getClient());
        assertEquals(OperationType.WITHDRAWAL, savedOperation.getOperationType());
        assertEquals(BigDecimal.valueOf(200), savedOperation.getAmount());
    }

    @Test
    void takeMoneyExceptionTest() {
        Client client = new Client();
        client.setId(12L);
        client.setBalance(BigDecimal.valueOf(1250.54));

        when(clientRepository.findById(12L)).thenReturn(Optional.empty());

        String res = clientService.takeMoney(client.getId(), BigDecimal.valueOf(299));

        assertEquals("0: Не найден пользователь", res);
    }

    @Test
    void transferMoneyTest() {
        Client client = new Client();
        client.setId(1L);
        client.setBalance(BigDecimal.valueOf(1000));

        Client toClient = new Client();
        toClient.setId(2L);
        toClient.setBalance(BigDecimal.valueOf(1000));

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenReturn(client);

        when(clientRepository.findById(2L)).thenReturn(Optional.of(toClient));
        when(clientRepository.save(toClient)).thenReturn(toClient);

        ArgumentCaptor<Operation> operationCaptor = ArgumentCaptor.forClass(Operation.class);
        when(operationRepository.save(operationCaptor.capture())).thenAnswer(inv -> inv.getArgument(0));

        String res = clientService.transferMoney(client.getId(), toClient.getId(), BigDecimal.valueOf(200));

        assertEquals("1", res);
        verify(clientRepository).findById(1L);
        verify(clientRepository).findById(2L);
        verify(clientRepository).save(client);
        verify(clientRepository).save(toClient);

        Operation savedOperation = operationCaptor.getValue();
        assertEquals(client, savedOperation.getClient());
        assertEquals(toClient, savedOperation.getToClient());
        assertEquals(OperationType.TRANSFER, savedOperation.getOperationType());
        assertEquals(BigDecimal.valueOf(200), savedOperation.getAmount());
    }

    @Test
    void transferMoneyExceptionTest() {
        Client client = new Client();
        client.setId(1L);
        client.setBalance(BigDecimal.valueOf(1000));

        Client toClient = new Client();
        toClient.setId(2L);
        toClient.setBalance(BigDecimal.valueOf(1000));

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        when(clientRepository.findById(2L)).thenReturn(Optional.empty());

        String res = clientService.transferMoney(client.getId(), toClient.getId(), BigDecimal.valueOf(200));

        assertEquals("0: Не найден один из пользователей", res);
    }
}