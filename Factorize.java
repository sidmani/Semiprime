public class Factorize
{
    public static void main(int num)
    {
        System.out.println(getDigit(num, 4) + "" + getDigit(num, 3) + "" + getDigit(num, 2) + "" + getDigit(num, 1));
        int tens = getDigit(num, 2);
        int a,b,c,d;
        int[][] ones_factors = getFactors(num);
        for (int i = 0; i < ones_factors.length; i++)
        {
            b = ones_factors[i][0];
            d = ones_factors[i][1];

        }
    }

    public static boolean eq1(int num, int fact1, int fact2)
    {
        int a = fact1/10;
        int b = fact1%10;
        int c = fact2/10;
        int d = fact2%10;

       // return (num%100)/10 == (b*d/10 + a*d%10 + b*c%10)%10;
        return (num%100)/10 == (b*d/10 + a*d + b*c)%10;
    }

    public static boolean eq2(int num, int fact1, int fact2)
    {
        int a = fact1/10;
        int b = fact1%10;
        int c = fact2/10;
        int d = fact2%10;

       // return (num%1000)/100==(((b*d)/10 + a*d%10 + b*c%10)/10 + a*d/10+a*c%10)%10;
       //return (num%1000)/100==((b*d/10 + a*d)/10 + (b*c/10 + a*c)%10)%10;
       //return (num%1000)/100 == (((b*d)/10 + a*d)/10 + ((b*c)/10)%10 + (a*c)%10)%10;
       return (num%1000)/100 == (((b*d/10)+ (a*d) + (b*c)%10)/10 + (b*c/10 + a*c))%10;
    }

    public static int getDigit(int num, int digit)
    {
        return (int) ((num%(Math.pow(10, digit))) / Math.pow(10, digit-1));
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
            out[0][0] = 9;
            out[0][1] = 9;
            out[1][0] = 7;
            out[1][1] = 3;
            out[2][0] = 1;
            out[2][1] = 1;
            break;
        }
        return out;
    }

}
