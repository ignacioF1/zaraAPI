package com.ignacio.zara.services;

import com.ignacio.zara.models.Price;
import com.ignacio.zara.repository.PriceRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Business logic
@Service
@AllArgsConstructor
public class PriceServiceImpl implements PriceService{
    @Autowired
    PriceRepository priceRepository;

    /**
     * @author IAM
     * @param brandId and productId
     * @return 0 if both present, 1 if no product, 2 if no brand, 3 if no product nor brand
     */
    public Integer checkBrandProductExist(Integer brandId,Integer productId){
        int value = 0;
        if(priceRepository.findByProductId(productId).isEmpty()){
            value = 1;  //No such product
        }
        if(priceRepository.findByBrandId(brandId).isEmpty()){
            if(value == 1){
                value = 3;  //No such product nor brand
            }else{
                value = 2;  //No such brand
            }
        }
        return value;
    }

    /**
     * @author IAM
     * @param brandId productId date
     * @return Price of the requested date
     */
    public Optional<Price> obtainPrice(Integer brandId,Integer productId,String date) {
        List<Price> list = null;
        Optional<Price> price = null;   //These variables store the price with the highest priority after
        Integer priorityResult = null;  //comparing the requested date with all the priceList's dates
        try {
            list = priceRepository.findByProductIdAndBrandId(productId,brandId);    //Find all the products with the requested productId
            for (Price priceI : list) { //Iterate through the list to find the requested date
                String startDate = priceI.getStartDate();
                String endDate = priceI.getEndDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
                Date fDate = sdf.parse(date);
                Date fStartDate = sdf.parse(startDate);
                Date fEndDate = sdf.parse(endDate);
                if((fDate.after(fStartDate)&&fDate.before(fEndDate)||fDate.equals(fStartDate)||fDate.equals(fEndDate))&&(priorityResult == null || priceI.getPriority() > priorityResult)) {
                        priorityResult = priceI.getPriority();
                        price = Optional.of(priceI);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

}
