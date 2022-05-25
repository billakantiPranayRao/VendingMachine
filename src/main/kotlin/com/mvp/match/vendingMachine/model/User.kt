package com.mvp.match.vendingMachine.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Table
import org.springframework.data.annotation.Id
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType


@Entity
@javax.persistence.Table(name = "user")
@Component
 class User {


    @JsonIgnore
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = 0

    @Column(name = "user_name", nullable = false, unique = true)
    var userName: String? = ""

    @Column(name = "role", nullable = false)
    var role = ""

    @JsonIgnore
    @Column(name = "deposit", nullable = false)
    var deposit: Long = 0


    // @JsonIgnore
    @Column(name = "password", nullable = false)
    var password = ""
        get() = field
    set(value) {
        val passwordEncoder = BCryptPasswordEncoder()
        field = passwordEncoder.encode(value)

    }



}



