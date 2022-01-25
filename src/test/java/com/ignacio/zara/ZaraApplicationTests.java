package com.ignacio.zara;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ignacio.zara.models.Price;
import com.ignacio.zara.repository.PriceRepository;
import com.ignacio.zara.services.PriceService;
import com.sun.deploy.net.HttpResponse;
import java.io.IOException;
import java.util.Optional;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
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

	//Test response if brandId does not exist
	@Test
	public void givenBrandIdDoesNotExists_then404IsReceived()
			throws ClientProtocolException, IOException {

		// Given
		Integer brandId = 2;
		HttpUriRequest request = new HttpGet( "http://localhost:8080/prices?brandId=" + brandId +"&productId=35455&date=2020-06-14-15.00.00");

		// When
		CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

		// Then
		assertEquals(httpResponse.getStatusLine().getStatusCode() , HttpStatus.SC_NOT_FOUND);
	}
	//Test ObtainPrice Endpoint

		/*
		Price price = new Price(1, 1, "2020-06-14-00.00.00", "2020-12-31-23.59.59", 35455, 0, 35.50, "EUR");
		Price price1 = new Price(2, 1, "2020-06-14-15.00.00", "2020-06-14-18.30.00", 35455, 0, 25.45, "EUR");
		Price price2 = new Price(3, 1, "2020-06-15-00.00.00", "2020-06-15-11.00.00", 35455, 0, 30.50, "EUR");
		Price price3 = new Price(4, 1, "2020-06-15-16.00.00", "2020-12-31-23.59.59", 35455, 0, 38.95, "EUR");
		priceRepository.save(price);
		priceRepository.save(price1);
		priceRepository.save(price2);
		priceRepository.save(price3);
		*/

		//Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
		@Test
		public void
		givenBrandIdProductIdExists_whenDateIsInPriceList_thenTestResponseIsCorrect()
  throws ClientProtocolException,IOException {

			// Given
			HttpUriRequest request = new HttpGet( "http://localhost:8080/prices?brandId=1&productId=35455&date=2020-06-14-10.00.00" );

			// When
			CloseableHttpResponse response = HttpClientBuilder.create().build().execute( request );

			// Then
			String result = EntityUtils.toString(response.getEntity());

			try {
				JSONObject jsonObj = new JSONObject(result);
				assertEquals(35455, jsonObj.getInt("productId"));
				assertEquals(1, jsonObj.getInt("brandId"));
				assertEquals(1, jsonObj.getInt("brandId"));
				assertEquals(1, jsonObj.getInt("priceList"));
				assertEquals("2020-06-14-00.00.00", jsonObj.getString("startDate"));
				assertEquals("2020-12-31-23.59.59", jsonObj.getString("endDate"));
				assertEquals(35.50, jsonObj.getDouble("price"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		//priceService.ObtainPrice(brandId, productId, date);

		//Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
		//Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
		//Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
		//Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)
	}
