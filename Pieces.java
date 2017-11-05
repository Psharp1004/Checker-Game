import javax.swing.JButton;

//Pieces will contain data such as myColor myVisibility
//Total 24 pieces will be instantiated by gamebody
//Each piece will have event handler, 
//upon click calls gamecontrol.click() pass this.info
//this.info will be an array of myColor, myLocation
//upon move calls gamecontrol.move
//this.info will be passed in order to determine legitimacy

public class Pieces extends JButton {

 static String myName = "I am Pieces";
 public Boolean isOccupied = false; //private access did not work
 public int greenSource = 0;
 private int myIndex = 100;
 
 public Pieces(){
  myName = "Initiated-Pieces";
 }
 
 
 
 
 
 
 
 
 
 
 
 public void setIndex(int i){
  this.myIndex =  i;
 }
 
 public int getIndex(){
  return this.myIndex; 
 } 
 
 public void PiecesChangeName(){
  myName = "Name Changed-Pieces";
 }
 
 public String myName(){
  return myName;
 }
}
