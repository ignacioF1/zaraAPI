package com.ignacio.zara;

import com.ignacio.zara.models.Price;
import com.ignacio.zara.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZaraApplication implements CommandLineRunner {

	@Autowired
	PriceRepository priceRepository;

	public static void main(String[] args) {
		SpringApplication.run(ZaraApplication.class, args);}
	
	@Override
	public void run(String... args) throws Exception {
		// Load Test Database at startup
		Price price1 = new Price(1,1,"2020-06-14-00.00.00","2020-12-31-23.59.59", 35455,0,35.50,"EUR");
		Price price2 = new Price(2,1,"2020-06-14-15.00.00","2020-06-14-18.30.00", 35455,1,25.45,"EUR");
		Price price3 = new Price(3,1,"2020-06-15-00.00.00","2020-06-15-11.00.00", 35455,1,30.50,"EUR");
		Price price4 = new Price(4,1,"2020-06-15-16.00.00","2020-12-31-23.59.59", 35455,1,38.95,"EUR");
		priceRepository.save(price1);
		priceRepository.save(price2);
		priceRepository.save(price3);
		priceRepository.save(price4);
	}
		
}
