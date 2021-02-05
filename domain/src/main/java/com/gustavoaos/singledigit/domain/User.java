package com.gustavoaos.singledigit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name", columnDefinition = "LONGTEXT")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email provided")
    @Column(name = "email", columnDefinition = "LONGTEXT")
    private String email;

    @Builder.Default
    @ElementCollection
    private List<SingleDigit> singleDigits = new ArrayList<>();

    @Column(name = "public_key", columnDefinition = "LONGTEXT")
    private String publicKey;

    @Column(name = "private_key", columnDefinition = "LONGTEXT")
    private String privateKey;

}
