package com.mvp.match.vendingMachine.service

import com.mvp.match.vendingMachine.exception.UserNotFoundException
import com.mvp.match.vendingMachine.model.User
import com.mvp.match.vendingMachine.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(i: Int): List<User> = userRepository.findAll()

    fun getUsersById(id: Long): User = userRepository.findById(id)
        .orElseThrow { UserNotFoundException("No matching user was found") }

    fun createUser(user: User): User = userRepository.save(user)

    fun updateUserById(id: Long, user: User): User {

        val userData: Optional<User> = userRepository.findById(id)
        return if (userRepository.existsById(id)) {

            val userUpdate: User = userData.get()


            userUpdate.id = user.id
            userUpdate.password = user.password
            userUpdate.role = user.role
            userUpdate.userName = user.userName

            userRepository.save(userUpdate)

        } else throw UserNotFoundException("No matching user was found")
    }

    fun depositUserMoney(depositMoney: Long ,userName : String ) {

        val userData: User = userRepository.findByUserName(userName)
        userData.deposit = depositMoney
        userRepository.save(userData)
    }

    fun deleteUserById(id: Long) {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
        } else throw UserNotFoundException("No matching user was found")
    }

    fun getUsersByUserName(userName: String): User? = userRepository.findByUserName(userName)



}