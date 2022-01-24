package com.ignacio.zara;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ignacio.zara.models.Price;
import com.ignacio.zara.repository.PriceRepository;
import java.util.Optional;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@DataJpaTest
public class ZaraApplicationTests {

	@Autowired
	private PriceRepository priceRepository;

	//Test DataBase
	@Test
	public void givenPrice_whenSave_thenGetOk() {
		Price price = new Price(1,1,"2020-06-14-00.00.00","2020-12-31-23.59.59", 35455,0,35.50,"EUR");
		priceRepository.save(price);
		Price price2 = priceRepository.getById(1);
		assertEquals(price, price2);
	}

	//Test ObtainPrice Endpoint
	@Test
	public void givenData_thenTestOutput() {
		Price price = new Price(1, 1, "2020-06-14-00.00.00", "2020-12-31-23.59.59", 35455, 0, 35.50, "EUR");
		Price price1 = new Price(2, 1, "2020-06-14-15.00.00", "2020-06-14-18.30.00", 35455, 0, 25.45, "EUR");
		Price price2 = new Price(3, 1, "2020-06-15-00.00.00", "2020-06-15-11.00.00", 35455, 0, 30.50, "EUR");
		Price price3 = new Price(4, 1, "2020-06-15-16.00.00", "2020-12-31-23.59.59", 35455, 0, 38.95, "EUR");
		priceRepository.save(price);
		priceRepository.save(price1);
		priceRepository.save(price2);
		priceRepository.save(price3);

		//TO DO

		}
	}
