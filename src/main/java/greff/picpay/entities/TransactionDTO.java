package greff.picpay.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @AllArgsConstructor
public class TransactionDTO implements Serializable {
    @NotNull
    private Long clientID;
    @NotNull
    private Long shopKeeperID;
    @NotNull
    @Min(0)
    private float amount;
}
