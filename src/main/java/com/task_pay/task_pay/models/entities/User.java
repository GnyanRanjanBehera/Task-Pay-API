package com.task_pay.task_pay.models.entities;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer userId;

    @Column(nullable = false)
    private String userType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String expertIn;

    private String about;

    private String profilePic;

    @Column(nullable = false,unique = true)
    private String invitationCode;

    private Date optVerifiedAt;


    private Date updatedAt;

    @Column(nullable = false)
    private Date createdAt;


    @Column(nullable = false)
    private boolean isBlock;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval=true)
    @ToString.Exclude
    private List<Token> tokens;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval=true)
    @ToString.Exclude
    private List<Invite> invites;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return mobileNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getUserId() != null && Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
