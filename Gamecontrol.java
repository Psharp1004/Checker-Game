import javax.swing.JLabel;
import java.awt.Color; 
import java.io.*;
import java.util.*;

//Control of moves, determination of legal moves
//One instance of game control object is allowed
//This object will be called over and over each time piece move is determined

//Gamecontrol allows moving around of pieces, 
//once game starts Board methods must stop from being called
//and gamecontrol method will determine whether move is legit by returning True/False/MakeApieceDisappear

public class Gamecontrol {

 static String myName = "I am control";
 
 public Gamecontrol(){
  myName = "Initiated-control";
 }
 
 public static boolean legalMoveAI(Pieces thisBoard[], JLabel infoAddr[]){
   //legalMoveAI is different from legalMove in that 
   //instead of just showing found piece,
   //it calls runItAI again automatically, which then a random(or reasonable) move will be chosen
   Pieces checkArray[] = thisBoard;
   Gamebody.legalMoves.clear(); //initialize
   int count = 0;
   
   System.out.println("legalMoveAI, clearlist, mustJump");
   Gamecontrol.mustJump(checkArray);
   if (findPiece(thisBoard,0) == true) { 
//findPiece will check through any cells painted green by mustJump(canJump)
     //findPiece returning with true means legalMoves has item inside
//Gamebody.legalMoves.size() > 0 this code is implied within findPiece
    Gamebody.runItAI(checkArray, infoAddr) ;
    return true;
   }
   //findPiece is false
   
   System.out.println("legalMoveAI, jump findPiece false"); 
   //this would be 'mustMove'
   for (int i = 11; i < 89; i ++) {     count = i;     Gamecontrol.canMove(checkArray,count);   }
   
   //canMove marked anything, then,,
   if (findPiece(thisBoard,0) == true) {
    Gamebody.runItAI(checkArray, infoAddr);
    return true;
   }
   System.out.println("legalMoveAI, move findPiece false"); 
   //false means game over, no more computer!
   //display player wins
   return false;
 }
 
 public static boolean legalMove(Pieces thisBoard[], int index){
   //System.out.println("legalMove " + index);
   Pieces checkArray[] = thisBoard;
   Gamebody.legalMoves.clear(); //initialize
   Board.here = index; //first click determines here, if true? 
                       //there will be captured from the next click
   
   //Gamecontrol.canJump(checkArray, index);
   //canJump will highlight possible moves in GREEN,
   
   Gamecontrol.mustJump(checkArray);
   //mustJump will highlight possible jumps, and won't allow move
     //System.out.println("findPiece for mustJump being called");
   if (findPiece(thisBoard, index) == true) {
     Gamebody.canJump = true;
     //System.out.println("this is after findPiece for canJump " + Gamebody.canJump);
     return true;
   //if findPiece finds any GREEN box, its true
   } else {
   //beginning of else
   //if canJump did not produce any GREEN box,
     Gamebody.legalMoves.clear(); //initialize
     //without this clearing, legalMoves array is cumulated
     Gamebody.canJump = false;
     Gamecontrol.canMove(checkArray, index);
     //System.out.println("this is findPiece else, do canMove " + Gamebody.canMove);
     //canMove will highlight possible moves in GREEN
        //System.out.println("findPiece for canMove being called");
   if (findPiece(thisBoard,index) == true) {
     Gamebody.canMove = true;
     return true; 
   }
   
   Gamebody.canMove = false;
   //this is gameover
   //GAME OVER
   //GAME OVER
   //false return from legalMove or legalMoveAI = gameover
  
   return false; 
   }
   //end of else
 }
 
