package com.fatec.sasbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SasBackendApplicationTests {

	Calculator calc = new Calculator();

	@Test
	void contextLoads() {
	}

	@Test
	void itShouldAddTwoNumbers() {
		//given
		int numberOne = 20;
		int numberTwo = 12;

		//when
		int result = calc.add(numberOne, numberTwo);

		//then
		int expected = 32;
		assertThat(result).isEqualTo(expected);

	}

	class Calculator {
		int add(int a, int b) {
			return a + b;
		}
	}

}
