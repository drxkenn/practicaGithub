package util;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES1 {

    private DES1() {
    }

    public static String cifrar(String encryptText, String key) {
        if (encryptText == null || key == null) {
            throw new IllegalArgumentException("No se puede cifrar valores nulos");
        }
        try {
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(encryptText.getBytes(Charset.forName("UTF-8")));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException |
                NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException("El cifrado fallo", e);
        }
    }

    public static String decifrar(String decryptText, String key) {
        if (decryptText == null || key == null) {
            throw new IllegalArgumentException("No se puede cifrar valores nulos");
        }
        try {
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(decryptText));
            return new String(bytes, Charset.forName("UTF-8"));
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException |
                NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException("El descifrado fallo", e);
        }
    }

    public static void main(String[] args) {
        String texto = "chiquitin06";
        String clave = "12345678";
        
        // Cifrar el texto
        String textocifrado = DES1.cifrar(texto, clave);
        System.out.println("Texto cifrado: " + textocifrado);
        
        // Descifrar el texto cifrado
        String textodescifrado = DES1.decifrar(textocifrado, clave);
        System.out.println("Texto descifrado: " + textodescifrado);
    }
}
