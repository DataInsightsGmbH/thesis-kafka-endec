package de.insights.endec;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.runtime.WorkerConfig;

import java.util.Map;

public class StandaloneEndeconfig extends WorkerConfig {
    private static final ConfigDef CONFIG;

    /**
     * <code>offset.storage.file.filename</code>
     */
    public static final String OFFSET_STORAGE_FILE_FILENAME_CONFIG = "offset.storage.file.filename";
    private static final String OFFSET_STORAGE_FILE_FILENAME_DOC = "File to store offset data in";
    private static final String Encryption_Param = "encryption";
    private static final String Encryption_doc = "Encryption Parameter";
    private static final String Decryption_doc = "Decryption Parameter";
    private static final String Encryption_class = "enctype";
    private static final String Decryption_class = "dectype";
    private static final String Secret_key="secretKey";

    static {
        CONFIG = baseConfigDef()
            .define(OFFSET_STORAGE_FILE_FILENAME_CONFIG,
                ConfigDef.Type.STRING,
                ConfigDef.Importance.HIGH,
                OFFSET_STORAGE_FILE_FILENAME_DOC)
            .define(Encryption_Param,ConfigDef.Type.INT,ConfigDef.Importance.HIGH,Encryption_doc)
            .define(Encryption_class, ConfigDef.Type.STRING,ConfigDef.Importance.HIGH,Encryption_doc)
            .define(Decryption_class, ConfigDef.Type.STRING,ConfigDef.Importance.HIGH,Decryption_doc)
            .define(Secret_key,ConfigDef.Type.STRING,ConfigDef.Importance.HIGH,Encryption_doc);
    }
    public StandaloneEndeconfig(Map<String, String> props) {
        super(CONFIG, props);

    }
}
