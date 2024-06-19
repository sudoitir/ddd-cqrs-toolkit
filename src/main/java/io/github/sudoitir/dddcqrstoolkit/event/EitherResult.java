package io.github.sudoitir.dddcqrstoolkit.event;

import io.vavr.control.Either;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class EitherResult {

    private EitherResult() {
        throw new IllegalArgumentException();
    }

    public static <L, R> Either<L, R> announceFailure(L left) {
        return left(left);
    }

    public static <L, R> Either<L, R> announceSuccess(R right) {
        return right(right);
    }
}
