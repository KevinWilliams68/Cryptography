import java.util.Arrays;

public class SDES {
    public static byte[] Encrypt(byte[] rawkey, byte[] plaintext) {
		
		//the keys that we got in generate keys
        byte[] key1 = key1(rawkey);
        byte[] key2 = key2(rawkey);
		
		//This is the IP. It is given in the book but like in generate key (permuted key) subtract by one on each number.
		byte[] ciphertext = { plaintext[1], plaintext[5], plaintext[2], plaintext[0], plaintext[3], plaintext[7], plaintext[4], plaintext[6] };
		
		ciphertext = fk(ciphertext, key1);
		
		ciphertext = switchFunction(ciphertext);
		
		ciphertext = fk(ciphertext, key2);
		
		byte[] result = { ciphertext[3], ciphertext[0], ciphertext[2], ciphertext[4], ciphertext[6], ciphertext[1], ciphertext[7], ciphertext[5] };
		return result;
	}
	
	public static byte[] Decrypt(byte[] rawkey, byte[] ciphertext) {
		
		//the keys that we got in generate keys
        byte[] key1 = key1(rawkey);
        byte[] key2 = key2(rawkey);
		//
		byte[] plaintext = { ciphertext[1], ciphertext[5], ciphertext[2], ciphertext[0], ciphertext[3], ciphertext[7], ciphertext[4], ciphertext[6] };

		plaintext = fk(plaintext, key2);
		
		plaintext = switchFunction(plaintext);
		
		plaintext = fk(plaintext, key1);
		byte[] result = { plaintext[3], plaintext[0], plaintext[2], plaintext[4], plaintext[6], plaintext[1], plaintext[7], plaintext[5] };
		return result;
	}
    
    private static byte[] key1(byte[] rawKey)
    {
        byte[] permutate = { rawKey[2], rawKey[4], rawKey[1], rawKey[6], rawKey[3], rawKey[9], rawKey[0], rawKey[8], rawKey[7], rawKey[5]};


		permutate = shiftLeft(permutate, 0, 5);
		permutate = shiftLeft(permutate, 5, 10);	
		
		//move P10 to get P8
		byte[] key1 = { permutate[5], permutate[2], permutate[6], permutate[3], permutate[7], permutate[4], permutate[9], permutate[8] };
		
        return key1;
    }
    private static byte[] key2(byte[] rawKey)
    {
        byte[] permutate = { rawKey[2], rawKey[4], rawKey[1], rawKey[6], rawKey[3], rawKey[9], rawKey[0], rawKey[8], rawKey[7], rawKey[5]};

        permutate = shiftLeft(permutate, 0, 5); 
        permutate = shiftLeft(permutate, 0, 5);
        permutate = shiftLeft(permutate, 0, 5);
        permutate = shiftLeft(permutate, 5, 10); 
        permutate = shiftLeft(permutate, 5, 10);
        permutate = shiftLeft(permutate, 5, 10);
		byte[] key2 = { permutate[5], permutate[2], permutate[6], permutate[3], permutate[7], permutate[4], permutate[9], permutate[8] };
        return key2;
    }

	//byte array was byte key.
	private static byte[] shiftLeft(byte[] array, int start, int end) { // End is exclusive
		byte[] newArray = new byte[array.length];
		for(int i = 0; i < start; i++){
			newArray[i] = array[i];
		}
		
		// Start Shift
		newArray[end - 1] = array[start];
		
		for(int i = start; i < end - 1; i++){
			newArray[i] = array[i+1];
		}
		
		// End Shift
		
		for(int i = end; i < array.length; i++){
			newArray[i] = array[i];
		}
		
		return newArray;
	}
	
	//This is to get the IP^-1. 
	public static byte[] fk(byte[] plaintext, byte[] key){
		byte[] output = new byte[8]; //rightmost 4 bits.
        byte[] right = {plaintext[4],plaintext[5],plaintext[6],plaintext[7]};
        byte[] left = {plaintext[0],plaintext[1],plaintext[2],plaintext[3]};
		
		byte[] toXOR = SK(right, key);
		
		for(int i = 0; i < 4; i++){
			// Set to XOR
			output[i] = (byte) (left[i] == toXOR[i] ? 0 : 1);
		}	
        for(int i = 4; i < 8; i++)
        {
            output[i] = right[i - 4];
        }
		
		return output;
	}
	
