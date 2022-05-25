package com.mvp.match.vendingMachine


import com.mvp.match.vendingMachine.model.Product
import com.mvp.match.vendingMachine.model.User
import com.mvp.match.vendingMachine.repository.UserRepository
import com.mvp.match.vendingMachine.service.UserService
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*


@Tag("unitTest")
@DisplayName("test cases for buy and deposit end points")
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@SpringBootTest
class VendingMachineApplicationTests {

	@org.junit.jupiter.api.Test
	fun contextLoads() {
	}

	@MockBean
	lateinit var userRepository: UserRepository

	@MockBean
	lateinit var userService: UserService

	@Autowired
	var mockMvc: MockMvc? = null

	fun userData(): User? {
		val user = User()
		user.id = 1
		user.deposit = 100
		user.password = "mvpMatch"
		user.role = "buyer"
		user.userName = "heyCar"
		return user
	}

	fun productData(): Product {

		return Product(1, "coolDrink", 10, 1, 1)
	}

	@org.junit.jupiter.api.Test
	@Throws(Exception::class)
	fun getUserByIdTest() {

		var usersList =  LinkedList<User>()
		userData()?.let { usersList.add(it) }

		val id = 1
		userData()
		`when`(userService.getAllUsers(id)).thenReturn(usersList)

		mockMvc?.perform(
			get("/users/ $id")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			?.andExpect(status().isOk)
			?.andDo { println() }
			//?.andExpect(MockMvcResultMatchers.jsonPath("$.user_name").value("heyCar"));

	}

	@org.junit.jupiter.api.Test
	@Throws(Exception::class)
	fun userDepositTest() {

		var usersList =  LinkedList<User>()
		userData()?.let { usersList.add(it) }


		val updateDeposit = """{
  "userName": "string",
  "role": "buyer",
  "deposit":50,
  "password": "${'$'}2a${'$'}10${'$'}32/T0./J4u8CXDHhgVNvf.zeghWQI1XbMw/6Wu54Pg1./h2XZnkOC"
}"""
		val id = 1
		val userName: String= "heyCar"
		userData()
		`when`(userService.getAllUsers(id)).thenReturn(usersList)

		mockMvc!!.perform(
			MockMvcRequestBuilders.put("/deposit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateDeposit)
				.accept(MediaType.APPLICATION_JSON)
		)
			.andExpect(status().isOk)
			//.andReturn().response.contentAsString.contains("50")

	}


	@Test
	@Throws(java.lang.Exception::class)
	fun buyTest() {
		userData()
		productData()
		val id : Long = 1;
		val amountAvailable : Long =1
		mockMvc!!.perform(get("/buy/ $id / $amountAvailable")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk)
			.andDo { println() }
			.andDo(MockMvcResultHandlers.print()).andReturn().response.contentAsString
	}


}
