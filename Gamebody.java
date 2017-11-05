import javax.swing.JLabel;
import java.awt.Color; 
import java.io.*;
import java.util.*;
import java.util.ArrayList;


public class Gamebody {
 public static boolean gameComputer = false;
 public static int gameTurnComp = 2;
 public static ArrayList<Integer> legalMoves = new ArrayList<Integer>();
 static boolean gameStarted = false;
 static String myName = "I am body";
 static boolean canJump = false; //keep track of immediate previous move to check double jump
 static boolean canMove = false;
 static long startTime = System.currentTimeMillis();
 static long endTime = 0;
 //constructor for gamebody
 public Gamebody(){
  myName = "Initiated-body";
  
  Board gameBoard = new Board();
  gameBoard.setVisible(true);
 }
 
 public static void runIt(Pieces lblBoard[], int countThis, int here, int there, JLabel infoAddr[]){
   System.out.println("runIT " + countThis + " " + here + " -> " + there + " turn " + Board.gameTurn);
   //timing begins
   Gamebody.startTime = System.currentTimeMillis();
   Gamebody.endTime = System.currentTimeMillis();

   //countThis = there they are the same index
      
   //Gamecontrol.showTurn(infoAddr); //shows turn //moved to inside GREEN since only useful after effective move
   
   if (lblBoard[countThis].getBackground() == Color.GREEN) { //this should only be triggered for 2nd click
     //Green button is legit moves 
  
     Gamecontrol.hidePiece(lblBoard); //hidePiece coming first, 
                                             //this will leave showPieces "GREEN"
                                             //previosly movePiece-hidePiece order removed
                                             //GREEN pieces for double jump even when available
     Gamecontrol.movePiece(lblBoard, there, infoAddr);
     //movePiece will change turn

     Gamecontrol.reachEnd(lblBoard); //check for any reg pieces reaching the end
     
     Gamecontrol.showTurn(infoAddr);
     //Each and every move will finish by clicking on a green button
     //therefore, as changing turn, check whether computer's turn kicked in,
     //if it's computer's turn, then make computer's move and let the player go again

     
     //Gamebody.legalMoves.clear();
     //this is implemented inside Gamecontrol.turnChange, after movePiece was successful
     Gamebody.legalMoves.clear();
     
     if (Board.gameTurn == Gamebody.gameTurnComp) { 
       Gamebody.runItAI(lblBoard, infoAddr); 
     }
     Gamebody.endGame(lblBoard, infoAddr);
     
   } else { //this is the first click
     //a bad click loses greens
     Gamecontrol.hidePiece(lblBoard); 
     
     if (Gamecontrol.legalMove(lblBoard, countThis) == false) { //this will trigger for every other click (non GREEN)
       //return was false, for 'countThis' there was no mustJump, no canMove
       //see whether other index with same color was ok to move
       
       //this part of code also highlights smallest index of a correct move
       int count = 0;
       for (int i = 11; i <89; i++) {
         if (Gamecontrol.legalMove(lblBoard, i) == true) { count ++; break; }
       }
       if (count==0) {
       System.out.println("endgame here, user has no move");
       endGameForce(lblBoard);
       }
     }
     //if (Board.gameTurn == Gamebody.gameTurnComp) { Gamebody.runItAI(lblBoard, countThis, here, there, infoAddr); }
   }
   
   //Gamecontrol.legalMove(lblBoard, countThis); //this will trigger for every click
   
 }
 
