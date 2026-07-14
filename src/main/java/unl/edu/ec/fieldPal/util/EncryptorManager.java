package unl.edu.ec.fieldPal.util;

import unl.edu.ec.fieldPal.exception.EncryptorException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 */

public class EncryptorManager {

    private static final String ALGORITHM = "AES"; //Advanced Encryption Standard
    private static final String DEFAULT_PRIVATE_KEY = "_tuClaveEnBase64"; // Las claves deben contener 16 caracteres

    /**
     * Generación de la clave secreta
     * @param key texto (la clave)
     * @return Convierte un texto (la clave) en un objeto SecretKey válido para AES.
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey getSecretKey(final String key) throws NoSuchAlgorithmException {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    }

    private static SecretKey getSecretKey() throws NoSuchAlgorithmException {
        return new SecretKeySpec(DEFAULT_PRIVATE_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    }

    /**
     * Genera una clave AES aleatoria
     * @return Devuelve la clave en Base64
     * @throws Exception
     */
    public static String generateKeySecretToStr() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
        generator.init(256); // Llave de 256 bits

        SecretKey secretKey = generator.generateKey();
        byte[] claveBytes = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(claveBytes);
    }

    /**
     * Encriptar texto (con clave por defecto)
     * @param text
     * @return
     * @throws Exception
     */

    public static String encrypt(String text) throws EncryptorException {
        try{
            Cipher cipher = cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            // Convertimos el mensaje a bytes y lo encriptamos
            byte[] encryptText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            // Convertimos el resultado a Base64 (para que sea texto legible)
            return Base64.getEncoder().encodeToString(encryptText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new EncryptorException(e.getMessage(), e);
        }
    }

    /**
     * Encriptar texto con una clave personalizada
     * @param text
     * @param keySecret clave personalizada
     * @return
     * @throws Exception
     */
    public static String encrypt(String text, String keySecret)
            throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(keySecret));
        // Convertimos el mensaje a bytes y lo encriptamos
        byte[] encryptText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        // Convertimos el resultado a Base64 (para que sea texto legible)
        return Base64.getEncoder().encodeToString(encryptText);
    }

    /**
     * Desencriptar texto (clave por defecto)
     * @param textEncrypted
     * @return
     * @throws Exception
     */
    public static String decrypt(String textEncrypted) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
        // Convertimos el Base64 a bytes normales
        byte[] bytesEncriypted = Base64.getDecoder().decode(textEncrypted);
        // Desencriptamos los bytes
        byte[] bytesOriginals = cipher.doFinal(bytesEncriypted);
        // Convertimos los bytes a texto normal
        return new String(bytesOriginals, StandardCharsets.UTF_8);
    }

    /**
     * Desencriptar texto con clave personalizada
     * @param textEncrypted
     * @param keySecret con una clave personalizada
     * @return
     * @throws Exception
     */
    public static String decrypt(String textEncrypted, String keySecret) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(keySecret));

        // Convertimos el Base64 a bytes normales
        byte[] bytesEncriypted = Base64.getDecoder().decode(textEncrypted);
        // Desencriptamos los bytes
        byte[] bytesOriginals = cipher.doFinal(bytesEncriypted);
        // Convertimos los bytes a texto normal
        return new String(bytesOriginals, StandardCharsets.UTF_8);
    }
}
