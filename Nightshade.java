
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Polymorphism
 */
/**
 * Time On Video: (16:30)
 *
 * @author 40044
 */
public class Nightshade extends Game {

    private int prevBoatRoom = 12; //Used to hold the previous boat room
    private int currRoomNum = 15; //The current room number
    private Room currRoom; //The current room
    private int numItemsInInventory = 0; //The number of items in your inventory
    private int numGlueIngredients = 0; //The number of ingredients used for glue
    private boolean trollFainted = false; //If the troll fainted or not

    private String verb;
    private String noun;
    private String completeNoun;
    
    protected String[] nounNames;
    protected String[] nounIndex;

    public Nightshade() {
        System.out.println("Nightshade - A Text Adventure Game");
        //System.out.println(player); //Used for testing purposes
        init();
    }
    
    private void setBoatRoom(int pbr){
    //Edit this later on
        rooms[prevBoatRoom] = new Room("In a small boat", "",29,0,0,0,0,pbr,0);
    }

    public void setRoomData(int room){
    
        switch(room){
            case 8:{}
            case 9:{}
            case 10:{codedItems[ItemsEnums.Items.TREES.ordinal()].setLocation(-currRoomNum);}
            break;
            case 11: {
                codedItems[ItemsEnums.Items.TREES.ordinal()].setLocation(-currRoomNum);
                codedItems[ItemsEnums.Items.SWAMP.ordinal()].setLocation(-currRoomNum);
                break;}
            case 15:{
                codedItems[ItemsEnums.Items.SWAMP.ordinal()].setLocation(-currRoomNum);
                break;
            }
            case 29: {setBoatRoom(prevBoatRoom); 
            break;}
                
        }
    }
    
    private int getDirection(String dir) {
        dir = dir.toUpperCase();

        switch (dir) {
            case "N":
                return 0;
            case "S":
                return 1;
            case "E":
                return 2;
            case "W":
                return 3;
            case "U":
                return 4;
            case "D":
                return 5;

        }

        return -1;
    }

    private void processInput() {
        Scanner in = new Scanner(System.in);

        System.out.println("What would you like to do now?");
        System.out.println("Get help(?): ");
        System.out.println("Exit Game(!): ");
        System.out.println("Enter a direction(N S E W U D)");

        String command;
        command = in.nextLine();
        //command = "take book";

        if (command.length() == 1) {
            if (command.equals("?")) {
                displayHelp();
            } else if (command.equals("!")) {
                System.exit(0);
            } else {
                movePlayer(command.toUpperCase());
            }

        } else {//Seperate into command(verb) and item(noun)
            in = new Scanner(command);
            verb = in.next().trim().toUpperCase();
            if (verb.length() >= 3) {
                verb = verb.substring(0, 3);
            }
            
            noun = in.next().trim().toUpperCase();
            completeNoun = noun; //We still need the complete noun for later
            if (noun.length() >= 3) {
                noun = noun.substring(0, 3);
            }

            processVerb();
            System.out.println("Verb: " + verb + " Noun: " + noun); //Used for testing
        }

    }

    private void processVerb() {
        System.out.println("Verb: " + verb);

        switch (verb) {
            case "TAK": //call a method;
                System.out.println("Taking...");
                break;
        }

    }

    private void movePlayer(String dir) {
        System.out.println("Moving");
        int direction = getDirection(dir);

        if (direction != -1) {
            if (currRoom.getExit(direction) > 0) {
                currRoomNum = currRoom.getExit(direction);

            } else {//invalid direction
                System.out.println("\nDenny can't move that way.\n");
            }
        } else {//load command ...not a direction
            System.out.println("\n Denny doesn't understand that command");
        }
        updateGame();
    }

    private void displayHelp() {
        System.out.println("Help coming soon");
    }

    private void updateGame() {
//        System.out.println("");
//        System.out.println("Current Location :" + currRoom);
//        System.out.println("-------------------------------");
        
        currRoom = rooms[currRoomNum];
        System.out.println(currRoom);
        showVisibleItems(5);
        showInventory();
        
        processInput();
        

//        Scanner in = new Scanner(System.in);
//        String input = in.nextLine();
//        switch (input) {
//            case "?":
//                System.out.println("Help is coming soon");
//                break;
//            case "!":
//                System.out.println("See you soon!");
//                System.exit(0);
//            default:
//                System.out.println("I don't understand that");
//                break;
//        }
//
//        if (input.equals("!")) {
//            System.exit(0);
//        }
    }

