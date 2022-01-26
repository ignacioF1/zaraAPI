package com.ignacio.zara.services;

import com.ignacio.zara.models.Price;
import java.util.Optional;

public interface PriceService {
    Optional<Price> obtainPrice(Integer brandId,Integer productId,String date);

    Integer checkBrandProductExist(Integer brandId, Integer productId);

}
