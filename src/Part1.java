
public class Part1 {
	
	/**
	 * This method returns the index of the first occurrence of
	 * stop codon that appears past start codon and is a multiple of 3 away from start codon.
	 * If there is no such stop codon, this method returns the length of the DNA strand.
	 * 
	 * @param dna        The string that holds the DNA strand  to search for stop codon
	 * @param startIndex The index of the first occurrence of the start codon ("ATG")
	 * @param stopCodon  The stop codon to search for
	 * @return           Index of the stop codon in the string
	 */
	public int findStopCodon(String dna, int startIndex, String stopCodon) {
		
		/*
		 * Track the index of the current stop codon
		 * We search for the first stop codon after the start codon
		 * because it makes no sense to start looking for the stop codon
		 * before the start codon
		 */
		int currentIndex = dna.indexOf(stopCodon, startIndex);
		
		/* Search for the stop codon in the DNA strand */
		while (currentIndex != -1) {
			
			/*
			 * Check if the stop codon is present
			 * and if the result substring between the start codon and the stop codon has a length multiple of 3
			 */
			if ((currentIndex != -1) && ((currentIndex - startIndex) % 3 == 0)) {
				
				/* A stop codon for a valid gene was found, so we return the stop codon */
				return currentIndex;
			} else {
				
				/* Search for the next stop codon */
				currentIndex = dna.indexOf(stopCodon, currentIndex + 1);
			}
		}
		
		/* There is no valid stop codon, so we return the DNA strand length */
		return dna.length();
	}
	
	/**
	 * Search for a valid gene in a given DNA strand
	 * 
	 * @param dna Given DNA strand to search for a valid gene
	 * @return a valid gene or an empty string if no gene is found
	 */
	public String findGene(String dna) {
		
		/* Set the given DNA strand to upper case */
		String upperCaseDNA = dna.toUpperCase();
		
		/* Track the start index of the gene in the given string */
		int startIndex = upperCaseDNA.indexOf("ATG");
		
		/* Check if the start codon is present */
		if (startIndex == -1) {
			
			/*
			 * The start codon is not present,
			 * so we return an empty string because there is no gene in the given DNA strand
			 */
			return "";
		} else {
			
			/*
			 * There is a strand codon, so we search for a stop codon
			 * First, we look for an "TAA" stop codon
			 */
			int stopCodonTAA = findStopCodon(upperCaseDNA, startIndex, "TAA");
			
			/* Then we look for an "TAG" stop codon */
			int stopCodonTAG = findStopCodon(upperCaseDNA, startIndex, "TAG");
			
			/* And finally, search for an "TGA" stop codon */
			int stopCodonTGA = findStopCodon(upperCaseDNA, startIndex, "TGA");
			
			/* Check if there is at least one valid stop codon */
			if (stopCodonTAA == dna.length() && stopCodonTAG == dna.length() && stopCodonTGA == dna.length()) {
				
				/* There is no valid stop codon, so we return an empty string */
				return "";
			} else {
				
				/*
				 * There is, at least, one valid stop codon
				 * so we get the global stop codon that is the first found codon
				 */
				int stopCodon = Math.min(Math.min(stopCodonTAA, stopCodonTAG), stopCodonTGA);
				
				/* And we then return the found gene */
				return dna.substring(startIndex, stopCodon + 3);
			}
		}
	}
	
	/**
	 * Search for ALL valid genes in a given DNA strand
	 * 
	 * @param dna Given DNA strand to search for a valid gene
	 * @return ALL valid genes or an empty string if no gene is found
	 */
	public String findAllGenes(String dna) {
		
		/* Set the given DNA strand to upper case */
		String upperCaseDNA = dna.toUpperCase();
		
		/* Track the start index of the gene in the given string */
		int startIndex = upperCaseDNA.indexOf("ATG");
		
		while (true) {
			
			/* Check if the start codon is present */
			if (startIndex == -1) {
				
				/* The start codon is not present, so we break the while loop */
				break;
			} else {
				
				/*
				 * There is a strand codon, so we search for a stop codon
				 * First, we look for an "TAA" stop codon
				 */
				int stopCodonTAA = findStopCodon(upperCaseDNA, startIndex, "TAA");
				
				/* Then we look for an "TAG" stop codon */
				int stopCodonTAG = findStopCodon(upperCaseDNA, startIndex, "TAG");
				
				/* And finally, search for an "TGA" stop codon */
				int stopCodonTGA = findStopCodon(upperCaseDNA, startIndex, "TGA");
				
				/* Check if there is at least one valid stop codon */
				if (!(stopCodonTAA == dna.length() && stopCodonTAG == dna.length() && stopCodonTGA == dna.length())) {
					
					/*
					 * There is, at least, one valid stop codon
					 * so we get the global stop codon that is the first found codon
					 */
					int stopCodon = Math.min(Math.min(stopCodonTAA, stopCodonTAG), stopCodonTGA);
					
					/* And we then print the found gene */
					System.out.println(dna.substring(startIndex, stopCodon + 3));
				}
			}
			
			/* search for another start codon */
			startIndex = upperCaseDNA.indexOf("ATG", startIndex + 1);
		}
		
		/* There are no more genes, if any, in the DNA strand, so we return an empty string */
		return "";
	}
	
