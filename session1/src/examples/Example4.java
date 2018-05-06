package examples;

import java.util.Arrays;
import java.util.List;

//Problem statement: Given a list of values , doulbe the even number and get the total. (imperative vs functional) 
public class Example4 {
	public static void main(String[] args) {

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
		// Imperative style
		int sum = 0;
		for (int integer : numbers) {

			if (integer % 2 == 0) {

				sum = sum += 2 * integer;
			}
		}
		System.out.println(sum);

		// functional style
		sum = numbers.stream()
				.filter(n -> n % 2 == 0)
				.map(n -> n * 2)
				.reduce(0, Integer::sum);
		//OR
		sum = numbers.stream()
				.filter(n -> n % 2 == 0)
				.mapToInt(n -> n * 2)
				.sum();
		System.out.println(sum);

	}

}
