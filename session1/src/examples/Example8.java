package examples;

import java.util.stream.Stream;

public class Example8 {
	public static void main(String[] args) {

		System.out.println(Stream.iterate(10, e -> e + 1)); // java.util.stream.ReferencePipeline$Head@1fb3ebeb

		/*
		 * problem : Given a number k , and a count n, find the total of the
		 * double of n even number starting with k whr sqarroot of each number >
		 * 20
		 */

	}

	// imperative style
	public static int compute(int k, int n) {

		int result = 0;
		int index = k;
		while (n < 0) { // Confusions:is it less or less than equal to ? Am
							// I going to get "off by 1" error?

			if (index % 2 == 0 && Math.sqrt(index) > 20)

				result += 2 * index;

			n++;
			index++;
		}
		return result;

	}
//functional way
	public static int computeFunc(int k, int n) {
		return Stream.iterate(k, e -> e + 1)
				// gives an infinite series of stream starting form k
				.filter(e -> e % 2 == 0)
				.filter(e ->Math.sqrt(e) > 20)
				.mapToInt(e -> e * 2)
				.limit(n)
				.sum();
	}

}
