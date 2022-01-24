package com.ignacio.zara.repository;

import com.ignacio.zara.models.Price;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price,Integer> {

    List<Price> findByProductIdAndBrandId(Integer productId, Integer brandId);

    List<Price> findByProductId(Integer productId);

    List<Price> findByBrandId(Integer brandId);
}