	private static byte[] SK(byte[] right, byte[] key){
		// Expansion Permutation
		byte[] EP = {right[3], right[0], right[1], right[2], right[1], right[2], right[3], right[0] };
		
		byte[] s0 = {1,0,3,2,3,2,1,0,0,2,1,3,3,1,3,2};
		byte[] s1 = {0,1,2,3,2,0,1,3,3,0,1,0,2,1,0,3};
		
        for(int i = 0; i < EP.length; i++)
        {
            EP[i] = (byte) (key[i] == EP[i] ? 0 : 1);
        }
		
		byte[] sBox0 = findSbox(0, EP, s0);
		byte[] sBox1 = findSbox(1, EP, s1);
		
		byte[] output = {sBox0[1], sBox1[1], sBox1[0], sBox0[0] };
		

		
		return output;
	}
	
	
	private static byte[] findSbox(int row, byte[] pbox, byte[] diagram){
		int decimal0;
        int decimal1;
        int solution;
        byte[] binary = new byte[2];
		if(row == 0){
			
            decimal0 = pbox[0] * 2 + pbox[3];
	
            decimal1 = pbox[1] * 2 + pbox[2];

            solution = diagram[decimal0 * 4 + decimal1];
            binary[0] = (byte) (solution >= 2 ? 1 : 0);
		    binary[1] = (byte) (solution % 2 == 1 ? 1 : 0);


		} else {
			
			//S1
			decimal0 = pbox[4] * 2 + pbox[7];
            decimal1 = pbox[5] * 2 + pbox[6];
            solution = diagram[decimal0 * 4 + decimal1];
            binary[0] = (byte) (solution >= 2 ? 1 : 0);
		    binary[1] = (byte) (solution % 2 == 1 ? 1 : 0);
		}
		
		return binary;
 
	}
	
	//Switching 0123 4567 become 4567 0123
	private static byte[] switchFunction(byte[] text){
		byte[] switched = {text[4], text[5], text[6], text[7], text[0], text[1], text[2], text[3]};
		return switched;
	}
	
	

    
    public static void main(String[] args) {
        byte[] rawkey1 = {0,0,0,0,0,0,0,0,0,0};
        byte[] plaintext1 = {0,0,0,0,0,0,0,0};
        byte[] rawkey2 = {1,1,1,1,1,1,1,1,1,1};
        byte[] plaintext2 = {1,1,1,1,1,1,1,1};
        byte[] rawkey3 = {0,0,0,0,0,1,1,1,1,1};
        byte[] plaintext3 = {0,0,0,0,0,0,0,0};
        byte[] rawkey4 = {0,0,0,0,0,1,1,1,1,1};
        byte[] plaintext4 = {1,1,1,1,1,1,1,1};
        
        byte[] answer1 = Encrypt(rawkey1, plaintext1);
        byte[] answer2 = Encrypt(rawkey2, plaintext2);
        byte[] answer3 = Encrypt(rawkey3, plaintext3);
        byte[] answer4 = Encrypt(rawkey4, plaintext4);

        System.out.println(Arrays.toString(answer1));
        System.out.println(Arrays.toString(answer2));
        System.out.println(Arrays.toString(answer3));
        System.out.println(Arrays.toString(answer4));

        byte[] rawkey5 = {0,0,0,0,0,0,0,0,0,0};
        byte[] ciphertext1 = {0,0,0,0,0,0,0,0};
        byte[] rawkey6 = {1,1,1,1,1,1,1,1,1,1};
        byte[] ciphertext2 = {1,1,1,1,1,1,1,1};
        byte[] rawkey7 = {0,0,0,0,0,1,1,1,1,1};
        byte[] ciphertext3 = {0,0,0,0,0,0,0,0};
        byte[] rawkey8 = {0,0,0,0,0,1,1,1,1,1};
        byte[] ciphertext4 = {1,1,1,1,1,1,1,1};
        
        byte[] answer5 = Decrypt(rawkey5, ciphertext1);
        byte[] answer6 = Decrypt(rawkey6, ciphertext2);
        byte[] answer7 = Decrypt(rawkey7, ciphertext3);
        byte[] answer8 = Decrypt(rawkey8, ciphertext4);

        System.out.println(Arrays.toString(answer5));
        System.out.println(Arrays.toString(answer6));
        System.out.println(Arrays.toString(answer7));
        System.out.println(Arrays.toString(answer8));
    }
}
