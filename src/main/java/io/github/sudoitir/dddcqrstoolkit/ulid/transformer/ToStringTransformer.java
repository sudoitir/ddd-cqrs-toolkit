package io.github.sudoitir.dddcqrstoolkit.ulid.transformer;


import io.github.sudoitir.dddcqrstoolkit.ulid.ULID;

public class ToStringTransformer implements ValueTransformer {

    public String transform(ULID ulid) {
        return ulid.toString();
    }

    public ULID parse(Object value) {
        return ULID.from((String) value);
    }

    private static class SingletonHelper {
        private static final ToStringTransformer INSTANCE = new ToStringTransformer();
    }

    public static ToStringTransformer getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private ToStringTransformer() {
    }
}
