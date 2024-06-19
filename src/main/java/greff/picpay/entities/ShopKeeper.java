package greff.picpay.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import java.io.Serializable;

@Entity
@Table(name = "shopkeeper")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class ShopKeeper implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @CNPJ
    @NotNull
    private String cnpj;
    @Email
    @NotNull
    private String email;
    @NotNull
    private float balance;
}
