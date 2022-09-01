package com.arijeet.db;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MerchantRepository extends R2dbcRepository<Merchant, Integer> {
}
