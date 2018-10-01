package de.insights.endec;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class StringDecryptor extends StringDeserializer implements Decryptor<String> {

    Logger log= LoggerFactory.getLogger(StringDecryptor.class);
    private int encryptionType;
    private String encoding = "UTF8";

    @Override
    public String deserialize(String topic, byte[] data) {
        try {
            byte[] decrypted;
            if(encryptionType == 1) {
                decrypted = decrypt(data);
            }
            else {
                decrypted = decrypt2(data);
            }
            return super.deserialize(topic, decrypted);
        }
        catch(NullPointerException exe) {
            log.error("Data inside decryptor is null"+exe.getMessage());
            return "Null Pointer";
        }
    }
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String propertyName = isKey ? "key.deserializer.encoding" : "value.deserializer.encoding";
        Object encodingValue = configs.get(propertyName);
        if (encodingValue == null)
            encodingValue = configs.get("deserializer.encoding");
        if (encodingValue != null && encodingValue instanceof String)
            encoding = (String) encodingValue;
        encryptionType = (Integer) configs.get("encryption");

    }
    @Override
    public byte[] decrypt(byte[] data) {

        for(int i=0;i<data.length;i++) {
            data[i]=(byte) (data[i]-2);
        }
        return data;

    }
    @Override
    public byte[] decrypt2(byte[] data) {

        for(int i=0;i<data.length;i++) {
            data[i]=(byte) (data[i]-4);
        }
        return data;

    }
}
