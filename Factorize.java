import java.util.*;
import java.awt.*;
import java.math.BigInteger;
import java.io.PrintWriter;
public class Factorize
{
    public static void brute_force()
    {
        for (float y = 0; y < 100; y++)
        {
            for (float x = 0; x < 100; x++)
            {
              //  for (float y0 = 0; y0 < 10; y0++)
              //  {
              //      for (float y1 = 0; y1 < 10; y1++)
              //      {
                        if ((x*((y%100) / 10) + y*((x%100) / 10) + (x*y-1)/10)%10 == 5)
                        {
                            System.out.println(x + " * " + y);
                        }
              //      }
              //  }
            }
        }
    }

    public static void generate_equations_unsimplified()
    {
        BigInteger x = new BigInteger("1243");
        System.out.println("Generating unsimplified  equations for " + x);
        int digits = x.toString().length();
        String previous = "";
        try{
            PrintWriter p = new PrintWriter("eq_test_us.txt");
            previous = "";
            for (int i = 0; i < digits; i++)
            {
                String s = "";
                for (int j = 0; j <= i; j++)
                {
                    s += "(x" + j + "*y" + (i - j) + ") + ";
                }
                //String temp = new String(s);
                if (previous.length() > 0)
                    s += "((" + previous + " - " + getDigit_BI(x, i) +")/10)";
                else
                    s = s.substring(0, s.length() - 3);
                if (i==digits-1)
                    p.println(getDigit_BI(x, i+1) + "= (" + s + ")");
                else if (i==digits-2)
                    p.println(getDigit_BI(x, i+1) + "= " + s + " - " + (10*getDigit_BI(x, i+2)));    
                else
                    p.println(getDigit_BI(x, i+1) + "= (" + s + ")-10*c" + i);
                previous = s;
            }
            p.close();
        }
        catch (Exception e) {}
    }

    public static void print_equations(BigInteger x)
    {
        System.out.println("Generating equations for " + x);
        int digits = x.toString().length();
        String previous = "";
        try{
            System.out.println("Simplified equations: (assuming x and y have the same number of digits)");
            previous = "";
            for (int i = 0; i < digits-1; i++)
            {
                String s = "";
                for (int j = 0; j <= i; j++)
                {
                    if (!((i-j) > digits/2-1 || j > digits/2-1))
                        s += "(x" + j + "*y" + (i - j) + ") + ";
                }
                if (previous.length() > 0)
                    s += "((" + previous + " - " + getDigit_BI(x, i) +")/10)";
                else
                    s = s.substring(0, s.length() - 3);
                if (i==digits-1)
                    System.out.println(getDigit_BI(x, i+1) + "= (" + s + ")");
                else if (i==digits-2)
                    System.out.println(getDigit_BI(x, i+1) + "= " + s + " - " + (10*getDigit_BI(x, i+2)));    
                else
                    System.out.println(getDigit_BI(x, i+1) + "= (" + s + ")-10*c" + i);
                previous = s;
            }

        }
        catch (Exception e) {}
    }

