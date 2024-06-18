package io.github.sudoitir.dddcqrstoolkit.valueobject;

import java.io.Serial;
import java.io.Serializable;

public final class Version implements Comparable<Version>, Serializable {

    @Serial
    private static final long serialVersionUID = 5412681989720822033L;

    @Override
    public int compareTo(final Version o) {
        return 0;
    }
}
