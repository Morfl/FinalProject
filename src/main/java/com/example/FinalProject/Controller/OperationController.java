package com.example.FinalProject.Controller;

import com.example.FinalProject.Entity.Operation;
import com.example.FinalProject.Service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OperationController {

    @Autowired
    public final OperationService operationService;

    @GetMapping("/getOperationList/{id}")
    public ResponseEntity<List<Operation>> getOperationList1(@PathVariable Long id, @RequestParam Optional<String> startDate, @RequestParam Optional<String> endDate) {
        if (startDate.isPresent() && endDate.isPresent()) {
            return ResponseEntity.ok(operationService.getOperationList(id, startDate.get(), endDate.get()));
        } else {
            return ResponseEntity.ok(operationService.getOperationList(id));
        }
    }

}