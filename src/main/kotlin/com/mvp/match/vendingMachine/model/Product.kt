package com.mvp.match.vendingMachine.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Table
import org.springframework.data.annotation.Id
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType

@Entity
@javax.persistence.Table(name = "product")
data class Product (
   // @JsonIgnore
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?=0,
    @Column(name = "product_name", nullable = false)
    var productName: String?,
    @Column(name = "cost", nullable = false)
    var cost: Long,
    @Column(name = "amount_available", nullable = false)
    var amountAvailable: Long,
    @Column(name = "seller_id", nullable = false)
    var sellerId: Long,

)
