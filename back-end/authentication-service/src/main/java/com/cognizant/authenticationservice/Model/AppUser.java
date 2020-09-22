package com.cognizant.authenticationservice.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class AppUser implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private User user;
    
   

	private Collection<? extends GrantedAuthority> authorities; // to store role
																// details

	public AppUser(User user) {
        System.out.println("HI APPUSER");
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(user.getUserType()));
        System.out.println("Authority List :" +authorityList);
        this.authorities = authorityList;
        System.out.println("Authorities :" +authorities);
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmailId();
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
