package com.ignacio.zara;

import com.ignacio.zara.models.Price;
import com.ignacio.zara.repository.PriceRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import sun.tools.jar.CommandLine;

@SpringBootApplication
public class ZaraApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(ZaraApplication.class, args);}
	
	@Override
	public void run(String... args) throws Exception {
		//Load test DB
		template.execute("DROP TABLE PRICE IF EXISTS");
		template.execute("CREATE TABLE PRICE (PRICE_LIST INTEGER(10) PRIMARY KEY,BRAND_ID INTEGER(10),START_DATE VARCHAR(19),END_DATE VARCHAR(19),PRODUCT_ID INTEGER(10),PRIORITY INTEGER(10), PRICE DOUBLE(20), CURRENCY VARCHAR(3))");
		template.update("INSERT INTO PRICE (PRICE_LIST,BRAND_ID,START_DATE,END_DATE,PRODUCT_ID,PRIORITY,PRICE,CURRENCY) VALUES(1, 1, '2020-06-14-00.00.00', '2020-12-31-23.59.59', 35455, 0, 35.50, 'EUR')");
		template.update("INSERT INTO PRICE (PRICE_LIST,BRAND_ID,START_DATE,END_DATE,PRODUCT_ID,PRIORITY,PRICE,CURRENCY) VALUES(2, 1, '2020-06-14-15.00.00', '2020-06-14-18.30.00', 35455, 1, 25.45, 'EUR')");
		template.update("INSERT INTO PRICE (PRICE_LIST,BRAND_ID,START_DATE,END_DATE,PRODUCT_ID,PRIORITY,PRICE,CURRENCY) VALUES(3, 1, '2020-06-15-00.00.00', '2020-06-15-11.00.00', 35455, 1, 30.50, 'EUR')");
		template.update("INSERT INTO PRICE (PRICE_LIST,BRAND_ID,START_DATE,END_DATE,PRODUCT_ID,PRIORITY,PRICE,CURRENCY) VALUES(4, 1, '2020-06-15-16.00.00', '2020-12-31-23.59.59', 35455, 1, 38.95, 'EUR')");
		System.out.println("DB LOADED");
	}
		
}
