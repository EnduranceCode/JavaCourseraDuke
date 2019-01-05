import edu.duke.URLResource;

public class Part4 {
	
	public String findYoutubeURL() {
		
		/* Track the result of the search */
		String result = "";
		
		/* Get the given URL Resource */
		URLResource urlResource = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
		
		/* Read the file in the given URL, word by word */
		for (String word : urlResource.words()) {
			
			/* Set the word to lowercase */
			String lowerCaseWord = word.toLowerCase();
			
			/* Get the index of "youtube.com" in the word */
			int indexYoutubeCom = lowerCaseWord.indexOf("youtube.com");
			
			/* Check if "youtube.com" is present in the word */
			if (indexYoutubeCom != -1) {
				
				/* 
				 * "youtube.com" is included in the word, so we look for the full URL,
				 * strating with the beggining of the URL
				 */
				int startIndex = lowerCaseWord.lastIndexOf('\"', indexYoutubeCom) + 1;
				
				/* Then we look for the end of the URL */
				int endIndex = lowerCaseWord.indexOf('\"', indexYoutubeCom);
				
				/* We get the URL and add it to the result string */
				result += word.substring(startIndex, endIndex) + "\n";
			}
			
			
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		
		Part4 part4 = new Part4();
		System.out.println("Youtube Video's URL found:\n" + part4.findYoutubeURL());
	}
}
