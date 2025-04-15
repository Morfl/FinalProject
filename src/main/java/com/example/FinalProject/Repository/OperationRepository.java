package com.example.FinalProject.Repository;

import com.example.FinalProject.Entity.Client;
import com.example.FinalProject.Entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByClient(Client id);

    List<Operation> findByClientAndDateBetween(Client client, String startDate, String endDate);
}
