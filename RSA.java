import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Random;

class RSA{
   private int bitLength;//maximum bit length for primes p and q
   private int s; //security parameter used to generate primes
   private BigInteger p;
   private BigInteger q;
   private BigInteger n;
   private BigInteger phiN;
   private BigInteger e, d;
   private Util u = new Util();
   /**
      RSA Key Generation 
      @param bitLength the maximum bit length of primes p and q
      @param s the security parameter
   */
   public RSA(int bitLength, int s){
      this.bitLength = bitLength;
      this.s = s;
      // Do RSA key generation:
      // 1. Generate primes to set this.p and this.q
      Random xx = new Random();
      p = u.probablePrime(bitLength,s);
      q = u.probablePrime(bitLength,s);
      System.out.println(p + " ================== " + q);
      // 2. Compute n
      n = p.multiply(q);
      // 3. Compute phiN
      phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
      // 4. Choose 1< e < phiN such that gcd(e, phiN) = 1
      BigInteger[] arr = null;
      System.out.println(phiN);
      while(true){

         e = new BigInteger(bitLength,xx);
         System.out.println(e + " " + phiN);
         if(e.compareTo(phiN) == -1){
            arr = u.eed(phiN,e);
            //System.out.println(arr[0] + " " + arr[1] + " " + arr[2]);
            if(arr[0].equals(BigInteger.ONE)){
               break;
            }
         }
      }
      System.out.println(arr[0] + " " + arr[1] + " " + arr[2]);

      
      //System.out.println("E: " + e + " PHI N: " + phiN);
      // 5. Compute d such that de = 1 mod phiN
   }
   

   
   /**
      Use your modPow method defined in the Util class      
      @param x plaintext
      @return x^e mod n
   */
   public BigInteger encrypt(BigInteger x){
     //To do
     return null;
   }
   /**
      Use your modPow method defined in the Util class
      @param y ciphertext
      @return y^d mod n
   */
   public BigInteger decrypt(BigInteger y){
     //To do
     return null;
   }
   /**
     Getter - completed.
     @return n   
   */
   public BigInteger getN(){
      return n;
   }
   
   public static void main(String[] args) throws FileNotFoundException{
         int securityParam = 5;
        
         RSA rsa = new RSA(512, securityParam);
         // Scanner in = new Scanner(new File(args[0]));  
//          BigInteger x = new BigInteger(in.nextLine(), 16);
//          //Make sure that x < n
//          if(x.bitLength() >= rsa.getN().bitLength()){
//             System.err.println("bit length of x must be lss than bit length of n");
//             System.exit(2);
//          }else{
//             BigInteger y = rsa.encrypt(x);
//             BigInteger z = rsa.decrypt(y);
//             System.out.println("plaintext x:\n" + x.toString(16));
//             System.out.println("ciphertext y:\n" + y.toString(16));
//             System.out.println("recovered z:\n" + z.toString(16));
//             System.out.println(x.equals(z));
//          }
   }
}