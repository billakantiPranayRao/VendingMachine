package com.mvp.match.vendingMachine.repository

import com.mvp.match.vendingMachine.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long>{


    fun findByUserName(userName: String?): User



}