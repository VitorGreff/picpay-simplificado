package greff.picpay.controllers;

import greff.picpay.entities.TransferDTO;
import greff.picpay.services.TransferService;
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
public class TransferController {
    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<Void> createPayment(@RequestBody @Valid TransferDTO transfer)
            throws SQLException {
        transferService.applyPayment(transfer);
        return ResponseEntity.ok().build();
    }
}
