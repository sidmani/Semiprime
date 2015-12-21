import java.util.*;
import java.awt.*;
import java.math.BigInteger;
import java.io.PrintWriter;
public class Factorize
{
    public static void test_speed()
    {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++)
        {
            main(i);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime + "ms");
    }

    public static void main(int num)
    {
        System.out.println("Factorizing " + num);
        int x[] = new int[(new String("" + num)).length()];
        int y[] = new int[(new String("" + num)).length()];
        //int x0,x1,y0,y1;
        ArrayList<Point> possibleSolutions = new ArrayList<Point>();
        int leftover;
        int[][] ones_factors = getFactors(num);
        if (ones_factors == null)
        {
            return;
        }
        for (int i = 0; i < ones_factors.length; i++)
        {
            x[0] = ones_factors[i][0];
            y[0] = ones_factors[i][1];
            int k = (getDigit(num, 2) - (x[0]*y[0])/10);
            //leftover = k / 10;
            //k = k % 10;
            System.out.println("Equation: " + k +" = (" + y[0] + "*x1 + " + x[0] + "*y1) - 10*c");
            int[][] additiveTable = getAdditiveTable(k);
            int[][] counterpartTable = getCounterpartTable(additiveTable, x[0], y[0]);
            // System.out.println(Arrays.deepToString(additiveTable));
            // System.out.println(Arrays.deepToString(additiveTable));
            int test_digits = getDigit(num,3) + getDigit(num,4) * 10;
            for (int j = 0; j < 10; j++)
            {
                // if (Math.abs(counterpartTable[0][j] * counterpartTable[1][j] - test_digits) < 18) 
                // {
                //if (counterpartTable[0][j]*x[0]+ counterpartTable[1][j]*y[0] - k <= (num%1000) / 10)
                //{
                Point p = new Point(counterpartTable[0][j] * 10 + y[0], counterpartTable[1][j] * 10 + x[0]);
                possibleSolutions.add(p);
                System.out.print("  Possible solution: " + (int)p.getX() + "*" + (int)p.getY() + " = ");
                System.out.print((int)p.getX() * (int)p.getY() + " ");
                if (p.getX() * p.getY() == num)
                {
                    System.out.print("[Actual solution] ");
                }
                System.out.println("c = "+(((int)p.getX()/10) * (((int)p.getY())%10) + (((int)p.getX())%10) * ((int)p.getY()/10)  - k)/10);
                //System.out.print(eq2(num, (int)p.getX(), (int)p.getY()));
                //System.out.println();
                //}
                // }
            }

        }
        for (Point p:possibleSolutions)
        {

        }
        System.out.println(possibleSolutions.size() + " possible solutions");
    }

    public static void m_test()
    {
        int[] a = {3,8};
        int[] b = {7,9};
        System.out.println(d(a, b, 3));
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

    public static void generate_equations(BigInteger x)
    {
        System.out.println("Generating equations for " + x);

        int digits = x.toString().length();
        String previous = "";
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
        try{
            PrintWriter p = new PrintWriter("/Users/Sid/Desktop/eq_test.txt");
            p.println("Simplified equations: (assuming x and y have the same number of digits)");
            previous = "";
            for (int i = 0; i < digits; i++)
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
                else
                    p.println(getDigit_BI(x, i+1) + "= (" + s + ")%10");
                previous = s;
            }
            p.close();
        }
        catch (Exception e) {}
    }

    public static int getDigit_BI(BigInteger x, int digit)
    {
        String s = x.toString();
        digit = s.length()-digit;
        return new Integer(s.substring(digit, digit+1));
    }

    public static void test_BI()
    {
        generate_equations(new BigInteger("5251"));
        //generate_equations(new BigInteger("25195908475657893494027183240048398571429282126204032027777137836043662020707595556264018525880784406918290641249515082189298559149176184502808489120072844992687392807287776735971418347270261896375014971824691165077613379859095700097330459748808428401797429100642458691817195118746121515172654632282216869987549182422433637259085141865462043576798423387184774447920739934236584823824281198163815010674810451660377306056201619676256133844143603833904414952634432190114657544454178424020924616515723350778707749817125772467962926386356373289912154831438167899885040445364023527381951378636564391212010397122822120720357"));
    }

    public static void test_eqs()
    {
        int count=0;
        try{
            PrintWriter p = new PrintWriter("/Users/Sid/Desktop/solutions_test.txt");
            for (float x0 = 0; x0 < 10; x0++)
            {
                for (float y0 = 0; y0 < 10; y0++)
                {
                    for (float x1 = 0; x1 < 10; x1++)
                    {
                        for (float y1 = 0; y1 < 10; y1++)
                        {

                            //if (1 == ((x0*y0) + 0)% 10 && 5 == ((x0*y1) + (x1*y0) + Math.floor(((x0*y0) + 0)/10))% 10 && 2 == ((x1*y1) + Math.floor(((x0*y1) + (x1*y0) + Math.floor(((x0*y0) + 0)/10))/10))%10 && 5== (Math.floor(((x1*y1) + Math.floor(((x0*y1) + (x1*y0) + Math.floor(((x0*y0) + 0)/10))/10))/10))% 10)
                            if (5==((((x1*y1) + (((x0*y1) + (x1*y0) + (((x0*y0) - 1)/10) - 5)/10) - 2)/10)))
                            //if (5 == ((((x1*y1) + (((x0*y1) + (x1*y0) + (((x0*y0) - 1)/10) - 5)/10) - 2)/10))% 10)
                            {
                                p.print("x0 = " + x0 + " y0 = " + y0 + " x1 = " + x1 + " y1 = " + y1);
                                //p.print (y1 == (15-(x0*y0*x1)%10)%10);
                                //p.print(y1==(x0*x1*y0+5)%10);
                                p.println();
                                count++;
                            }
                        }
                    }
                }

            }
            p.close();
            System.out.println(count);
        }
        catch (Exception e){}
    }

    public static int getLastNDigits(int num, int n)
    {
        return num % (int) Math.pow(10,n);
    }

    public static int arrayToNum(int[] a, int size)
    {
        int i, k = 0;
        for (i = 0; i < size; i++)
            k = 10 * k + a[i];
        return k;
    }

    public static void test()
    {
        for (int i = 0; i<100; i++)
        {
            for (int j = 0; j < 100; j++)
            {

                if (i*j % 100 == 30)
                {
                    System.out.println(i + " * " + j + "=" + i*j);
                }
            }
        }
    }

    public static boolean eq1(int num, int fact1, int fact2)
    {
        int a = fact1/10;
        int b = fact1%10;
        int c = fact2/10;
        int d = fact2%10;

        // return (num%100)/10 == (b*d/10 + a*d%10 + b*c%10)%10;
        return (num%100)/10 == (b*d/10 + a*d + b*c%10)%10;
    }

    public static boolean eq2(int num, int fact1, int fact2)
    {
        int a = fact1/10;
        int b = fact1%10;
        int c = fact2/10;
        int d = fact2%10;

        return (num%1000)/100 == (((b*d/10)+ (a*d) + (b*c)%10)/10 + b*c/10 + a*c%10)%10;
    }

    public static boolean eq3(int num, int fact1, int fact2)
    {
        int a = fact1/10;
        int b = fact1%10;
        int c = fact2/10;
        int d = fact2%10;

        return (num%10000)/1000 == ((((b*d/10)+ (a*d) + (b*c)%10)/10 + b*c/10 + a*c%10)/10 + (a*c)/-10)%10;
    }
    /////////////////////
    ///utility methods///
    ////////////////////
    public static int[][] getAdditiveTable(int k)
    {
        int[][] output = new int[2][10];
        for (int i = 0; i < 10; i++)
        {
            output[0][i] = i;
        }
        for (int i = 0; i < 10; i++)
        {
            output[1][i] = (k+10 - i) % 10;
        }
        return output;
    }

    public static int[][] getCounterpartTable(int[][] table, int x0, int y0)
    {
        int[][] output = new int[2][10];
        for (int i = 0; i < 10; i++)
        {
            output[0][i] = getCounterpart(x0, table[0][i]);
        }
        for (int i = 0; i < 10; i++)
        {
            output[1][i] = getCounterpart(y0, table[1][i]);
        } 
        return output;
    }

    public static int getDigit(long num, int digit)
    {
        return (int) ((num%(Math.pow(10, digit))) / Math.pow(10, digit-1));
    }

    public static long getDigitLong(long num, int digit)
    {
        return (long) ((num%(Math.pow(10, digit))) / Math.pow(10, digit-1));
    }

    public static int getCounterpart(int x0, int f1)
    {
        switch (f1)
        {
            case 0:
            return 0;
            case 1:
            switch (x0)
            {
                case 1:
                return 1;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                return 7;
                case 7:
                case 8:
                return 3;
                case 9:
                return 9;
            }
            break;
            case 2:
            switch (x0)
            {
                case 1:
                return 2;
                case 3:
                return 4;
                case 7:
                return 6;
                case 9:
                return 8;
            }
            break;
            case 3:
            switch (x0)
            {
                case 1:
                return 3;
                case 3:
                return 1;
                case 7:
                return 9;
                case 9:
                return 7;
            }
            break;
            case 4:
            switch (x0)
            {
                case 1:
                return 4;
                case 3:
                return 8;
                case 7:
                return 2;
                case 9:
                return 6;
            }
            break;
            case 5:
            return 5;
            case 6:
            switch (x0)
            {
                case 1:
                return 6;
                case 3:
                return 2;
                case 7:
                return 8;
                case 9:
                return 4;
            }
            break;
            case 7:
            switch (x0)
            {
                case 1:
                return 7;
                case 3:
                return 9;
                case 7:
                return 1;
                case 9:
                return 3;
            }
            case 8:
            switch (x0)
            {
                case 1:
                return 8;
                case 3:
                return 6;
                case 7:
                return 4;
                case 9:
                return 2;
            }
            break;
            case 9:
            switch (x0)
            {
                case 1:
                return 9;
                case 3:
                return 3;
                case 7:
                return 7;
                case 9:
                return 1;
            }
            break;
        }
        return -1;
    }

    public static int[][] getFactors(int num)
    {
        int[][] out = null;
        int digit = getDigit(num, 1);
        switch(digit)
        {
            case 1:
            out = new int[3][2];
            out[0][0] = 9;
            out[0][1] = 9;
            out[1][0] = 7;
            out[1][1] = 3;
            out[2][0] = 1;
            out[2][1] = 1;
            break;
            case 3:
            out = new int[2][2];
            out[0][0] = 1;
            out[0][1] = 3;
            out[1][0] = 9;
            out[1][1] = 7;
            break;
            case 7:
            out = new int[2][2];
            out[0][0] = 9;
            out[0][1] = 3;
            out[1][0] = 1;
            out[1][1] = 7;
            break;
            case 9:
            out = new int[3][2];
            out[0][0] = 7;
            out[0][1] = 7;
            out[1][0] = 1;
            out[1][1] = 9;
            out[2][0] = 3;
            out[2][1] = 3;
            break;
        }
        return out;
    }

}
