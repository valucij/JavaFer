package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class is utility class, it was made so some code is not
 * written twice. Methods of this class deal with getting informations 
 * for voting webapp
 * 
 * @author Lucija Valentić
 *
 */
public class GlasanjeUtil {

	/**
	 * Method reads informations from given file, and
	 * returns map where key is id of some band, and 
	 * value is number of votes that band got. 
	 * 
	 * @param fileName String
	 * @return Map<String, String> map
	 */
	public static Map<String, String> returnMapResults(String fileName){
		
		List<String> lines = getLinesFromFile(fileName);
		
		Map<String, String> mapRezultati = new LinkedHashMap<String, String>();
		
		for(String s : lines) {
			String[] temp = s.split("\t");
			mapRezultati.put(temp[0], temp[1]);
			
		}
		
		return mapRezultati;
	}
	
	/**
	 * Method sorts given map by values, and returns list of
	 * keys with the biggest values. It is used usually to sort
	 * map of results, and returns ids of bands with most votes.
	 * 
	 * @param <K> String usually
	 * @param <V> String usually
	 * @param map Map<String, String> usually
	 * @return List<String>
	 */
	public static <K, V extends Comparable<? super V>> List<String> sortByValueAndReturnWinners(Map<K, V> map){
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());
		
		List<String> winners = new ArrayList<String>();
		
		Collections.reverse(list);
		
		int maxVotes = Integer.parseInt((String) list.get(0).getValue());
		
		map.clear();
		
		for(Entry<K, V> e : list) {
			
			int vote = Integer.parseInt((String) e.getValue());
			
			if(vote == maxVotes) {
				winners.add((String)e.getKey());
			}
			
			map.put(e.getKey(), e.getValue());
		}
		
		return winners;
	
	}
	
	/**
	 * Method reads informations from given file, and returns map
	 * where key is id of a band, and value is band name
	 * 
	 * @param fileName String
	 * @return Map<String, String> map
	 */
	public static Map<String, String> returnMapIdAndBandName(String fileName){
		
		List<String> lines = getLinesFromFile(fileName);

		Map<String, String> mapBandInformations = new LinkedHashMap<String, String>();
		
		for(String s : lines) {
			
			String[] temp = s.split("\t");
			mapBandInformations.put(temp[0], temp[1]);
		
		}
		
		return mapBandInformations;
		
	}
	
	/**
	 * Method reads informations from given file, and returns map
	 * where key is id of a band, and value is link to the song of said band
	 * 
	 * @param fileName String
	 * @return Map<String, String> map
	 */
	public static Map<String, String> returnMapIdAdLinks(String fileName){
		
		List<String> lines = getLinesFromFile(fileName);
	
		Map<String, String> idAndLinks = new LinkedHashMap<String, String>();
	
		for(String s : lines) {
			
			String[] temp = s.split("\t");
			idAndLinks.put(temp[0], temp[2]);
		
		}
	
		return idAndLinks;
	}
	
	/**
	 * Method receives file name, opens that file, and reads all the lines from the 
	 * file. Lines are put in the list of strings. Then it returns said list.
	 * 
	 * @param fileName String
	 * @return List<String> list
	 */
	private static List<String> getLinesFromFile(String fileName){
	
		List<String> lines;
		
		try {
			lines = Files.readAllLines(Paths.get(fileName),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			return null;
		}
		
		return lines;
	}

	/**
	 * Method creates map where keys are name of bands, and 
	 * values are percentage of votes for each bands. This map
	 * is needed for creating piechart showing results of voting.
	 * 
	 * @param fileNameResults String
	 * @param fileNameInformations String
	 * @return Map<String, Integer>
	 */
	public static Map<String, Integer> returnMapNamesAndPercVotes(String fileNameResults, String fileNameInformations) {
		
		Map<String, String> bands = returnMapIdAndBandName(fileNameInformations);
		Map<String, String> votes = returnMapResults(fileNameResults);
		
		int sumAllVotes = votes.values().stream().mapToInt(Integer::parseInt).sum();
		
		Map<String, Integer> pieChart = new LinkedHashMap<String, Integer>();
		
		for(String s : votes.keySet()) {
			int perc = getPercentage(Integer.parseInt(votes.get(s)), sumAllVotes);
			pieChart.put(bands.get(s), perc);
			
		}
		
		return pieChart;
		
	}

	/**
	 * Method calculates percentage of given number "oneVote" 
	 * of given number "sumAllVotes".
	 * 
	 * @param oneVote int
	 * @param sumAllVotes int
	 * @return int
	 */
	private static int getPercentage(int oneVote, int sumAllVotes) {
		
		double div = (double) oneVote/sumAllVotes;
		return (int)Math.ceil(div * 100);
	}


}
