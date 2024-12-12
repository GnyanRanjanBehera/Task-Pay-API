package com.task_pay.task_pay.models.entities;
import com.task_pay.task_pay.models.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import java.util.Objects;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity

public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer tokenId;

    @Column(unique = true,nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Token token = (Token) o;
        return getTokenId() != null && Objects.equals(getTokenId(), token.getTokenId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
