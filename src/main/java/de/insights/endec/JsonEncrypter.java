package de.insights.endec;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.connect.json.*;

import java.util.Arrays;
import java.util.Map;


public class JsonEncrypter extends JsonSerializer implements Encryptor<JsonNode> {
    Logger log = LoggerFactory.getLogger(JsonEncrypter.class);
    //private String encryptionType;
    private int encryption;
    private String secretKey="default";

    @Override
    public byte[] serialize(String topic, JsonNode data) {
        try {


            byte[] serialized = super.serialize(topic, data);
            byte[] encrypted;
            //if(encryptionType == "de.insights.endec.JsonEncrypter") {
                encrypted = encrypt(topic, serialized);
            //}
            /*else {
                encrypted = encrypt2(topic, serialized);
            }*/

            return encrypted;
        } catch (NullPointerException exe) {
            log.error("Encountered Null pointer exception " + exe.getMessage());
            byte[] sample = "Hello".getBytes();
            return sample;
        }

    }

    @Override
    public void configure(Map<String, ?> config, boolean isKey) {
        log.info("line 040");
        /*
        try {
            log.info(config.get("enctype").toString());
        }
        catch (NullPointerException exe){

        }*/
        log.info(config.keySet().toString());
        log.info(String.valueOf(config.containsKey("enctype")));
        log.info(String.valueOf(config.containsKey("encryption")));

        if (config.get("encryption") != null) {
            //encryptionType = (String)config.get("enctype");
            encryption = (Integer)config.get("encryption");
        } else {
            //encryptionType = "";
            encryption = 0;
        }
        if (config.get("secretKey") != null) {
            secretKey = (String)config.get("secretKey");
        }
        else
            secretKey="default";
    }

    @Override
    public byte[] encrypt(String topic, byte[] serialized) {

        /*for (int i = 0; i < serialized.length; i++) {
            serialized[i] = (byte) (serialized[i] + encryption);
        }
        return serialized;*/
        /*
        log.info("beforeore encryption "+serialized);
        log.info("inside encrypter "+secretKey);
        byte[] encrypted = AES.encrypt(serialized,secretKey);
        log.info("after encryption "+encrypted);
        return encrypted;*/
        char arr[] = new char[5000];
        Arrays.fill(arr,'x');
        String s=new String(arr);
        log.info(Integer.toString(s.getBytes().length));
        return s.getBytes();

    }

    @Override
    public byte[] encrypt2(String topic, byte[] serialized) {

        for (int i = 0; i < serialized.length; i++) {
            serialized[i] = (byte) (serialized[i] + 4);
        }
        return serialized;
    }


}