    public static void generate_equations(BigInteger x)
    {
        System.out.println("Generating equations for " + x);
        /*for (int i = 0; i < digits; i++)
        {
        String s = "";
        for (int j = 0; j <= i; j++)
        {
        s += "(x" + j + "*y" + (i - j + ") + ");
        }
        //String temp = new String(s);
        if (previous.length() > 0)
        s += "floor((" + previous + ")/10)";
        else
        s+="0";
        System.out.println(getDigit_BI(x, i+1) + "= (" + s + ")mod 10");
        previous = s;
        }*/
        //generate_equations(x, true);

        int digits = x.toString().length();
        String previous = "";
        try{
            PrintWriter p = new PrintWriter("eq_test.txt");
            p.println("Simplified equations: (assuming x and y have the same number of digits)");
            previous = "";
            for (int i = 0; i < digits-1; i++)
            {
                String s = "";
                for (int j = 0; j <= i; j++)
                {
                    if (!((i-j) > digits/2-1 || j > digits/2-1))
                        s += "(x" + j + "*y" + (i - j) + ") + ";
                }
                //String temp = new String(s);
                if (previous.length() > 0)
                    s += "((" + previous + " - " + getDigit_BI(x, i) +")/10)";
                else
                    s = s.substring(0, s.length() - 3);
                if (i==digits-1)
                    p.println(getDigit_BI(x, i+1) + "= (" + s + ")");
                else if (i==digits-2)
                    p.println(getDigit_BI(x, i+1) + "= " + s + " - " + (10*getDigit_BI(x, i+2)));    
                else
                    p.println(getDigit_BI(x, i+1) + "= (" + s + ")-10*c" + i);
                   //p.println(Math.tan((getDigit_BI(x, i+1)-5)*3.1415926/10) + " = tan((" + s + "-5)*π/10)");
                previous = s;
            }
            p.close();
        }
        catch (Exception e) {}
    }

    public static void run()
    {
        generate_equations(new BigInteger("5251"));
        //generate_equations(new BigInteger("25195908475657893494027183240048398571429282126204032027777137836043662020707595556264018525880784406918290641249515082189298559149176184502808489120072844992687392807287776735971418347270261896375014971824691165077613379859095700097330459748808428401797429100642458691817195118746121515172654632282216869987549182422433637259085141865462043576798423387184774447920739934236584823824281198163815010674810451660377306056201619676256133844143603833904414952634432190114657544454178424020924616515723350778707749817125772467962926386356373289912154831438167899885040445364023527381951378636564391212010397122822120720357"));
    }

    public static void print_remainders()
    {
        try {
            PrintWriter p = new PrintWriter("remainders.txt");
            for (int num = 1001; num < 10000; num++)
            {
                int digits = (int)Math.ceil(Math.log10(num));
                System.out.println(num);
                ArrayList<Integer> a = SemiPrime.primeFactors(num);
                if(a != null && a.size() == 2)
                {
                    for (int i = 0; i < digits; i++)
                    {
                        int digit = getDigitLong(num, digits-i);
                        p.print(digit + " ");
                    }
                    p.println();

                    if (a != null)
                    {
                        //System.out.println(a.get(0));
                        int[] x = numToArray(a.get(0));
                        int[] y = numToArray(a.get(1));
                        for (int i = 0; i < digits; i++)
                        {
                            p.print((d(x,y,digits-i-1) + getDigitLong(num, digits-i-1))/10 + " ");

                        }
                        p.println();

                    }
                    p.println("");
                }
            }
            p.close();

        }

        catch (Exception e) {
            System.out.println("Error");
        }  
    }

    public static int d(int x[], int y[], int n)
    {
        if (n == -1)
        {
            return 0;
        }
        int p = 0;
        for (int i = 0; i <= n; i++)
        {
            int a;
            int b;
            if (i > x.length-1)
                a = 0;
            else 
                a=x[i];
            if (n-i > y.length-1)
                b = 0; 
            else
                b = y[n-i];

            p += a*b;

        }
        return p+(d(x,y,n-1)/10);
    }
    /////////////////////
    ///utility methods///
    ////////////////////
    public static int getLastNDigits(int num, int n)
    {
        return num % (int) Math.pow(10,n);
    }

    public static int[] numToArray(int num)
    {
        int length = (int)Math.ceil(Math.log10(num));
        int[] output = new int[length];
        for (int i = 0; i < length; i ++)
        {
            output[i] = num % 10;
            num = num / 10;
        }
        return output;
    }

    public static int getDigit_BI(BigInteger x, int digit)
    {
        String s = x.toString();
        digit = s.length()-digit;
        return new Integer(s.substring(digit, digit+1));
    }

    public static int getDigitLong(long num, int digit)
    {
        return (int) ((num%(Math.pow(10, digit))) / Math.pow(10, digit-1));
    }

}
