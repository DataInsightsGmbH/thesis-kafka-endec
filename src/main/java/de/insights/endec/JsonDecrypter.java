package de.insights.endec;

import com.fasterxml.jackson.databind.JsonNode;
import de.insights.endec.Decryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.connect.json.*;

import java.util.Map;

public class JsonDecrypter extends JsonDeserializer implements Decryptor<JsonNode> {

    Logger log = LoggerFactory.getLogger(JsonDecrypter.class);
    //private String encryptionType;
    private int encryption;
    private String secretKey="default";

    @Override
    public JsonNode deserialize(String topic, byte[] data) {
        try {
            byte decrypted[];
           // if(encryptionType == "de.insights.endec.JsonEncrypter") {
                decrypted = decrypt(data);
            //}
            /*else {
                decrypted = decrypt2(data);
            }*/
            return super.deserialize(topic, decrypted);
        } catch (NullPointerException exe) {
            log.error("Data inside decryptor is null" + exe.getMessage());
            return null;
        }
    }

    @Override
    public void configure(Map<String, ?> config, boolean isKey) {
        log.info("line 35");
        log.info(config.keySet().toString());
        log.info(String.valueOf(config.containsKey("enctype")));
        log.info(String.valueOf(config.containsKey("encryption")));
        if (config.get("encryption") != null) {
            //encryptionType = (String) config.get("enctype");
            encryption = (Integer) config.get("encryption");
            //log.info("37 "+encryptionType);
        } else {
            encryption = 0;
        }
        if (config.get("secretKey") != null) {
            secretKey = (String)config.get("secretKey");
        }
        else
            secretKey="default";
    }

    @Override
    public byte[] decrypt(byte[] data) {

       /* for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (data[i] - encryption);
        }*/
        return data;
/*
    log.info("Inside Json Decryptor "+secretKey);
    log.info("before decryption "+data);
    byte []decrypted = AES.decrypt(data,secretKey);
    log.info("after decryption "+decrypted);
    return  decrypted;
    */
    }
    @Override
    public byte[] decrypt2(byte[] data) {
        for(int i = 0; i< data.length; i++) {
            data[i] = (byte) (data[i] - 4);
        }
        return data;
    }
}
