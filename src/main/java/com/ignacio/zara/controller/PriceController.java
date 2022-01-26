package com.ignacio.zara.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ignacio.zara.models.Price;
import com.ignacio.zara.services.PriceService;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;
    //Test Example
    // localhost:8080/prices?brandId=1&productId=35455&date=2020-06-14-10.00.00
    @GetMapping
    @ResponseBody
    public ResponseEntity getPrice(@RequestParam Integer brandId, Integer productId, String date) {
        Integer value = priceService.checkBrandProductExist(brandId, productId);
        Optional<Price> price = priceService.obtainPrice(brandId, productId, date);
        String productIdVal, brandIdVal, priceListVal, startDateVal, endDateVal, priceVal;
        if (value == 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such product");
        } else if (value == 2) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such brand");
        } else if (value == 3) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such product or brand");
        }
        if (price != null) {
            Map<String, String> response = new LinkedHashMap<>();
            response.put("productId", price.get().getProductId().toString());
            response.put("brandId", price.get().getBrandId().toString());
            response.put("priceList", price.get().getPriceList().toString());
            response.put("startDate", price.get().getStartDate());
            response.put("endDate", price.get().getEndDate());
            response.put("price", price.get().getPrice().toString());
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            String responseJson = gson.toJson(response);
            return ResponseEntity.status(HttpStatus.OK).body(responseJson);
        } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no list with such date");
            }
        }
    }

