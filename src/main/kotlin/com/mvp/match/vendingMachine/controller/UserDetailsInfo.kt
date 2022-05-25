package com.mvp.match.vendingMachine.controller

import com.mvp.match.vendingMachine.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class UserDetailsInfo(user: User) : UserDetails {
    private var user: User? = null

    /*override fun getAuthorities(): Collection<GrantedAuthority?> {
        val roles: Set<Role> = user.getRoles()
        val authorities: MutableList<SimpleGrantedAuthority?> = ArrayList()
        for (role in roles) {
            authorities.add(SimpleGrantedAuthority(role.getName()))
        }
        return authorities
    }*/

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String? {
        return user!!.password
    }


    override fun getUsername(): String? {
        return user?.userName
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }


    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }

}
