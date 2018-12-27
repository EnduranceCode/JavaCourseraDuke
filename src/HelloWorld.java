import edu.duke.*;

public class HelloWorld {
	public void runHello () {
		FileResource res = new FileResource("hello_unicode.txt");
		for (String line : res.lines()) {
			System.out.println(line);
		}
	}
	
	/*
	 * As we are not running Blue J, we have to add a main method to be able to create a {@link HelloWorld} object
	 * and the invoke runHello()
	 */
	public static void main (String[] args) {
		HelloWorld helloWorld = new HelloWorld();
		helloWorld.runHello();
	}
}
