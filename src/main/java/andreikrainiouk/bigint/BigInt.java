package andreikrainiouk.bigint;

import java.util.ArrayList;
import java.util.List;

public class BigInt {

    //ArrayList representation of a natural number
    private final List<Integer> value;
    //Denotes sign
    private final boolean positive;

    //Constructor from list
    public BigInt(List<Integer> value, boolean positive) {
        if (!positive) {
            this.positive = false;
        } else {
            this.positive = true;
        }
        this.value = value;
    }

    //Constructor from int
    public BigInt(int value) {
        if (value < 0) {
            positive = false;
            this.value = toList(value * -1);
        } else {
            positive = true;
            this.value = toList(value);
        }
    }

    //Helper for constructor, converts positive integer into ArrayList
    private List<Integer> toList(int value) {
        List<Integer> newList = new ArrayList<Integer>();
        for (int i = 0; i < IntHelper.numDigits(value); i++) {
            newList.add(IntHelper.getDigitAt(value, i));
        }
        return newList;
    }

    //Creates copy of given BigInt with opposite sign
    public BigInt negCopy() {
        int thisValue = 0;
        for (int i = 0; i < this.value.size(); i++) {
            thisValue += this.value.get(i) * IntHelper.intPow(10, i);
        }
        if (this.positive) return new BigInt(thisValue * -1);
        else return new BigInt(thisValue);
    }

    //Prints out BigInt
    public void printBigInt() {
        if (!positive) System.out.print("-");
        for (int i = value.size(); i > 0; i--) {
            System.out.print(value.get(i - 1));
        }
        System.out.println();
    }

    //Compares 2 BigInts, returns -1 for this < that, 0 for this = that, and 1 for this > that,
    //and 2 for error
    public int compareBigInt(BigInt that) {
        //Cases for opposite signs
        if (this.positive && !that.positive) return 1;
        if (!this.positive && that.positive) return -1;
        //Cases for positive signs
        if (this.positive && that.positive) {
            //Cases of differing number of digits
            if (this.value.size() > that.value.size()) return 1;
            else if (this.value.size() < that.value.size()) return -1;
            else {
                //Digit by digit comparison
                for (int i = this.value.size() - 1; i >= 0; i++) {
                    if (this.value.get(i) > that.value.get(i)) return 1;
                    else if (this.value.get(i) < that.value.get(i)) return -1;
                }
                return 0;
            }
        //Cases for negative signs
        }if (!this.positive && !that.positive) {
            //Cases of differing number of digits
            if (this.value.size() < that.value.size()) return 1;
            else if (this.value.size() > that.value.size()) return -1;
            else {
                //Digit by digit comparison
                for (int i = this.value.size() - 1; i >= 0; i--) {
                    if (this.value.get(i) < that.value.get(i)) return 1;
                    else if (this.value.get(i) < that.value.get(i)) return -1;
                }
                return 0;
            }
        } else return 2;
    }

    //Externally called add function, runs sum function for largerInput.sum(smallerInput)
    public BigInt add(BigInt that) {
        if (this.compareBigInt(that) == 0) return this.multiply(2);
        else if (this.compareBigInt(that) == 1) return this.sum(that);
        else return that.sum(this);
    }

    //Externally called subtraction function
    public BigInt subtract(BigInt that) {
        return this.add(that.negCopy());
    }

    //Called by add and subtract, actual code that calculates sums
    private BigInt sum(BigInt that) {
        int size;
        int carry = 0;
        if (this.value.size() > that.value.size()) size = that.value.size();
        else size = this.value.size();
        List<Integer> total = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            int sum = this.value.get(i) + that.value.get(i) + carry;
            total.add(sum % 10);
            carry = sum - (sum % 10);
        }
        return that;
    }

    public BigInt multiply(int factor) {
        return this;
    }
}