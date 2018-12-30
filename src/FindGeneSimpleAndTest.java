/**
 * Simple example of class that search for genes in a DNA strand
 */
public class FindGeneSimpleAndTest {
	
	public String findGeneSimple(String dna) {
		
		/*
		 * Start codon is "ATG"
		 * Stop codon is "TAA"
		 */
		
		/* Track the result (Gene) and set it to null */
		String result = null;
		
		/* Track the starting index of the gene in the string */
		int startIndex = dna.indexOf("ATG");
		
		/* Check if the start codon was found */
		if (startIndex == -1) {
			
			/* 
			 * There is no start codon, therefore there is no Gene
			 * and we return a null result
			 */
			return result;
		}
		
		/* Track the ending index of the gene in the string */
		int stopIndex = dna.indexOf("TAA", startIndex + 3);
		
		/* Check if the stop codon was found */
		if (stopIndex == -1) {
			
			/*
			 * There is no stop codon, therefore there is no Gene
			 * and we return a null result
			 */
			return result;
		}
		
		/* Get the existing Gene */
		result = dna.substring(startIndex, stopIndex + 3);
		
		/* Return the gene found */
		return	result;
	}
	
	public void testFindGeneSimple() {
		
		/* First string to test */
		String dna1 = "AATGCGTAATATGGT";
		
		/* Print out the input DNA */
		System.out.println("DNA strand is " + dna1);
		
		/* Return the found gene */
		System.out.println("Gene is " + findGeneSimple(dna1));
		
		/* Second string to test */
		/*
		 * String dna2 = "AATGCTTAGGGTAATATGGT";
		 * 
		 * Let's use a string without stop codonm therefore without a Gene
		 */
		String dna2 = "CGATGGTTG";
		
		/* Print out the input DNA */
		System.out.println("DNA strand is " + dna2);
		
		/* Print out the found Gene */
		System.out.println("Gene is " + findGeneSimple(dna2));
		
		/* Third string to test */
		String dna3 = "ATCCTATGCTTCGGCTGTCTAATATGGT";
		
		/* Print out the input DNA */
		System.out.println("DNA strand is " + dna3);
		
		/* Print out the found Gene */
		System.out.println("Gene is " + findGeneSimple(dna3));
		
		/* Fourth string to test */
		/*
		 * String dna4 = "ATGTAA";
		 * 
		 * Let's use a string without the start codon, therefore without a Gene
		 */
		String dna4 = "TTATAA";
		
		/* Print out the input DNA */
		System.out.println("DNA strand is " + dna4);
		
		/* Print out the found Gene */
		System.out.println("Gene is " + findGeneSimple(dna4));
	}

	public static void main(String[] args) {
		FindGeneSimpleAndTest findGeneSimpleAndTest = new FindGeneSimpleAndTest();
		findGeneSimpleAndTest.testFindGeneSimple();
	}
}
