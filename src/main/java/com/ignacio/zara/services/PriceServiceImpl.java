package com.ignacio.zara.services;

import com.ignacio.zara.models.Price;
import com.ignacio.zara.repository.PriceRepository;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// Business logic
@Service
@AllArgsConstructor
public class PriceServiceImpl implements PriceService{

    private final PriceRepository priceRepository;

    /**
     * @author IAM
     * @version 1.0
     * @param brandId and productId
     * @return 0 if both present, 1 if no product, 2 if no brand, 3 if no product nor brand
     */
    public Integer ObtainProduct(Integer brandId,Integer productId){
        Integer value = 0;
        if(priceRepository.findByProductId(productId).size() == 0){
            value = 1;  //No such product
        }
        if(priceRepository.findByBrandId(brandId).size() == 0){
            if(value == 1){
                value = 3;  //No such product nor brand
            }else{
                value = 2;  //No such brand
            }
        }
        return value;
    }

    public Optional<Price> ObtainPrice(Integer brandId,Integer productId,String date) {
        List<Price> list = null;
        Optional<Price> price = null;
        Integer priceListResult = null; //These variables store the priceList with the highest priority after
        Integer priorityResult = null;  //comparing the requested date with all the priceList's dates
        try {
            //Find all the products with the requested productId
            list = priceRepository.findByProductIdAndBrandId(productId,brandId);
            //Iterate through the list to find the requested date
            for (Price priceI : list) {
                String startDate = priceI.getStartDate();
                String endDate = priceI.getEndDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
                Date fDate = sdf.parse(date);
                Date fStartDate = sdf.parse(startDate);
                Date fEndDate = sdf.parse(endDate);
                if(fDate.after(fStartDate)&&fDate.before(fEndDate)||fDate.equals(fStartDate)||fDate.equals(fEndDate)) {
                    if (priorityResult == null || priceI.getPriority() > priorityResult) {
                        priceListResult = priceI.getPriceList();
                        priorityResult = priceI.getPriority();
                        price = Optional.of(priceI);
                        System.out.println(priceI);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

}
