import java.math.BigInteger;
import java.security.SecureRandom;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;

public class RSAGenKey
{
    private final static BigInteger one      = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();


public static void RSAk(int k)
{    

    BigInteger p = BigInteger.probablePrime(k, random);
    BigInteger q = BigInteger.probablePrime(k, random);
    BigInteger N = p.multiply(q);
    BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
    BigInteger e = BigInteger.probablePrime(k, random);
    BigInteger d = e.modInverse(phi);

    try {
        FileWriter myWriter = new FileWriter("pub_key.txt");
        myWriter.write("e = " + e + "\n" + "n = " + N);
        myWriter.close();
        FileWriter Writer = new FileWriter("pri_key.txt");
        Writer.write("d = " + d + "\n" + "n = " + N);
        Writer.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException ex) {
        System.out.println("An error occurred.");
        ex.printStackTrace();
      }
    
}

public static void RSA(int p, int q, int e)
{    

    BigInteger P = BigInteger.valueOf(p);
    BigInteger Q = BigInteger.valueOf(p);
    BigInteger N = P.multiply(Q);
    BigInteger phi = (P.subtract(one)).multiply(Q.subtract(one));
    BigInteger E = BigInteger.valueOf(e);
    BigInteger d = E.modInverse(phi);

    try {
        FileWriter myWriter = new FileWriter("pub_key.txt");
        myWriter.write("e = " + e + "\n" + "n = " + N);
        myWriter.close();
        FileWriter Writer = new FileWriter("pri_key.txt");
        Writer.write("d = " + d + "\n" + "n = " + N);
        Writer.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException ex) {
        System.out.println("An error occurred.");
        ex.printStackTrace();
      }
    
}
    public static void main(String[] args)
    {
        if (args.length == 1)
        {
        int k = Integer.parseInt(args[0]);
        RSAk(k);
        System.out.println(0);
        }
        else if (args.length == 3)
        {
            int p = Integer.parseInt(args[0]);
            int q = Integer.parseInt(args[1]);
            int e = Integer.parseInt(args[2]);
            RSA(p,q,e);
        }
        else
        {
            System.out.println("An error occured!");
        }

    }
}