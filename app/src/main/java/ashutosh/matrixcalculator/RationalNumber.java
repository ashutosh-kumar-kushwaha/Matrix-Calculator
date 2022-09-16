package ashutosh.matrixcalculator;

public class RationalNumber {
    int num;
    int den;

    public RationalNumber(){
        num = 0;
        den = 1;
    }

    public RationalNumber(int p){
        num = p;
        den = 1;
    }

    public RationalNumber(int p, int q) {
        if (q < 0) {
            p *= -1;
            q *= -1;
        }

        // taking mod for finding HCF
        int a;
        int b;
        if (p < 0) {
            a = p * -1;
        } else {
            a = p;
        }
        if (q < 0) {
            b = q * -1;
        } else {
            b = q;
        }

        // HCF
        int hcf;
        int c;
        while (true){
            c = a%b;
            if (c == 0){
                hcf = b;
                break;
            }
            a = b;
            b = c;
        }

        if (p == 0){
            num = 0;
            den = 1;
        }
        else {
            num = p / hcf;
            den = q / hcf;
        }


    }

    public String toString() {
        if (den == 1) {
            return Integer.toString(num);
        }
        else {
            return (num + "/" + den);
        }
    }

    public RationalNumber add(RationalNumber n) {
        RationalNumber result = new RationalNumber();
        int num2 = num * n.den + n.num * den;
        int den2 = den * n.den;

        //if both Nr and Dr are -ve
        if (den2 < 0) {
            num2 = num2 * -1;
            den2 = den2 * -1;
        }


        // taking mod for finding HCF
        int a;
        int b;
        if (num2 < 0) {
            a = num2 * -1;
        } else {
            a = num2;
        }
        if (den2 < 0) {
            b = den2 * -1;
        } else {
            b = den2;
        }

        // HCF
        int hcf = 1;
        int c;
        while(true){
            c = a%b;
            if (c == 0){
                hcf = b;
                break;
            }
            a = b;
            b = c;
        }

        if (num2 == 0){
            result.num = 0;
            result.den = 1;
        }
        else {
            result.num = num2 / hcf;
            result.den = den2 / hcf;
        }

        return result;
    }

    public RationalNumber subtract(RationalNumber n) {
        RationalNumber result = new RationalNumber();
        int num2 = num * n.den - n.num * den;
        int den2 = den * n.den;
        //if both Nr and Dr are -ve
        if (den2 < 0) {
            num2 = num2 * -1;
            den2 = den2 * -1;
        }


        // taking mod for finding HCF
        int a;
        int b;
        if (num2 < 0) {
            a = num2 * -1;
        } else {
            a = num2;
        }
        if (den2 < 0) {
            b = den2 * -1;
        } else {
            b = den2;
        }

        // HCF
        int hcf = 1;
        int c;
        while(true){
            c = a%b;
            if (c == 0){
                hcf = b;
                break;
            }
            a = b;
            b = c;
        }

        if (num2 == 0){
            result.num = 0;
            result.den = 1;
        }
        else {
            result.num = num2 / hcf;
            result.den = den2 / hcf;
        }

        return result;
    }

    public RationalNumber multiply(RationalNumber n) {
        RationalNumber result = new RationalNumber();
        int num2 = num * n.num;
        int den2 = den * n.den;
        //if both Nr and Dr are -ve
        if (den2 < 0) {
            num2 = num2 * -1;
            den2 = den2 * -1;
        }


        // taking mod for finding HCF
        int a;
        int b;
        if (num2 < 0) {
            a = num2 * -1;
        } else {
            a = num2;
        }
        if (den2 < 0) {
            b = den2 * -1;
        } else {
            b = den2;
        }

        // HCF
        int hcf = 1;
        int c;
        while(true){
            c = a%b;
            if (c == 0){
                hcf = b;
                break;
            }
            a = b;
            b = c;
        }

        if (num2 == 0){
            result.num = 0;
            result.den = 1;
        }
        else {
            result.num = num2 / hcf;
            result.den = den2 / hcf;
        }

        return result;
    }

    public RationalNumber divide(RationalNumber n) {
//            return (this.multiply(this.reciprocal()));
        RationalNumber result = new RationalNumber();
        int num2 = num * n.den;
        int den2 = den * n.num;
        //if both Nr and Dr are -ve
        if (den2 < 0) {
            num2 = num2 * -1;
            den2 = den2 * -1;
        }


        // taking mod for finding HCF
        int a;
        int b;
        if (num2 < 0) {
            a = num2 * -1;
        } else {
            a = num2;
        }
        if (den2 < 0) {
            b = den2 * -1;
        } else {
            b = den2;
        }

        // HCF
        int hcf = 1;
        int c;
        while(true){
            c = a%b;
            if (c == 0){
                hcf = b;
                break;
            }
            a = b;
            b = c;
        }

        if (num2 == 0){
            result.num = 0;
            result.den = 1;
        }
        else {
            result.num = num2 / hcf;
            result.den = den2 / hcf;
        }

        return result;
    }

    public RationalNumber reciprocal() {
        if (num < 0) {
            num *= -1;
            den *= -1;
        }
        if (num != 0) {
            return new RationalNumber(den, num);
        }
        else {
            return null;
        }
    }

    public boolean equals(RationalNumber number) {
        return ((double) num / (double) den)   == ((double) number.num / (double) number.den);
    }

}