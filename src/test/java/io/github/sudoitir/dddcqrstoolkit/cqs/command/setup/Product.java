package io.github.sudoitir.dddcqrstoolkit.cqs.command.setup;

import io.github.sudoitir.dddcqrstoolkit.valueobject.ULID;

public class Product {
    private ULID id;
    private String name;

    // Constructors, getters, setters (omit constructor if using Lombok)

    public Product(ULID id, String name) {
        this.id = id;
        this.name = name;
    }

    public ULID getId() {
        return id;
    }

    public void setId(ULID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
