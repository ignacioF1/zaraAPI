package com.ignacio.zara.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ignacio.zara.models.Price;
import com.ignacio.zara.services.PriceService;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
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
    public ResponseEntity getPrice(@RequestParam Integer brandId,Integer productId,String date) {
        Integer value = priceService.ObtainProduct(brandId, productId);
        Optional<Price> price = priceService.ObtainPrice(brandId, productId, date);
        String productIdVal,brandIdVal,priceListVal,startDateVal,endDateVal,priceVal,message;
        message="";
        if(value == 1){
            message = "No such product";
        }else if(value == 2){
            message = "No such brand";
        }else if(value == 3){
            message = "No such product nor brand";
        }
        if(price!= null) {
            productIdVal = price.get().getProductId().toString();
            brandIdVal = price.get().getBrandId().toString();
            priceListVal = price.get().getPriceList().toString();
            startDateVal = price.get().getStartDate();
            endDateVal = price.get().getEndDate();
            priceVal = price.get().getPrice().toString();
            message = "Found!";
        }else{
            productIdVal=brandIdVal=priceListVal=startDateVal=endDateVal=priceVal=null;
            if(value == 0) {
                message = "There is no list with such date";
            }
        }
            Map<String, String> response = new LinkedHashMap<>();
            response.put("productId", productIdVal);
            response.put("brandId", brandIdVal);
            response.put("priceList", priceListVal);
            response.put("startDate", startDateVal);
            response.put("endDate", endDateVal);
            response.put("price", priceVal);
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            String responseJson = gson.toJson(response);
            return ResponseEntity.status(HttpStatus.OK).body(message + responseJson);
        }
    }

