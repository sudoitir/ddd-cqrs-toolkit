package io.github.sudoitir.dddcqrstoolkit.ulid.transformer;


import io.github.sudoitir.dddcqrstoolkit.ulid.ULID;

public class PassThroughTransformer implements ValueTransformer {

    private PassThroughTransformer() {
    }

    public static PassThroughTransformer getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public ULID transform(ULID ulid) {
        return ulid;
    }

    public ULID parse(Object value) {
        return (ULID) value;
    }

    private static class SingletonHelper {

        private static final PassThroughTransformer INSTANCE = new PassThroughTransformer();
    }
}
