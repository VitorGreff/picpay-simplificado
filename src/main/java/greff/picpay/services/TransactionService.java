package greff.picpay.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import greff.picpay.entities.Client;
import greff.picpay.entities.ShopKeeper;
import greff.picpay.entities.TransactionDTO;
import greff.picpay.exceptions.IncompleteOperationException;
import greff.picpay.exceptions.InsufficientBalanceException;
import greff.picpay.exceptions.UnauthorizedTransactionException;
import greff.picpay.repositories.ClientRepository;
import greff.picpay.repositories.ShopKeeperRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class TransactionService {
    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private ShopKeeperRepository shopKeeperRepo;
    @Autowired
    private RestTemplate restTemplate;
    private static final String AUTHORIZATION_URL = "https://util.devi.tools/api/v2/authorize";

    public void applyPayment(TransactionDTO transfer) throws SQLException, InsufficientBalanceException {
        Client client = clientRepo.findById(transfer.getClientID()).orElseThrow(
                () -> new SQLException("Client not found")
        );
        ShopKeeper shopKeeper = shopKeeperRepo.findById(transfer.getShopKeeperID()).orElseThrow(
                () -> new SQLException("ShopKeeper not found")
        );
        if (client.getBalance() < transfer.getAmount()) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient balance for client <%s> to apply the transaction", client.getName())
            );
        }
        authorizeRequest();
        client.setBalance(client.getBalance() - transfer.getAmount());
        shopKeeper.setBalance(shopKeeper.getBalance() + transfer.getAmount());
        clientRepo.save(client);
        shopKeeperRepo.save(shopKeeper);
        notifyShopKeeper(shopKeeper.getEmail());
    }

    public void authorizeRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity(AUTHORIZATION_URL, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            JsonNode node = null;
            try {
                node = new ObjectMapper().readTree(response.getBody());
            } catch (JsonProcessingException e) {
                throw new UnauthorizedTransactionException(e.getMessage());
            }
            Boolean isAuthorized = node
                    .get("data")
                    .get("authorization")
                    .asBoolean();
            if (!isAuthorized) {
                throw new UnauthorizedTransactionException("Unauthorized transaction");
            }
        } else {
            throw new UnauthorizedTransactionException("Unable to authorize request");
        }
    }

    public void notifyShopKeeper(String email) {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        ResponseEntity<String> response = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", null,
                String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IncompleteOperationException("Unable to notify shop keeper");
        }
    }
}

