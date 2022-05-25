package com.mvp.match.vendingMachine.exception

import org.springframework.http.HttpStatus


class ProductNotFoundException(message: String): Exception(message)
