package za.ac.ss.helper;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static final String AES = "AES";
    private static final String SECRET = "secret-key-12345";

    private final Key key;
    private final Cipher cipher;

    public AttributeEncryptor() throws Exception {
        key = new SecretKeySpec(SECRET.getBytes(), AES);
        cipher = Cipher.getInstance(AES);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
    	Key key = new SecretKeySpec(SECRET.getBytes(), AES);
        try {
        	if (attribute == null)
                return null;
        	cipher.init(Cipher.ENCRYPT_MODE, key);
			return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8)));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
        	log.error(" {} {} ", attribute, e.getMessage());
        	throw new IllegalStateException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
    	Key key = new SecretKeySpec(SECRET.getBytes(), AES);
        try {
        	if (dbData == null)
                return null;
        	cipher.init(Cipher.DECRYPT_MODE, key);
			return new String(cipher.doFinal(Base64.getDecoder().decode(dbData.getBytes(StandardCharsets.UTF_8))));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
        	log.error(" {} {} ", dbData, e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }
}
