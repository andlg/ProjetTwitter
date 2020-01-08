import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class BaseDeTweets {
	
    ArrayList<Tweet> liste;
	
    public void initialise() {
        liste=new ArrayList<Tweet>();
    }
    
    public int get_size(){
        return liste.size();
    }
    //Recuperation des premiere ou derniere date du jeu de donnees
    public LocalDate date(int choix){
        //Recuperer premiere date de la liste
        if(choix==0){
            return liste.get(0).get_date().toLocalDate();
        }
        //Recuperer dernière date de la liste 
        else{
            return liste.get(liste.size()-1).get_date().toLocalDate();
        }
    }

    //------PLUS UTILISE------------
    public void lire() {
		
	Iterator<Tweet> it =liste.iterator();
        while(it.hasNext()) {
            Tweet t=it.next();
            String s=t.toString();
            System.out.println(s);	
	}
    }
    //--------------------------------

    //Affichage du jeu de donnees entier 
    public String lire2() {
        String sortie = "Liste : \n";
        Iterator<Tweet> it =liste.iterator();
        while(it.hasNext()) {
            Tweet t=it.next();
            String s=t.toString();
            sortie=sortie+s+"\n";	
	}
        sortie=sortie+"dernier"+liste.get(liste.size()-1);
        return sortie;	
    }
	
    //------PLUS UTILISE------------
    public void lire(int nb) {
	int i=1;
	Iterator<Tweet> it =liste.iterator();
	while(it.hasNext() & i<=nb) {
            Tweet t=it.next();
			String s=t.toString();
			System.out.println(s);
			i=i+1;
		}
		System.out.println("dernier"+liste.get(liste.size()-1));
		
	}
    //------------------
    
    //Affichage des nb premiers tweets 
    public String lire2(int nb) {
        String sortie = "Liste : \n";
        int i=1;
	Iterator<Tweet> it =liste.iterator();
	while(it.hasNext() & i<=nb) {
            Tweet t=it.next();
            String s=t.toString();
            sortie=sortie+s+"\n";
            i=i+1;  
	}
	sortie=sortie+"dernier"+liste.get(liste.size()-1);
	return sortie ;
    }
    
    //------PLUS UTILISE------------
	public void lire(LocalDate d) {
		boolean sup=false;
		Iterator<Tweet> it =liste.iterator();
		while(it.hasNext() & sup==false) {
			Tweet t=it.next();
			if (t.get_date().toLocalDate().equals(d)) {
				String s=t.toString();
				System.out.println(s);
			}
			if(t.get_date().toLocalDate().isAfter(d)) {
				sup=true;
			}
		}
	}
        //-------------------------
        
    //Affichage des tweets d'un jour donne
    public String lire2(LocalDate d) {
        String sortie = "Liste : \n";
        boolean sup=false;
	Iterator<Tweet> it =liste.iterator();
	while(it.hasNext() & sup==false) {
            Tweet t=it.next();
            if (t.get_date().toLocalDate().equals(d)) {
		String s=t.toString();
                sortie=sortie+s+"\n";
            }
            if(t.get_date().toLocalDate().isAfter(d)) {
		sup=true;
            }
	}
        return sortie;
    }
	
    //------PLUS UTILISE------------
	public void lire(LocalDateTime d1, LocalDateTime d2) {
		boolean sup=false;
		Iterator<Tweet> it =liste.iterator();
		while(it.hasNext() & sup==false) {
			Tweet t=it.next();
			if (t.get_date().isAfter(d1) & t.get_date().isBefore(d2)) {
				String s=t.toString();
				System.out.println(s);
			}
			if(t.get_date().isAfter(d2)) {
				sup=true;
			}
		}
	}
    //--------------------------
    
    //Affichage des tweets entre 2 dates 
    public String lire2(LocalDateTime d1, LocalDateTime d2) {
        String sortie = /*"Liste : \n"*/"\n";
        boolean sup=false;
	Iterator<Tweet> it =liste.iterator();
	while(it.hasNext() & sup==false) {
            Tweet t=it.next();
            if (t.get_date().isAfter(d1) & t.get_date().isBefore(d2)) {
		String s=t.toStringPartiel();
                sortie=sortie+s+"\n";
            }
            if(t.get_date().isAfter(d2)) {
		sup=true;
            }
	}
        return sortie;
    }
	
	public void ouvrir(String nom) throws Exception {
				FileInputStream in = new FileInputStream(nom);
			   ObjectInputStream read = new ObjectInputStream(in);
			   Object lu =read.readObject();
			   liste = (ArrayList<Tweet>)lu;
			   read.close();
			   in.close();
			   System.out.println("Ouverture ok");

		}
	
	private void readObject(java.io.ObjectInputStream in) 
	throws IOException, ClassNotFoundException{
		in.defaultReadObject();
		
	}
	
    //Ajout d'un tweet
    public void ajoute(Tweet t) {
	liste.add(t);
    }
	
	public void creer(BaseDeTweets b, LocalDateTime d1, LocalDateTime d2 ) {
		
		//ArrayList<Tweet> liste2= new ArrayList<Tweet>();
		boolean sup=false;
		Iterator<Tweet> it =liste.iterator();
		while(it.hasNext() & sup==false) {
			Tweet t=it.next();
			if (t.get_date().isAfter(d1) & t.get_date().isBefore(d2)) {
				b.ajoute(t);
			}
			if(t.get_date().isAfter(d2)) {
				sup=true;
			}
		}
		System.out.println("ok");
		Duration dif = Duration.between(d1,d2);
		System.out.println(dif.toMinutes());
	}
        
        
	
	
        
    //TOP RT sur 1 jour 
    public String lireretweet3(LocalDate date) {
            
        //Compte des retweets par utilisateur 
        Iterator<Tweet> it =liste.iterator();
        TreeMap<String, Integer> tmap = new TreeMap<String, Integer>();
        while(it.hasNext()) {
            Tweet t=it.next();
            if(t.get_date().toLocalDate().equals(date)){
                String s=t.get_retweet();
                //On exclu les tweets sans valeur dans le champs retweet
                if(s.length()>0){	
                    //Si l'utilisateur est deja dans la liste: 
                    //incrementation du nombre d'occurence pour cet utilisateur 
                    if(tmap.containsKey(s)) {
			int nb=tmap.get(s);
			//hmap.remove(s);
			tmap.put(s,nb+1);
                    }
                    //sinon inscription de l'utilisateur dans la liste 
                    else {
			tmap.put(s,1);
                    }
                }
            }
        }
		
	//MODIFIER BOUCLE CAR COPIE COLLE
	for (Map.Entry mapentry : tmap.entrySet()) {
	     System.out.println("clé: "+mapentry.getKey() 
	                              + " | valeur: " + mapentry.getValue());
	}
                
        String sortie="";
        System.out.println("------------");
            
        //Creation de la liste pour le classement/TOP
        ArrayList al = new ArrayList();
        DecimalFormat nf = new DecimalFormat("000000");
            
        int max=0;
        String ind="null";
            
        //On veut le classement des 5 utilisateurs les plus retweetes
        for(int j=1;j<=5;j++){
                
            //Parcours de la map precedemment creee
            //Recherche de l'utilisateur le plus retweete
            for (Map.Entry mapentry : tmap.entrySet()) {
                
                if(Integer.parseInt(mapentry.getValue().toString())>max){
                        
                    max=Integer.parseInt(mapentry.getValue().toString());
                    ind=mapentry.getKey().toString();
                }  
            }
            //Ajout de l'utilisateur au classement (+ nombre de retweets)
            al.add(ind+" - "+max);
            //On enleve cet utilisateur de la map pour pouvoir chercher le nouveau maximum
            tmap.remove(ind);
            max=0;
        }
                  
        System.out.println("TOP");
            
        //Parcours du TOP
        Iterator it2=al.iterator();
        String srt;
        while(it2.hasNext()){ 
            srt=it2.next().toString();
            System.out.println("clé2: "+srt);
            sortie=sortie+srt+"\n";       
        }
            
        return sortie;
    }
        
    //TOP RT entre 2 jours    
        public String lireretweet3(LocalDate d1, LocalDate d2) {
            
            
            Iterator<Tweet> it =liste.iterator();
            TreeMap<String, Integer> tmap = new TreeMap<String, Integer>();
            while(it.hasNext()) {
		Tweet t=it.next();
                if (t.get_date().isAfter(d1.atStartOfDay()) 
                & t.get_date().isBefore(d2.plusDays(1).atStartOfDay())) {
                    
                    String s=t.get_retweet();
                    if(tmap.containsKey(s)) {
                        int nb=tmap.get(s);
                        tmap.put(s,nb+1);
                    }
                    else {
			tmap.put(s,1);
                    }
                }
            }
		
		//MODIFIER BOUCLE CAR COPIE COLLE
		for (Map.Entry mapentry : tmap.entrySet()) {
	           System.out.println("clé: "+mapentry.getKey() 
	                              + " | valeur: " + mapentry.getValue());
	        }
                
            String sortie="";
            System.out.println("------------");
            ArrayList al = new ArrayList();
            DecimalFormat nf = new DecimalFormat("000000");
            int max=0;
            String ind="null";
            for(int j=1;j<=5;j++){
                 for (Map.Entry mapentry : tmap.entrySet()) {
                     //On enelève les retweets vides
                    if(mapentry.getKey().toString().length() !=0){    
                         if(Integer.parseInt(mapentry.getValue().toString())>max){
                             max=Integer.parseInt(mapentry.getValue().toString());
                             ind=mapentry.getKey().toString();
                             System.out.println("max: "+max+"");
                         }
                    }  
                 }
                al.add(ind+" - "+max);
                tmap.remove(ind);
                max=0;
            }
    
            System.out.println("TOP");
            Iterator it2=al.iterator();
            int i=1;
            String srt;
            while(it2.hasNext()){      
                srt=it2.next().toString();
                System.out.println("clé2: "+srt);
                sortie=sortie+srt+"\n";
                i=i+1;
            }
            
            return sortie;    
	}
        
        //Popularite d'un utilisateur 
        public TreeMap lireretweet(String stuser) {
           // yng_lea  lxrd93
                System.out.println("user"+stuser);
		
		TreeMap<LocalDate, Integer> tmap = new TreeMap<LocalDate, Integer>();
               // Initialisation du TreeMap --> pour avoir axe des abscisses complet
                Iterator<Tweet> it2 =liste.iterator();
                while(it2.hasNext()){
                    Tweet t=it2.next();
                    tmap.put(t.get_date().toLocalDate(), 0);
                }
                
                Iterator<Tweet> it =liste.iterator();
		while(it.hasNext()) {
                    Tweet t=it.next();
                    //Recuperation du jour du tweet 
                    LocalDate d=t.get_date().toLocalDate();
                    //S'il s'ahit d'un retweet de l'utilisateur choisi
                    if (t.get_retweet().contains(stuser)){    
			//Augmentation du nombre de retweet su rla journee
			int nb=tmap.get(d);
			int nb2=nb+1;
                        tmap.replace(d, nb+1);
                        System.out.println("aug"+d.toString()+" nb :"+nb2);
                    }
		}
                for (Map.Entry mapentry : tmap.entrySet()) {
	           System.out.println("clé: "+mapentry.getKey() 
	                              + " | valeur: " + mapentry.getValue());
	        }
            return tmap;
		
	}
        
        //Popularite d'un utilisateur 
        public TreeMap lireretweet(String stuser, LocalDate date) {
           // yng_lea  lxrd93
                System.out.println("user"+stuser);
		
		TreeMap<Integer, Integer> tmap = new TreeMap<Integer, Integer>();
               // Initialisation du TreeMap --> pour avoir axe des abscisses complet
                Iterator<Tweet> it2 =liste.iterator();
                while(it2.hasNext()){
                    Tweet t=it2.next();
                    tmap.put(t.get_date().getHour(), 0);
                }
                
           
                	LocalDateTime dt=date.atStartOfDay();
                int nb=0;
                Iterator<Tweet> it =liste.iterator();
		while(it.hasNext()& (dt.toLocalDate().isBefore(date) || dt.toLocalDate().equals(date))) {
                    Tweet t=it.next();
                    //Recuperation du jour du tweet 
                    int d=t.get_date().getHour();
                    
                    //S'il s'ahit d'un retweet de l'utilisateur choisi
                    if (t.get_retweet().contains(stuser) && dt.toLocalDate().equals(date) ){  
                        if(d==dt.getHour()){
			//Augmentation du nombre de retweet su rla journee
			 nb=nb+1;
			
                        }
                        if(d > dt.getHour()) {
                        tmap.replace(d, nb);
                        dt=t.get_date();
                       // d=dt.getHour();
                        nb=0;
                        }
                    }
                    
		}
                for (Map.Entry mapentry : tmap.entrySet()) {
	           System.out.println("clé: "+mapentry.getKey() 
	                              + " | valeur: " + mapentry.getValue());
	        }
            return tmap;
		
	}
        
        //Fonction pour popularite d'un user --> pas utilisée
        public TreeMap rt(BaseDeTweets b, String stuser ) {
		ArrayList<Tweet> lrt=new ArrayList<Tweet>();
		//ArrayList<Tweet> liste2= new ArrayList<Tweet>();
		boolean sup=false;
		Iterator<Tweet> it =liste.iterator();
		while(it.hasNext()) {
			Tweet t=it.next();
			if (t.get_retweet().contains(stuser)) {
				b.ajoute(t);
                                lrt.add(t);
			}
			
		}
		System.out.println("ok");
                TreeMap<LocalDate, Integer> tmap = new TreeMap<LocalDate, Integer>();
                Iterator<Tweet> ite = lrt.iterator();
                while(ite.hasNext()){
                    Tweet t= ite.next();
                    LocalDate d= t.get_date().toLocalDate();
                    if(tmap.containsKey(d)) {
				int nb=tmap.get(d);
				//hmap.remove(s);
				tmap.put(d,nb+1);
			}
			else {
				tmap.put(d,1);
			}
                }
                for (Map.Entry mapentry : tmap.entrySet()) {
	           System.out.println("clé: "+mapentry.getKey() 
	                              + " | valeur: " + mapentry.getValue());
	        }
            return tmap;
	}
	
	
	public TreeSet lireuserretweet() {
		Iterator<Tweet> it =liste.iterator();
		TreeSet hset=  new TreeSet();
		while(it.hasNext()) {
			Tweet t=it.next();
			String s=t.get_retweet();
			if (s !=" "){
                            hset.add(s);
                        }
			
		}
		return hset;
		
		
	}
	
	//Nb de tweets par jour 
	public void nbtweets(LocalDate d1, LocalDate d2) {
		//boolean sup=false;
		int nb=0;
		int j=0;
		int tot=0;
		Iterator<Tweet> it =liste.iterator();
		while(it.hasNext() & (d1.isBefore(d2) || d1.equals(d2))) {
			Tweet t=it.next();
			if (t.get_date().toLocalDate().equals(d1)) {
				nb=nb+1;
			}
			if(t.get_date().toLocalDate().isAfter(d1)) {
				tot=tot+nb;
				System.out.println(d1+" : "+nb+" tweets");
				nb=1;
				j=j+1;
				d1=t.get_date().toLocalDate();
				//sup=true;
			}
		}
		
		System.out.println("nb moyen de tweets par jour: "+ tot/j);
	}
	
	public TreeMap nbtweets2(LocalDate d1, LocalDate d2) {
		
		TreeMap<LocalDate, Integer> tmap = new TreeMap<LocalDate, Integer>();
		//boolean sup=false;
		int nb=0;
		int j=0;
		int tot=0;
		Iterator<Tweet> it =liste.iterator();
		while(it.hasNext() & (d1.isBefore(d2) || d1.equals(d2))) {
			Tweet t=it.next();
			if (t.get_date().toLocalDate().equals(d1)) {
				nb=nb+1;
			}
			if(t.get_date().toLocalDate().isAfter(d1)) {
				tot=tot+nb;
				System.out.println(d1+" : "+nb+" tweets");
				tmap.put(d1,nb);
				nb=1;
				j=j+1;
				d1=t.get_date().toLocalDate();
				//sup=true;
			}
		}
		
		//System.out.println("nb moyen de tweets par jour: "+ tot/j);
		return tmap;
	}
	
	//Nb de tweets par heure
	public void nbtweets(LocalDate d) {
		
		LocalDateTime dt=d.atStartOfDay();
		System.out.println("dt : "+dt);
		//boolean sup=false;
		int nb=0;
		int h=0;
		int tot=0;
		Iterator<Tweet> it =liste.iterator();
		while(it.hasNext() & (dt.toLocalDate().isBefore(d) || dt.toLocalDate().equals(d))) {
			Tweet t=it.next();
			if (t.get_date().toLocalDate().equals(d)) {
				
				if(t.get_date().getHour() == dt.getHour()) {
					nb=nb+1;
				}
				
				if(t.get_date().getHour() > dt.getHour()) {
					tot=tot+nb;
					System.out.println(dt.getHour()+" : "+nb+" tweets");
					nb=1;
					h=h+1;
					//dt=dt.plus(1,ChronoUnit.HOURS); //augmentation d'une h mais pas bon par / au compteur
					dt=t.get_date();
				}
				
			}
		}
		
		System.out.println("nb moyen de tweets par heure: "+ tot/h);
	}
	
        
        //Nb de tweets par heure
	public TreeMap nbtweets2(LocalDate d) {
		
                TreeMap<Integer, Integer> tmap = new TreeMap<Integer, Integer>();
                int i;
                for (i=0;i<=23;i++){
                    tmap.put(i, 0);
                }
		LocalDateTime dt=d.atStartOfDay();
		System.out.println("dt : "+dt);
		//boolean sup=false;
		int nb=0;
		int h=0;
		int tot=0;
                //Dernier créneau: crénau impossible 25 si pas passé ds boucle 
                int lasth=25;
		Iterator<Tweet> it =liste.iterator();
		while(it.hasNext() & (dt.toLocalDate().isBefore(d) || dt.toLocalDate().equals(d))) {
			Tweet t=it.next();
			if (t.get_date().toLocalDate().equals(d)) {
				
				if(t.get_date().getHour() == dt.getHour()) {
					nb=nb+1;
                                        lasth=dt.getHour();
				}
				
				if(t.get_date().getHour() > dt.getHour()) {
					tot=tot+nb;
					System.out.println(dt.getHour()+" : "+nb+" tweets");
					 tmap.replace(dt.getHour(),nb);
                                        nb=1;
					//h=h+1;//pas bon ==>24
                                       
					//dt=dt.plus(1,ChronoUnit.HOURS); //augmentation d'une h mais pas bon par / au compteur
					dt=t.get_date();
				}
				
			}
		}
                if(lasth !=25){
                    tmap.replace(lasth,nb);
                }
                 
		
		//System.out.println("nb moyen de tweets par heure: "+ tot/h);
                return tmap;
	}
        
	public void write(String nom) throws IOException {
		FileOutputStream f = new FileOutputStream(nom);
		ObjectOutputStream o = new ObjectOutputStream(f);
		o.writeObject(liste);
		o.close();
		f.close();
		System.out.println("sauvegarde ok");
	}
	
	public void ouvrirAL(String nom) throws IOException, ClassNotFoundException {
		FileInputStream in = new FileInputStream(nom);
		ObjectInputStream read = new ObjectInputStream(in);
		Object lu=read.readObject();
		liste=(ArrayList<Tweet>)lu;
		read.close();
		in.close();
		System.out.println("ouverture ok");
		
	}
        
        
        
        
        
        
        
        
        public TreeMap lrt() {
		Iterator<Tweet> it =liste.iterator();
		TreeMap<String, Integer> hmap = new TreeMap<String, Integer>();
		while(it.hasNext()) {
			Tweet t=it.next();
			String s=t.get_retweet();
			//System.out.println(s);
			
			
			if(hmap.containsKey(s)) {
				int nb=hmap.get(s);
				//hmap.remove(s);
				hmap.put(s,nb+1);
			}
			else {
				hmap.put(s,1);
			}
		}
		
		//MODIFIER BOUCLE CAR COPIE COLLE
		for (Map.Entry mapentry : hmap.entrySet()) {
	           System.out.println("clé: "+mapentry.getKey() 
	                              + " | valeur: " + mapentry.getValue());
	        }
                return hmap;
	}
        
        
        public String lireretweet2(TreeMap<String, Integer> tmap) {
            
                String sortie="";
                 System.out.println("------------");
                 //Treemap décroissant
                 TreeMap<String, String> hmap = new TreeMap<String, String>(Collections.reverseOrder());
                 DecimalFormat nf = new DecimalFormat("000000");
            
                 for (Map.Entry mapentry : tmap.entrySet()) {
                     //On enelève les retweets vides
                    if(mapentry.getKey().toString().length() !=0){    
                            //on transforme les clés en valeur et inversement 
                            String val=mapentry.getKey().toString();
                             int key=Integer.parseInt(mapentry.getValue().toString());
                             String key2=nf.format(key);
                             hmap.put(key2, val);
                    }
                 } 
                 for (Map.Entry mapentry : hmap.entrySet()) {
                        
                          System.out.println("clé2: "+mapentry.getKey() 
	                              + " | valeur2: " + mapentry.getValue());
                 } 
                 
                 System.out.println("TOP");
                 Iterator<Map.Entry<String,String>> it=hmap.entrySet().iterator();
                 int i=1;
                 while(it.hasNext() && i<=5){
                    Map.Entry mapentry= it.next();
                    
                     System.out.println("clé2: "+mapentry.getKey() 
	                              + " | valeur2: " + mapentry.getValue());
                     sortie=sortie+mapentry.getValue()+" - "
                             +Integer.parseInt(mapentry.getKey().toString())+"\n";
                     i=i+1;
                 }
                 return sortie;
              /*  HashMap<String, Integer> hmap = new HashMap<String, Integer>();
                int max;
                String key="null";
                String val;
                for(int i=1; i<=5;i++){
                    max=0;
                    for (Map.Entry mapentry : tmap.entrySet()) {
                        if( Integer.parseInt(mapentry.getValue().toString())>=max){
                            System.out.println(i+"max: "+Integer.parseInt(mapentry.getValue().toString()));
                             key=mapentry.getKey().toString();
                             max=Integer.parseInt(mapentry.getValue().toString());
                        // hmap.put(mapentry.getKey().toString(),mapentry.getValue().toString());
                        } 
                        
                    }
                    hmap.put(key, max);
                        tmap.remove(key);
	        }
                for (Map.Entry mapentry : hmap.entrySet()) {
	           System.out.println("clé2: "+mapentry.getKey() 
	                              + " | valeur2: " + mapentry.getValue());
	        }*/
	}
        
        
        
        public void lireretweet() {
		Iterator<Tweet> it =liste.iterator();
		TreeMap<String, Integer> hmap = new TreeMap<String, Integer>();
		while(it.hasNext()) {
			Tweet t=it.next();
			String s=t.get_retweet();
			//System.out.println(s);
			
			
			if(hmap.containsKey(s)) {
				int nb=hmap.get(s);
				//hmap.remove(s);
				hmap.put(s,nb+1);
			}
			else {
				hmap.put(s,1);
			}
		}
		
		//MODIFIER BOUCLE CAR COPIE COLLE
		for (Map.Entry mapentry : hmap.entrySet()) {
	           System.out.println("clé: "+mapentry.getKey() 
	                              + " | valeur: " + mapentry.getValue());
	        }
	}
        
        

}
