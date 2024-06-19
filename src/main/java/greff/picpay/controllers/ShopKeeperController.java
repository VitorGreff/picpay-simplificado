package greff.picpay.controllers;

import greff.picpay.entities.ShopKeeper;
import greff.picpay.services.ShopKeeperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/shopkeeper")
public class ShopKeeperController {
    @Autowired
    private ShopKeeperService shopKeeperService;

    @GetMapping
    public ResponseEntity<List<ShopKeeper>> getShopKeepers() {
        return ResponseEntity.ok(shopKeeperService.getShopKeepers());
    }

    @PostMapping
    public ResponseEntity<ShopKeeper> createShopKeeper(@RequestBody @Valid ShopKeeper newShopKeeper) {
        return ResponseEntity.ok(shopKeeperService.createShopKeeper(newShopKeeper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShopKeeper(@PathVariable Long id) {
        shopKeeperService.deleteShopKeeper(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopKeeper> updateShopKeeper(@PathVariable Long id, @RequestBody @Valid ShopKeeper shopKeeper)
            throws SQLException {
        return ResponseEntity.ok(shopKeeperService.updateShopKeeper(id, shopKeeper));
    }
}
