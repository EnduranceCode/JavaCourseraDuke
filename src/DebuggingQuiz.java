
public class DebuggingQuiz {
	public void findAbc(String input) {
	    int index = input.indexOf("abc");
	    while (true) {
	        if (index == -1) {
	            break;
	        }
	        String found = input.substring(index+1, index+4);
	        System.out.println(found);
	        index = input.indexOf("abc", index+4);
	    }
	}
	   public void test() {
		   findAbc("abcdabc");
	}
	public static void main(String[] args) {
		DebuggingQuiz debuggingQuiz = new DebuggingQuiz();
		debuggingQuiz.test();
	}

}
