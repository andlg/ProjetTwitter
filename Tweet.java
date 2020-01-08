import java.io.Serializable;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tweet implements Serializable{
	
	//private int id_tweet;
	private String id_tweet;
	private String id_user;
	private LocalDateTime /*String */ date;
	private String mess;
	private String id_uretweet;
	
	public Tweet (String p_tweet, String p_user, LocalDateTime /*String*/ p_date, String p_mess, String p_retweet) {
		id_tweet=p_tweet;
		id_user=p_user;
		date=p_date;
		mess=p_mess;
		id_uretweet=p_retweet;
		
	}
	
	public String toString() {
		String string_iso = "ID: "+ id_tweet+ " - User: "+id_user+" - date: "+date+" \n mess: "+mess+" \n retweet: "+id_uretweet+"\n ------";
		String string_utf = new String(string_iso.getBytes(),Charset.forName("UTF-8"));
		return string_utf;
	}
        
        public String toStringPartiel() {
            
		String string_iso = date+"\n "+id_user+" a tweeté :  \n"+mess+" \n Retweet de: "+id_uretweet+"\n -------------";
		String string_utf = new String(string_iso.getBytes(),Charset.forName("UTF-8"));
		return string_utf;
	}
	
	public LocalDateTime get_date() {
		return date;
	}
	
	public String get_retweet() {
		return id_uretweet;
	}

}
