
public class Part2 {
	/**
	 * Calculate the ratio of C’s and G’s in string
	 * 
	 * @param	dna
	 * @return
	 */
	public float cgRatio(String dna) {
		
		/* Track the length of the given string */
		int stringLength = dna.length();
		
		/* Track the number of C's and G's in the string */
		int numberCG = 0;
		
		/* Converts all of the characters in the string to upper case */
		String dnaUpperCase = dna.toUpperCase();
		
		/* Count the occurrences of 'C' and 'G' in the string */
		for (int index = 0; index < stringLength; index++) {
			if (dnaUpperCase.charAt(index) == 'C' || dnaUpperCase.charAt(index) == 'G') {
				numberCG += 1;
			}
		}
		
		return (float) numberCG / stringLength;
	}
	
	/**
	 * Calculate the number of CTG codons in the DNA strand
	 * 
	 * @param	dna
	 * @return
	 */
	public int countCTG(String dna) {
		
		/* Converts all of the characters in the string to upper case */
		String dnaUpperCase = dna.toUpperCase();
		
		/* Search string */
		String searchString = "CTG";
		
		/* Track the length of the given string */
		int stringLength = dna.length();
		
		/* Track the number of CTG's in the string */
		int numberCTG = 0;
		
		/* Track current index of searchString in the given string */
		int currentIndex = dna.indexOf(searchString, 0);
		
		while (currentIndex != -1) {
			numberCTG += 1;
			currentIndex = dna.indexOf(searchString, currentIndex + searchString.length());
		}

		return numberCTG;
	}

	/**
	 * Tests the {@link #cgRatio(String)} method
	 */
	public void testCGRatio() {
		System.out.println("\nTest cgRatio():");
		System.out.println(cgRatio("ATGCCATAG"));
	}
	
	/**
	 * Test the {@link #countCTG(String)}
	 */
	public void testCountCTG() {
		System.out.println("\nTest countCTG():");
		System.out.println(countCTG("CTGCCTGCATAGCTG"));
	}

	public static void main(String[] args) {
		Part2 part2 = new Part2();
		part2.testCGRatio();
		part2.testCountCTG();
	}
}
