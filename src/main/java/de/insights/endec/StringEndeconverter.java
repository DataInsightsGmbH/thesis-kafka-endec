package de.insights.endec;

import org.apache.kafka.common.enddec.*;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaAndValue;
import org.apache.kafka.connect.errors.DataException;
import org.apache.kafka.connect.storage.Converter;
import org.apache.kafka.connect.storage.StringConverter;
import de.insights.endec.Endeconverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StringEndeconverter extends StringConverter implements Converter {

    private static final Logger log = LoggerFactory.getLogger(StringConverter.class);
    //private final StringEncryptor encrypter = new StringEncryptor();
    //private final StringDecryptor decrypter = new StringDecryptor();
    private Encryptor<String> encrypter= null;
    private Decryptor<String> decrypter = null ;
    private Object type;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Map<String, Object> serializerConfigs = new HashMap<>();
        serializerConfigs.putAll(configs);
        Map<String, Object> deserializerConfigs = new HashMap<>();
        deserializerConfigs.putAll(configs);

        Object encodingValue = configs.get("converter.encoding");
        if (encodingValue != null) {
            serializerConfigs.put("serializer.encoding", encodingValue);
            deserializerConfigs.put("deserializer.encoding", encodingValue);
        }

        encrypter.configure(serializerConfigs, isKey);
        decrypter.configure(deserializerConfigs, isKey);
    }

    @Override
    public byte[] fromConnectData(String topic, Schema schema, Object value) {
        try {
            log.info("Value of topic is " + topic + " value is " + value);
            return encrypter.serialize(topic, value == null ? null : value.toString());
        } catch (SerializationException e) {
            throw new DataException("Failed to serialize to a string: ", e);
        }
    }

    @Override
    public SchemaAndValue toConnectData(String topic, byte[] value) {
        try {
            return new SchemaAndValue(Schema.OPTIONAL_STRING_SCHEMA, decrypter.deserialize(topic, value));
        } catch (SerializationException e) {
            throw new DataException("Failed to deserialize string: ", e);
        }
    }

}
