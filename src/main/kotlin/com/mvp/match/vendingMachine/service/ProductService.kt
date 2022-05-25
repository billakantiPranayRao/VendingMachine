package com.mvp.match.vendingMachine.service

import com.mvp.match.vendingMachine.exception.ProductNotFoundException
import com.mvp.match.vendingMachine.model.Product
import com.mvp.match.vendingMachine.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*


@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProduct(): List<Product> = productRepository.findAll()

    fun getProductsById(id: Long): Optional<Product> = productRepository.findById(id)



    fun createProduct(product: Product): Product = productRepository.save(product)

    fun updateProductById(sellerId: Long, product: Product): Product {
        return if (productRepository.existsById(sellerId)) {
            productRepository.save(
                Product(
                    id = product.id,
                    productName = product.productName,
                    cost = product.cost,
                    amountAvailable = product.amountAvailable,
                    sellerId = product.sellerId

                )
            )
        } else throw ProductNotFoundException("No matching product was found")
    }

    fun deleteProductById(id: Long) {
        return if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
        } else throw ProductNotFoundException("No matching product was found")
    }
}