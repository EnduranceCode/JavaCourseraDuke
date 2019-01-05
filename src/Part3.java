
public class Part3 {
	
	public boolean twoOccurrences(String stringa, String stringb) {
		
		/* Track the index of the first occurrence of stringa in stringb */
		int firstOccurrenceIndex = stringb.indexOf(stringa);
		
		/* Check if string a is present in string b */
		if (firstOccurrenceIndex == -1) {
			
			/* String a is no present in string b, so we return false */
			return false;
		} else {
			
			/* 
			 * String a is present in string b, so we look for a second occurrence of string a
			 * but we only do it this first occurrence isn't at the end of string b
			 */
			if (stringb.length() == firstOccurrenceIndex + stringa.length()) {
				
				/* The first occurrence of string a is at the end of string b, so we return false */
				return false;
			} else {
				
				/*
				 * The first occurrence of string a isn't at the end of string b,
				 * so we look for a second occurrence of string a in string b
				 */
				int secondOccurrenceIndex = stringb.indexOf(stringa, firstOccurrenceIndex + stringa.length());
				
				/* Check if string a is present in string b for a second time */
				if (secondOccurrenceIndex == -1 ) {
					
					/*
					 * String a is not present for a second time in string b,
					 * so we return false
					 */
					return false;
				} else {
					
					/* There is a second occurrence of string a in string b,
					 * so we return true
					 */
					return true;
				}
			}
		}
	}
	
	String lastPart(String stringa, String stringb) {
		
		/* Track the index of the first occurrence of string a in string b */
		int firstOccurrenceIndex = stringb.indexOf(stringa);
		
		/* Check if string a is present in string b */
		if (firstOccurrenceIndex == -1) {
			
			/* String a isn't present in string b, therefore we return string b */
			return stringb;
		} else {
			
			/* 
			 * String a is present in string b, 
			 * therefore we return the part of string b after the first occurrence of string a
			 */
			return stringb.substring(firstOccurrenceIndex + stringa.length());
		}
	}
	
	public void testing() {
		
		System.out.println("Test twoOccurrences():");
		
		System.out.println("Test double occurrence of \"by\" in \"A story by Abby Long\": " + twoOccurrences("by", "A story by Abby Long"));
		
		System.out.println("Test double occurrence of \"Long\" in \"A story by Abby Long\": " + twoOccurrences("Long", "A story by Abby Long"));
		
		System.out.println("Test double occurrence of \"a\" in \"banana\": " + twoOccurrences("a", "banana"));
		
		System.out.println("Test double occurrence of \"atg\" in \"ctgtatgta\": " + twoOccurrences("atg", "ctgtatgta"));
		
		System.out.println("\nTest lastPart():");
		
		System.out.println("Test \"an\" in \"banana\": " + lastPart("an", "banana"));
		
		System.out.println("Test \"zoo\" in \"forest\": " + lastPart("zoo", "forest"));
	}
	
	public static void main(String[] args) {
		
		Part3 part3 = new Part3();
		part3.testing();
	}
}
