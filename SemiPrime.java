import java.math.*;
import java.util.*;
import java.io.*;
public class SemiPrime{
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static List<BigInteger> primeDecomp(BigInteger a){
        // impossible for values lower than 2
        if(a.compareTo(TWO) < 0){
            return null; 
        }

        //quickly handle even values
        List<BigInteger> result = new ArrayList<BigInteger>();
        while(a.and(BigInteger.ONE).equals(BigInteger.ZERO)){
            a = a.shiftRight(1);
            result.add(TWO);
        }

        //left with odd values
        if(!a.equals(BigInteger.ONE)){
            BigInteger b = BigInteger.valueOf(3);
            while(b.compareTo(a) < 0){
                if(b.isProbablePrime(10)){
                    BigInteger[] dr = a.divideAndRemainder(b);
                    if(dr[1].equals(BigInteger.ZERO)){
                        result.add(b);
                        a = dr[0];
                    }
                }
                b = b.add(TWO);
            }
            result.add(b); //b will always be prime here...
        }
        return result;
    }

    public static boolean isSemi(BigInteger x){
        List<BigInteger> decomp = primeDecomp(x);
        return decomp != null && decomp.size() == 2;
    }

    public static ArrayList<Integer> primeFactors(long number) {
        ArrayList<Integer> primefactors = new ArrayList<Integer>();
        long copyOfInput = number;
        //   long startTime = System.currentTimeMillis();
        for (int i = 2; i <= copyOfInput; i++) {
            if (copyOfInput % i == 0) {
                primefactors.add(i); // prime factor
                copyOfInput /= i;
                i--;
            }
        }

        //     long stopTime = System.currentTimeMillis();
        //    long elapsedTime = stopTime - startTime;
        //      System.out.println(elapsedTime);
        return primefactors;
    }

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        PrintWriter writer = null;

        double count_true = 0;
        double count = 0;
        try
        {
            writer = new PrintWriter("/Users/Sid/Desktop/semiprimes_test2.txt");
            for(int i = 100000; i <= 110000; i++){
                if(isSemi(BigInteger.valueOf(i))){

                    ArrayList a = primeFactors(i);
                    if (a.size() == 2 && a.get(0).toString().length() == a.get(1).toString().length())
                    {
                        writer.print(i + ",");
                        writer.println(a.get(0) + "," + a.get(1) + " ");
                        // boolean b = Factorize.eq1(i, (Integer)(a.get(0)), (Integer)(a.get(1)));
                        // if (b)
                        //  {
                        //    count_true++;
                        //  }
                        // writer.println(b);
                        // count++;
                    }
                }
            }
        }
        catch (IOException e)
        {
        }
        writer.close();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime + "ms");

    }
}