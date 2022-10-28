package com.cantaur.practice.model.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(exclude = "passwd")
@Getter
@Setter
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = -2356654715019746670L;
    private Long memberUid;
    private String email;
    private String passwd;
    private String name;
    private List<String> roles;

    @JsonCreator
    User(@JsonProperty("memberUid") @NotNull final Long memberUid,
         @JsonProperty("email") @NotNull final String email,
         @JsonProperty("passwd") @NotNull final String passwd,
         @JsonProperty("name") @NotNull final String name,
         @JsonProperty("roles") @NotNull final List<String> roles
    ) {
        super();
        this.memberUid = requireNonNull(memberUid);
        this.email = requireNonNull(email);
        this.passwd = requireNonNull(passwd);
        this.name = name;
        this.roles = requireNonNull(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return passwd;
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