import edu.duke.StorageResource;
import edu.duke.FileResource;

public class Part3 {
	
	/**
	 * This method processes all the strings in StorageResource to find out
	 * information about them.
	 * 
	 * Specifically, this method does the following:
	 * 	- Print all the STRINGS in StorageResource that are longer than 9 characters
	 * 	- Print the number of STRINGS in StorageResource that are longer than 9 characters
	 * 	- Print the STRINGS in StorageResource whose C-G-ratio is higher than 0.35
	 * 	- Print the number of STRINGS in StorageResource whose C-G-ratio is higher than 0.35
	 * 	- Print the length of the longest GENE in StorageResource
	 * 
	 * @param	storageResource
	 */
	public void processGenes (StorageResource storageResource) {
		
		/* Stores the strings longer than 60 characters */
		StorageResource stringsLongerNine = new StorageResource();
		
		/* Tracks the number of strings longer than 60 characters */
		int countLongerSixty = 0;
		
		/* Stores the strings with C-G-ratio higher than 0.35 */
		StorageResource stringsCGRatioHigher035 = new StorageResource();
		
		/* Tracks the number of strings whose C-G-ratio is higher than 0.35 */
		int countCGRatioHigher035 = 0;
		
		/* Stores the valid genes extracted from the given StorageResource */
		StorageResource genesStorageResource = extractGenes(storageResource);
		
		/* Tracks the length of the longest gene */
		int longestGeneLength = 0;
		
		for (String string : storageResource.data()) {
			
			/* Check strings longer than 60 characters */
			if (string.length() > 60) {
				stringsLongerNine.add(string);
				countLongerSixty += 1;
			}
			
			/* Check strings with C-G-ratio higher than 0.35 */
			if (cgRatio(string) > 0.35) {
				stringsCGRatioHigher035.add(string);
				countCGRatioHigher035 += 1;
			}
		}
		
		for (String string : genesStorageResource.data()) {
			if (string.length() > longestGeneLength) {
				longestGeneLength = string.length();
			}
		}
		
		/* Print all the Strings that are longer than 60 characters */
		if (countLongerSixty > 0) {
			System.out.println("Strings longer than 60 characters:");
			for (String string : stringsLongerNine.data()) {
				System.out.println(string);
			}
			System.out.println("\n");
		}
		
		/* Print the number of Strings longer than 60 characters */
		System.out.println("The number of strings longer than 60 characters is " + countLongerSixty);
		System.out.println("\n");
		
		/* Print the Strings whose C-G-ratio is higher than 0.35 */
		if (countCGRatioHigher035 > 0 ) {
			System.out.println("Strings whose C-G-ratio is higher than 0.35:");
			for (String string : stringsCGRatioHigher035.data()) {
				System.out.println(string);
			}
			System.out.println("\n");
		}
		
		/* Print the number of strings with C-G-ratio higher than 0.35 */
		System.out.println("The number of strings with C-G-ratio higher than 0.35 is " + countCGRatioHigher035);
		System.out.println("\n");
		
		/* print the length of the longest gene */
		System.out.println("The length of the longest gene is " + longestGeneLength);
		System.out.println("\n");
	}
	
	/**
	 * Calculate the C-G-ratio in string
	 * 
	 * @param	string
	 * @return
	 */
	public float cgRatio(String string) {
		
		/* Track the length of the given string */
		int stringLength = string.length();
		
		/* Track the number of C's and G's in the string */
		int numberCG = 0;
		
		/* Converts all of the characters in the string to upper case */
		String dnaUpperCase = string.toUpperCase();
		
		/* Count the occurrences of 'C' and 'G' in the string */
		for (int index = 0; index < stringLength; index++) {
			if (dnaUpperCase.charAt(index) == 'C' || dnaUpperCase.charAt(index) == 'G') {
				numberCG += 1;
			}
		}
		
		return (float) numberCG / stringLength;
	}
	
