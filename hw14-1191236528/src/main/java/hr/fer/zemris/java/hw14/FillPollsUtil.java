package hr.fer.zemris.java.hw14;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * This class is used to fill prepared database and prepared
 * (already made) tables. There are two tables -> Polls, and
 * PollOptions.
 * 
 * @author Lucija Valentić
 *
 */
public class FillPollsUtil {

	/**
	 * First method, fills already prepared tables with one poll.
	 * The poll asks user to choose his favorite band. It returns
	 * id of a poll that was newly made.
	 * 
	 * @return long
	 */
	public static long fillPoll1() {	
		
		long idPoll;
		
		Poll poll = new Poll();
		poll.setName("Glasanje za omiljeni bend");
		poll.setMessage("Od sljedećih bendova, koji vam je najdraži? Kliknite na link kako biste glasali!");
		idPoll = DAOProvider.getDao().insertIntoTablePolls(poll);
		
		PollOption option = new PollOption();
		option.setPollId(idPoll);
		option.setOptionTitle("Death Grips");
		option.setOptionLink("https://www.youtube.com/watch?v=2MHhLDCJ57E");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Fleetwood Mac");
		option.setOptionLink("https://www.youtube.com/watch?v=6ul-cZyuYq4");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Gorillaz");
		option.setOptionLink("https://www.youtube.com/watch?v=crC4DKiKPYY");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Led Zeppelin");
		option.setOptionLink("https://www.youtube.com/watch?v=7IZ-jATBq9A");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
			
		option.setPollId(idPoll);
		option.setOptionTitle("Nick Cave & The Bad Seeds");
		option.setOptionLink("https://www.youtube.com/watch?v=LnHoqHscTKE");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
			
		option.setPollId(idPoll);
		option.setOptionTitle("Pink Floyd");
		option.setOptionLink("https://www.youtube.com/watch?v=cWGE9Gi0bB0");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("The Doors");
		option.setOptionLink("https://www.youtube.com/watch?v=MsP6EKAzEjI");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Radiohead");
		option.setOptionLink("https://www.youtube.com/watch?v=6hgVihWjK2c");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("The Strokes");
		option.setOptionLink("https://www.youtube.com/watch?v=TOypSnKFHrE");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Tame Impala");
		option.setOptionLink("https://www.youtube.com/watch?v=O2lzmpEs29M");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
		
		option.setPollId(idPoll);
		option.setOptionTitle("Talking Heads");
		option.setOptionLink("https://www.youtube.com/watch?v=O52jAYa4Pm8");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
		
		option.setPollId(idPoll);
		option.setOptionTitle("Black Sabbath");
		option.setOptionLink("https://www.youtube.com/watch?v=uk_wUT1CvWM");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
		
		option.setPollId(idPoll);
		option.setOptionTitle("The Beatles");
		option.setOptionLink("https://www.youtube.com/watch?v=BOuu88OwdK8");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Queen");
		option.setOptionLink("https://www.youtube.com/watch?v=fJ9rUzIMcZQ");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);

		return idPoll;

	}
	
	public static long fillPoll2() {	
		
		long idPoll;
		
		Poll poll = new Poll();
		poll.setName("Glasanje za omiljenog izvođača");
		poll.setMessage("Od sljedećih izvođača, koji vam je najdraži? Kliknite na link kako biste glasali!");
		idPoll = DAOProvider.getDao().insertIntoTablePolls(poll);
		
		PollOption option = new PollOption();
		option.setPollId(idPoll);
		option.setOptionTitle("Frank Ocean");
		option.setOptionLink("https://www.youtube.com/watch?v=r4l9bFqgMaQ");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Nick Cave");
		option.setOptionLink("https://www.youtube.com/watch?v=LnHoqHscTKE");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Kanye West");
		option.setOptionLink("https://www.youtube.com/watch?v=Co0tTeuUVhU");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("David Bowie");
		option.setOptionLink("https://www.youtube.com/watch?v=bsYp9q3QNaQ");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
			
		option.setPollId(idPoll);
		option.setOptionTitle("Lou Reed");
		option.setOptionLink("https://www.youtube.com/watch?v=QYEC4TZsy-Y");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
			
		option.setPollId(idPoll);
		option.setOptionTitle("Mac Demarco");
		option.setOptionLink("https://www.youtube.com/watch?v=wIuBcb2T55Q");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Tyler, The Creator");
		option.setOptionLink("https://www.youtube.com/watch?v=HmAsUQEFYGI");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			
		option.setPollId(idPoll);
		option.setOptionTitle("Frank Sinatra");
		option.setOptionLink("https://www.youtube.com/watch?v=mQR0bXO_yI8");
		option.setVotes(0);
		DAOProvider.getDao().insertIntoTablePollOptions(option);
			

		return idPoll;

	}
	
}
