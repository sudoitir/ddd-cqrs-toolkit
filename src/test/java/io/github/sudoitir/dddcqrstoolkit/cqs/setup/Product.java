package io.github.sudoitir.dddcqrstoolkit.cqs.setup;

import io.github.sudoitir.dddcqrstoolkit.entity.BaseAggregateRootEntity;
import io.github.sudoitir.dddcqrstoolkit.ulid.ULID;

public class Product extends BaseAggregateRootEntity<ULID> {

    private String name;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
