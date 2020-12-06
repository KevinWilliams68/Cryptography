import java.util.Arrays;

public class TripleDES {
    
    public static byte[] Encrypt( byte[] rawkey1, byte[] rawkey2, byte[] plaintext )
    {
        //E3DES(p) = EDES(k1,DDES(k2,EDES(k1, p)))
        byte[] encrypt1 = SDES.Encrypt(rawkey1, plaintext);
        byte[] decrypt = SDES.Decrypt(rawkey2, encrypt1);
        byte[] encrypt2 = SDES.Encrypt(rawkey1, decrypt);

        return encrypt2;
    }
    public static byte[] Decrypt( byte[] rawkey1, byte[] rawkey2, byte[] ciphertext )
    {
        //D3DES(c) = DDES(k1,EDES(k2,DDES(k1, c)))
        byte[] decrypt1 = SDES.Decrypt(rawkey1, ciphertext);
        byte[] encrypt = SDES.Encrypt(rawkey2, decrypt1);
        byte[] decrypt2 = SDES.Decrypt(rawkey1, encrypt);
        
        return decrypt2;
    }



    public static void main(String[] args) 
    {
        //Encrypt

        byte[] rawkeya = {0,0,0,0,0,0,0,0,0,0};
        byte[] rawkeyb = {0,0,0,0,0,0,0,0,0,0};
        byte[] plaintexta = {0,0,0,0,0,0,0,0,0,0};
        byte[] answera = Encrypt(rawkeya, rawkeyb, plaintexta);

        byte[] rawkeyc = {1,0,0,0,1,0,1,1,1,0};
        byte[] rawkeyd = {0,1,1,0,1,0,1,1,1,0};
        byte[] plaintextb = {1,1,0,1,0,1,1,1};
        byte[] answerb = Encrypt(rawkeyc, rawkeyd, plaintextb);

        byte[] rawkeye = {1,0,0,0,1,0,1,1,1,0};
        byte[] rawkeyf = {0,1,1,0,1,0,1,1,1,0};
        byte[] plaintextc = {1,0,1,0,1,0,1,0};
        byte[] answerc = Encrypt(rawkeye, rawkeyf, plaintextc);

        byte[] rawkeyg = {1,1,1,1,1,1,1,1,1,1};
        byte[] rawkeyh = {1,1,1,1,1,1,1,1,1,1};
        byte[] plaintextd = {1,0,1,0,1,0,1,0};
        byte[] answerd = Encrypt(rawkeyg, rawkeyh, plaintextd);

        //Decrypt

        byte[] rawkeyi = {1,0,0,0,1,0,1,1,1,0};
        byte[] rawkeyj = {0,1,1,0,1,0,1,1,1,0};
        byte[] ciphertexta = {1,1,1,0,0,1,1,0};
        byte[] answere = Decrypt(rawkeyi, rawkeyj, ciphertexta);

        byte[] rawkeyk = {1,0,1,1,1,0,1,1,1,1};
        byte[] rawkeyl = {0,1,1,0,1,0,1,1,1,0};
        byte[] ciphertextb = {0,1,0,1,0,0,0,0};
        byte[] answerf = Decrypt(rawkeyk, rawkeyl, ciphertextb);

        byte[] rawkeym = {0,0,0,0,0,0,0,0,0,0};
        byte[] rawkeyn = {0,0,0,0,0,0,0,0,0,0};
        byte[] ciphertextc = {1,0,0,0,0,0,0,0};
        byte[] answerg = Decrypt(rawkeym, rawkeyn, ciphertextc);

        byte[] rawkeyo = {1,1,1,1,1,1,1,1,1,1};
        byte[] rawkeyp = {1,1,1,1,1,1,1,1,1,1};
        byte[] ciphertextd = {1,0,0,1,0,0,1,0};
        byte[] answerh = Decrypt(rawkeyo, rawkeyp, ciphertextd);

        //Encrypt
        System.out.println("Ciphertext 1-4");
        System.out.println(Arrays.toString(answera));
        System.out.println(Arrays.toString(answerb));
        System.out.println(Arrays.toString(answerc));
        System.out.println(Arrays.toString(answerd));
        //Decrypt
        System.out.println("Plaintext 5-8");
        System.out.println(Arrays.toString(answere));
        System.out.println(Arrays.toString(answerf));
        System.out.println(Arrays.toString(answerg));
        System.out.println(Arrays.toString(answerh));
    }
}
