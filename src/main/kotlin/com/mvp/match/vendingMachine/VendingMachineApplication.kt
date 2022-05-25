package com.mvp.match.vendingMachine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

import springfox.documentation.swagger2.annotations.EnableSwagger2

//@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
@EnableSwagger2
class VendingMachineApplication

fun main(args: Array<String>) {
	runApplication<VendingMachineApplication>(*args)
}
