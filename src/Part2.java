
public class Part2 {
	
	/**
	 * This method returns the number of occurrences of a string in another string.
	 * 
	 * @param stringa	The string whose appearances will be counted
	 * @param stringb	The string to be searched for the appearance of stringa
	 * @return			Number of appearances of stringa in stringb
	 */
	public int howMany (String stringa, String stringb) {
		
		/* Track the number of occurrences of stringa in stringb */
		int numberOccurrences = 0;
		
		/* Set index to start looking for stringb in stringa */
		int startIndex = 0;
		
		/* Set the given strings to upper case */
		String stringaUpperCase = stringa.toUpperCase();
		String stringbUpperCase = stringb.toUpperCase();
		
		while (true) {
			startIndex = stringbUpperCase.indexOf(stringaUpperCase, startIndex);
			
			if (startIndex == -1) {
				break;
			} else {
				numberOccurrences += 1;
				startIndex += stringaUpperCase.length();
			}
		}
		
		return numberOccurrences;
	}
	
	/**
	 * This method tests the method {@link #howMany(String, String) howMany} method
	 */
	public void testHowMany () {
		/*
		 * String A: GAA
		 * String B: ATGAACGAATTGAATC
		 */
		String stringa = "GAA";
		String stringb = "ATGAACGAATTGAATC";
		System.out.println(stringa + " appears " + howMany(stringa, stringb) + " times(s) in " + stringb);
		
		/*
		 * String A: AA
		 * String B: ATAAAA
		 */
		stringa = "AA";
		stringb = "ATAAAA";
		System.out.println(stringa + " appears " + howMany(stringa, stringb) + " times(s) in " + stringb);
		
		/*
		 * String A: AA
		 * String B: ATAAAAA
		 */
		stringa = "AA";
		stringb = "ATAAAA";
		System.out.println(stringa + " appears " + howMany(stringa, stringb) + " times(s) in " + stringb);
	}

	public static void main(String[] args) {
		Part2 part2 = new Part2();
		part2.testHowMany();
	}
}