 public static void runItAI(Pieces lblBoard[], JLabel infoAddr[]){
   //this is computer's turn now : called at end of each play by user
   //initial method call = computer's full turn
   //fix the loop, move one piece first, then double jump when applicable
   
   //check through index 88-11 and allow moving of first move
   //this is the most basic way computer AI will work
   
   //further reasonable move will be implemented
   //@ which piece to move, when making move decision;
   System.out.println("runIT AI goodMoves " + Gamebody.legalMoves);
   
   if (Gamebody.legalMoves.size() > 0) {
     System.out.println("runIt AI second click " + Gamebody.legalMoves);
     //if there's any green box -> previously legal move ran
     //therefore just move one of them from legalMoves[]
     
     //choose one of the available jump, from array
         //if another jump possible, do it from array
          //no jump possible, do move
          //move that piece, find out if 2nd move possible, else 
     
     Gamecontrol.hidePiece(lblBoard); 
     
     //@reasonable move here
     Random random = new Random(1234567);
     int min = 0;
     int max = Gamebody.legalMoves.size();
     int chosen = (int)(Math.random() * max) + min;     
     //chosen one gets chosen randomly
     
     
     int there = Gamebody.legalMoves.get(chosen); //this is the chosen one (index)
     
     //Gamebody.legalMoves.clear();
     //went to gamecontrol movepiece
     
     Gamecontrol.movePiece(lblBoard, there, infoAddr);
     Gamecontrol.reachEnd(lblBoard); //check for any reg pieces reaching the end
     Gamecontrol.showTurn(infoAddr);
     endGame(lblBoard,infoAddr);
   } else {
     //nothing to move here
     //find out if Jump or Move will be possible
     
     System.out.println("runit AI first click " + Gamebody.legalMoves.size());
     //otherwise run legalMove, so we can find some legalMoves
     if (Gamecontrol.legalMoveAI(lblBoard,infoAddr) == false) {
//legalMoveAI always returns true when AI can move
       System.out.println("endgame here, computer has no move");
       //if false no move possible, gameover
       //endGameForce(lblBoard);
     }
//if anything useful is found by legalMove, runItAI again with renewed Gamebody.legalMoves list
     if (Gamebody.legalMoves.size() > 0 ) {Gamebody.runItAI(lblBoard,infoAddr); }
   }

 }

 public static void countPiece(Pieces boardMe[]){
  //see if game over
   //for (int i = 11; 
 }
 
 //save and load
 public static void saveBoard(Pieces boardMe[]){
   //Save board by checking through array of buttons, lblBoard[11]-[88]
   //get background colour and store into a new array that will be stored
   //1: red piece 2: red king 3: yellow piece 4: yellow king
   int[] saveMe = new int[78]; //button11-button88 has 78 entries, 64 useful
   int offset = 11;
   int countMe = 0;

   for(int i=0; i<=77; i++){
     //iterate through 78 entries
     //offset for lblBoard = +11
     countMe = i + offset;
     saveMe[i] = 0; //0 do nothing
     if (boardMe[i+11].getBackground() == Color.RED) {
       saveMe[i] = 1;
       if (boardMe[i+11].getText() == "K") {
         saveMe[i] = 2;
       }
       //End of red conditions
     } else if (boardMe[i+11].getBackground() == Color.YELLOW) {
       saveMe[i] = 3;
       if (boardMe[i+11].getText() == "K") {
        saveMe[i] = 4; 
       } 
   }//End of yellow conditions
   } //end of for loop
   
   try {
   File file = new File("save.txt");
   BufferedWriter output = new BufferedWriter(new FileWriter(file));
   for (int i = 0; i <saveMe.length; i++) {
   output.write(Integer.toString(saveMe[i]) + " ");  
   }
   //output.write(text);
   output.close();
   } catch ( IOException e ) {
   e.printStackTrace();
   }
   
 } //end of saveBoard
 
 public static int[] loadBoard(){
   //if there is save.txt, load;
   //if there is no such file, do nothing;
   String fileName = "save.txt";
   int loadMe2[] = new int[78];
     
   try{

    //Create object of FileReader
    FileReader inputFile = new FileReader(fileName);

    //Instantiate the BufferedReader Class
    BufferedReader bufferReader = new BufferedReader(inputFile);

    //Variable to hold the one line data
    String line;
    //char aChar;
    int loadMe[] = new int[78];
    
    // Read file line by line and print on the console
    while ((line = bufferReader.readLine()) != null)   {
    //Embedded try
    try{
    for (int i = 0; i <77; i++) {
     //aChar = line.charAt(i);
     loadMe[i] = Integer.parseInt(line.split(" ")[i]); 
     loadMe2[i] = loadMe[i];
     //System.out.println(loadMe[i] + " : loadMe[ " + i); //this comment was used for testing
    }
    
    } catch (NumberFormatException e) {
      //throw exception
      System.out.println("Error while parsing int:" + e.getMessage());
    }
    //end of Embedded try
    }
    
    //Close the buffer reader
    bufferReader.close();
    }catch(Exception e){
            System.out.println("Error while reading file line by line:" 
            + e.getMessage());                      
    }
    //line is loaded and parsed
    
    //set colours according to the code 1 red 2 rK 3 yel 4 yelK
    //System.out.print(loadMe2); //reference to loadMe2
    return loadMe2;
    
 } //end of loadBoard
 
