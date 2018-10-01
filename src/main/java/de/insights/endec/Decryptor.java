package de.insights.endec;
import org.apache.kafka.common.serialization.Deserializer;

/*
An Interface to Decrypt and Deserialize
*/
public interface Decryptor<T> extends Deserializer<T> {

    byte[] decrypt(byte[] data);

    byte[] decrypt2(byte[] data);

}
