package examples;

import java.util.Arrays;
import java.util.List;

public class Example2 {

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3);
		// external iterator
		/*
		 * for (int i = 0; i < list.size(); i++) {
		 * System.out.println(list.get(i)); }
		 */
		// still external
		for (Integer integer : list) {
			System.out.println(integer);
		}

		// internal
		/*
		 * list.forEach(new Consumer<Integer>() {
		 * 
		 * @Override public void accept(Integer t) { System.out.println(t);
		 * 
		 * } });
		 */

		// internal iterator
		// list.forEach((Integer t) -> System.out.println(t));

		// type inference is allowed in lamda
		// list.forEach( (t) -> System.out.println(t));

		// Parenthesis is optional for single parameter
		// list.forEach( t -> System.out.println(t));

		// For simple pass through, method reference can be used. method reference can be passed as argument in method
		list.forEach(System.out::println);

	}

}
