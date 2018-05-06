package examples;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*problem : Given an ordered list, find the double of the first even number greater than 3*/

public class Example9 {
	public static void main(String[] args) {

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

		int result = 0;
		// how much work?  8 unit of work
		for (int n : numbers) {
			if (n > 3 && n % 2 == 0) {
				result = 2 * n;
				break;
			}
		}
		System.out.println(result);
		// how much work?  
		Optional<Integer> result1 = numbers.stream()
				.filter(Example9::isGT3)
				.filter(Example9::isEven)
				.map(Example9::doubleIt)
				.findFirst();
		System.out.println(result1.get());

	}

	private static int doubleIt(Integer n) {
		System.out.println("doubleIt: "+n);
		return n * 2;
	}

	private static boolean isEven(Integer n) {
		System.out.println("isEven: "+n);
		return n % 2 == 0;
	}

	private static boolean isGT3(Integer n) {
		System.out.println("isGT3: "+n);
		return n > 3;
	}

}
