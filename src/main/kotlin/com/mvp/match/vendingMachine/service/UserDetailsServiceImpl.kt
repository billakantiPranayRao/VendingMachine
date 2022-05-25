package com.mvp.match.vendingMachine.service

import com.mvp.match.vendingMachine.controller.UserDetailsInfo
import com.mvp.match.vendingMachine.model.User
import com.mvp.match.vendingMachine.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class UserDetailsServiceImpl : UserDetailsService {

   lateinit var userRepository : UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userName: String?): UserDetails? {
        val user: User = userName?.let { userRepository.findByUserName(it) }
            ?: throw UsernameNotFoundException("Could not find user with that email")
        return UserDetailsInfo(user)
    }
}