	/**
	 * This method Search for ALL valid genes in a given StorageResources
	 * and returns a StorageResource with all genes found
	 * 
	 * @param	storageResource
	 * @return	StorageResource with all found genes in the given StorageResource
	 */
	public StorageResource extractGenes(StorageResource storageResource) {
		
		StorageResource genesStorageResource = new StorageResource();
		
		for (String string : storageResource.data()) {
			
			/* Convert the given string to upper case */
			String dna = string.toUpperCase();
			
			/* Track the start index of the gene in the given string */
			int startIndex = dna.indexOf("ATG");
			
			/* Search for valid genes in the given DNA */
			while (startIndex != -1) {
				
				/*
				 * There is a start codon, so we search for a stop codon
				 * First, we look for an "TAA" stop codon
				 */
				int stopCodonTAA = findStopCodon(dna, startIndex, "TAA");
				
				/* Then we look for an "TAG" stop codon */
				int stopCodonTAG = findStopCodon(dna, startIndex, "TAG");
				
				/* And finally, search for an "TGA" stop codon */
				int stopCodonTGA = findStopCodon(dna, startIndex, "TGA");
				
				/* Check if there is at least one valid stop codon */
				if (!(stopCodonTAA == dna.length() && stopCodonTAG == dna.length() && stopCodonTGA == dna.length())) {
					
					/*
					 * There is, at least, one valid stop codon
					 * so we get the global stop codon that is the first found codon
					 */
					int stopCodon = Math.min(Math.min(stopCodonTAA, stopCodonTAG), stopCodonTGA);
					
					/* We then add the found gene to the genesStorageResource */
					genesStorageResource.add(string.substring(startIndex, stopCodon + 3));
					
					/* And we look for another start codon after the end of the found gene */
					startIndex = dna.indexOf("ATG", stopCodon + 3);
				} else {
					
					/* There is no stop codon, so we look for a new start codon after the previous found start codon */
					startIndex = dna.indexOf("ATG", startIndex + 3);
				}
			}
		}
		
		return genesStorageResource;		
	}

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
		
		/* Convert given strings to upper case */
		String dnaUpperCase = dna.toUpperCase();
		String stopCodonUpperCase = stopCodon.toUpperCase();
		
		/*
		 * Track the index of the current stop codon
		 * We search for the first stop codon after the start codon
		 * because it makes no sense to start looking for the stop codon
		 * before the start codon
		 */
		int currentIndex = dnaUpperCase.indexOf(stopCodonUpperCase, startIndex);
		
		/* Search for the stop codon in the DNA strand */
		while (currentIndex != -1) {
			
			/*
			 * A stop codon is present so we check if the result substring
			 * between the start codon and the stop codon has a length multiple of 3
			 */
			if ((startIndex - currentIndex) % 3 == 0) {
				
				/* A stop codon for a valid gene was found, so we return the stop codon */
				return currentIndex;
			} else {
				
				/* Search for the next stop codon */
				currentIndex = dnaUpperCase.indexOf(stopCodonUpperCase, currentIndex + 1);
			}
		}
		
		/* There is no valid stop codon, so we return the DNA strand length */
		return dna.length();
	}

	/**
	 * Tests the {@link #processGenes(StorageResource)} method
	 */
	public void testProcessGenes() {
		
		FileResource fileResource = new FileResource("brca1line.fa");
		String dna = fileResource.asString();
		StorageResource fileStorageResource = new StorageResource();
		fileStorageResource.add(dna);
		StorageResource genesStorageResource = extractGenes(fileStorageResource);
		processGenes(genesStorageResource);
	}
	
	public	void quizAnswers() {
		FileResource fileResource = new FileResource("GRch38dnapart.fa");
		String dna = fileResource.asString();
		StorageResource fileStorageResource = new StorageResource();
		fileStorageResource.add(dna);
		StorageResource genesStorageResource = extractGenes(fileStorageResource);
		
		/* Count Genes in the file */
		System.out.println("Number of genes in the file: " + genesStorageResource.size());
		System.out.println("\n");
		
		/* Count the codon CTG in the given DNA strand */
		Part2 part2 = new Part2();
		System.out.println("Number codon CTG in the file: " + part2.countCTG(dna));
		System.out.println("\n");
		
		processGenes(genesStorageResource);
	}
	
	public static void main(String[] args) {
		Part3 part3 = new Part3();
		part3.testProcessGenes();
		
		System.out.println("FINAL QUIZ ANSWERS");
		System.out.println("\n");
		part3.quizAnswers();
	}

}
