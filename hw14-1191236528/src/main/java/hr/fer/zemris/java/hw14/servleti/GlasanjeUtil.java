package hr.fer.zemris.java.hw14.servleti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

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
	public static Map<String, String> returnMapResults(long id){
		
		List<PollOption> lines = DAOProvider.getDao().getPollOptionsByIdPoll(id);
		
		Map<String, String> mapRezultati = new LinkedHashMap<String, String>();
		
		for(PollOption o : lines) {
			mapRezultati.put(String.valueOf(o.getId()), String.valueOf(o.getVotes()));
			
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
	public static Map<String, String> returnMapIdAndBandName(long id){
		
		List<PollOption> lines = DAOProvider.getDao().getPollOptionsByIdPoll(id);

		Map<String, String> mapBandInformations = new LinkedHashMap<String, String>();
		
		for(PollOption s : lines) {
			
			mapBandInformations.put(String.valueOf(s.getId()), String.valueOf(s.getOptionTitle()));
		
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
	public static Map<String, String> returnMapIdAdLinks(long id){
		
		List<PollOption> lines = DAOProvider.getDao().getPollOptionsByIdPoll(id);
	
		Map<String, String> idAndLinks = new LinkedHashMap<String, String>();
	
		for(PollOption s : lines) {
			
			idAndLinks.put(String.valueOf(s.getId()), String.valueOf(s.getOptionLink()));
		
		}
	
		return idAndLinks;
	}
	

	/**
	 * Method creates map where keys are name of bands, and 
	 * values are percentage of votes for each bands. This map
	 * is needed for creating piechart showing results of voting.
	 * 
	 * @param long id
	 * @return Map<String, Integer>
	 */
	public static Map<String, Integer> returnMapNamesAndPercVotes(long id) {
		
		List<PollOption> options = DAOProvider.getDao().getPollOptionsByIdPoll(id);
		
		int sumAllVotes = 0;
		for(PollOption o : options) {
			sumAllVotes += o.getVotes();
		}
		
		Map<String, Integer> pieChart = new LinkedHashMap<String, Integer>();
		
		for(PollOption s : options) {
			int perc = getPercentage(s.getVotes(), sumAllVotes);
			pieChart.put(s.getOptionTitle(), perc);
			
		}
		
		return pieChart;
		
	}

	/**
	 * Method calculates percentage of given number "oneVote" 
	 * of given number "sumAllVotes".
	 * 
	 * @param oneVote long
	 * @param sumAllVotes int
	 * @return int
	 */
	private static int getPercentage(long oneVote, int sumAllVotes) {
		
		double div = (double) oneVote/sumAllVotes;
		return (int)Math.ceil(div * 100);
	}


}
