/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TennisProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author quinn
 */
public class MainPage extends Application {
    // ADMIN //////////////////////////////////////////////////////////////////
    private final Label adminTeamLabel = new Label("Enter a New Team:");
    private final Label adminPlayerLabel = new Label("Enter the name of a New Player:");
    private final Label adminMatchLabel = new Label("This will generate a match between all teams.");
    private final Label adminWarningLabel = new Label("Warning: all pre-existing match information will be removed.");
    private final Label adminStatsLabel = new Label("The stats report will be generated automatically every 100 sec.");
    private final Label adminClickLabel = new Label("You can also generate it by clicking the button on the right.");
    private final Label adminPassLabel = new Label("Enter Password:");
    private Label adminPassCheckLabel = new Label("Not Logged in");
    
    private TextField adminTeamText  = new TextField();
    private TextField adminPlayerText = new TextField();
    private TextField adminPassText = new TextField();
    
    private final Button adminTeamBtn = new Button("Add Team");
    private final Button adminPlayerBtn = new Button("Register Player");
    private final Button adminFixtureBtn = new Button("Generate Fixtures");
    private final Button adminStatsBtn = new Button("Generate Team Stats");
    private final Button adminPassBtn = new Button("Log In");

    private ComboBox adminTeamCb = new ComboBox();

    private final Separator separator1 = new Separator(Orientation.HORIZONTAL);
    private final Separator separator2 = new Separator(Orientation.HORIZONTAL);
    private final Separator separator3 = new Separator(Orientation.HORIZONTAL);
    private final Separator separator4 = new Separator(Orientation.HORIZONTAL);
    private final Separator separator5 = new Separator(Orientation.HORIZONTAL);
    
    // VIEWER /////////////////////////////////////////////////////////////////
    private TextArea viewText = new TextArea();
    
    private ComboBox viewHomeCb = new ComboBox();
    private ComboBox viewAwayCb = new ComboBox();
    
    private final Button viewChartBtn = new Button("View fixture and result chart");
    private final Button viewStatsBtn = new Button("Show all team stats");
    private final Button viewRankBtn = new Button("Show all team ranking");
    private final Button viewScoreBtn = new Button("View a match score");
    
    private final Separator separator6 = new Separator(Orientation.HORIZONTAL);

    // SCORE SHEET ////////////////////////////////////////////////////////////
    private final Label ssHomeLabel = new Label("Home Team");
    private final Label ssAwayLabel = new Label("Away Team");
    private final Label ssSingleLabel = new Label("Single Sets");
    private final Label ssDoubleLabel = new Label("Double Sets");
        
    // Home 1 + Away 1
    private TextField oneFour = new TextField();
    private TextField oneFive = new TextField();
    private TextField oneSix = new TextField();
    
    // Home 1 + Away 2
    private TextField twoFour = new TextField();
    private TextField twoFive = new TextField();
    private TextField twoSix = new TextField();
    
    // Home 2 + Away 1
    private TextField oneSeven = new TextField();
    private TextField oneEight = new TextField();
    private TextField oneNine = new TextField();
    
    // Home 2 + Away 2
    private TextField twoSeven = new TextField();
    private TextField twoEight = new TextField();
    private TextField twoNine = new TextField();
    
    // Double sets
    private TextField oneEleven = new TextField();
    private TextField oneTwelve = new TextField();
    private TextField oneThirteen = new TextField();
    private TextField ssScore = new TextField();
    
    private final Button ssNewBtn = new Button("New Sheet");
    private final Button ssModifyBtn = new Button("Modify Sheet");
    private final Button ssSubmitBtn = new Button("Calculate and submit scores");

    private ComboBox ssHomeCb = new ComboBox();
    private ComboBox ssAwayCb = new ComboBox();
    private ComboBox ssAPlayer1Cb = new ComboBox();
    private ComboBox ssAPlayer2Cb = new ComboBox();
    private ComboBox ssHPlayer1Cb = new ComboBox();
    private ComboBox ssHPlayer2Cb = new ComboBox();

    private final Separator ssSeparator = new Separator(Orientation.HORIZONTAL);
    
