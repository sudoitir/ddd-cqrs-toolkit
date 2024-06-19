package io.github.sudoitir.dddcqrstoolkit.ulid.transformer;


import io.github.sudoitir.dddcqrstoolkit.ulid.ULID;

import java.io.Serializable;

public interface ValueTransformer {
    Serializable transform(ULID ulid);

    ULID parse(Object value);
}