 public static boolean secondJump(Pieces thisBoard[], int index){
   //System.out.println("secondJump " + index);
   Pieces checkArray[] = thisBoard;
   
   Board.here = index; //first click determines here, if true? (for second jump)
   //there will be captured from the next click //that next 'there' being used here
   
   Gamecontrol.canJump(checkArray, index);
   //canJump will highlight possible moves in GREEN,
   
   if (findPiece(thisBoard, index) == true) {
     Gamebody.canJump = true;
     //System.out.println("secondJump canJump " + Gamebody.canJump);
     //From Gamebody.runIt, hidePiece will be called right after this, so need to show pieces somehow
     
     return true;
     //if findPiece finds any GREEN box, its true
     
   } else {
     //if canJump did not produce any GREEN box, TERMINATE
     Gamebody.canJump = false;
     //System.out.println("secondJump cant Jump " + Gamebody.canJump);
     return false; 
   }
 }
 
 public static void turnChange(){
  //System.out.println("turnChange " + Board.gameTurn);
   if (Board.gameTurn == 0) {
     Board.gameTurn = 1;
     //Gamebody.info.setText("Yellow's Turn");
     //Turns -> text taken care at button event @ Board
   } else if (Board.gameTurn == 1) {
     Board.gameTurn = 0;
     //gameBoard.info.setText("Red's Turn");
   }
   Gamebody.legalMoves.clear();
   System.out.println("turnChange");
 }
 
 public static void showTurn(JLabel infoadr[]){
   //if (turn == 0) { info.setText("Red's Turn");}
   //if (turn == 1) { Board.l1.setText("Yellow's Turn");}
   //System.out.println("showTurn " + Board.gameTurn);
   
   if (Board.gameTurn == 0) { infoadr[0].setText("Red's Turn");}
   if (Board.gameTurn == 1) { infoadr[0].setText("Yellow's Turn");}
   
 }
 
 public static void movePiece(Pieces thisBoard[], int there, JLabel infoAddr[]) {
   //Use gamebody.canJump and gamebody.canMove,
   //if moved, turn is over
   //if jumped, check another jump is possible, false return -> turn is over
   if (Gamebody.canMove == true) { 
     Gamebody.canMove = false; 
   }
   if (Gamebody.canJump == true) { 
     Gamebody.canJump = false; 
     //Gamecontrol.canJump(thisBoard, there);
     //this checking for second jump needs to be done once move has happened
   }
   
   //reassign 'here' to make sure greenSource is implemented
   int here = thisBoard[there].greenSource;
   //now here works, so movePiece only requires there as input
   System.out.println(here + " -> " + there);
   
   thisBoard[there].setBackground( thisBoard[here].getBackground() );
   thisBoard[there].setText( thisBoard[here].getText() );
   thisBoard[here].setBackground(Color.BLACK);
   thisBoard[here].setText( "" );
   //System.out.println("move Piece " + here + " -> " + there);
   
   //following if routine removes jumped opponent piece
   if (Math.abs(there - here) >= 12) {
   //here to there takes more than 12 cell means remove the jumped opponent!
     //63 -> 54 -> 45 diff = 18
     //65 -> 54 -> 43 diff = 22
     //take difference of (there - here)
     //take smaller #
     //add diff/2
     int target = Math.min(here,there) + Math.abs(there-here)/2;
     thisBoard[target].setBackground(Color.BLACK);
     thisBoard[target].setText("");
     //since this was a jump, check for second jump if false, then turnchange
     if (secondJump(thisBoard, there) != true) { 
       //if cant second jump, change turn
       Gamecontrol.turnChange(); 
       //clear legal move array
       Gamebody.legalMoves.clear();
     } else { //if can second jump, automatically do it for computers, then finalize turn
       if (Board.gameTurn == Gamebody.gameTurnComp) { 
         
         //Gamecontrol.movePiece(thisBoard, there); 
         //THIS LINE CAUSED INFINITE LOOP
         
         //if second jump was possible, and is AI turn, do secondJump by calling runItAI instead
         Gamebody.runItAI(thisBoard, infoAddr);
       }
     }
     
     
   } else {
     //System.out.println("move Piece triggers turnChange " + Board.gameTurn);
     Gamecontrol.turnChange(); //if difference <= 12, it was regular move, end tern
     //System.out.println("move Piece triggers turnChange " + Board.gameTurn);
   }
   
   //terminate turn after each move
 }
 
