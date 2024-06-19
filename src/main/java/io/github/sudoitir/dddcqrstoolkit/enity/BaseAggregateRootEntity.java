package io.github.sudoitir.dddcqrstoolkit.enity;

import io.github.sudoitir.dddcqrstoolkit.valueobject.Version;
import java.io.Serializable;

public abstract class BaseAggregateRootEntity<ID extends Serializable> extends BaseEntity<ID> {

    private Version version;

    protected BaseAggregateRootEntity() {
        this.version = new Version(0);
    }

    protected BaseAggregateRootEntity(ID id, Version version) {
        this.setId(id);
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public void incrementVersion() {
        this.version = this.version.increment();
    }
}
