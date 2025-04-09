package com.efederation.Model;

import com.efederation.Enums.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private boolean validated;

    @CreationTimestamp
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Wrestler> wrestlers;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
