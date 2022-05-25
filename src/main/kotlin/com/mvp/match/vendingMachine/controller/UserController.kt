package com.mvp.match.vendingMachine.controller

import com.mvp.match.vendingMachine.exception.NotAuthorizedException
import com.mvp.match.vendingMachine.exception.UserNotFoundException
import com.mvp.match.vendingMachine.model.User
import com.mvp.match.vendingMachine.service.UserService
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore


@Controller
@Component
class UserController (private val userService: UserService) {


    var user =""

    @ApiIgnore
    @GetMapping("/registration")
    fun viewRegistrationPage(): String? {
        return "registration"
    }

    @ApiIgnore
    @GetMapping("/login")
    fun viewLoginPage(): String? {
        return "login"
    }

    @ApiIgnore
    @GetMapping("/welcome")
    fun viewWelcomePage(): String? {
        return "welcome"
    }

     fun userNameFromContr(): User? {

        return userService.getUsersByUserName(user)
    }


    @GetMapping("/users")
    @ResponseBody
    fun getAllUsers(): List<User> = userService.getAllUsers(1)

    @GetMapping("/users/{id}")
    @ResponseBody
    fun getUsersById(@PathVariable("id") id: Long): User =
        userService.getUsersById(id)


    @PostMapping("/createUser")
    @ResponseBody
    fun createUser(@RequestBody payload: User): User = userService.createUser(payload)


    @PutMapping("/updateUser/{id}")
    @ResponseBody
    fun updateUserById(@PathVariable("id") id: Long, @RequestBody payload: User): User =
        userService.updateUserById(id, payload)

    @DeleteMapping("/deleteUser/{id}")
    @ResponseBody
    fun deleteUserById(@PathVariable("id") id: Long): Unit =
        userService.deleteUserById(id)

    @PutMapping("/reset")
    @ResponseBody
    fun reset() {

        var name : User? = userService.getUsersByUserName(user)

        name!!.deposit = 0

        var id: Long? = name!!.id

        if (id != null) {
            userService.updateUserById(id, name)
        }
    }

    @ApiIgnore
    @PostMapping("/registration")
    fun welcomePage(model: ModelMap, @RequestParam("username") userName: String, @RequestParam("password") password: String, @RequestParam("role") role: String) : String{

        var usrObj = User()
        usrObj.userName = userName
        usrObj.role = role
        usrObj.password = password
        userService.createUser(usrObj)

        return "login"

    }

    @ApiIgnore
    @PostMapping("/login")
    fun loginPage(model: ModelMap, @RequestParam("username") userName: String, @RequestParam("password") password: String,  @RequestParam("role") role: String) : String{


        var name : User? = userService.getUsersByUserName(userName)

        if (name != null) {
            println("Username from getUsersByUserName ${name.userName}")
        }
        // val passwordEncoder = BCryptPasswordEncoder()

        if (name != null) {
            if(userName == name.userName) {

                if (name.role == "buyer" || name.role == "seller") {


                    user = userName

                    return  "welcome"
                } else throw NotAuthorizedException("User is not authorized")
            }else throw UserNotFoundException("No matching user was found")
        }
        model["error"] = "wrong credentials";
         return  "welcome"
    }


    @PutMapping("/deposit")
    @ResponseBody
    fun deposit(@RequestParam("depositMoney") depositMoney: Int) {

        var name: User? = userService.getUsersByUserName(user)

        if (name != null) {
            name.deposit = name.deposit +depositMoney
            println("Total Account Money ${name.deposit}")
        }

        val cash = arrayOf(5, 10, 20, 50, 100)


        for (money in cash) {

            if (depositMoney == money && name != null)
                name.userName?.let {
                    userService.depositUserMoney(name.deposit, it)
                }
        }

    }

}