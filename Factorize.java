import java.util.*;
import java.awt.*;
public class Factorize
{
    public static void test_speed()
    {
        long startTime = System.currentTimeMillis();
        for (int i = 100000; i < 110000; i++)
        {
            main(i);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime + "ms");
    }

    public static void main(int num)
    {
        System.out.println(getDigit(num, 4) + "" + getDigit(num, 3) + "" + getDigit(num, 2) + "" + getDigit(num, 1));
        int x0,x1,y0,y1;
        ArrayList<Point> possibleSolutions = new ArrayList<Point>();
        int[][] ones_factors = getFactors(num);
        if (ones_factors == null)
        {
            return;
        }
        for (int i = 0; i < ones_factors.length; i++)
        {
            x0 = ones_factors[i][0];
            y0 = ones_factors[i][1];
            int k = (10 + getDigit(num, 2) - (x0*y0)/10)%10;
            System.out.println(k +" = (" + y0 + "*x1 + " + x0 + "*y1)mod10");
            int[][] additiveTable = getAdditiveTable(k);
            int[][] counterpartTable = getCounterpartTable(additiveTable, x0, y0);
            int test_digits = getDigit(num,3) + getDigit(num,4) * 10;
            for (int j = 0; j < 10; j++)
            {
                if (Math.abs(counterpartTable[0][j] * counterpartTable[1][j] - test_digits) < 10)
                {
                    possibleSolutions.add(new Point(counterpartTable[0][j] * 10 + y0, counterpartTable[1][j] * 10 + x0));
                }
            }
        }
        for (Point p:possibleSolutions)
        {
            System.out.print("Possible solution: " + p.getX() + "*" + p.getY());
            if (p.getX() * p.getY() == num)
            {
                System.out.print("[Actual solution]");
            }
            System.out.println();
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

    public static int getDigit(int num, int digit)
    {
        return (int) ((num%(Math.pow(10, digit))) / Math.pow(10, digit-1));
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
                case 3:
                return 7;
                case 7:
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
                return 6;
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
