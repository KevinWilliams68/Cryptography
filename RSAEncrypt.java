import java.math.BigInteger;
import java.security.SecureRandom;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class RSAEncrypt 
{
    


public static String toNumbers(String data)
{
    String up = data.toUpperCase();
    StringBuilder sb = new StringBuilder();
    String key = " ";
    for (char c : up.toCharArray())
    {
    if(((int)c - 65) < 0)
    {
        sb.append(26);
        
    }
    else if(((int)c - 65 ) == 0)
    {
        sb.append(0);
        sb.append(0);
        
    }
    else if(((int)c - 65) < 10)
    {
        sb.append(0);
        sb.append((int)c - 65);
        
    }
    else
    {
        sb.append((int)c - 65);
        
    }
}

    key = sb.toString();
    return key;

}

static int getValue(String data)
{
    int ans = 0;
    String[] words = data.split(" ");
    ans = Integer.parseInt(words[2]);
    return ans;
}


public static void Encrypt(int E, int N, String key)
{

    BigInteger e = BigInteger.valueOf(E);
    BigInteger mod = BigInteger.valueOf(N);
    BigInteger bi1, bi2;
    int n = 6;
    StringBuilder str = new StringBuilder(key);
    int idx = str.length() - n;
    while (idx > 0)
    {
        str.insert(idx, " ");
        idx = idx - n;
    }
    String toNum = str.toString();

    String[] strArray = toNum.split(" ");
    int[] nums = new int[strArray.length];
    String[] encrypt = new String[nums.length];
    for(int i = 0; i < strArray.length; i++)
    {
        nums[i] = Integer.parseInt(strArray[i]);
        bi1 = BigInteger.valueOf(nums[i]);
        bi2 = bi1.modPow(e, mod);
        encrypt[i] = String.valueOf(bi2);
    }

    try {
        FileWriter writer = new FileWriter("texqqqt.txt", true);
        for (int i = 0; i < encrypt.length; i++) {
            
                writer.write(encrypt[i] + " ");
            
        }
        writer.write("\r\n");   // write new line
        writer.close();
    } catch (IOException x) {
        x.printStackTrace();
    }
}
    public static void main(String[] args)
    {
        int value = 0;
        String euler = null;
        String n = null;
        String data = " ";
        String key = " ";
       // String sb = " ";
        try {
            File myObj = new File("text.txt");
            File myObj1 = new File("pub_key.txt");
            Scanner myReader = new Scanner(myObj);
            Scanner myReader1 = new Scanner(myObj1);
            String line1 = myReader.nextLine();
            String line2 = myReader.nextLine();
            String line3 = myReader.nextLine();
            String line4 = myReader.nextLine();
            String line5 = myReader.nextLine();
            data = line1 + line2 + line3 + line4 + line5;
            euler = myReader1.nextLine();
             n = myReader1.nextLine();

            myReader.close();
            myReader1.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

          int E = getValue(euler);
          int N = getValue(n);
          key = toNumbers(data);
          Encrypt(E,N, key);
          
    }
        
    
}
