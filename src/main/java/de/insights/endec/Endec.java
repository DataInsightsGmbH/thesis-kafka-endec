package de.insights.endec;

import org.apache.kafka.common.serialization.Serde;

import java.io.Closeable;
import java.util.Map;

public interface Endec<T> extends Closeable {

    Encryptor<T> encryptor();

    Decryptor<T> decryptor();

    void configure(Map<String, ?> configs, boolean isKey);

    /**
     * Close this endec class, which will close the underlying encrypter and decrypter.
     * This method has to be idempotent because it might be called multiple times.
     */
    @Override
    void close();
}
