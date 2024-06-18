package io.github.sudoitir.dddcqrstoolkit.valueobject;

import java.io.Serial;
import java.io.Serializable;

public record Version(long version) implements Comparable<Version>, Serializable {

    @Serial
    private static final long serialVersionUID = 5412681989720822033L;

    public static Version zero() {
        return new Version(0L);
    }

    @Override
    public int compareTo(final Version val) {
        return Long.compare(this.version, val.version);
    }

}