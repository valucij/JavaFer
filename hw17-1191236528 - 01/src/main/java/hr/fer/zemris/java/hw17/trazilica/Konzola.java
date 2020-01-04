package hr.fer.zemris.java.hw17.trazilica;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import hr.fer.zemris.java.hw17.trazilica.util.Util;

/**
 * Class represents simple search engine. Next part
 * show how it can be used:
 * 
 * <pre>
 * 
 * Veličina riječnika je 10896 riječi.
 * Enter command > bla
 * Nepoznata naredba.
 * Enter command > query darovit glumac zadnje akademske klase
 * Query is: [darovit, glumac, zadnje, akademske, klase]
 * Najboljih 10 rezultata:
 * [ 0] (0.1222) d:\clanci\vjesnik-1999-12-7-kul-5
 * [ 1] (0.0151) d:\clanci\vjesnik-1999-12-3-kul-3
 * [ 2] (0.0104) d:\clanci\vjesnik-1999-9-5-kul-2
 * Enter command > type 0
 *
 * Dokument: d:\clanci\vjesnik-1999-12-7-kul-5
 * ----------------------------------------------------------------
 * Predstava dobre vjere
 * Dječje kazalište u Osijeku / Praizvedba »Badnjak u garaži« Davora Špišića i
 * Saše Anočića, po motivima Kozarčeve priče / Nov iskaz osječkih glumaca, praksom pretežito lutkara
 * …
 * Predstavu »Badnja u garaži« likovno je vrlo dobro osmislio Željko Zorica, čijoj sceni su se pridružili
 * izvanredni kostimi Mirjane Zagorec. Osobito valja istaknuti prinos maskera i vlasuljara Josipa Saboa.
 * Igor Valeri je autor primjerena glazbe, a Ksenija Zec bila je odlična redateljeva suradnica za koreografiju
 * i scenski pokret. Premijerna publika ispratila je »Badnjak u garaži« s odobravanjem, u kojem se osjetio i
 * tračak nedoumice o budućem životu predstave. Uostalom, najbolje da sama predstava dokaže svoju
 * opstojnost.
 * ----------------------------------------------------------------
 * Enter command > results
 * [ 0] (0.1222) d:\clanci\vjesnik-1999-12-7-kul-5
 * [ 1] (0.0151) d:\clanci\vjesnik-1999-12-3-kul-3
 * [ 2] (0.0104) d:\clanci\vjesnik-1999-9-5-kul-2
 * Enter command > exit
 * Goodbye!
 * </pre>
 * @author Lucija Valentić
 *
 */
public class Konzola {

	/**
	 * List of results
	 */
	private static List<String> results = new ArrayList<String>();
	/**
	 * Path of directory that has all documents/files
	 */
	private static String environmentPath = "";
	
	/**
	 * Main method called in the beginning of a program
	 * @param args arguments of command line
	 */
	public static void main(String[] args) {
	
		if(args.length != 1) {
			throw new SearchException("You didn't put path to documents");
		}
		
		environmentPath = args[0];
		
		Vocabulary.fillVocabulary(environmentPath, "src/main/resources/stopRijeci/stop.txt");
		DocumentsHandling.setEnvironmentPath(environmentPath);
		DocumentsHandling.gatherDocumentsVectors();
		System.out.println("Veličina riječnika je " + Vocabulary.sizeVocabulary() + " riječi.");
		
		Scanner sc = new Scanner(System.in);
		
		while(true){

			System.out.print("Unesite komandu > ");
			
			if(sc.hasNextLine()) {
				
				String input = sc.nextLine();
				String[] inputWords = input.split("\\s+");
				
				if(inputWords[0].equals("query")) {
					processInput(inputWords);
				}else if(inputWords[0].equals("results")){
					showResults();
				}else if(inputWords[0].equals("type")) {
					showSpecificDocument(inputWords);
				}else if(inputWords[0].equals("exit")) {
					System.out.println("Pozdrav!");
					break;
				}else {
					System.out.println("Nepoznata naredba, pokušaj ponovno!");
				}
			}
		}
		sc.close();
	}

