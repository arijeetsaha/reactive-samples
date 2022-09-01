package com.arijeet.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    private List<Merchant> createMerchants(int j) {
        List<Merchant> merchantList = new ArrayList<>();
        for ( int i=1000*j+1; i<=1000*j+1000;i++) {
            Merchant merchant = new Merchant(i, "fname"+i, "lname"+i);
            merchant.setNewMerchant(true);
            merchantList.add(merchant);
        }
        return merchantList;
    }

    public Flux<Merchant> bulkInsert(int i) {
        List<Merchant> merchantList = createMerchants(i);
        return merchantRepository.saveAll(merchantList);
    }

    public Flux<Merchant> insertOneByOne(int i) {
        List<Merchant> merchantList = createMerchants(i);
        return Flux.fromIterable(merchantList)
                .flatMap(merchant -> merchantRepository.save(merchant));
    }
}
