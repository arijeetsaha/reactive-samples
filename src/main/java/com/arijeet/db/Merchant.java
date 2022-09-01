package com.arijeet.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

@Data
@RequiredArgsConstructor
public class Merchant implements Persistable<Integer> {

    @Id
    @NonNull
    Integer id;
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    @Transient
    private boolean newMerchant;

    @Override
    @Transient
    public boolean isNew() {
        return true;
    }

    public Merchant setAsNew() {
        this.newMerchant = true;
        return this;
    }
}
