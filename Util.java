import java.math.BigInteger;
import java.util.*;

/**
The Util class provides useful methods needed in the RSA class
*/
public class Util {
   /**
      Modify your Extended Euclidean implementation in part 1
      to a BigInteger version
   */
   public static BigInteger[] eed(BigInteger r0, BigInteger r1) {
      BigInteger s0 = BigInteger.valueOf(1);
      BigInteger s1 = BigInteger.valueOf(0);
      BigInteger t0 = BigInteger.valueOf(0);
      BigInteger t1 = BigInteger.valueOf(1);
      BigInteger r0g = r0;
      BigInteger r1g = r1;
      while(!r1.equals(BigInteger.valueOf(0))){
         
         if(!BigInteger.valueOf(1).equals(gcd(r0,r1))){
            break;
         }
         
         // Compute the set of Q
         ArrayList<BigInteger> q = new ArrayList<BigInteger>();
         BigInteger r2 = BigInteger.TEN;
         while(!r2.equals(BigInteger.valueOf(0))){
            
            int value = (int)r0.intValue()/(int)r1.intValue();
            q.add(BigInteger.valueOf(value));
            
            BigInteger step2 = BigInteger.valueOf(value).multiply(r1);
            
            r2 = r0.subtract(step2);
            r0 = r1;
            r1 = r2;
            
         }
         
        // Compute Rx Sx Tx
         int c = 0;
         BigInteger rx = BigInteger.TEN;
         BigInteger sx = BigInteger.ZERO;
         BigInteger tx = BigInteger.ZERO;
         r1 = r1g;
         r0 = r0g;
         while(!r1.equals(BigInteger.valueOf(0))){
            rx = r0.subtract(q.get(c).multiply(r1));
            sx = s0.subtract(q.get(c).multiply(s1));
            tx = t0.subtract(q.get(c).multiply(t1));
            
            r0 = r1;
            r1 = rx;
            s0 = s1;
            s1 = sx;
            t0 = t1;
            t1 = tx;
            
            c++;
         }
         
         
      }
      BigInteger[] array = new BigInteger[3];
      array[0] = r0;
      array[1] = s0;
      array[2] = t0;
     
      return array;
   }
   
   public static BigInteger gcd(BigInteger r0, BigInteger r1){
      while(r1 != BigInteger.ZERO){
         BigInteger r2 = r0.mod(r1);
         r0 = r1;
         r1 = r2;
      }
      return r0;
   }
        
   
   /**
      The modPow method is used for RSA encryption and decryption 
      Use recursion to implement the fast exponent idea we discussed (or will discuss soon)      
      @return a BigInteger whose value is (x^e mod n)
   */
   public static BigInteger modPow(BigInteger x, BigInteger e, BigInteger n){
      
      // x = x.mod(n);
      
      
      if(e.equals(BigInteger.ZERO)){
         return BigInteger.ONE;
      }
      if(e.equals(BigInteger.ONE)){
         return x;
      }
   
   
      BigInteger res = BigInteger.ZERO;
      //if e is even
      if(e.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
         res = modPow(x,e.divide(new BigInteger("2")),n);
         return res.multiply(res).mod(n);
      }
      //if e is odd
      else{
         //res = modPow(x,e-1,n)
         res = x.multiply(modPow(x,e.subtract(BigInteger.ONE).divide(new BigInteger("2")),n));
         return res.multiply(res).multiply(x).mod(n);        
      }
//       return null;
   }
   
   /**
      The probablePrime method is used to generate primes p and q in 
      RSA key generation
      Use Miller–Rabin Primality Test (Sec 7.6.2) to generate a prime    
      @param bitLength maximum bit length of prime to be generated
      @param s security parameter
      @return a probable prime
   */
   public static BigInteger probablePrime(int bitLength, int s){
      
      
      // Say we get bitLength = 4  0000 min = 0, max = 1111 = 15
      boolean prime = false;
      while(prime != true){
      // Given bitlength of 4, we should generate numbers from 0-15
      // int random = (int)(range * Math.random());
      //       System.out.println("Random: " + random);
      //       
      
      // Okay, so we've now generated a random number based on bitlength
      // Now we must put it through the miller-rabin primality test
      
      //p-1 = 2^u * r
         BigInteger random = new BigInteger(bitLength, new Random());
         if(random.mod(new BigInteger("2")).equals(BigInteger.ZERO)){
            random = random.add(BigInteger.ONE); // Random number
         }
         
//          System.out.println("Randie: " + random);
      
      // Find u and r
         int u = 0;
         BigInteger r = BigInteger.ZERO;
         BigInteger p = random;
//          System.out.println("Randie-1: " + random);
         BigInteger testVal = p.subtract(BigInteger.ONE);
         while(true){
            if(!testVal.mod(new BigInteger("2")).equals(BigInteger.ZERO)){
               break;
            }
            u++;
            testVal = testVal.divide(new BigInteger("2"));
         }
         r = testVal;
         //2^u * r
//          System.out.println("Got out: u:" + u + " r: " + r + " p: " + p);
      
      // p = random number u = power r=num to multiply
      
         for(int i=0;i<s;i++){
            prime = primeTest(u,r,p,s,bitLength);
         }
      
         if(prime == true){
            return p;
         }
      
      }
      return BigInteger.ZERO;
   }
   
   public static boolean primeTest(int u, BigInteger r, BigInteger p, int s, int bitLength){
      //2u * r  
//       System.out.println("Inside prime test");

      
      //for
      for(int i = 0; i < s; i++){  
      
      
         //2 + random 
         BigInteger a = new BigInteger(bitLength,new Random());
         
         
//          System.out.print("Negative exponent: " + r);
         //a^r % p
         BigInteger z = modPow(a,r,p);
//          System.out.println(z);


         if(!z.equals(BigInteger.ONE) || !z.equals(p.subtract(BigInteger.ONE)) ){
            
            for(int j = 0; j<=u-1; j++){

               z = z.multiply(z).mod(p);
               
               if(z.equals(BigInteger.ONE)){
                  return false;
               }
//                System.out.println(j);

            }
            if(!z.equals(p.subtract(BigInteger.ONE))){
                  return false;
            }
//             System.out.println(s);
         }//init if
      }//for
      return true;
   }
   
   
   public static void main(String[] args){
      // BigInteger[] r0s = {BigInteger.valueOf(10), BigInteger.valueOf(973), BigInteger.valueOf(973), BigInteger.valueOf(973333333), BigInteger.valueOf(973333333), BigInteger.valueOf(523456789)};
   //       BigInteger[] r1s = {BigInteger.valueOf(5), BigInteger.valueOf(299), BigInteger.valueOf(301), BigInteger.valueOf(30000), BigInteger.valueOf(56789010), BigInteger.valueOf(67890982)};
   //       for(int i=0; i<r0s.length; i++) {
   //          BigInteger r0 = r0s[i];
   //          BigInteger r1 = r1s[i];
   //          BigInteger[] array = eed(r0, r1);
   //       
   //          System.out.println(array[0] + " " + array[1] + " " + array[2]); //true
   //       
   //       }
   
         // System.out.println(modPow(BigInteger.valueOf(23895),BigInteger.valueOf(15),BigInteger.valueOf(14189)));
   
         
         
        
   
   }

}


