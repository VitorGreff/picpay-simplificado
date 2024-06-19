package greff.picpay.controllers;

import greff.picpay.entities.TransactionDTO;
import greff.picpay.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/transfer")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> createPayment(@RequestBody @Valid TransactionDTO transfer)
            throws SQLException {
        transactionService.applyPayment(transfer);
        return ResponseEntity.ok().build();
    }
}
