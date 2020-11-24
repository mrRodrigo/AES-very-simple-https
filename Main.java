
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.Random;

/*
    * To build: javac -cp .;* *.java 
    * To Run: java Main
    * java path set path=C:\Program Files\Java\jdk1.8.0_271\bin
**/

public class Main {

    public static String p = "B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C69A6A9DCA52D23B616073E28675A23D189838EF1E2EE652C013ECB4AEA906112324975C3CD49B83BFACCBDD7D90C4BD7098488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708DF1FB2BC2E4A4371";
    public static String g = "A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507FD6406CFF14266D31266FEA1E5C41564B777E690F5504F213160217B4B01B886A5E91547F9E2749F4D7FBD7D3B9A92EE1909D0D2263F80A76A6A24C087A091F531DBF0A0169B6A28AD662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24855E6EEB22B3B2E5";
    
    public static String a = "708C96B4EEBED9B66B4EC20FB2C421EDA20547A1CB6714A4C5C2504DE9C2CA68FF881E6615B0A18E6EB617EAB939343A4047BC6AB544AABDCECD87ACAE3B95E37638C1FC04ECC2B41EDEC8C05ED8B611EB2132D40A96877DB7D80F7AEE4819788CC5DB6B5BACB7BC5F6BA7F61814ACD56C8B547767ACE5F34BEEB9BA23537DE5";
    
    public static String B = "00A91147034AB0A7C35620075A30321B81C396B2C54B07058BEE7D8B252EDA0F860BB6CE4E568EF4AD937CF0D8D71C3A91401A05A3ABBC380C72F572571CFCF13B9637110A629E89BF3B1976E75EB2337EE272F74EE0AA1326245114DDEBE41C8855C5336E7466A972BC09397D79BBA5808D103A7C484D6891FBD71C01036EC8A5";



    public static void main(String[] args) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        // A enviado por email
        String sentAToEmail = "5B5D877441FD9FAE38B6C5F19A835738C539954627BA73466BF8097938C98EEC10AE62605797643D260E70F98024B99B4CF6869AFB51EE1A22CC9B89400C52FF69944956B081E08020EC5AA8587E05292E25B96C347170E27260AA63E05CF4FEE6221949A191D3C53E7321CFCA6EC820BE3BCAFE409734F3D5A32B47ED160668";
        
        // Mensagem recebida por email
        String encryptedString = "D037B12F42319949AC4980013FC76AA776FDFE753FEC428057EF8836BB655F75B47536F4900E128CA230AC8769EDD9CC147ED79BF3E61DE65072EA87CC67E0F6A8E6356F99D2C173C9DD7D69DAF9FEE01DD687F71F8C6848082190C3A824A006860D3633E9AE22E0401B4E45E46151AA";

        BigInteger V = generateV();
        String S = Cryptography.generatePassword(V);

        System.out.println("===========================");

        System.out.println("Meu valor de A enviado por email:  " + generateA(sentAToEmail));
        System.out.println("Valor de S: " + S);
        
        System.out.println("===========================");

        // mensagem recebida por email decifrada
        String decryptedString = Cryptography.decrypt(encryptedString, S);
        System.out.println("=> Mensagem recebida em texto claro: " + decryptedString);

        // mensagem recebida por email decifrada e invertida
        String decryptedStringReversed = new StringBuilder(decryptedString).reverse().toString();
        System.out.println("=> Mensagem em texto claro invertida: " + decryptedStringReversed);

        // Mensagem revertida, cifrada e enviada por emai
        String encryptedStringReversed = Cryptography.encrypt(decryptedStringReversed.getBytes(), S);
        System.out.println("=> Mensagem revertida, cifrada e enviada por email: " + encryptedStringReversed);

    }

    public static String generateA(String sentByEmail) {
        // sempre retorna A enviado por email
        if(sentByEmail != null && !sentByEmail.isEmpty()) return sentByEmail;

        // logica para gerar A
        Random random = new Random();
        BigInteger _a = new BigInteger(new BigInteger(p, 16).bitLength() - 1, random);
        BigInteger A = modPow(g, a, p);
        return A.toString(16);
    } 

    public static BigInteger generateV() {
        return modPow(B, a, p);
    }

    public static BigInteger modPow(String _number, String _exp, String _mod) {
        BigInteger number = new BigInteger(_number, 16);

        return number.modPow(new BigInteger(_exp, 16), new BigInteger(_mod, 16));
    }
}
