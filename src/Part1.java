import edu.duke.StorageResource;

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
			if ((currentIndex != -1) && ((startIndex - currentIndex) % 3 == 0)) {
				
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
	 * Search for ALL valid genes in a given DNA strand and stores it
	 * in a StorageResourse
	 * 
	 * @param	dna Given DNA strand to search for a valid gene
	 * @return	StorageResource holding all valid genes found
	 */
	public StorageResource getAllGenes(String dna) {
		
		/* Track the genes found in the dna strand */
		StorageResource storageResource = new StorageResource();
		
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
				 * There is a start codon, so we search for a stop codon
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
					
					/* We then print the found gene */
					storageResource.add(dna.substring(startIndex, stopCodon + 3));
					
					/* And we look for another start codon after the end of the found gene */
					startIndex = upperCaseDNA.indexOf("ATG", stopCodon + 3);
				} else {
					
					/* There is no stop codon, so we look for a new start codon after the previous found start codon */
					startIndex = upperCaseDNA.indexOf("ATG", startIndex + 3);
				}
			}
		}
		
		/* There are no more genes, if any, in the DNA strand, so we return the storageResource we've created */
		return storageResource;
	}
	
	public void testGetAllGenes () {
		
		String dna;
		final String DNA = "DNA: ";
		
		System.out.println("Testing getAllGenes()");
		
		/* DNA with two start codons and one gene */
		dna = "ATGTAAaaaATGbbTAG";
		System.out.println("\n" + DNA + dna);
		for (String string : getAllGenes(dna).data()) {
			System.out.println(string);
		}
		
		/* DNA with two start codons and two genes */
		dna = "ATGTAAaaaATGbbbTGA";
		System.out.println("\n" + DNA + dna);
		for (String string : getAllGenes(dna).data()) {
			System.out.println(string);
		}

		/* DNA with one start codon and two stop codons*/
		dna = "ATGATGBTAABBTAAATG";
		System.out.println("\n" + DNA + dna);
		for (String string : getAllGenes(dna).data()) {
			System.out.println(string);
		}
	}

	public static void main(String[] args) {
		Part1 part1 = new Part1();
		part1.testGetAllGenes();
	}
}
