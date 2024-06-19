package greff.picpay.services;

import greff.picpay.entities.Client;
import greff.picpay.entities.ShopKeeper;
import greff.picpay.entities.TransferDTO;
import greff.picpay.exceptions.InsufficientBalanceException;
import greff.picpay.exceptions.UnauthorizedTransactionException;
import greff.picpay.repositories.ClientRepository;
import greff.picpay.repositories.ShopKeeperRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;

@Service
@Transactional
public class TransferService {
    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private ShopKeeperRepository shopKeeperRepo;
    private static final String AUTHORIZATION_URL = "https://util.devi.tools/api/v2/authorize";

    public void applyPayment(TransferDTO transfer) throws SQLException, InsufficientBalanceException {
        Client client = clientRepo.findById(transfer.getClientID()).orElseThrow(
                () -> new SQLException("Client not found")
        );
        ShopKeeper shopKeeper = shopKeeperRepo.findById(transfer.getShopKeeperID()).orElseThrow(
                () -> new SQLException("ShopKeeper not found")
        );

        authorizeRequest();
        if (client.getBalance() < transfer.getAmount()) {
            throw new InsufficientBalanceException(
                    String.format("Insufficient balance for client <%s> to apply the transaction", client.getName())
            );
        }

        client.setBalance(client.getBalance() - transfer.getAmount());
        shopKeeper.setBalance(shopKeeper.getBalance() + transfer.getAmount());

        clientRepo.save(client);
        shopKeeperRepo.save(shopKeeper);
    }

    public static void authorizeRequest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(AUTHORIZATION_URL, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println(response.getBody());
        } else {
            throw new UnauthorizedTransactionException(response.getBody());
        }
    }
}

