package com.example.FinalProject.cervice;

import com.example.FinalProject.entity.Client;
import com.example.FinalProject.entity.Operation;
import com.example.FinalProject.repository.ClientRepository;
import com.example.FinalProject.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private OperationService operationService;

    @Test
    void getOperationListWithDateTest() {
        Client client = new Client();
        client.setId(1L);
        String startDate = "2023-01-01";
        String endDate = "2023-12-31";

        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation());

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(operationRepository.findByClientAndDateBetween(client, startDate, endDate))
                .thenReturn(operations);

        List<Operation> result = operationService.getOperationList(client.getId(), startDate, endDate);

        assertNotNull(result);
        assertEquals(operations, result);
        verify(clientRepository).findById(client.getId());
        verify(operationRepository).findByClientAndDateBetween(client, startDate, endDate);
    }

    @Test
    void getOperationListWithoutDateTest() {
        Client client = new Client();
        client.setId(1L);

        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation());

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(operationRepository.findByClient(client))
                .thenReturn(operations);

        List<Operation> result = operationService.getOperationList(client.getId());

        assertNotNull(result);
        assertEquals(operations, result);
        verify(clientRepository).findById(client.getId());
        verify(operationRepository).findByClient(client);
    }

    @Test
    void getOperationListWithoutDateExceptionTest() {
        Client client = new Client();
        client.setId(100L);

        when(clientRepository.findById(client.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> operationService.getOperationList(client.getId()));
    }

}