 public static boolean findPiece(Pieces thisBoard[], int index){
   //findPieces detects any highlighted buttons
   //then add those green buttons to legalMoves array
   //this array gets cleaned from runIT / runITAI at the end of each turn
   int count = 0;
   for (int i = 0; i < 100; i ++) {
     if(thisBoard[i].getBackground() == Color.GREEN ) { 
       count ++; 
       Gamebody.legalMoves.add(i);
       //System.out.println("findPiece-legalMoves arraylist : " + Gamebody.legalMoves);
     }
   }
System.out.println("findPiece : " + Gamebody.legalMoves);
   
   if (count > 0) { return true; } else { return false; }
 }
   
 public static void showPiece(Pieces thisBoard[], int index){
   //System.out.println("show piece");
   //canMove will call showpiece while being called by legalMove
   
   //hidePiece before showing any new click @canMove
   
   Pieces aBoard[] = thisBoard;
   int anumber = index;
   //System.out.println("hilight : " + anumber);
   thisBoard[index].setBackground(Color.GREEN);
 }
 

 public static void hidePiece(Pieces thisBoard[]){
 
   Pieces aBoard[] = thisBoard;
      
   for (int i = 0; i< 100; i++) {
   
   if (thisBoard[i].getBackground() == Color.GREEN){
   thisBoard[i].setBackground(Color.BLACK);
   }
   
   }
 }
 
 
 public static boolean chkBoundary(Pieces thisBoard[], int index){
  //checks for KING's turn ***
   
  //System.out.println("chk boundary");
  //inBoundary is legit move check #1
   
  //checks turn
   if ((thisBoard[index].getText() == "K")){
     //yellow or red's turn
     //KING OR NO
     return true;
   } else {
     return false;    
   }
   
  //this checks for DESTINATION location exists
  //11-18 21-28 31-38 41-48 51-58 61-68 71-78 81-88 within a bound
  //If return is true, check next status 
  
  //only between 11 88 //only buttons between 11 and 88 in a proper board allowed to be clicked
  
 }
 
 public static boolean canMove(Pieces thisBoard[], int index){
 
  //checks for original piece colour & whether if it was a king
  
   if (chkBoundary(thisBoard, index) == true){
     //KING"s TURN
     //outer most if from canMove begins, it's someone's turn!
     //beginning of KING piece
     //if check boundary says TRUE, it's a king piece, can go anywhere
     
     //A KING's TURN
     if ((Board.gameTurn == 0) && (thisBoard[index].getBackground() == Color.RED) && (thisBoard[index].getText() == "K")) {
       //for red KING move
       if (thisBoard[index-9].getBackground() == Color.BLACK) {
       showPiece(thisBoard, index-9);
       thisBoard[index-9].greenSource = index;
       }
       if (thisBoard[index-11].getBackground() == Color.BLACK) {
       showPiece(thisBoard, index-11);
       thisBoard[index-11].greenSource = index;
       }
       if (thisBoard[index+9].getBackground() == Color.BLACK) {
       showPiece(thisBoard, index+9);
       thisBoard[index+9].greenSource = index;
       }
       if (thisBoard[index+11].getBackground() == Color.BLACK) {
       showPiece(thisBoard, index+11);
       thisBoard[index+11].greenSource = index;
       }
       //remove old
       //put new
       return true;
       } else if ((Board.gameTurn == 1) && (thisBoard[index].getBackground() == Color.YELLOW) && (thisBoard[index].getText() == "K")) {
       //for yellow KING's move
       if (thisBoard[index-9].getBackground() == Color.BLACK) {
       showPiece(thisBoard, index-9);
       thisBoard[index-9].greenSource = index;
       }
       if (thisBoard[index-11].getBackground() == Color.BLACK) {
       showPiece(thisBoard, index-11);
       thisBoard[index-11].greenSource = index;
       }
       if (thisBoard[index+9].getBackground() == Color.BLACK) {
       showPiece(thisBoard, index+9);
       thisBoard[index+9].greenSource = index;
       }
       if (thisBoard[index+11].getBackground() == Color.BLACK) {
       showPiece(thisBoard, index+11);
       thisBoard[index+11].greenSource = index;
       }
       //remove old
       //put new
       return true;
       //end of yellow KING move
       }
//end of ALL KING move       
   } else { //regular turns
       //if checkBoundary says its not KING
//beginning of ALL REG move

         if ((Board.gameTurn == 0) && (thisBoard[index].getBackground() == Color.RED)) {
           //for RED's move
           if (thisBoard[index-9].getBackground() == Color.BLACK) {
             showPiece(thisBoard, index-9);
             thisBoard[index-9].greenSource = index;
           } 
           if (thisBoard[index-11].getBackground() == Color.BLACK) {
             showPiece(thisBoard, index-11);
             thisBoard[index-11].greenSource = index;
           } 
           //remove old
           //put new
           return true;
           //end of RED's move
         } else if ((Board.gameTurn ==1) && (thisBoard[index].getBackground() == Color.YELLOW)) {
           //for YELLOW's move
           if (thisBoard[index+9].getBackground() == Color.BLACK) {
             showPiece(thisBoard, index+9);
             thisBoard[index+9].greenSource = index;
           }
           if (thisBoard[index+11].getBackground() == Color.BLACK) {
             showPiece(thisBoard, index+11 );
             thisBoard[index+11].greenSource = index;
           }
           //remove old
           //put new
           return true;
           //end of YELLOW's move
     } else {
       return false;
     
     //END OF REG turns
    }
     //set turn ,end of class funcation
   } 

   //THIS IS NEVER REACHED
   return false;
 }//end of public canMove
  