	/**
	 * Method is called if first word in input was "query".
	 * If there is no other words, appropriate message is written and
	 * nothing happens. In other case, input is separated
	 * into words. Those words are handled like they
	 * are some kind of document and TF-IDF vector is calculated. 
	 * Then, all documents that are similar to those list of
	 * words are found, and results are written in console.
	 * @param inputWords String[]
	 */
	private static void processInput(String[] inputWords) {
		
		if(inputWords.length == 1) {
			System.out.println("Nije uneđena nijedna riječ. Pokušaj ponovno!");
			return;
		}
		
		List<String> words = new ArrayList<String>();
		
		List<String> afterRemovalWords = Vocabulary.removeWordsNotInVocabulary(inputWords);
		
		System.out.print("Upit jest: [");
		boolean flag = true;
		
		for(int i = 0; i < afterRemovalWords.size(); i++) {
		
			if(flag) {
				flag = false;
			}else {
				System.out.print(", ");
			}
			System.out.print(afterRemovalWords.get(i).toLowerCase());
			words.add(afterRemovalWords.get(i).toLowerCase());
		}
		System.out.print("]");
		System.out.println();
		
		DocumentVector frVector = DocumentsHandling.makeFrequencyVector(words);
		DocumentVector tfidfVector = DocumentsHandling.makeTfIdfVector(frVector);
		
		Map<Double, String> similaritiesAndDocumentNames = DocumentsHandling.calculateSimilaritiesBetweenAllDocuments(tfidfVector);
		similaritiesAndDocumentNames = Util.sortByValue(similaritiesAndDocumentNames);
		
		results.clear();
		
		for(Double d : similaritiesAndDocumentNames.keySet()) {
			if(results.size() < 10 && d != 0.0) {
				results.add("[" + results.size() + "] (" + String.format("%.4f", d) +") " + similaritiesAndDocumentNames.get(d));
			}
		}
		
		
		if(results.isEmpty()) {
			System.out.println("Ništa nije pronađeno");
			return;
		}
		
		System.out.println("Najboljih " + "10" + " rezultata:");
		
		for(String s : results) {
			System.out.println(s);
		}
		
	}

	/**
	 * Method is called if first word in input is "type". If there
	 * is no more words, or if there are more words than one, appropriate message
	 * is written and nothing happens. In other case, 
	 * if number is less than 0, or bigger that result size, 
	 * appropriate message is written and nothing happens.
	 * If everything is ok, then content of a file that is 
	 * in chosen position in results is written out in console.
	 * 
	 * @param inputWords String[]
	 * @throws NumberFormatException
	 */
	private static void showSpecificDocument(String[] inputWords) {
		
		if(inputWords.length != 2) {
			System.out.println("Naredba je netočno unešena, previše/premalo argumenata!");
			return;
		}
		int number;
		
		try {
			
			number = Integer.parseInt(inputWords[1]);
			
			if(number < 0 || number >= results.size()) {
				System.out.println("Unešeni broj je netočan, rezultat pod tim indeksom ne postoji!");
			}else {
				String[] result = results.get(number).split("\\s+");
				List<String> fileContent = Util.getFileContent(result[2]);
				System.out.println("Dokument: " + result[2]);
				System.out.println("---------------------------------------");
				System.out.println();
				for(String line : fileContent) {
					System.out.println(line);
				}
			}
			
		}catch(NumberFormatException ex) {
			System.out.println("Drugi unešeni argument nije broj!");
		}
		
	}

	/**
	 * Method is called if the first word in input was "results". 
	 * If results are empty, appropriate message is written out. 
	 * In other case, all content from results is written out in
	 * console.
	 */
	private static void showResults() {
	
		if(results.isEmpty()) {
			System.out.println("Još ništa nije nađeno!");
		}else {
			for(String s : results) {
				System.out.println(s);
			}
		}
		
	}
}
