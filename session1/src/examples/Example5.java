package examples;

import java.util.Arrays;
import java.util.List;

//Problem statement:Think what problem does below code resolving? (imperative vs functional) 
public class Example5 {
	public static void main(String[] args) {

		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		// Imperative style (study 85 sec, 82 % correct as dev are familiar)
		int result = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > 5 && isEven(list.get(i)) && list.get(i) < 9
					&& list.get(i) * 2 > 15) {
				result = list.get(i);
				break;
			}
		}

		System.out.println(result);
		/* functional style:
		(study 55 sec to understand but 75 % gave correct
		 answer, interesting thing ppl got declarative wrong got it very
		 quicky..so I would like to fail fast rather than fail slow )*/
		result = list.stream()
				.filter(n -> n > 5)
				.filter(n -> isEven(n))
				.filter(n -> n < 9)
				.filter(n -> n * 2 > 15)
				.findFirst()
				.get();

		System.out.println(result);
	}

	private static boolean isEven(int num) {
		return num % 2 == 0;
	}
}
