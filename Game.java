//This class will instantiate game body,
//Only 1 instance is allowed
//Will contain array of game-progress for save and load
//Runs game

public class Game {

 public static void main(String[] args) {
  // TODO Auto-generated method stub
 
   //test whether all classes instantiate, then initiate the game
   //testClasses();
   
   //aBoard aBody aPiece aControl has been generated,
   //aBoard and aBody will be used to play the game,
   //aPiece will be abandoned
   //aControl will be used to impose rules of the game
   
   //No further action required for this class
   gameStart();
 }

 public static void testClasses() {
   
  Board aBoard = new Board();
  //aBoard is not being used in the game
  //instead try gameBoard from aBody, Gamebody
  System.out.println(aBoard.myName());
  aBoard.BoardChangeName();
  System.out.println(aBoard.myName());

  Gamebody aBody = new Gamebody();
  //this test case actually runs the game
  System.out.println(aBody.myName());
  aBody.GamebodyChangeName();
  System.out.println(aBody.myName());

  Pieces aPiece = new Pieces();
  System.out.println(aPiece.myName());
  aPiece.PiecesChangeName();
  System.out.println(aPiece.myName());

  Gamecontrol aControl = new Gamecontrol();
  System.out.println(aControl.myName());
  aControl.GamecontrolChangeName();
  System.out.println(aControl.myName());
 }
 
 public static void gameStart() {
  Gamebody bBody = new Gamebody(); 
 }
 
}
