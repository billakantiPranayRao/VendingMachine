package com.mvp.match.vendingMachine.repository

import com.mvp.match.vendingMachine.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ProductRepository : JpaRepository<Product, Long>