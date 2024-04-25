package com.task_pay.task_pay.models.entities;
import com.task_pay.task_pay.models.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer tokenId;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @ToString.Exclude
    public User user;

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
