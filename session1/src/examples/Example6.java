package examples;

import java.util.Arrays;
import java.util.List;

/*Only consider parallel ones if
 a. It makes sense when problem is parallelizable
 b. When data size is big enough (massive amount of items ) that gives benefit in performance
 c. When computation is big enough that gives benefit in performance
 d. It have a performance problem in the first place
 e.. I don't already run the process in a multi-thread environment (for example: in a web container, if I already have many requests to process in parallel, adding an additional layer of parallelism inside each request could have more negative than positive effects)
 */
public class Example6 {
	public static void main(String[] args) {

		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		/*
		 * int sum = numbers.stream() .filter(n -> n % 2 == 0)
		 * .mapToInt(compute()) .sum();
		 */
		Runnable seqTask = () -> numbers.stream()
				.filter(n -> n % 2 == 0)
				.mapToInt(Example6::compute)
				.sum();
		Timewait.get(seqTask);

		Runnable parallelTask = () -> numbers.parallelStream()
				.filter(n -> n % 2 == 0)
				.mapToInt(Example6::compute)
				.sum();
		Timewait.get(parallelTask);
	}

	private static int compute(int n) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return n * 2;
	}

	static class Timewait {
		public static void get(Runnable block) {
			Long start = System.nanoTime();
			try {
				block.run();
			} finally {
				Long end = System.nanoTime();
				System.out.println("time taken :" + (end - start) / 1.0e9);
			}
		}
	}
}
