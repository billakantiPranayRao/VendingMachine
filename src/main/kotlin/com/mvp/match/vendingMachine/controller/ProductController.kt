package com.mvp.match.vendingMachine.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.mvp.match.vendingMachine.exception.*
import com.mvp.match.vendingMachine.model.Product
import com.mvp.match.vendingMachine.model.User
import com.mvp.match.vendingMachine.service.ProductService
import com.mvp.match.vendingMachine.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import java.util.*


@Controller
@Component
class ProductController(private val productService: ProductService,private val userService: UserService)  {



    @Autowired
    var userController : UserController?= null

    fun getUser(): User? {
        return userController!!.userNameFromContr()
    }


    @GetMapping("/products")
    @ResponseBody
    fun getAllProducts(): List<Product> = productService.getAllProduct()


    @GetMapping("/products/{id}")
    @ResponseBody
    fun getProductsById(@PathVariable("id") id: Long): Optional<Product> =
        productService.getProductsById(id)


    @PostMapping("/products")
    @ResponseBody
    fun createProduct(@RequestBody payload: Product): Product {

        var saveProduct = productService.createProduct(payload)

       if (this.getUser()!!.role == "seller"){

           return saveProduct
      }else throw UserNotFoundException("No matching user was found")

    }

    @PutMapping("/products/{id}")
    @ResponseBody
    fun updateProductById(@PathVariable("id") sellerId: Long, @RequestBody payload: Product): Product
    {
        var updateProduct = productService.updateProductById(sellerId, payload)

        if (this.getUser()!!.role == "seller"){

            return updateProduct
        }else throw UserNotFoundException("No matching user was found")
    }

    @DeleteMapping("/products/{id}")
    @ResponseBody
    fun deleteProductById(@PathVariable("id") id: Long) {


        var updateProduct = productService.deleteProductById(id)

        if (this.getUser()!!.role == "seller"){

            return updateProduct
        }else throw UserNotFoundException("No matching user was found")

    }

    @GetMapping("/buy/{id}/{quantity}")
    @ResponseBody
    fun buy(@PathVariable("id") id: Long, @PathVariable("quantity") amountAvailable: Long): ObjectNode {

        val mapper = ObjectMapper()

        val objectNode: ObjectNode = mapper.createObjectNode()
        val product: Optional<Product> = productService.getProductsById(id)
        var totalPrice: Long = 0
        val zero : Long =0
        var change: Long =0
        val item: String? = product.get().productName
        val cost: Long = product.get().cost
        val quantityAvailable: Long = product.get().amountAvailable

        val accountBalance: Long = this.getUser()!!.deposit

        var moneySpent : Long = 0


            if (quantityAvailable > 0) {
                if (amountAvailable <= quantityAvailable) {

                    totalPrice = amountAvailable * cost
                } else throw InsufficientQuantityException("can't proceed ahead due to insufficient quantity")

            } else throw ItemUnAvailableException("sorry item is unavailable")
        if (this.getUser()!!.role=="buyer") {
            if (accountBalance != zero && accountBalance >= totalPrice) {

                if (accountBalance > totalPrice) {
                    change = accountBalance - totalPrice
                    moneySpent = accountBalance - change
                } else {
                    moneySpent = totalPrice
                    change = 0
                }

            } else throw InsufficientAccountBalance("Insufficient account balance")
        }else throw UserNotFoundException("No matching user was found")

        objectNode.put("product", item)
        objectNode.put("change", change)
        objectNode.put("moneySpent", moneySpent)

        return objectNode
    }

}

