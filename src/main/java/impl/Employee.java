package impl;

import java.util.Arrays;

public class Employee {

    public int one;
    public int tvo;
    public String string;
    public int[] arr;

    public Employee(int one, int tvo, String string, int[] arr) {
        this.one = one;
        this.tvo = tvo;
        this.string = string;
        this.arr = arr;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "one=" + one +
                ", tvo=" + tvo +
                ", string='" + string + '\'' +
                ", arr=" + Arrays.toString(arr) +
                '}';
    }
}
