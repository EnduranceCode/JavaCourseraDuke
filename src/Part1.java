
public class Part1 {
	
	public String findSimpleGene(String dna) {
		
		/*
		 * Start codon is "ATG"
		 * Stop codon is "TAA"
		 */
		
		/* Track the result (Gene) and set it to an empty string */
		String gene = "";
		
		/* Track the start index of the gene in the given string */
		int startIndex = dna.indexOf("ATG");
		
		/* Check if the start codon is present */
		if (startIndex == -1) {
			
			/* 
			 * The start codon is not present, 
			 * so we return an empty string because there's no gene in the given string 
			 */
			return gene;
		}
		
		/* Track the end index of the gene in the given string */
		int stopIndex = dna.indexOf("TAA", startIndex + 3);
		
		/* Check if the stop codon is present */
		if (stopIndex == -1) {
			
			/*
			 * The stop codon is not present,
			 * so we return an empty string because there's no gene in the given string
			 */
			return gene;
		}
		
		/* There's a start codon and a stopo codon,
		 * so we will get the string that start and stops with these codons
		 */
		gene = dna.substring(startIndex, stopIndex + 3);
		
		/* Check if the found length of the found string is multiple of 3 */
		if (gene.length() % 3 == 0) {
			
			/* The found string is a valid gene, therefore we will return it */
			return gene;
		} else {
			
			/* The found string is not a valid gene, therefore we will return an empty string */
			return "";
		}
	}
	
	public void testSimpleGene() {
		
		/* Test string with no "ATG" */
		String dna1 = "TTATAA";
		
		/* Print out the input DNA */
		System.out.println("DNA strand is " + dna1);
		
		/* Return the found gene */
		System.out.println("Gene is " + findSimpleGene(dna1));
		
		/* Test string with no "TAA" */
		String dna2 = "CGATGGTTG";
		
		/* Print out the input DNA */
		System.out.println("DNA strand is " + dna2);
		
		/* Print out the found Gene */
		System.out.println("Gene is " + findSimpleGene(dna2));
		
		/* Test string with no “ATG” or “TAA” */
		String dna3 = "ATCCTCTTCGGCTGTCTATGGT";
		
		/* Print out the input DNA */
		System.out.println("DNA strand is " + dna3);
		
		/* Print out the found Gene */
		System.out.println("Gene is " + findSimpleGene(dna3));
		
		/* Test string with ATG, TAA and the substring between them is a multiple of 3 */
		String dna4 = "ATGATCCTCTTCGGCTGTCTATGGTAA";
		
		/* Print out the input DNA */
		System.out.println("DNA strand is " + dna4);
		
		/* Print out the found Gene */
		System.out.println("Gene is " + findSimpleGene(dna4));
		
		/* Test string with ATG, TAA and the substring between them is NOT a multiple of 3 */
		String dna5 = "ATGATCCGGCTGTCTATGGTAA";
		
		/* Print out the input DNA */
		System.out.println("DNA strand is " + dna5);
		
		/* Print out the found Gene */
		System.out.println("Gene is " + findSimpleGene(dna5));
	}
	
	public static void main(String[] args) {
		
		Part1 part1 = new Part1();
		part1.testSimpleGene();
	}
}