 public static boolean canJump(Pieces thisBoard[], int index){
   //check if opponent piece available, && jump space available -> showPiece
   //for king
   //System.out.println("canJump index " + index + " " + thisBoard[index].getBackground());
   
   //canjump also saves here, there information for each green box highlighted, so relevant pieces are tracked
   if (thisBoard[index].getText() == "K") {
     //red king
     if ((Board.gameTurn == 0) && (thisBoard[index].getBackground() == Color.RED)) {
       if (thisBoard[index-9].getBackground() == Color.YELLOW) {
         //System.out.println("hilight here1");
         if (thisBoard[index-9-9].getBackground() == Color.BLACK) { thisBoard[index-9-9].greenSource = index; showPiece(thisBoard, index-9-9); }
       }
       if (thisBoard[index-11].getBackground() == Color.YELLOW) {
         //highlight here
         //System.out.println("hilight here2, " + (index-11-11) );
         if (thisBoard[index-11-11].getBackground() == Color.BLACK) { thisBoard[index-11-11].greenSource = index;  showPiece(thisBoard, index-11-11); }
       }
       if (thisBoard[index+9].getBackground() == Color.YELLOW) {
         //highlight here
         //System.out.println("hilight here3");
         if (thisBoard[index+9+9].getBackground() == Color.BLACK) { thisBoard[index+9+9].greenSource = index;  showPiece(thisBoard, index+9+9); }
       }
       if (thisBoard[index+11].getBackground() == Color.YELLOW) {
         //highlight here
         //System.out.println("hilight here4");
         if (thisBoard[index+11+11].getBackground() == Color.BLACK) { thisBoard[index+11+11].greenSource = index;  showPiece(thisBoard, index+11+11); }
       }
       return true;
     }
     //yellow king
     if ((Board.gameTurn == 1) && (thisBoard[index].getBackground() == Color.YELLOW)) {
       if (thisBoard[index-9].getBackground() == Color.RED) {
         //highlight here
         if (thisBoard[index-9-9].getBackground() == Color.BLACK) { thisBoard[index-9-9].greenSource = index;  showPiece(thisBoard, index-9-9); }
       }
       if (thisBoard[index-11].getBackground() == Color.RED) {
         //highlight here
         if (thisBoard[index-11-11].getBackground() == Color.BLACK) {thisBoard[index-11-11].greenSource = index; showPiece(thisBoard, index-11-11); }
       }
       if (thisBoard[index+9].getBackground() == Color.RED) {
         //highlight here
         if (thisBoard[index+9+9].getBackground() == Color.BLACK) {thisBoard[index+9+9].greenSource = index;  showPiece(thisBoard, index+9+9); }
       }
       if (thisBoard[index+11].getBackground() == Color.RED) {
         //highlight here
         if (thisBoard[index+11+11].getBackground() == Color.BLACK) {thisBoard[index+11+11].greenSource = index;   showPiece(thisBoard, index+11+11); }
       }
       return true;
     }
     
   } else {   
   //for reg
       //red reg
     if ((Board.gameTurn == 0) && (thisBoard[index].getBackground() == Color.RED)) {
       if (thisBoard[index-9].getBackground() == Color.YELLOW) {
         if (thisBoard[index-9-9].getBackground() == Color.BLACK) {thisBoard[index-9-9].greenSource = index; showPiece(thisBoard, index-9-9); }
       }
       if (thisBoard[index-11].getBackground() == Color.YELLOW) {
         if (thisBoard[index-11-11].getBackground() == Color.BLACK) {thisBoard[index-11-11].greenSource = index; showPiece(thisBoard, index-11-11); }
       }
       return true;
     }
     //yellow reg
     if ((Board.gameTurn == 1) && (thisBoard[index].getBackground() == Color.YELLOW)) {

       if (thisBoard[index+9].getBackground() == Color.RED) {
         if (thisBoard[index+9+9].getBackground() == Color.BLACK) {thisBoard[index+9+9].greenSource = index;  showPiece(thisBoard, index+9+9); }
       }
       if (thisBoard[index+11].getBackground() == Color.RED) {
         if (thisBoard[index+11+11].getBackground() == Color.BLACK) { thisBoard[index+11+11].greenSource = index;  showPiece(thisBoard, index+11+11); }
       }
       return true;
     }   
     
   //return true;
   } //end of else : reg
   //this is reached
   return false;
 }
 
