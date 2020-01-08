import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Projet implements Serializable{
	
	static BaseDeTweets tab = new BaseDeTweets();
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
	static BaseDeTweets tab2 = new BaseDeTweets();
	
public static void main(String[] args) {
	
	
	Scanner sc = new Scanner(System.in);
	int choix;
	
	do {
		
		System.out.print("Votre choix ?");
		choix=sc.nextInt();
		
		switch(choix) {
		
		case 1:
			charger();
		break;
		
		case 2:
			afficher();
		break;
		
		case 3:
			afficherdate();
		break;
		
		case 4:
			afficherperiode();
		break;
		
		case 5:
			creerperiode();
		break;
		
		case 6:
			retweet();
		break;
		
		case 7:
			nbtweets();
		break;
		
		case 8:
			nbtweetsh();
		break;
		case 9:
			write();
		break;
		case 10:
			ouvrirAL();
		break;
		
			
		}
		
		
	}while(choix !=0);
	
	
	
	
	

	
	
	} 

	public static void charger() {
		tab.initialise();
		/*	try {
				tab.ouvrir("Foot.txt");
			}catch(Exception e) {
				System.out.println("erreur ouverture");
				e.printStackTrace();
			}
			try {
				tab.lire();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		*/
			Tweet t;
			try {
			FileReader r = new FileReader("Foot.txt");
			BufferedReader br = new BufferedReader(r);
			
			String ligne;
			//28/12
			ligne = br.readLine();
			
			
			do {
				//28/12
				//ligne = br.readLine();
			if(ligne.isEmpty() ){
				System.out.println("nul");
			}else {
				String[] sp = ligne.split("	", 5); 
				if(Character.isDigit(sp[0].charAt(0))) {
				/*for (String a : sp) {
			        System.out.println(a); 
				} */
				/*System.out.println(sp[0]);
				System.out.println("\n");*/
			    
			    try {
			    	LocalDateTime date=LocalDateTime.parse(sp[2],formatter);
			    	//System.out.println(date);
			   // t= new Tweet(sp[1],sp[2].substring(0,19),sp[3],sp[4]);
			  //  t= new Tweet(Integer.parseInt(sp[0]),sp[1],date,sp[3],sp[4]);
			    	t= new Tweet(sp[0],sp[1],date,sp[3],sp[4]);
			    tab.ajoute(t);
			    System.out.println("zz");
			    //tab.lire();
			    }catch(DateTimeParseException e){
			    	e.printStackTrace();
			    }
			    //28/12
				}
			}
			    ligne = br.readLine();
				
			}while(ligne !=null);
			
			
			
			
			
			
			
			/*String s = br.readLine();
			System.out.println(s);
			String[] sp = s.split("	", 5); 
			for (String a : sp) {
		        System.out.println(a); 
			} 
			String s2 = br.readLine();
			System.out.println(s2);*/
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
	}
	
	
	public static void ouvrirAL() {
		Scanner sc = new Scanner(System.in);
		System.out.println("nom?");
		String nom=sc.nextLine();
		try {
			tab.ouvrirAL(nom);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void write() {
		Scanner sc = new Scanner(System.in);
		System.out.println("nom?");
		String nom=sc.nextLine();
		try {
			tab.write(nom);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void afficher() {
		System.out.print("Cb de tweet à aff?");
		Scanner sc = new Scanner(System.in);
		int nb=sc.nextInt();
		tab.lire(nb);
		
	}
	
	public static void afficherdate() {
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		System.out.print("date des tweets");
		Scanner sc = new Scanner(System.in);
		String sdate=sc.nextLine();
		try {
	    	LocalDate date=LocalDate.parse(sdate,formatter2);
	    	tab.lire(date);
		}catch(DateTimeParseException e){
	    	e.printStackTrace();
	    }
	}
	
	public static void afficherperiode() {
		Scanner sc = new Scanner(System.in);
		System.out.print("date des depart");
		String sdate1=sc.nextLine();
		System.out.print("date de fin");
		String sdate2=sc.nextLine();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
	    	LocalDateTime date1=LocalDateTime.parse(sdate1,formatter2);
	    	LocalDateTime date2=LocalDateTime.parse(sdate2,formatter2);
	    	tab.lire(date1,date2);
		}catch(DateTimeParseException e){
	    	e.printStackTrace();
	    }
	}
	
	public static void creerperiode() {
		
		tab2.initialise();
		Scanner sc = new Scanner(System.in);
		System.out.print("date des depart");
		String sdate1=sc.nextLine();
		System.out.print("date de fin");
		String sdate2=sc.nextLine();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
	    	LocalDateTime date1=LocalDateTime.parse(sdate1,formatter2);
	    	LocalDateTime date2=LocalDateTime.parse(sdate2,formatter2);
	    	tab.creer(tab2,date1,date2);
	    	tab2.lire();
		}catch(DateTimeParseException e){
	    	e.printStackTrace();
	    }
		
	}
	
	public static void retweet() {
		//2019-06-21 11:46:19
		//2019-06-21 11:47:35
		
		tab2.lireretweet();
	}
	
	//nb de tweets par jour 
	public static void nbtweets() {
		Scanner sc = new Scanner(System.in);
		System.out.print("date des depart");
		String sdate1=sc.nextLine();
		System.out.print("date de fin");
		String sdate2=sc.nextLine();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
	    	LocalDate date1=LocalDate.parse(sdate1,formatter2);
	    	LocalDate date2=LocalDate.parse(sdate2,formatter2);
	    	tab.nbtweets(date1,date2);
		}catch(DateTimeParseException e){
	    	e.printStackTrace();
	    }
	}
	
	//Nb de twwets par heure pour un jour donné
	public static void nbtweetsh() {
		Scanner sc = new Scanner(System.in);
		System.out.print("date");
		String sdate=sc.nextLine();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
	    	LocalDate date=LocalDate.parse(sdate,formatter2);
	    	tab.nbtweets(date);
		}catch(DateTimeParseException e){
	    	e.printStackTrace();
	    }
	}
	

}
