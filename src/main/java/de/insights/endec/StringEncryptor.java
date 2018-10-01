package de.insights.endec;

import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.insights.endec.*;

import java.util.Map;

public class StringEncryptor extends StringSerializer implements Encryptor<String> {
    Logger log = LoggerFactory.getLogger(StringEncryptor.class);
    private String encoding = "UTF8";
    private int encryptionType;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.serializer.encoding" : "value.serializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if (encodingValue == null)
            encodingValue = configs.get("serializer.encoding");
        if (encodingValue != null && encodingValue instanceof String)
            encoding = (String) encodingValue;
        encryptionType = (Integer) configs.get("encryption");
    }


    @Override
    public byte[] serialize(String topic, String data) {
        try {

            byte[] serialized = super.serialize(topic, data);
            byte encrypted[];
            if(encryptionType == 1) {
                encrypted = encrypt(topic, serialized);
            }
            else {
                encrypted = encrypt2(topic, serialized);
            }

            return encrypted;
        } catch (NullPointerException exe) {
            log.error("Encountered Null pointer exception " + exe.getMessage());
            byte[] sample = "Hello".getBytes();
            return sample;
        }

    }

    @Override
    public byte[] encrypt(String topic, byte[] serialized) {

        for (int i = 0; i < serialized.length; i++) {
            serialized[i] = (byte) (serialized[i] + 2);
        }
        return serialized;
    }
    public byte[] encrypt2(String topic, byte[] serialized) {

        for (int i = 0; i < serialized.length; i++) {
            serialized[i] = (byte) (serialized[i] + 4);
        }
        return serialized;
    }
}
