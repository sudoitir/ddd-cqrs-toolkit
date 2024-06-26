package io.github.sudoitir.dddcqrstoolkit.valueobject;

import java.io.Serial;
import java.io.Serializable;

public record Version(int version) implements Comparable<Version>, Serializable {

    @Serial
    private static final long serialVersionUID = 5412681989720822033L;


    public Version {
        if (version < 0) {
            throw new IllegalArgumentException("Version cannot be negative");
        }
    }

    public static Version zero() {
        return new Version(0);
    }

    public Version increment() {
        return new Version(this.version + 1);
    }

    @Override
    public int compareTo(final Version val) {
        return Integer.compare(this.version, val.version);
    }

}
