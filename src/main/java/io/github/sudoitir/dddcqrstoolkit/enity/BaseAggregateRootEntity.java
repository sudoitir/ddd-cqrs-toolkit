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


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseAggregateRootEntity<?> that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        return getVersion().equals(that.getVersion());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getVersion().hashCode();
        return result;
    }
}
