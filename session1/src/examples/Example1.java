package examples;

public class Example1 {
	public static void main(String[] args) {
		// java 7 style
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("In new thread");

			}
		});
		// intention is to print a line in another thread ..but we are doing lot
		// of things here.. Like kid can go to park alone he will go with parent
		// java 8 style
		th = new Thread(() -> System.out.println("In new thread"));
		th.start();
		System.out.println("In main thread");
	}
}
