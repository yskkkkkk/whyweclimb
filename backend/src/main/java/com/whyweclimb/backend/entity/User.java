package com.whyweclimb.backend.entity;

import lombok.*;

import java.util.Collection;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tb_user")
public class User implements UserDetails{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userSeq;
    @Column(length = 100)
    private String userId;
    private String userPassword;
    private Integer backgroundSound;
    private Integer effectSound;
	@ColumnDefault("0")
	private Integer maxLevel;
	@ColumnDefault("1")
	private Integer skinSeq;
	@ColumnDefault("true")
	private boolean accountNonExpired = true;
	@ColumnDefault("true")
	private boolean accountNonLocked = true;
	@ColumnDefault("true")
	private boolean credentialsNonExpired = true;
	@ColumnDefault("true")
	private boolean enabled = true;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return this.userId.equals(((User)obj).getUserId());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.userId.hashCode();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		return this.userPassword;
	}
	@Override
	public String getUsername() {
		return this.userId;
	}

	@Override
	public boolean isAccountNonExpired() {
	    return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
	    return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
	    return this.enabled;
	}
}
