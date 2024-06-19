package greff.picpay.services;

import greff.picpay.entities.Client;
import greff.picpay.entities.ShopKeeper;
import greff.picpay.entities.TransferDTO;
import greff.picpay.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepo;

    public List<Client> getClients(){
        return clientRepo.findAll();
    }

    public Client createClient(Client client)  {
        return clientRepo.save(client);
    }

    public Client updateClient(Long id, Client client) throws SQLException {
       Client newClient = clientRepo.findById(id).orElseThrow(() -> new SQLException("Client not found"));
       newClient.setName(client.getName());
       newClient.setCpf(client.getCpf());
       newClient.setEmail(client.getEmail());
       newClient.setBalance(client.getBalance());
       return clientRepo.save(newClient);
    }

    public void deleteClient(Long id)  {
        clientRepo.deleteById(id);
    }
}
