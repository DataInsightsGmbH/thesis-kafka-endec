package de.insights.endec;
import org.apache.kafka.common.serialization.Serializer;

    public interface Encryptor<T> extends Serializer<T> {

        byte[] encrypt(String topic, byte[] data);

        byte[] encrypt2(String topic, byte[] data);

    }


