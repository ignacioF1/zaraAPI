package com.ignacio.zara;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.stream.Stream;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ZaraApplicationTests {
	@ParameterizedTest	// 5 Data Driven Tests
		@MethodSource("methodForArguments")
		void
		givenBrandIdProductIdExists_whenDateIsInPriceList_thenTestResponseIsCorrect(String testNum , String testUrl, Integer expProductId , Integer expBrandId , Integer expPriceList , String expStartDate , String expEndDate , Double expPrice)
  throws ClientProtocolException,IOException {
			System.out.println("Test " + testNum);
			HttpUriRequest request = new HttpGet( "http://localhost:8080/prices?" + testUrl);
			CloseableHttpResponse response = HttpClientBuilder.create().build().execute( request );
			String result = EntityUtils.toString(response.getEntity());
			try {
				JSONObject jsonObj = new JSONObject(result);
				assertEquals(expProductId, jsonObj.getInt("productId"));
				assertEquals(expBrandId, jsonObj.getInt("brandId"));
				assertEquals(expPriceList, jsonObj.getInt("priceList"));
				assertEquals(expStartDate, jsonObj.getString("startDate"));
				assertEquals(expEndDate, jsonObj.getString("endDate"));
				assertEquals(expPrice, jsonObj.getDouble("price"));
			} catch (JSONException e) {
				e.printStackTrace();
				assert(false);
			}
		}
	private static Stream<Arguments> methodForArguments() {
		return Stream.of(
				// Test cases: testNum , testUrl , (Expected result) expProductId , expBrandId , expPriceList , expStartDate , expEndDate , expPrice
				Arguments.of("1", "brandId=1&productId=35455&date=2020-06-14-10.00.00", 35455,1,1,"2020-06-14-00.00.00","2020-12-31-23.59.59",35.5),
				Arguments.of("2", "brandId=1&productId=35455&date=2020-06-14-16.00.00", 35455,1,2,"2020-06-14-15.00.00","2020-06-14-18.30.00",25.45),
				Arguments.of("3", "brandId=1&productId=35455&date=2020-06-14-21.00.00", 35455,1,1,"2020-06-14-00.00.00","2020-12-31-23.59.59",35.5),
				Arguments.of("4", "brandId=1&productId=35455&date=2020-06-15-10.00.00", 35455,1,3,"2020-06-15-00.00.00","2020-06-15-11.00.00",30.50),
				Arguments.of("5", "brandId=1&productId=35455&date=2020-06-16-21.00.00", 35455,1,4,"2020-06-15-16.00.00","2020-12-31-23.59.59",38.95)
		);
	}
	}
