package greff.picpay.services;

import greff.picpay.entities.Client;
import greff.picpay.entities.ShopKeeper;
import greff.picpay.entities.TransferDTO;
import greff.picpay.exceptions.InsufficientBalanceException;
import greff.picpay.repositories.ClientRepository;
import greff.picpay.repositories.ShopKeeperRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Transactional
public class TransferService {
    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private ShopKeeperRepository shopKeeperRepo;

    public void applyPayment(TransferDTO transfer) throws SQLException, InsufficientBalanceException {
        Client client = clientRepo.findById(transfer.getClientID()).orElseThrow(
                () -> new SQLException("Client not found")
        );
        ShopKeeper shopKeeper = shopKeeperRepo.findById(transfer.getShopKeeperID()).orElseThrow(
                () -> new SQLException("ShopKeeper not found")
        );

        System.out.println("client -> "+ client);
        System.out.println("shopKeeper -> "+ shopKeeper);

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
}