	public void testFindStopCodon() {
		
		String dna;
		final String STOP_CODON_INDEX_LABEL = "Stop codon index is: ";
		final String FAILURE_LABEL = "There's no valid gene. The length of DNA strand is ";
		
		System.out.println("Testing findStopCodon()\n");
		
		/* First DNA test strand */
		dna = "ATGaaaTAA";
		if (findStopCodon(dna, 0, "TAA") != dna.length()) {
			System.out.println(STOP_CODON_INDEX_LABEL + findStopCodon(dna, 0, "TAA"));
		} else {
			System.out.println(FAILURE_LABEL + findStopCodon(dna, 0, "TAA"));
		}
		
		/* First DNA test strand */
		dna = "ATGaaTAAbTAA";
		if (findStopCodon(dna, 0, "TAA") != dna.length()) {
			System.out.println(STOP_CODON_INDEX_LABEL + findStopCodon(dna, 0, "TAA"));
		} else {
			System.out.println(FAILURE_LABEL + findStopCodon(dna, 0, "TAA"));
		}
		
		/* First DNA test strand */
		dna = "ATGaaTAATAA";
		if (findStopCodon(dna, 0, "TAA") != dna.length()) {
			System.out.println(STOP_CODON_INDEX_LABEL + findStopCodon(dna, 0, "TAA"));
		} else {
			System.out.println(FAILURE_LABEL + findStopCodon(dna, 0, "TAA"));
		}
		
		System.out.println("\n");
	}
	
	public void testFindGene() {
		
		String dna;
		final String DNA = "DNA: ";
		final String GENE = "Gene: ";
		
		System.out.println("Testing findGene();\n");
		
		/* DNA with no "ATG" */
		dna = "aaabbbccc";
		System.out.println(DNA + dna);
		System.out.println(GENE + findGene(dna) + "\n");
		
		/* DNA with "ATG" and one valid stop codon */
		dna = "aaaTGbbbcccTAAddd";
		System.out.println(DNA + dna);
		System.out.println(GENE + findGene(dna) + "\n");
		
		/* DNA with “ATG” and multiple valid stop codons */
		dna = "aaaTGbbbcccTAAdddTAGeeeTGA";
		System.out.println(DNA + dna);
		System.out.println(GENE + findGene(dna) + "\n");
		
		/* DNA with “ATG” and no valid stop codons */
		dna = "aaaTGbbbcccTAdddTAGeeeTGA";
		System.out.println(DNA + dna);
		System.out.println(GENE + findGene(dna) + "\n");
		
		/* DNA with “ATG” and one nod valid stop codons and one valid stop codon */
		dna = "aaaTGbbbcccTAdddTAGeeeeTGA";
		System.out.println(DNA + dna);
		System.out.println(GENE + findGene(dna) + "\n");
		
		System.out.println("\n");
	}
	
	public void testFindAllGenes() {
		
		String dna;
		final String DNA = "DNA: ";
		
		System.out.println("Testing findAllGenes();\n");
		
		/* DNA with two start codons and one gene */
		dna = "ATGTAAaaaATGbbTAG";
		System.out.println(DNA + dna);
		findAllGenes(dna);
		System.out.println();
		
		/* DNA with two start codons and two genes */
		dna = "ATGTAAaaaATGbbbTGA";
		System.out.println(DNA + dna);
		findAllGenes(dna);
		System.out.println();
		
		System.out.println("\n");
	}

	public static void main(String[] args) {
		Part1 part1 = new Part1();
		part1.testFindStopCodon();
		part1.testFindGene();
		part1.testFindAllGenes();
	}
}