 public static void mustJump(Pieces thisBoard[]){
   //this method will check for all pieces (for loop index 11-88)
   //if canJump returns true
   //highlight that one
   //then other moves restricted
   //therefore user must jump
   
   //check canJump, halt at the first true
   //so clicking other buttons will only indicate this one which can jump first,
   //multiple pieces can jump -> then should be able to choose
   //just show both, by not clearing
   
   //1. check through all the pieces with corresponding turn
   //2. highlight possible jumps
   //3. any jump possible -> then end routine
   //4. no jump possible, then move on to check move
   int count = 0;
   
   for (int i = 11; i < 89; i++ ) {
     count = i;
     Gamecontrol.canJump(thisBoard, count);
//     if ( Gamecontrol.canJump(thisBoard, count) )  {
//       return true;
//     } else {
//      return false; 
//     }
     
   }
   
 }
 
 public static void reachEnd(Pieces thisBoard[]){
   //Check a normal piece has reached the end, once it did it gets K
   
   //check each lift down if it has reached the end
   
   //check if red@top yellow@bottom and give them K
   int count = 0;
   for (int i = 1; i < 9; i++) {
     count = i + 10; //will check 11-18
     if (thisBoard[count].getBackground() ==Color.RED) { thisBoard[count].setText("K"); }
   }
   for (int i = 1; i < 9; i++) {
     count = i + 80; //will check 81-88
     if (thisBoard[count].getBackground() ==Color.YELLOW) { thisBoard[count].setText("K"); }
   }   
 }
 
 public static void timer(){
  //http://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds
  //Board.infoAddr2[1].setText(); 
   
 //Timer timer = new Timer();
 //timer.schedule(new function(), 0, 1000);
 }
 
 public void GamecontrolChangeName(){
  myName = "Name Changed-control";
 }
 
 public String myName(){
  return myName;
 }
}
