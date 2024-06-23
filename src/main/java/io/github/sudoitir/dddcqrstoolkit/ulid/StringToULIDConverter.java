package io.github.sudoitir.dddcqrstoolkit.ulid;

import io.github.sudoitir.dddcqrstoolkit.ulid.transformer.ToStringTransformer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class StringToULIDConverter implements Converter<String, ULID> {

    @Override
    public ULID convert(@NonNull String source) {
        try {
            return ToStringTransformer.getInstance().parse(source);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ULID format: " + source, e);
        }
    }
}