 public static void clearBoard(Pieces thisBoard[]){
  //reset button11 to button88 if background is yellow or red turn it to black
   for (int i = 11; i < 88; i++) {
     if ((thisBoard[i].getBackground() == Color.RED) || (thisBoard[i].getBackground() == Color.YELLOW)) {
      thisBoard[i].setBackground(Color.BLACK);
      thisBoard[i].setText("");
     }
   }//end of for
   Board.setyelMax(12);
   Board.setredMax(12);
   
   Board.infoAddr3[6].setText("CLEAR");
 }
 
 public static void standardBoard(Pieces thisBoard[]){
   //reset button11 to button88 if background is yellow or red turn it to black
   //then setup the standard position
   //yellow top, red bottom
   for (int i = 11; i < 88; i++) {
     if ((thisBoard[i].getBackground() == Color.RED) || (thisBoard[i].getBackground() == Color.YELLOW)) {
       thisBoard[i].setBackground(Color.BLACK);
       thisBoard[i].setText("");
     }
   } //end of for to clear
   int countThis = 0;
   for(int x=0; x<=9; x++){ //ROW
     for(int y=0; y<=9; y++){ //COLlblBoard[(x*10)+y]
       countThis = x*10 + y;
       //black ones will not get warning
       if((x%2)==(y%2)){ 
         //lblBoard[(x*10)+y].setBackground(Color.WHITE); 
         //System.out.println("on a white block");
       } else {
         //System.out.println("on a black block");
         if (thisBoard[countThis].getBackground() == Color.BLACK){
           
           if (countThis <= 39) {
             thisBoard[countThis].setBackground(Color.YELLOW);
           }//block is top 3 lines
           
           else if ( (countThis > 60)&&(countThis <88)) {
             thisBoard[countThis].setBackground(Color.RED);
           }//block is bottom 3 lines
           
         }//block is black
       }
       
     }//end of inside for
   }//end of outside for
   Board.setredMax(0);
   Board.setyelMax(0);
   
 }
 
 public void GamebodyChangeName(){
  myName = "Name Changed-body";
 }
 
 
  public static boolean startGame(int rM, int yM){
      
       if ((rM < 12) && (yM < 12)){

         gameStarted = true; 
       //Board.infoAddr2[0].setText("Game Starting..");
       //System.out.println("Game starting : " + Board.infoAddr2[0]);
           //System.out.println("startgame function");
           return true;
       }
           else {
       //System.out.println("Game is not starting");
        return false;
       }
       
 }
  
  public static void endGameForce(Pieces thisBoard[]) {
    Board.infoAddr2[0].setText("GAME OVER");
    endGame(thisBoard, Board.infoAddr2);
  }
  
  public static boolean endGame(Pieces thisBoard[], JLabel thisAddr[]){
    int count1 = 0;
    int count0 = 0;
    
    if (thisAddr[0].getText() =="GAME OVER") {
      //System.out.println("endGame setFALSE");
      for (int i=0; i<100; i++) {
        thisBoard[i].setEnabled(false);
      }
      count1 ++;
      count0 ++;
      //prevent infinite loop from second self-call
      //Board.infoAddr2[0].setText("GameOOver"); //Reference to a static, which refers to private works statically
      
      //put some fun stuff here, reMatch, etc...
      Board.infoAddr3[4].setEnabled(false); //can not save anymore once game finished
      Board.infoAddr3[6].setText("RESET");
      Board.infoAddr3[6].setEnabled(true); //can now clear to rematch
    }
    
    for (int i = 11; i <89; i++) {
      if (thisBoard[i].getBackground() == Color.YELLOW) { count1 ++; }
      if (thisBoard[i].getBackground() == Color.RED) { count0 ++; }
    }
    
    if ( (count1 == 0) || (count0 == 0) ) {
      thisAddr[0].setText("GAME OVER"); 
      endGame(thisBoard, thisAddr);
      return true;
    }
    
    return false;
  }
  
  
 public String myName(){
  return myName;
 }
 
 }

   