    // MISC ///////////////////////////////////////////////////////////////////
    private final String csvFile = "teamData.csv";
    private final String matchFile = "matchData.csv";
    private final String statsFile = "statData.csv";
    private final String fixFile = "fixtureData.csv";
    private final String passFile = "password.txt";
    private List<List<String>> teamsList2D = new ArrayList<List<String>>();
    private List<List<String>> statsList2D = new ArrayList<List<String>>();
    private boolean loggedIn = false;
    private String passwordAuth;
    
    
    @Override
    public void start(Stage primaryStage) {
        // Main Layout
        Pane mainPane = new Pane();
        TabPane tabPane = new TabPane();
        Tab adminTab = new Tab("Admin");
        Tab viewerTab = new Tab("Viewer");
        Tab ssTab = new Tab("Score Sheet");
        adminTab.setClosable(false);        
        viewerTab.setClosable(false);
        ssTab.setClosable(false);
        
        // ADMIN //////////////////////////////////////////////////////////////
        
        // Admin Page
        GridPane adminPane = new GridPane();
        adminPane.setHgap(5);
        adminPane.setVgap(5);
        adminPane.setPadding(new Insets(10, 10, 10, 10));
        
        // Admin Page New Team 
        adminPane.add(adminTeamLabel,       0,  0);
        adminPane.add(adminTeamText,        1,  0,  5,  1);
        adminPane.add(adminTeamBtn,         5,  1);
        adminPane.add(separator1,           0,  2,  6,  1);
        
        // Admin Page New Player 
        adminPane.add(adminPlayerLabel,     0,  3,  2,  1);
        adminPane.add(adminPlayerText,      2,  3);
        adminPane.add(adminTeamCb,          3,  3,  3,  1);
        adminPane.add(adminPlayerBtn,       5,  4);
        adminPane.add(separator2,           0,  5,  6,  1);
        
        // Admin Page Generate Fixtures
        adminPane.add(adminMatchLabel,      0,  6,  3,  1);
        adminPane.add(adminWarningLabel,    0,  7,  4,  1);
        adminPane.add(adminFixtureBtn,      5,  8);
        adminPane.add(separator3,           0,  9,  6,  1);
        
        // Admin Page Generate Stats 
        adminPane.add(adminStatsLabel,      0,  10,  5,  1);
        adminPane.add(adminClickLabel,      0,  11,  4,  1);
        adminPane.add(adminStatsBtn,        5,  11);
        adminPane.add(separator4,           0,  12,  6,  1);

        // Admin Page Admin Password
        adminPane.add(adminPassLabel,       0,  13);
        adminPane.add(adminPassText,        1,  13,  4,  1);
        adminPane.add(adminPassBtn,         5,  13);
        adminPane.add(adminPassCheckLabel,  5,  14);
        adminPane.add(separator5,           0,  15,  6,  1);
        
        // Default Values
        adminTeamCb.setPromptText("Select a team name");
        
        // Set properties for UI
        adminPane.setAlignment(Pos.TOP_LEFT);
        adminTeamText.setAlignment(Pos.BOTTOM_LEFT);
        adminPlayerText.setAlignment(Pos.BOTTOM_LEFT);
        adminPassText.setAlignment(Pos.BOTTOM_LEFT);
        adminPlayerText.setPrefWidth(80);

        // Position Align
        GridPane.setHalignment(adminTeamBtn, HPos.RIGHT);
        GridPane.setHalignment(adminPlayerBtn, HPos.RIGHT);
        GridPane.setHalignment(adminFixtureBtn, HPos.RIGHT);
        GridPane.setHalignment(adminStatsBtn, HPos.RIGHT);
        GridPane.setHalignment(adminPassBtn, HPos.RIGHT);
        GridPane.setHalignment(adminPassCheckLabel, HPos.RIGHT);
        
        // Process events
        adminTeamBtn.setOnAction(e -> adminAddNewTeam());
        adminPlayerBtn.setOnAction(e -> adminAddNewPlayer());
        adminFixtureBtn.setOnAction(e -> generateFixture());
        adminStatsBtn.setOnAction(e -> generateStats());
        adminPassBtn.setOnAction(e -> logIn());
        
        // ADMIN END //////////////////////////////////////////////////////////
        
        // VIEWER /////////////////////////////////////////////////////////////
        
        // Viewer Page
        GridPane viewerPane = new GridPane();
        viewerPane.setHgap(5);
        viewerPane.setVgap(5);
        viewerPane.setPadding(new Insets(10, 10, 10, 10));
        
        // Viewer Page All
        viewerPane.add(viewChartBtn,        0,  0);
        viewerPane.add(viewStatsBtn,        0,  1);
        viewerPane.add(viewRankBtn,         0,  2);
        viewerPane.add(separator6,          0,  3);
        viewerPane.add(viewHomeCb,          0,  4);
        viewerPane.add(viewAwayCb,          0,  5);
        viewerPane.add(viewScoreBtn,        0,  6);
        viewerPane.add(viewText,            1,  0,  1,  8);
        
        // Set properties for UI
        viewerPane.setAlignment(Pos.TOP_LEFT);
        viewText.setPrefHeight(320);
        viewText.setPrefWidth(340);

        // Position Align
        GridPane.setHalignment(viewChartBtn, HPos.CENTER);
        GridPane.setHalignment(viewStatsBtn, HPos.CENTER);
        GridPane.setHalignment(viewRankBtn, HPos.CENTER);
        GridPane.setHalignment(viewHomeCb, HPos.CENTER);
        GridPane.setHalignment(viewAwayCb, HPos.CENTER);
        GridPane.setHalignment(viewScoreBtn, HPos.CENTER);
        
        // Default Values
        viewHomeCb.setPromptText("Home Team");
        viewAwayCb.setPromptText("Away Team");
        
        // Process events
        viewChartBtn.setOnAction(e -> viewResults());
        viewStatsBtn.setOnAction(e -> showAllStats());
        viewRankBtn.setOnAction(e -> showAllRankings());
        viewScoreBtn.setOnAction(e -> viewMatchScores());
        
        // VIEWER END /////////////////////////////////////////////////////////
        
        // SCORE SHEET ////////////////////////////////////////////////////////
        
        // Score Sheet Page
        GridPane ssPane = new GridPane();
        ssPane.setHgap(5);
        ssPane.setVgap(5);
        ssPane.setPadding(new Insets(10, 10, 10, 10));
        
        // Score Sheet Header 
        HBox ssHeader = new HBox();
        ssHeader.getChildren().addAll(ssNewBtn, ssModifyBtn);
        ssHeader.setSpacing(5);
        ssPane.add(ssHeader,                0,  0,  4,  1);
        ssPane.add(ssSeparator,             0,  1,  5,  1);
        
        // Score Sheet Teams
        HBox ssTeams = new HBox();
        ssTeams.setSpacing(5);
        ssTeams.getChildren().addAll(ssHomeLabel, ssHomeCb, ssAwayLabel, ssAwayCb);
        ssPane.add(ssTeams,                 0,  2,  4,  1);
        
        int column1 = 1;
        int column2 = 2;
        
        // Score Sheet Sets
        ssPane.add(ssSingleLabel,           0,  3);
        ssPane.add(ssAPlayer1Cb,            column1,  3);
        ssPane.add(ssAPlayer2Cb,            column2,  3);
        ssPane.add(ssHPlayer1Cb,            0,  4,  1,  3);
        ssPane.add(oneFour,                 column1,  4);
        ssPane.add(oneFive,                 column1,  5);
        ssPane.add(oneSix,                  column1,  6);
        ssPane.add(twoFour,                 column2,  4);
        ssPane.add(twoFive,                 column2,  5);
        ssPane.add(twoSix,                  column2,  6);
        ssPane.add(ssHPlayer2Cb,            0,  7,  1,  3);
        ssPane.add(oneSeven,                column1,  7);
        ssPane.add(oneEight,                column1,  8);
        ssPane.add(oneNine,                 column1,  9);
        ssPane.add(twoSeven,                column2,  7);
        ssPane.add(twoEight,                column2,  8);
        ssPane.add(twoNine,                 column2,  9);
        ssPane.add(ssDoubleLabel,           0,  12,  1,  1);
        ssPane.add(oneEleven,               column1,  11);
        ssPane.add(oneTwelve,               column1,  12);
        ssPane.add(oneThirteen,             column1,  13);
        ssPane.add(ssScore,                 column2,  11,  1,  3);
        ssPane.add(ssSubmitBtn,             0,  14,  4,  1);
        
        // Set properties for UI
        int ssColHeight = 80;
        int ssColWidth = 150;
        
        // Position Align
        ssPane.setAlignment(Pos.TOP_LEFT);
        ssSingleLabel.setAlignment(Pos.CENTER);
        ssDoubleLabel.setAlignment(Pos.BOTTOM_CENTER);
        ssTeams.setAlignment(Pos.TOP_RIGHT);
        
        ssScore.setEditable(false);
        
        // Resize items Height
        ssSingleLabel.setPrefHeight(ssColHeight);
        ssAPlayer1Cb.setPrefHeight(ssColHeight);
        ssAPlayer2Cb.setPrefHeight(ssColHeight);
        ssHPlayer1Cb.setPrefHeight(ssColHeight);
        ssHPlayer2Cb.setPrefHeight(ssColHeight);
        ssScore.setPrefHeight(ssColHeight);
        
        // Resize items Width
        ssSingleLabel.setMaxWidth(ssColWidth);
        ssAPlayer1Cb.setMaxWidth(ssColWidth);
        ssAPlayer2Cb.setMaxWidth(ssColWidth);
        ssHPlayer1Cb.setMaxWidth(ssColWidth);
        oneFour.setMaxWidth(ssColWidth);
        oneFive.setMaxWidth(ssColWidth);
        oneSix.setMaxWidth(ssColWidth);
        twoFour.setMaxWidth(ssColWidth);
        twoFive.setMaxWidth(ssColWidth);
        twoSix.setMaxWidth(ssColWidth);
        ssHPlayer2Cb.setMaxWidth(ssColWidth);
        oneSeven.setMaxWidth(ssColWidth);
        oneEight.setMaxWidth(ssColWidth);
        oneNine.setMaxWidth(ssColWidth);
        twoSeven.setMaxWidth(ssColWidth);
        twoEight.setMaxWidth(ssColWidth);
        twoNine.setMaxWidth(ssColWidth);
        ssDoubleLabel.setMaxWidth(ssColWidth);
        oneEleven.setMaxWidth(ssColWidth);
        oneTwelve.setMaxWidth(ssColWidth);
        oneThirteen.setMaxWidth(ssColWidth);
        ssScore.setMaxWidth(ssColWidth);

        ssSubmitBtn.setPrefWidth(500);
        
        // Bold
        ssSingleLabel.setFont(Font.font(null, FontWeight.BOLD, 12));
        ssDoubleLabel.setFont(Font.font(null, FontWeight.BOLD, 12));
        
        // Default Values
        ssHomeCb.setPromptText("Home Team");
        ssAwayCb.setPromptText("Away Team");
        ssAPlayer1Cb.setPromptText("Away Player");
        ssAPlayer2Cb.setPromptText("Away Player");
        ssHPlayer1Cb.setPromptText("Home Player");
        ssHPlayer2Cb.setPromptText("Home Player");
        
        
        // Process events
        ssNewBtn.setOnAction(e -> newSheet());
        ssModifyBtn.setOnAction(e -> modifySheet());
        ssSubmitBtn.setOnAction(e -> submitSheet());
        
        // SCORE SHEET END ////////////////////////////////////////////////////
        
        // Tabs
        adminTab.setContent(adminPane);
        viewerTab.setContent(viewerPane);
        ssTab.setContent(ssPane);
        tabPane.getTabs().addAll(adminTab, viewerTab, ssTab);
        mainPane.getChildren().add(tabPane);
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(mainPane, 700, 500);
        primaryStage.setTitle("Tennis"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        
        // Run Threads
        step();
        startTask();
        
    }    
    
    // Function Classes ///////////////////////////////////////////////////////
    
    // Keep ComboBoxes Up to Date
    public void step(){
        // Init Thread
        Runnable stepTask = () -> {
            while(true){
                
                Platform.runLater(() -> {
                    try {
                        // Load file
                        teamsList2D = openCSV(csvFile);
                        List<List<String>> matchList2D = openCSV(matchFile);
                        
                        // Get Current ComboBox Values
                        String adminTeamSelect = (String) adminTeamCb.getValue();
                        String viewHomeSelect = (String) viewHomeCb.getValue();
                        String viewAwaySelect = (String) viewAwayCb.getValue();
                        String ssHomeSelect = (String) ssHomeCb.getValue();
                        String ssAwaySelect = (String) ssAwayCb.getValue();
                        String ssHPlayer1Select = (String) ssHPlayer1Cb.getValue();
                        String ssHPlayer2Select = (String) ssHPlayer2Cb.getValue();
                        String ssAPlayer1Select = (String) ssAPlayer1Cb.getValue();
                        String ssAPlayer2Select = (String) ssAPlayer2Cb.getValue();
                        
                        // Clear ComboBox Values
                        adminTeamCb.getItems().clear();
                        viewHomeCb.getItems().clear();
                        viewAwayCb.getItems().clear();
                        ssHomeCb.getItems().clear();
                        ssAwayCb.getItems().clear();
                        ssHPlayer1Cb.getItems().clear();
                        ssHPlayer2Cb.getItems().clear();
                        ssAPlayer1Cb.getItems().clear();
                        ssAPlayer2Cb.getItems().clear();
                        
                        // Fill Team Combobox
                        for (List<String> stepTeamList : teamsList2D) {
                            String team = stepTeamList.get(0);
                            
                            adminTeamCb.getItems().add(team);
                            viewHomeCb.getItems().add(team);
                            viewAwayCb.getItems().add(team);
                            ssHomeCb.getItems().add(team);
                            ssAwayCb.getItems().add(team);
                        }
                        
                        // Fill Players ComboBox
                        for (List<String> stepTeamList : teamsList2D) {
                            String team = stepTeamList.get(0);
                            
                            // Home Players
                            if (team.equals(ssHomeSelect)){
                                for (String item : stepTeamList.subList(1, stepTeamList.size())){
                                    ssHPlayer1Cb.getItems().add(item);
                                    ssHPlayer2Cb.getItems().add(item);
                                }
                            }
                            // Away Players
                            if (team.equals(ssAwaySelect)){
                                for (String item : stepTeamList.subList(1, stepTeamList.size())){
                                    ssAPlayer1Cb.getItems().add(item);
                                    ssAPlayer2Cb.getItems().add(item);
                                }
                            }
                        }
                        
                        // Reinstate Selected Values
                        adminTeamCb.setValue(adminTeamSelect);
                        viewHomeCb.setValue(viewHomeSelect);
                        viewAwayCb.setValue(viewAwaySelect);
                        ssHomeCb.setValue(ssHomeSelect);
                        ssAwayCb.setValue(ssAwaySelect);
                        ssHPlayer1Cb.setValue(ssHPlayer1Select);
                        ssHPlayer2Cb.setValue(ssHPlayer2Select);
                        ssAPlayer1Cb.setValue(ssAPlayer1Select);
                        ssAPlayer2Cb.setValue(ssAPlayer2Select);
                        
                    } catch (Exception ex) {
                        errorPopUp(fixFile + ": File not found!");
                    }
                });
                
                try {
                    // Wait for 0.5 seconds
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    errorPopUp("Task Interupted.");
                }
            }
        };
        
        // Create Thread Object and make it run in the background
        Thread stepThread = new Thread(stepTask);
        stepThread.setDaemon(true);
        stepThread.start();
    }   
    
    // Generate Team Stats every 100 Seconds
    public void startTask(){
        // Init Thread
        Runnable startTask;
        startTask = () -> {
            while(true){
                
                Platform.runLater(() -> {
                    try {
                        // Load Files
                        statsList2D = openCSV(statsFile);
                    } catch (Exception ex) {
                        errorPopUp(statsFile + ": File not found!");
                    }
                });
                
                try{
                    // Wait for 100 seconds
                    Thread.sleep(100000);
                }catch (InterruptedException ex) {
                    errorPopUp("Task Interupted");
                }
            }
        };
        
        // Create Thread Object and make it run in the background
        Thread backgroundThread = new Thread(startTask);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
     

    // Load CSV File
    // Return 2D version of CSV File
    private List openCSV(String csvFile) throws Exception {
        File file = new File(csvFile); 
        Scanner input = new Scanner(file); 
        input.useDelimiter(","); 
        List<List<String>> dataList = new ArrayList<List<String>>();
        
        // Grab Data
        while (input.hasNext()){  
            String rowStr = input.nextLine();
            List<String> row = Arrays.asList(rowStr.split(", "));
            
            dataList.add(row);
        }   
        input.close();
        return dataList;
    }
    
    // Create Error Message in a Child Pop Up Window
    private void errorPopUp(String message){
        final Stage dialog = new Stage();  
        dialog.setTitle("Error");
        BorderPane popUp = new BorderPane();
        VBox closeBox = new VBox(5);
        
        Label messageLabel = new Label(message);
        Separator errorSep = new Separator();
        Button closeBtn = new Button("Close");
        
        popUp.setCenter(messageLabel);
        closeBox.getChildren().addAll(errorSep, closeBtn);
        popUp.setBottom(closeBox);
        
        closeBox.setPadding(new Insets(10,10,10,10));
        closeBox.setAlignment(Pos.CENTER_RIGHT);
        closeBtn.setOnAction(e -> dialog.close());

        Scene scene = new Scene(popUp, 400, 150);
        dialog.setScene(scene); 
        dialog.show();
    }
    
    // Add New Team to Team CSV File
    private void adminAddNewTeam(){
        String newTeam = adminTeamText.getText();
        String newTeamSub = "";
        Boolean containsNum = false;
        
        // Validation
        if (!"".equals(newTeam)){
            newTeamSub = newTeam.substring(0, 1).toUpperCase() + newTeam.substring(1);
            containsNum = numInString(newTeamSub);
        }
        
        try {
            teamsList2D = openCSV(csvFile);
        } catch (Exception ex) {
            errorPopUp(csvFile + ": File not found!");
        }
        
        // Validation
        if ("".equals(newTeam)){
            errorPopUp("Player name text field has been left blank.");
            return;
        } else if (containsNum == true) {
            errorPopUp("Numbers are not allowed in the name text field.");
            return;
        } else if (loggedIn != true){ // Checks if Admin Password Has been Authorised
            errorPopUp("Not authorised to use admin features.");
            return;
        }
        
        // Clear Text Field
        adminTeamText.setText("");

        // Write to Permanent File
        try{
            // Append Team Name to Data CSV
            FileWriter writer = new FileWriter(csvFile,true); 
            writer.append(newTeamSub + "\n");
            writer.close();
            
            // Append Team Name to Stats CSV
            writer = new FileWriter(statsFile,true); 
            writer.append(newTeamSub + ", 0, 0, 0\n");
            writer.close();
            statsList2D = openCSV(statsFile);

        } catch (Exception ex){
            errorPopUp(csvFile + ": File not found!");
        }
    }
    
    // Add New Player to Team CSV File
    private void adminAddNewPlayer(){
        String newPlayer = adminPlayerText.getText();
        String newPlayerSub = "";
        String teamSelect = (String) adminTeamCb.getValue();
        Boolean containsNum = false;

        // Capitalise String
        // Does it Contain Numbers
        if (!"".equals(newPlayer)){
            newPlayerSub = newPlayer.substring(0, 1).toUpperCase() + newPlayer.substring(1);
            containsNum = numInString(newPlayerSub);
        }
        
        // Validation
        if ("".equals(newPlayer)){
            errorPopUp("Player name text field has been left blank.");
            return;
        } else if (teamSelect == null){
            errorPopUp("Team name has not been selected.");
            return;
        } else if (containsNum == true) {
            errorPopUp("Numbers are not allowed in the name text field.");
            return;
        } else if (loggedIn != true){ // Checks if Admin Password Has been Authorised
            errorPopUp("Not authorised to use admin features.");
            return;
        }
                
        // Grab all Team Data
        try{
            adminPlayerText.setText("");
            teamsList2D = openCSV(csvFile);

            for (List<String> inputRow : teamsList2D) {
                String team = inputRow.get(0);

                if (team.equals(teamSelect)){
                    List<String> copyRow = new  ArrayList<>(inputRow);
                    copyRow.add(newPlayer);
                    teamsList2D.set(teamsList2D.indexOf(inputRow) , copyRow);
                }
            }   

            // Write New Player to Permanent CSV
            FileWriter writer = new FileWriter(csvFile);

            for (List<String> writeList : teamsList2D) {
                for (String item : writeList) {
                    writer.write(item + ", ");
                }
                writer.write('\n');
            }
            writer.close();

        } catch (Exception ex){
            errorPopUp(csvFile + ": File not found!");
        }
    }
    
    // Used to Check if String has Integers in it
    // Returns true or false
    private boolean numInString(String check){ 
        Boolean containsNum = false;
    
        for(char ch : check.toCharArray()){
            if(Character.isDigit(ch)){
                containsNum = true;
            }
        }
        return containsNum;
    }
    
    // Reset Fixture Permanent File to Accomadate New Scores
    private void generateFixture() {
        // Checks if Admin Password Has been Authorised
        if (loggedIn != true){
            errorPopUp("Not authorised to use admin features.");
            return;
        }
        
        // Open Writer access to File
        try{
            FileWriter writer = new FileWriter(fixFile);
            writer.write("");
            writer.close();
            
            // Reset the File
            writer = new FileWriter(fixFile, true);
            writer.write("Filton, UWE, np\n");
            writer.write("Filton, FCC, np\n");
            writer.write("Filton, Page, np\n");
            writer.write("UWE, Filton, np\n");
            writer.write("UWE, FCC, np\n");
            writer.write("UWE, Page, np\n");
            writer.write("FCC, Filton, np\n");
            writer.write("FCC, UWE, np\n");
            writer.write("FCC, Page, np\n");
            writer.write("Page, Filton, np\n");
            writer.write("Page, UWE, np\n");
            writer.write("Page, FCC, np\n");
            writer.close();
        } catch (Exception ex){
            errorPopUp(fixFile + ": File not found!");
        }
    }
    
    // Generate Stats of Matches Played, Matched Won and Sets Won
    private void generateStats(){
        // Checks if Admin Password Has been Authorised
        if (loggedIn != true){
            errorPopUp("Not authorised to use admin features.");
            return;
        }
        
        // Load Data to Program Local Storage
        try {
            statsList2D = openCSV(statsFile);
        } catch (Exception ex) {
            errorPopUp(statsFile + ": File not found!");
        }
        
    }
    
    // Used to Lock Admin Features
    private void logIn(){
        File passwordFile = new File(passFile); 
        String passStr = (String) adminPassText.getText();
        
        // Get Password From File
        try{
            Scanner input = new Scanner(passwordFile);
            
            while (input.hasNext()){  
                passwordAuth = input.nextLine();
            }
            input.close();
        } catch (Exception ex){
            errorPopUp(passFile + ": File not found!");
        }
        
        // Validation
        if ("".equals(passStr)){
            errorPopUp("Password text field has been left blank.");
            return;
        
        // Check if Typed Password Matches Password in File
        } else if (!passwordAuth.equals(passStr)){
            errorPopUp("Incorrect password.");
            return;
            
        }
        adminPassText.setText("");
        adminPassText.setEditable(false);
        adminPassCheckLabel.setText("Admin Page Authorised");
        loggedIn = true;
        
        // Delete Password from File from the Local Storage to Prevent Leaks
        passwordAuth = "";
        
    }
    
    // Print Out Match Reasult in a Child Window
    private void viewResults() {
        final Stage dialog = new Stage();  
        dialog.setTitle("Fixtures");
        GridPane fixPane = new GridPane();
        
        Label zeroZero = new Label("---");
        Label zeroOne = new Label("Filton");
        Label zeroTwo = new Label("UWE");
        Label zeroThree = new Label("KCC");
        Label zeroFour = new Label("Page");
        
        Label oneZero = new Label("Filton");
        Label oneOne = new Label("---");
        Label oneTwo = new Label();
        Label oneThree = new Label();
        Label oneFour = new Label();
        
        Label twoZero = new Label("UWE");
        Label twoOne = new Label();
        Label twoTwo = new Label("---");
        Label twoThree = new Label();
        Label twoFour = new Label();
        
        Label threeZero = new Label("KCC");
        Label threeOne = new Label();
        Label threeTwo = new Label();
        Label threeThree = new Label("---");
        Label threeFour = new Label();
        
        Label fourZero = new Label("Page");
        Label fourOne = new Label();
        Label fourTwo = new Label();
        Label fourThree = new Label();
        Label fourFour = new Label("---");
        
        Button closeBtn = new Button("Close");
        
        fixPane.add(zeroZero,                0,  0);
        fixPane.add(zeroOne,                 1,  0);
        fixPane.add(zeroTwo,                 2,  0);
        fixPane.add(zeroThree,               3,  0);
        fixPane.add(zeroFour,                4,  0);

        fixPane.add(oneZero,                 0,  1);
        fixPane.add(oneOne,                  1,  1);
        fixPane.add(oneTwo,                  2,  1);
        fixPane.add(oneThree,                3,  1);
        fixPane.add(oneFour,                 4,  1);
        
        fixPane.add(twoZero,                 0,  2);
        fixPane.add(twoOne,                  1,  2);
        fixPane.add(twoTwo,                  2,  2);
        fixPane.add(twoThree,                3,  2);
        fixPane.add(twoFour,                 4,  2);
        
        fixPane.add(threeZero,               0,  3);
        fixPane.add(threeOne,                1,  3);
        fixPane.add(threeTwo,                2,  3);
        fixPane.add(threeThree,              3,  3);
        fixPane.add(threeFour,               4,  3);
        
        fixPane.add(fourZero,                0,  4);
        fixPane.add(fourOne,                 1,  4);
        fixPane.add(fourTwo,                 2,  4);
        fixPane.add(fourThree,               3,  4);
        fixPane.add(fourFour,                4,  4);
        
        fixPane.add(closeBtn,                4,  5);

        // Get File
        try{
            List<List<String>> fixList2D = openCSV(fixFile);
            
            for (List<String> fixture : fixList2D){
                String homeFix = fixture.get(0);
                String awayFix = fixture.get(1);
                String result = fixture.get(2);
                
                // Check File against Teams
                // Fill Fixtures and Results
                if (homeFix.equals("Filton") && awayFix.equals("UWE")){
                    oneTwo.setText(result);
                } else if (homeFix.equals("Filton") && awayFix.equals("FCC")){
                    oneThree.setText(result);
                } else if (homeFix.equals("Filton") && awayFix.equals("Page")){
                    oneFour.setText(result);
                } else if (homeFix.equals("UWE") && awayFix.equals("Filton")){
                    twoOne.setText(result);
                } else if (homeFix.equals("UWE") && awayFix.equals("Fcc")){
                    twoThree.setText(result);
                } else if (homeFix.equals("UWE") && awayFix.equals("Page")){
                    twoFour.setText(result);
                } else if (homeFix.equals("FCC") && awayFix.equals("Filton")){
                    threeOne.setText(result);
                } else if (homeFix.equals("FCC") && awayFix.equals("UWE")){
                    threeTwo.setText(result);
                } else if (homeFix.equals("FCC") && awayFix.equals("Page")){
                    threeFour.setText(result);
                } else if (homeFix.equals("Page") && awayFix.equals("Filton")){
                    fourOne.setText(result);
                } else if (homeFix.equals("Page") && awayFix.equals("UWE")){
                    fourTwo.setText(result);
                } else if (homeFix.equals("Page") && awayFix.equals("FCC")){
                    fourThree.setText(result);
                }
            }
        } catch(Exception ex){
            errorPopUp(fixFile + ": File not found!");
        }
        

        fixPane.setPadding(new Insets(10,10,10,10));
        fixPane.setHgap(10);
        fixPane.setVgap(10);
        
        closeBtn.setAlignment(Pos.CENTER_RIGHT);
        closeBtn.setOnAction(e -> dialog.close());

        Scene scene = new Scene(fixPane, 250, 200);
        dialog.setScene(scene); // Place the scene in the stage
        dialog.show();
    }
    
    // Print Stats
    private void showAllStats() {
        String teamStats = "Team Stats \n \n";
        viewText.clear();
        
        for (List<String> showStat : statsList2D){
            String team = showStat.get(0) + ": ";
            String played = " matchesPlayed = " + showStat.get(1);
            String mWon = ", matchesWon = " + showStat.get(2);
            String sWon = ", setsWon = " + showStat.get(3);
            
            teamStats = teamStats + team + played + mWon + sWon + " \n";
        }
        viewText.setText(teamStats);
    }
    
    // Print Stats in Order by Sets Won
    private void showAllRankings() {
        String teamRank = "Team Rankings \n \n";
        viewText.clear();
        
        // Sort Stats List
        List<List<String>> sortList = statsList2D;
        sortList = sortListCom(sortList);
        
        // Build String
        for (List<String> showStat : sortList){
            String team = showStat.get(0) + ": ";
            String played = " matchesPlayed = " + showStat.get(1);
            String mWon = ", matchesWon = " + showStat.get(2);
            String sWon = ", setsWon = " + showStat.get(3);
            
            teamRank = teamRank + team + played + mWon + sWon + " \n";
        }
        viewText.setText(teamRank);
    }
    
    // Sort List With Selection Sort
    // Return Sorted List
    private List<List<String>> sortListCom(List<List<String>> arr2D){
        
        // Run Through List
        for(int i = 0; i < arr2D.size()-1; i++){
            int min = i;
            List<String> iList = arr2D.get(i);
            String iStr = iList.get(3); 
            int iWin = Integer.parseInt(iStr);
            
            // i + 1
            for(int j = i+1; j < arr2D.size(); j++){
                List<String> jList = arr2D.get(j);
                String jStr = jList.get(3); 
                int jWin = Integer.parseInt(jStr);
                
                // Switch if There is a Larger Item in List
                if (iWin < jWin){
                    min = j;      
                } 
            }
            // Switch Places
            List<String> temp = arr2D.get(min);
            arr2D.set(min, arr2D.get(i));
            arr2D.set(i, temp);  
            
        }
        return arr2D;
    }
     
    // Print Match Scores
    private void viewMatchScores() {
        
        try{
            String teamSets = "Team Sets \n \n No Match Results.";
            String result = "";
            viewText.clear();
            
            String teamH = (String) viewHomeCb.getValue();
            String teamA = (String) viewAwayCb.getValue();

            List<List<String>> matchList2D = openCSV(matchFile);
            List<List<String>> fixList2D = openCSV(fixFile);
            
            for (List<String> fixture : fixList2D){
                if (teamH.equals(fixture.get(0)) && teamA.equals(fixture.get(1))){
                    result = fixture.get(2);
                }
            }
            
            // Build String
            for(int i = 0; i < matchList2D.size(); i+=6){
                List<String> fixList1 = matchList2D.get(i);
                List<String> fixList2 = matchList2D.get(i+1);
                List<String> fixList3 = matchList2D.get(i+2);
                List<String> fixList4 = matchList2D.get(i+3);
                List<String> fixList5 = matchList2D.get(i+4);
                List<String> fixList6 = matchList2D.get(i+5);
                
                if (teamH.equals(fixList1.get(0)) && teamA.equals(fixList1.get(1))){
                    teamSets = "Team Sets \n \n";
                    teamSets = teamSets + fixList1.get(0) + " " + fixList1.get(1) + " \n";
                    teamSets = teamSets + "Single: " + fixList2.get(0) + " vs " + fixList2.get(1) + " = " + fixList2.get(2) + " | " + fixList2.get(3) + " | " + fixList2.get(4) + " \n";
                    teamSets = teamSets + "Single:" + fixList3.get(0) + " vs " + fixList3.get(1) + " = " + fixList3.get(2) + " | " + fixList3.get(3) + " | " + fixList3.get(4) + " \n";
                    teamSets = teamSets + "Single: " + fixList4.get(0) + " vs " + fixList4.get(1) + " = " + fixList4.get(2) + " | " + fixList4.get(3) + " | " + fixList4.get(4) + " \n";
                    teamSets = teamSets + "Single:" + fixList5.get(0) + " vs " + fixList5.get(1) + " = " + fixList5.get(2) + " | " + fixList5.get(3) + " | " + fixList5.get(4) + " \n";
                    teamSets = teamSets + "Double: " + fixList6.get(0) + " vs " + fixList6.get(1) + " = " + fixList6.get(2) + " | " + fixList6.get(3) + " | " + fixList6.get(4) + " \n";
                    teamSets = teamSets + "Final Score: " + result + " \n";
                } 
                viewText.setText(teamSets);
            }
        } catch(Exception ex){
            errorPopUp(matchFile + ": File not found!");
        }
    }
      
    // Clear Sheet
    private void newSheet() {
        // Clear Score Sheet Page
        oneFour.setText("");
        oneFive.setText("");
        oneSix.setText("");
        twoFour.setText("");
        twoFive.setText("");
        twoSix.setText("");

        oneSeven.setText("");
        oneEight.setText("");
        oneNine.setText("");
        twoSeven.setText("");
        twoEight.setText("");
        twoNine.setText("");

        oneEleven.setText("");
        oneTwelve.setText("");
        oneThirteen.setText("");
        ssScore.setText("");
    }
    
    // Grab Team Values
    private void modifySheet() {
        String teamH = (String) ssHomeCb.getValue();
        String teamA = (String) ssAwayCb.getValue();
        String teamH1 = (String) ssHPlayer1Cb.getValue();
        String teamH2 = (String) ssHPlayer2Cb.getValue();
        String teamA1 = (String) ssAPlayer1Cb.getValue();
        String teamA2 = (String) ssAPlayer2Cb.getValue();
        
        // Validation
        if (teamH == null){
            errorPopUp("Home team has not been selected.");
            return;
        } else if (teamA == null){
            errorPopUp("Away team has not been selected.");
            return;
        } else if (teamH1 == null){
            errorPopUp("Home team player 1 has not been selected.");
            return;
        } else if (teamH2 == null){
            errorPopUp("Home team player 2 has not been selected.");
            return;
        } else if (teamA1 == null){
            errorPopUp("Away team player 1 has not been selected.");
            return;
        } else if (teamA2 == null){
            errorPopUp("Away team player 2 has not been selected.");
            return;
        }
    }

    // Grab Text Field Values
    private void submitSheet() {
        String h1a11 = (String) oneFour.getText();
        String h1a12 = (String) oneFive.getText();
        String h1a13 = (String) oneSix.getText();
        String h1a21 = (String) twoFour.getText();
        String h1a22 = (String) twoFive.getText();
        String h1a23 = (String) twoSix.getText();

        String h2a11 = (String) oneSeven.getText();
        String h2a12 = (String) oneEight.getText();
        String h2a13 = (String) oneNine.getText();
        String h2a21 = (String) twoSeven.getText();
        String h2a22 = (String) twoEight.getText();
        String h2a23 = (String) twoNine.getText();

        String d1 = (String) oneEleven.getText();
        String d2 = (String) oneTwelve.getText();
        String d3 = (String) oneThirteen.getText();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
