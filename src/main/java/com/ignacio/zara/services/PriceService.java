package com.ignacio.zara.services;

import com.ignacio.zara.models.Price;
import java.util.Optional;

public interface PriceService {
    Optional<Price> ObtainPrice (Integer brandId,Integer productId,String date);

    Integer ObtainProduct(Integer brandId, Integer productId);
}
