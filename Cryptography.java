import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Cryptography {

  public static String encrypt(byte[] messageBinary, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
      byte[] keyBinary = DatatypeConverter.parseHexBinary(key);
      
      SecretKey secretKey = new SecretKeySpec(keyBinary, "AES");

      byte[] iv = new byte[16];
      new Random().nextBytes(iv);

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

      byte[] encryptedText = cipher.doFinal(messageBinary);

      int totalSizeWithIv = iv.length + encryptedText.length;

      byte[] encryptedTextWithIv = ByteBuffer.allocate(totalSizeWithIv).put(iv).put(encryptedText).array();
      
      return DatatypeConverter.printHexBinary(encryptedTextWithIv);
  }


  public static String decrypt(String msg, String key) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException {
      byte[] messageBinary = DatatypeConverter.parseHexBinary(msg);
      byte[] keyBinary = DatatypeConverter.parseHexBinary(key);

      SecretKey secretKey = new SecretKeySpec(keyBinary, "AES");

      byte[] iv = new byte[16];

      System.arraycopy(messageBinary, 0, iv, 0, 16);

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

      byte[] bytes = cipher.doFinal(messageBinary, 16, messageBinary.length - 16);

      return new String(bytes);
  }

  public static String generatePassword(BigInteger V) throws NoSuchAlgorithmException {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

      messageDigest.update(V.toByteArray());

      byte[] bytes = messageDigest.digest();

      return DatatypeConverter.printHexBinary(Arrays.copyOfRange(bytes, 0, 16));
  }
}
