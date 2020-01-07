/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import static javafx.scene.input.KeyCode.X;
import static javafx.scene.input.KeyCode.Y;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author Anthony
 */
public class F_Accueil extends Application {
    
    Stage myStage;
    Stage StageMenu;
    String data="null";
        
    static BaseDeTweets tab = new BaseDeTweets();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    static BaseDeTweets tab2 = new BaseDeTweets();
     
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        Stage myStage = primaryStage;
        myStage.setResizable(false);
        StageStyle DECORATED = StageStyle.DECORATED;
        primaryStage.setTitle("Accueil");
        primaryStage.setScene(construitScene());
        primaryStage.sizeToScene();
        primaryStage.show();
    }
	
    public static void main(String[] args) {
	launch(args);
    }
    
    //Accueil: choix du fichier
    public Scene construitScene() {
            
        BorderPane bpane= new BorderPane();
        bpane.setStyle("-fx-background-color: white;");
        Text txtintro=new Text("\n Outil de reporting sur les données de Twitter \n");
        txtintro.setFont(new Font("Arial",20));
        
       
        Label ldata =  new Label("Choisissez le fichier à utiliser : ");  
        
        ToggleGroup tog = new ToggleGroup();
        RadioButton rbfoot = new RadioButton("Foot");
        rbfoot.setToggleGroup(tog);
        rbfoot.setSelected(true);
        RadioButton rbclim = new RadioButton("Climat");
        rbclim.setToggleGroup(tog);
            
        //Choix du jeu de donnees
        Button bcharge=new Button("Charger");
        bcharge.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
    
                //Chargement des donnees foot
                if(rbfoot.isSelected() && data!="foot"){
                    // charger("Foot.txt");
                    //  tab.ouvrirAL("footobjet.txt");
                    charger("/Users/Anthony/Desktop/fichiers java/Foot.txt");
                    data="foot";
                }
                //Chargement des donnees climat
                else if(rbclim.isSelected() && data!="climat"){
                    charger("/Users/Anthony/Desktop/fichiers java/Climat.txt");
                    data="climat";
                }
                
                //Creation de la page de "menu"
                StageMenu = new Stage();
                StageMenu.setResizable(false);
                StageMenu.setTitle("Menu");
                StageMenu.setScene(construitMenu());
                StageMenu.sizeToScene();
                //  StageMenu.initModality(Modality.WINDOW_MODAL);
                // StageMenu.initOwner(myStage);
                StageMenu.show();
            } 
        });
     
        Button bquit=new Button("Quitter");
               
        VBox vbox= new VBox(2);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);
        vbox.getChildren().addAll(txtintro,ldata,rbfoot,rbclim,bcharge,bquit);
    
        bpane.setCenter(vbox);

        StackPane root=new StackPane();
        root.getChildren().addAll(bpane);
        Scene scene= new Scene(root,500,300);
        return scene;
		
    }
        
   
    public Scene construitMenu(){
        BorderPane grid = new BorderPane();
        
         Text txtintro=new Text("Liste des fonctionnalités de l'outil");  
                
        Button baff = new Button("Afficher");
        baff.setOnAction((ActionEvent t) -> {
            lecture();
        });
        Font font1 = new Font("Dialog", 16);
        baff.setFont(font1);

        Button bnb = new Button("Nombre de tweets");
        bnb.setOnAction((ActionEvent t) -> {
            nbTweets();
        });

        Button brt = new Button("Retweets");
        brt.setOnAction((ActionEvent t) -> {
            Retweets();
        });
        
        HBox hbox = new HBox();
        hbox.setSpacing(15);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(baff, bnb, brt);
        VBox vbox = new VBox();
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(txtintro,hbox);
	
        MenuBar menu=menuB(StageMenu);
        //Agencement des elements    
        grid.setTop(menu);
        grid.setCenter(vbox);

        StackPane root = new StackPane();
        root.getChildren().addAll(grid);
        Scene scene = new Scene(root, 400, 200);
        return scene;
		
    }
       
    
    public static void charger(String fichier) {
        tab.initialise();

        Tweet t;
        try {
            FileReader r = new FileReader(fichier);
            BufferedReader br = new BufferedReader(r);

            String ligne;
            ligne = br.readLine();

            do {
                //On decoupe les lignes du fichier pour associer 
                //chaque element a un champs de tweet
                try {
                    String[] sp = ligne.split("	", 5);
                    if (Character.isDigit(sp[0].charAt(0)) && (sp[2].length() > 0)
                            && Character.isDigit(sp[2].charAt(0))) {
                        try {
                            LocalDateTime date = LocalDateTime.parse(sp[2], formatter);
                            t = new Tweet(sp[0], sp[1], date, sp[3], sp[4]);
                            //Ajout du tweet a la liste 
                            tab.ajoute(t);
                            // System.out.println("zz");
                        } catch (DateTimeParseException e) {
                            e.printStackTrace();
                        }
                        //28/12
                    }
                } catch (Exception e) { 
                //Certaines lignes ne contiennent pas 5 elements et ne sont donc pas traitees
                    System.out.println("manque éléments");
                }

                ligne = br.readLine();
            //Tant qu'il y a une ligne a lire dans le fichier 
            } while (ligne != null);
        Alert alert = new Alert(AlertType.INFORMATION);
            
            alert.setTitle("Chargement");
            alert.setHeaderText("Chargement des données terminé");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("size: " + tab.get_size());
        System.out.println(tab.lire2(5));
    }

    //Page pour les traitements sur le nombre de tweets 
    public void nbTweets() {

        Stage StageNb = new Stage();
        StageNb.setTitle("Nombre de tweets");
        ScrollPane scrollPane = new ScrollPane();

        //Abscisse
        CategoryAxis x = new CategoryAxis();
         x.setTickMarkVisible(true);
        //Ordonee
        NumberAxis y = new NumberAxis();
         y.setLabel("Nombre de tweets");
        
        //Graphique
        BarChart lc = new BarChart<String, Number>(x, y);
       /*  XYChart.Series serietest = new XYChart.Series();
                serietest.setName("Nombre de tweets");
       
        serietest.getData().add(new XYChart.Data<String, Number>(tab.date(0).toString(), 0));
        serietest.getData().add(new XYChart.Data<String, Number>(tab.date(0).plusDays(1).toString(), 0));
       lc.getData().add(serietest);*/
       
        //Abscisse
        CategoryAxis x2 = new CategoryAxis();
        //Ordonee
        NumberAxis y2 = new NumberAxis();
         y.setLabel("Nombre de tweets");
       
         BarChart lc2 = new BarChart<String, Number>(x2, y2);
        // lc.getData().add(new XYChart.Data("2019-06-21", 23));;

        String st = "Nombre moyen de tweets par jour sur la période : ";
        Label l = new Label(st);
        BorderPane bp = new BorderPane();
        VBox vbox = new VBox();
        vbox.setSpacing(15);
        //Calendrier depart
        DatePicker d1 = new DatePicker();
        d1.setValue(tab.date(0));
        //Calendrier arrivee
        DatePicker d2 = new DatePicker();
        d2.setValue(tab.date(0));
        
        ObservableList<String> xlabels = FXCollections.observableArrayList();
                ObservableList<String> x2labels = FXCollections.observableArrayList();
        x2labels.addAll("0h-1h","2h-3h","3h-4h","4h-5h","5h-6h","6h-7h","7h-8h"
        ,"8h-9h","9h-10h","10h-11h","11h-12h","12h-13h","13h-14h","14h-15h","15h-16h","16h-17h"
        ,"17h-18h","18h-19h","19h-20h","20h-21h","21h-22h","22h-23h","23h-24h");
        x2.setCategories(x2labels);
        TreeMap<LocalDate, Integer> tmapabs = tab.nbtweets2(tab.date(0), tab.date(1));
                for (Map.Entry mapentry : tmapabs.entrySet()) {
              // monthNames.add(mapentry.getKey().toString());
                }

        //Affichage du nombre de tweets entre 2 jours 
        Button but = new Button("Afficher");
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                
                xlabels.clear();
                //Récuperation du nombre de tweets sur la période selectionnee
                TreeMap<LocalDate, Integer> hmap = tab.nbtweets2(d1.getValue(), d2.getValue());

                XYChart.Series series = new XYChart.Series();
                series.setName("Nombre de tweets");
                //series.getData().clear();
                int tot = 0;

                //Définition des valeurs de la serie a afficher 
                for (Map.Entry mapentry : hmap.entrySet()) {
                    series.getData().add(new XYChart.Data(mapentry.getKey().toString(), mapentry.getValue()));
                    //Somme des tweets
                    tot = tot + hmap.get(mapentry.getKey());
                    xlabels.add(mapentry.getKey().toString());
                }
                lc.getData().clear();
                
        x.setCategories(xlabels);
       
        
//Ajout de la serie au graphique 
                lc.getData().add(series);
                

                //Affichage du nombre moyen de tweets par jour sur la periode 
                String st = "Nombre moyen de tweets par jour sur la période : " + Integer.toString(tot / hmap.size());
                l.setText(st);
            }
        });

        DatePicker d3 = new DatePicker();
        d3.setValue(tab.date(0));
        //affichage du nombre de tweets sur une journee
        Button but2 = new Button("Afficher");
        but2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //Récuperation du nombre de tweets sur la période selectionnee
                TreeMap<Integer, Integer> hmap = tab.nbtweets2(d3.getValue());

                XYChart.Series series = new XYChart.Series();
                series.setName("Nombre de tweets");
                //series.getData().clear();
                int tot = 0;

                //Définition des valeurs de la serie a afficher 
                for (Map.Entry mapentry : hmap.entrySet()) {
                    series.getData().add(new XYChart.Data(mapentry.getKey().toString() + "h-" + (Integer.parseInt(mapentry.getKey().toString()) + 1) + "h", mapentry.getValue()));
                    //Somme des tweets
                    tot = tot + hmap.get(mapentry.getKey());
                }
                lc2.getData().clear();
                //Ajout de la serie au graphique 
                lc2.getData().add(series);

                //Affichage du nombre moyen de tweets par jour sur la periode 
                String st = "Nombre moyen de tweets par jour sur la période : " + Integer.toString(tot / 24);
                l.setText(st);
                lc2.setTitle(d3.getValue().toString());
                lc2.setStyle("-fx-bar-fill: red");
            }
        });

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(d1, d2, but, d3, but2);
        bp.setLeft(vbox);

        VBox vbox2 = new VBox();
        vbox2.setSpacing(15);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(lc, l,lc2);
        bp.setCenter(vbox2);
        
        MenuBar menu=menuB(StageNb);
        bp.setTop(menu);

        StackPane root = new StackPane();
        root.getChildren().addAll(bp);
        Scene scene = new Scene(root /*, 800, 400*/);
        StageNb.setScene(scene);
        StageNb.sizeToScene();
        StageNb.show();

    }
    
    public void graphique1(){
        
    }


    public void lecture() {

        Text tf = new Text();
        Stage StageAff = new Stage();
        StageAff.setTitle("Aff");

        BorderPane bp = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(bp);
        scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);

        VBox vbox = new VBox();
        vbox.setSpacing(15);

        Button baff = new Button("Afficher tout");
        baff.setOnAction((ActionEvent t) -> {
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setResizable(true);
            alert.setWidth(800);
            alert.setHeight(400);
            alert.setTitle("Affichage complet");
            alert.setHeaderText("L'affichage de l'ensemble des données peut être long."
              + " Etes-vous sûrs de vouloir tout afficher ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                 String resall = tab.lire2();
                tf.setText(resall);
            }
           
        });
       
        DatePicker d1 = new DatePicker();
        //Valeur initiale du calendrier = date du premier tweet de la liste 
        d1.setValue(tab.date(0));
       
        Button but = new Button("Afficher");
        //Affichage des tweets d'un certain jour 
        but.setOnAction((ActionEvent t) -> {
            String resd = tab.lire2(d1.getValue());
            tf.setText(resd);
        });

        HBox hbox = new HBox();
        hbox.setSpacing(15);
        HBox hbox2 = new HBox();
        hbox2.setSpacing(15);
        DatePicker d2 = new DatePicker();
        d2.setValue(tab.date(0));
        
        //CHoix des heure de depart et d'arrivee
        Spinner<Integer> spinh = new Spinner<Integer>(0, 23, 0);
        spinh.setPrefWidth(60);
        Label lheures1=new Label("h");
        Spinner<Integer> spinm = new Spinner<Integer>(0, 59, 0);
        spinm.setPrefWidth(60);
        Spinner<Integer> spinh2 = new Spinner<Integer>(0, 23, 0);
        spinh2.setPrefWidth(60);
        Label lheures2=new Label("h");
        Spinner<Integer> spinm2 = new Spinner<Integer>(0, 59, 0);
        spinm2.setPrefWidth(60);
        Button but2 = new Button("Afficher heures");
        but2.setOnAction((ActionEvent t) -> {
            
            DecimalFormat nf = new DecimalFormat("00");

            String j = d2.getValue().toString();
            //"yyyy-MM-dd HH:mm:ss"
            String s;
            s = j + " " + nf.format(spinh.getValue()) + ":" + nf.format(spinm.getValue());
            System.out.println(s);
            String s2;
            s2 = j + " " + nf.format(spinh2.getValue()) + ":" + nf.format(spinm2.getValue());
            System.out.println(s);
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            try {
                LocalDateTime date1 = LocalDateTime.parse(s, formatter2);
                LocalDateTime date2 = LocalDateTime.parse(s2, formatter2);
                //tab.lire(date1,date2);
                String resd1d2 = tab.lire2(date1, date2);
                tf.setText(resd1d2);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        });

        MenuBar menu=menuB(StageAff);
        
        hbox.getChildren().addAll(baff, d1/*,d2*/, but);
        hbox2.getChildren().addAll(d2, spinh,lheures1, spinm, spinh2,lheures2, spinm2, but2);
        Label lperiode=new Label("Lecture des tweets sur une plage horaire d'une journée: ");
        vbox.getChildren().addAll(menu,hbox,lperiode, hbox2);
        bp.setTop(vbox);

        bp.setCenter(tf);

        StackPane root = new StackPane();
        Scene scene = new Scene(scrollPane, 800, 400);
        root.getChildren().addAll(bp);
        StageAff.setScene(scene);
        StageAff.sizeToScene();
        //  StageMenu.initModality(Modality.WINDOW_MODAL);
        // StageMenu.initOwner(myStage);
        StageAff.show();

    }

    
    public void graphique(TreeMap<?,Integer> tmap){
        
    }
    
    
    public void Retweets() {
        
        Stage StageRT = new Stage();
        StageRT.setTitle("Retweets");

        //Abscisse
        CategoryAxis x = new CategoryAxis();
        //Ordonee
        NumberAxis y = new NumberAxis();
         y.setLabel("Nombre de retweets");
        x.setTickLabelRotation(45);
        
        //Graphique
        BarChart bchart = new BarChart<String, Number>(x, y);
        // lc.getData().add(new XYChart.Data("2019-06-21", 23));;
        bchart.setLegendVisible(false);
        Label l = new Label();
        BorderPane bp = new BorderPane();
        VBox vbox = new VBox();
        vbox.setSpacing(15);
        Label ltop = new Label("TOP Retweets entre 2 jours");
        //Calendrier depart
        DatePicker d1 = new DatePicker();
        d1.setValue(tab.date(0));
        //Calendrier arrivee
        DatePicker d2 = new DatePicker();
        d2.setValue(tab.date(1));

        //Affichage du TOP 5
        String sttop = "TOP 5 \n  ";
        Text ttop = new Text(sttop+" -- \n -- \n -- \n -- \n --");
        ttop.setTextAlignment(TextAlignment.CENTER);
        
        
        //TOP 5 Retweets entre 2 jours
        Button but = new Button("Afficher");
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                tab2.initialise(); 
                String sortie = tab.lireretweet3(d1.getValue(), d2.getValue());
                ttop.setText(sttop + sortie);
            }
        });

        //TOP 5 Retweets sur une journee
        Label ltop2 = new Label("TOP Retweets sur une journée");
        DatePicker d3 = new DatePicker();
        d3.setValue(tab.date(0));

        Button but2 = new Button("Afficher");
        but2.setOnAction((ActionEvent t) -> {
            String res = tab.lireretweet3(d3.getValue());
            ttop.setText(sttop + res);
        });
        
        
        //Suivi de la popularite des utilisateurs 
        
        //Recuperation de la liste des utilisateurs retweetes
        ComboBox comb = new ComboBox();
        Label luser = new Label("Liste des utilisateurs retweetés : ");
        TreeSet tset = tab.lireuserretweet();
        Iterator it = tset.iterator();
        while (it.hasNext()) {
            comb.getItems().add(it.next());
        }
        
        ObservableList<String> xlabels = FXCollections.observableArrayList();
        LocalDate currentdate=tab.date(0);
        Period dif = Period.between(currentdate,tab.date(1));
        for(int i=0;i<=dif.getDays();i++){
            xlabels.add(currentdate.toString());
             currentdate=currentdate.plusDays(1);
        }
        x.setCategories(xlabels);
        Button but3 = new Button("Afficher");
        but3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                TreeMap<LocalDate, Integer> tmap = tab.lireretweet(comb.getValue().toString());
                XYChart.Series series = new XYChart.Series();
               
                int tot = 0;

                //Définition des valeurs de la serie a afficher 
                for (Map.Entry mapentry : tmap.entrySet()) {
                    series.getData().add(new XYChart.Data(mapentry.getKey().toString(), mapentry.getValue()));
                    //Somme des tweets
                    tot = tot + tmap.get(mapentry.getKey());
                }
                bchart.getData().clear();
                y.setUpperBound(tot);
                y.setTickUnit(1);
             
                //Ajout de la serie au graphique 
                bchart.getData().add(series);

                //Affichage du nombre moyen de tweets par jour sur la periode 
                String st = "Nombre moyen de retweets par jour sur la période : " + Integer.toString(tot / tmap.size());
                l.setText(st);
            }
        });

        DatePicker d4 = new DatePicker();
        d4.setValue(tab.date(0));
        
        //Abscisse
        CategoryAxis x2 = new CategoryAxis();
        //Ordonee
        NumberAxis y2 = new NumberAxis();
         y2.setLabel("Nombre de retweets");
        x2.setTickLabelRotation(45);
        
        //Graphique
        BarChart bchart2 = new BarChart<String, Number>(x2, y2);
        bchart2.setLegendVisible(false);
        
        ObservableList<String> x2labels = FXCollections.observableArrayList();
        x2labels.addAll("0h-1h","2h-3h","3h-4h","4h-5h","5h-6h","6h-7h","7h-8h"
        ,"8h-9h","9h-10h","10h-11h","11h-12h","12h-13h","13h-14h","14h-15h","15h-16h","16h-17h"
        ,"17h-18h","18h-19h","19h-20h","20h-21h","21h-22h","22h-23h","23h-24h");
        x2.setCategories(x2labels);
        Button but4 = new Button("Retweets sur la journée");
        but4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                TreeMap<LocalDate, Integer> tmap = tab.lireretweet(comb.getValue().toString(), d4.getValue());
                /*    tab2.initialise();  
              TreeMap <LocalDate, Integer>  tmap= tab.rt(tab2,"lxrd93");*/
                //  tab2.lire();
                XYChart.Series series = new XYChart.Series();
                
                //series.getData().clear();
                int tot = 0;

                //Définition des valeurs de la serie a afficher 
                for (Map.Entry mapentry : tmap.entrySet()) {
                    series.getData().add(new XYChart.Data(mapentry.getKey().toString(), mapentry.getValue()));
                    //Somme des tweets
                    tot = tot + tmap.get(mapentry.getKey());
                }
                bchart2.getData().clear();
                 y2.setUpperBound(tot);
                //Ajout de la serie au graphique 
                bchart2.getData().add(series);

                //Affichage du nombre moyen de tweets par jour sur la periode 
                String st = "Nombre moyen de retweets par jour sur la période : " + Integer.toString(tot / tmap.size());
                l.setText(st);
            }
        });

        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(ltop, d1, d2, but,ltop2, d3, but2, ttop);
        vbox.setStyle("-fx-background-color: #B0C4DE;");
        bp.setLeft(vbox);

        MenuBar menu=menuB(StageRT);
        VBox vboxtop= new VBox();
        HBox hbox = new HBox();
        hbox.setSpacing(15);
        hbox.getChildren().addAll(luser, comb, but3);
        hbox.setAlignment(Pos.CENTER);
        HBox hbox2=new HBox();
        hbox2.setSpacing(15);
        hbox2.getChildren().addAll(d4,but4);
        vboxtop.setSpacing(15);
        vboxtop.getChildren().addAll(menu);
        bp.setTop(vboxtop);

        VBox vbox2 = new VBox();
        vbox2.setSpacing(15);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.getChildren().addAll(hbox,bchart, l,hbox2,bchart2);
        bp.setCenter(vbox2);

        StackPane root = new StackPane();
        root.getChildren().addAll(bp);
        Scene scene = new Scene(root);
        StageRT.setScene(scene);
        StageRT.sizeToScene();
        StageRT.show();
    }
   
    
    public MenuBar menuB(Stage CurrentStage){
        MenuBar menu = new MenuBar();
        Menu menu1 = new Menu("Application");
        menu.getMenus().addAll(menu1);
        MenuItem itemacc = new MenuItem("Accueil");
        MenuItem itemmenu= new MenuItem("Menu");
        itemmenu.setOnAction((ActionEvent t)->{
            CurrentStage.close();
            StageMenu.isFocused();
        });
        MenuItem itemquit = new MenuItem("Quitter");
        itemquit.setOnAction((ActionEvent t)->{
            System.exit(0);
        });
        itemquit.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        
        if(CurrentStage.equals(StageMenu)){
            menu1.getItems().addAll(itemacc,itemquit);
        }
        else{
            menu1.getItems().addAll(itemacc,itemmenu,itemquit);
        }

        itemacc.setOnAction((ActionEvent t) -> {
            CurrentStage.close();
            if(!(CurrentStage.equals(StageMenu))){
                StageMenu.close();
            }
            
           // myStage.isFocused();
        });
	return menu;	
    }
    
    

}
