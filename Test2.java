import java.util.*;

public class Test2 {
	public int a;
	public void adder(int b) {
		b = b + 1;
	}
	public static void main(String[] args) {
		Test2 test = new Test2();
		test.a = 5;
		test.adder(test.a);
		System.out.println(test.a);
	}
}
