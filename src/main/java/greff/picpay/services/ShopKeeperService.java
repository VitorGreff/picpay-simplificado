package greff.picpay.services;

import greff.picpay.entities.ShopKeeper;
import greff.picpay.repositories.ShopKeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ShopKeeperService {
    @Autowired
    private ShopKeeperRepository shopKeeperRepo;

    public List<ShopKeeper> getShopKeepers(){
        return shopKeeperRepo.findAll();
    }

    public ShopKeeper createShopKeeper(ShopKeeper shopKeeper)  {
        return shopKeeperRepo.save(shopKeeper);
    }

    public ShopKeeper updateShopKeeper(Long id, ShopKeeper shopKeeper) throws SQLException {
       ShopKeeper newShopKeeper = shopKeeperRepo.findById(id).orElseThrow(() -> new SQLException("ShopKeeper not found"));
       newShopKeeper.setName(shopKeeper.getName());
       newShopKeeper.setCnpj(shopKeeper.getCnpj());
       newShopKeeper.setEmail(shopKeeper.getEmail());
       newShopKeeper.setMoney(shopKeeper.getMoney());
       return shopKeeperRepo.save(newShopKeeper);
    }

    public void deleteShopKeeper(Long id)  {
        shopKeeperRepo.deleteById(id);
    }
}