    private void loadItems() {
        //System.out.println(System.getProperty("user.dir"));
        File file = new File("src/ItemsEnum.txt");
        //System.out.println(file); //For testing purposes

        //try to read the file
        try {
            Scanner in = new Scanner(file); //From Line 28 to
            //System.out.println("File Found");

            //count the num of items in the file
            int numLines = 0;
            while (in.hasNextLine()) {
                in.nextLine();
                numLines++;
                //System.out.println(numLines); //Line 36, is what we do if the file exists
            }
            allItems = new String[numLines];
            in.close();

            in = new Scanner(file); //reopen the gile
            numLines = 0; //index value of the array to store the items
            while (in.hasNextLine()) {
                allItems[numLines] = in.nextLine();
                numLines++;
            }
            in.close();

        } catch (FileNotFoundException e) {//You can leave this blank
            //happens if the files isn't found
            System.out.println("Items file not found");

        }

        //load codedItems
        file = new File("src/codedItems.txt");
        try {
            Scanner in = new Scanner(file);

            int numLines = 0;
            while (in.hasNextLine()) {
                in.nextLine();
                numLines++;
                //System.out.println(numLines); 
            }
            codedItems = new Item[numLines];
            in.close();

            in = new Scanner(file); //reopen the gile
            //numLines = 0; //index value of the array to store the items
            for (int i = 0; i < numLines; i++) {
                String name = in.next();
                int loc = in.nextInt();

                codedItems[i] = new Item(name, loc);
               // System.out.println(codedItems[i]);//testing
            }
            in.close();

        } catch (FileNotFoundException e) {//You can leave this blank
            //happens if the files isn't found
            System.out.println("Items file not found");

        }
        
        //Load noun items
        file = new File("src/nounNames.txt");
         try {
            Scanner in = new Scanner(file); //From Line 28 to
            //System.out.println("File Found");

            //count the num of items in the file
            int numLines = 0;
            while (in.hasNextLine()) {
                in.nextLine();
                numLines++;
                //System.out.println(numLines); //Line 36, is what we do if the file exists
            }
            nounNames = new String[numLines];
            nounIndex = new String[numLines];
            in.close();

            in = new Scanner(file); //reopen the gile
            //index value of the array to store the items
            //while (in.hasNextLine()) {
            for (int i = 0; i < numLines; i++) {
                nounNames[i] = in.next();
                nounIndex[i] = in.next();
                //in.nextLine();
                //in.nextLine();
               // nounIndex[numLines] = in.nextInt();
                //numLines++;
                System.out.println("names: " + nounNames[i] + " numbers/room: " + nounIndex[i]);
            }
            in.close();

        } catch (FileNotFoundException e) {//You can leave this blank
            //happens if the files isn't found
            System.out.println("Items file not found");

        }

    }

    private void addRooms() {

        System.out.println("Adding Rooms");
        prevBoatRoom = 12;

        Room entry = new Room("Entry", "A large entry", 10, 13, 0, 11, 0, 0, 0); //Add a new room to the game to test it
        //System.out.println(entry);
        File file = new File("src/RoomData.txt");

        //may remove this later as we know we need 30 elements 0 to 30..... ignoring the
        //room nums we aren't using
        try {
            Scanner in = new Scanner(file); //Scans the file

            int numLines = 0;
            while (in.hasNextLine()) {
                in.nextLine();
                numLines++;
            }

            in.close();
            rooms = new Room[31]; //indexes =-30
            numLines /= 2;

            in = new Scanner(file);
            for (int i = 0; i < numLines; i++) {
                int roomNum = in.nextInt();
                String name = in.next();
                String desc = in.nextLine();

                int n = in.nextInt();
                int s = in.nextInt();
                int e = in.nextInt();
                int w = in.nextInt();
                int u = in.nextInt();
                int d = in.nextInt();

                rooms[roomNum] = new Room(name, desc, roomNum, n, s, e, w, u, d);
                System.out.println(rooms[i]);
            }

        } catch (FileNotFoundException e) {

        }

        //entry.getExits();
    }

    private void listCodedItems() {
        //for testing
        for (int i = 0; i < codedItems.length; i++) {
            //System.out.println(codedItems[i]);
        }
    }

    protected Player setUpPlayer() {
        System.out.println("\nNightShade - setUpPlayer");
        player = super.setUpPlayer();
        //do other nightshade specific stuff
        //Ask for avatar name
        //other nightshade game player setup details that you would NOT need in Spookymansion
        System.out.println(player);
        return player;
    }

    private void init() {
        System.out.println("Setting up Nightshade");

        player = setUpPlayer();
        addRooms();
        loadItems();
        listCodedItems();

        //Will be altered later
        //codedItems = new Item[1];
        //codedItems[0] = new Item("KEY", 1); //Create an item for testing

        currRoom = rooms[currRoomNum]; //Displays the current room

        //test the getter
        //System.out.println("Coded Items" + getItems(1));
        updateGame();
    }

    private void listRooms() {
        for (int i = 0; i < rooms.length; i++) {
            System.out.println(rooms[i]);
        }
    }
}
