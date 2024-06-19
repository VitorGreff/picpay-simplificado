package greff.picpay.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

@Entity
@Table(name = "client")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @CPF
    @NotNull
    private String cpf;
    @Email
    @NotNull
    private String email;
    @NotNull
    private float balance;
}
