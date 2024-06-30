package com.task_pay.task_pay.models.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.task_pay.task_pay.models.enums.Role;
import com.task_pay.task_pay.models.enums.UserType;
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
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String mobileNumber;

    @Column(nullable = false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String password;

    private String expertIn;

    private String about;

    private String profilePic;

    @Column(nullable = false,unique = true)
    private String invitationCode;

    private String fcmToken;

    @Column(nullable = false)
    private Date otpVerifiedAt;


    private Date updatedAt;


    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private boolean isBlock;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval=true)
    private List<Token> tokens=new ArrayList<>();

    @OneToMany(mappedBy = "inviteUser",cascade = CascadeType.ALL,orphanRemoval=true)
    private List<Invite> inviteUsers=new ArrayList<>();

    @OneToMany(mappedBy = "invitedUser",cascade = CascadeType.ALL,orphanRemoval=true)
    private List<Invite> invitedUsers=new ArrayList<>();

    @OneToMany(mappedBy = "senderUser",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Task> sendingTasks=new ArrayList<>();

    @OneToMany(mappedBy = "receiverUser",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Task> receivingTasks=new ArrayList<>();

    @OneToMany(mappedBy = "senderUser",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Payment> sendingPayments=new ArrayList<>();

    @OneToMany(mappedBy = "receiverUser",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Task> receivingPayments=new ArrayList<>();

    @OneToMany(mappedBy = "senderUser",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MileStonePayment> sendingMilestonePayments=new ArrayList<>();

    @OneToMany(mappedBy = "receiverUser",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MileStonePayment> receivingMilestonePayments=new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
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
