package com.training.wallet.domain.model;

import com.training.wallet.domain.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String token;
    @Enumerated
    @Column(nullable = false)
    private TokenType type = TokenType.BEARER;
    @Column(nullable = false)
    private boolean revoked;
    @Column(nullable = false)
    private boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
