//https://stackoverflow.com/questions/3656762/n-gram-generation-from-a-sentence
//aioobe

package ngram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class Test {
	 public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {//A function to sort given map
		 
	        
		 List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());//create a list using given map paramater 
		 
	        
		 Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
	            public int compare(Map.Entry<String, Integer> o1,  
	                               Map.Entry<String, Integer> o2) 
	            { 
	                return (o2.getValue()).compareTo(o1.getValue());//comparing map values 
	            } 
	        }); 
	          
	        
	        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
	        for (Map.Entry<String, Integer> aa : list) { //from sorted list to temp map
	            temp.put(aa.getKey(), aa.getValue()); 
	        } 
	        return temp; 
	    } 
	public static void CreateMap(List<String> ngram) {//create a map such as "key" : "value"
		Map<String, Integer> frequencyMap = new HashMap<>();
		Map<String, Integer> SortedFrequencyMap = new HashMap<>();
		int countEntry = 0;
		for (String s: ngram) {
            Integer count = frequencyMap.get(s);
            if (count == null)
                count = 0;
 
            frequencyMap.put(s, count + 1);
        }
		SortedFrequencyMap = sortByValue((HashMap<String, Integer>)frequencyMap);
		System.out.println("First 100 Entry: ");
		for (Map.Entry<String, Integer> entry : SortedFrequencyMap.entrySet()) {
			if (countEntry < 100) {
				countEntry++;
				System.out.println(countEntry + ": "+entry.getKey() + ": " + entry.getValue());
			}
            
        }
	}
	public static List<String> GetFileNames(String path){
		List<String> results = new ArrayList<String>();
		File[] files = new File(path).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		return results ;
	}
	
	public static String ReadFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename, Charset.forName("ISO-8859-9")));//TURKISH CHARSET
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) { 
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		//https://www.journaldev.com/875/java-read-file-to-string
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();
		String content = stringBuilder.toString();
		content = content.replaceAll("\\p{Punct}", "");
		content = content.replaceAll("\n", "");
		content = content.trim().replaceAll(" +", " ");
		return content;
	}
	
	public static List<String> ngrams(int n, String str) {
		List<String> ngrams = new ArrayList<String>();
		String[] words = str.split(" ");
		for (int i = 0; i < words.length - n + 1; i++)
			ngrams.add(concat(words, i, i + n));
		return ngrams;
	}

	public static String concat(String[] words, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++)
			sb.append((i > start  ? " " : "") + words[i]);// değişecek
		return sb.toString();
	}
	public static void Menu(List<String> oneG,List<String> twoG,List<String> threeG) {
		int choice = 0;
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.print("Choose 1 / 2 / 3 - gram model, insert '4' to exit. \n \n 1: 1 - gram \n 2: 2 - gram \n 3: 3 - gram \n \n-->");
			choice = scan.nextInt();
			if(choice == 1) {
				CreateMap(oneG);
				
			}
			else if(choice == 2) {
				CreateMap(twoG);
				
			}
			else if(choice == 3) {
				CreateMap(threeG);
				
			}
			else if(choice == 4) {
				System.out.print("\n");
				break;
			}
			else {
				System.out.println("Wrong Input! \n");
			}
			
		}
		System.out.println("Session is over");
		
	}
	public static void main(String[] args) throws IOException {
		List<String> names = GetFileNames("books");
		String corpus = "";
		List<String> ngram_results = new ArrayList<>();
		List<String> ngram_results_2gram = new ArrayList<>();
		List<String> ngram_results_3gram = new ArrayList<>();
		for (int i = 0; i < names.size(); i++) {
			corpus += ReadFile("books/" + names.get(i)) +"";//combine the books together
		}
		for (String ngram : ngrams(1,corpus))
			ngram_results.add(ngram);
		for (String ngram : ngrams(2,corpus))
			ngram_results_2gram.add(ngram);
		for (String ngram : ngrams(3,corpus))
			ngram_results_3gram.add(ngram);
		Menu(ngram_results,ngram_results_2gram,ngram_results_3gram);
		//CreateMap(ngram_results_3gram);
		
	}
}
