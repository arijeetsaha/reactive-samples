package com.arijeet.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/merchants")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @PostMapping(value = "/bulk/{i}", produces="text/event-stream")
    public Flux<Merchant> bulkInsert(@PathVariable("i") int i) {
        return merchantService.bulkInsert(i);
    }

    @PostMapping(value= "/separate/{i}",produces="text/event-stream")
    public Flux<Merchant> separateInsert(@PathVariable("i") int i) {
        return merchantService.insertOneByOne(i);
    }
}
