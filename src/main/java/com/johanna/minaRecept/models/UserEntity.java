package com.johanna.minaRecept.models;

import jakarta.persistence.*;
import com.johanna.minaRecept.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.Size;
import java.util.Collection;


    @Entity
    @Table(name = "\"user\"")
    public class UserEntity implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private long id;

        @Size(min=4, max=64)
        private String username;
        @Size(min =4, max =64)
        private String password;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean accountEnabled;
        private boolean credentialsNonExpired;
        @Enumerated(EnumType.STRING)
        private Role role;

        public UserEntity() {}
        public UserEntity(String username, String password, Role role,
                          boolean accountNonExpired, boolean accountNonLocked, boolean accountEnabled, boolean credentialsNonExpired) {
            this.username = username;
            this.password = password;
            this.role = role;
            this.accountNonExpired = accountNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.accountEnabled = accountEnabled;
            this.credentialsNonExpired = credentialsNonExpired;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return role.getAuthorities();
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return accountNonExpired;
        }

        @Override
        public boolean isAccountNonLocked() {
            return accountNonLocked;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return credentialsNonExpired;
        }

        @Override
        public boolean isEnabled() {
            return accountEnabled;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setAccountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
        }

        public void setAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
        }

        public void setAccountEnabled(boolean accountEnabled) {
            this.accountEnabled = accountEnabled;
        }

        public void setCredentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }
    }


