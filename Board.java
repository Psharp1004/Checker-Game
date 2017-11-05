//This class will set up the board
//Only 1 instance of board may exist
//Provides interaction between pieces, control

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout; 
import java.awt.GridLayout; 
import java.awt.GridBagLayout; 
import java.awt.GridBagConstraints; 
import java.awt.Color; 
import java.awt.Font; 


import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants; 
import javax.swing.border.*; //EmptyBorder methods requires this import
import javax.swing.border.EmptyBorder; 
import javax.swing.border.BevelBorder; 
import javax.swing.border.LineBorder; 
import javax.swing.JToolBar;

import java.io.*;
import java.util.*;
import java.util.Random;




public class Board extends JFrame {
 //Pieces
 private int colourSelect = 2; // 0 for regular 1 for king
 private int kingSelect = 0; //0 for red 1 for yellow
 static int redMax = 12;
 static int yelMax = 12;
 static int gameTurn = 2; //0 for red 1 for yellow 2 initial
 static int here = 0;
 static int there = 0; //here and there are indexes from first click to second click
 static JLabel infoAddr2[] = new JLabel[2];
 static JButton infoAddr3[] = new JButton[8]; //button 0 1 2 3 4 5 6 follows the order they appear
 //JPanel
 private JPanel Checkers;
 //JMenu
 private JMenuItem menuOption;
 
 
 static String myName = "I am a board";

 
 public Board(){
  myName = "Initiated-board";
  final Pieces lblBoard[] = new Pieces[100]; //lblBoard array contains 100 JButtons datatype
  final JLabel infoAddr[] = new JLabel[1]; //contains JLabel address
  //infoAddr2[0] = infoAddr[0];
  //infoAddr2 is being used for static use
  //especially in endGameForce
  
//  infoAddr[0] = new JLabel();
//  infoAddr[0] = info; //these two lines can be found near btn declaration
  
//  JLabel pubinfoAddr[] = new JLabel[1];
//  pubinfoAddr[0] = new JLabel();
//  pubinfoAddr[0] = infoAddr[0];
 
   
//***Assignment1*** Following is the previous code
//Any modified parts are omitted from this and added outside
  //JFrame setup
  setTitle("CHECKERS"); 
  setResizable(false); 
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  setBounds(10, 10, 710, 580); //520x580 window, 690x580 changed to accomodate menu, 
  //End of setting up the JFrame
  
  //Initiate JPanel, Checkers and setup
  Checkers = new JPanel(); 
  Checkers.setBorder(new EmptyBorder(0, 0, 0, 0)); 
  setContentPane(Checkers); 
  Checkers.setLayout(null); 
   
  //Set up board
  JPanel boardPanel = new JPanel(); 
  boardPanel.setBackground(Color.WHITE); 
  boardPanel.setBounds(10, 10, 500, 500); //board size 500x500
  Checkers.add(boardPanel); 
  boardPanel.setLayout(new GridLayout(10, 10)); 

  for(int x=0; x<=9; x++){ //ROW
   for(int y=0; y<=9; y++){ //COLlblBoard[(x*10)+y]
    lblBoard[(x*10)+y] = new Pieces(); 
    lblBoard[(x*10)+y].setOpaque(true); 
    lblBoard[(x*10)+y].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    lblBoard[(x*10)+y].setForeground(Color.GRAY); 
    lblBoard[(x*10)+y].setHorizontalAlignment(SwingConstants.CENTER);
    //This line will display index of the button
    //lblBoard[(x*10)+y].setText(""+((x*10)+y)); 
//    lblBoard[(x*10)+y].setText(""+((x*10)+y)); 
    lblBoard[(x*10)+y].setBackground(Color.BLACK); 
    final int countThis = x*10 + y;
    lblBoard[(x*10)+y].setIndex(countThis);     
    lblBoard[countThis].greenSource = countThis;
    //System.out.println("green source: " + lblBoard[countThis].greenSource);
    
    //All buttons will get an action listener
    lblBoard[(x*10)+y].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        //Execute when button is pressed
        //This line will show selected colour / whether king is selected upon clicking
        //System.out.println(colourSelect + " " + kingSelect);
        
        //if game started this behaves differently
        if (Gamebody.gameStarted == true){
          //beginning of game has started
          Board.there = countThis; //this index has most recent click which triggers movePiece, as destination
          
          
          //runit takes care of the first click
          Gamebody.runIt(lblBoard, countThis, here, there, infoAddr);
          //Gamebody.countPiece(lblBoard);
//          Board.there = countThis; //this index has most recent click which triggers movePiece, as destination
//          
//          Gamecontrol.showTurn(infoAddr); //shows turn
//          
//          if (lblBoard[countThis].getBackground() == Color.GREEN) {
//            //Green button is legit moves 
//            //thisBoard[index].setBackground(Color.RED);
//            //move piece here to there
//            Gamecontrol.movePiece(lblBoard, here, there);
//            Gamecontrol.hidePiece(lblBoard, there);
//          } else {
//            //a bad click loses greens
//            Gamecontrol.hidePiece(lblBoard, countThis); 
//          }
//          
//          Gamecontrol.legalMove(lblBoard, countThis);
          
          //end of game has started
        } else {
          //beginning of game has not started
          
          //Conditional for positioning a piece == black & not occupied
          if ((lblBoard[countThis].getBackground() == Color.BLACK) && (lblBoard[countThis].isOccupied == false)){
            //System.out.println("You may put it here");
            //Conditional for positioning a red piece
            if ((colourSelect == 0) && (redMax >= 1)) {
              lblBoard[countThis].setBackground(Color.RED);
              lblBoard[countThis].isOccupied = true;
              System.out.println(lblBoard[countThis].isOccupied);
              if (kingSelect ==1) {lblBoard[countThis].setText("K");}
              redMax --;
            }
            //Conditional for positioning a yellow piece
            if ((colourSelect == 1) && (yelMax >= 1)) {
              lblBoard[countThis].setBackground(Color.YELLOW); 
              lblBoard[countThis].isOccupied = true;
              if (kingSelect ==1) {lblBoard[countThis].setText("K");}
              yelMax --;
            }
            
            //End of the conditional for OK to place a piece
          } else {
            //Beginning of the conditional for NOT OK to place a piece
            
            //System.out.println("You may not put it herE");
            //however if the cell is already occupied, it will toggle availability, reset count
            if (lblBoard[countThis].isOccupied) {
              lblBoard[countThis].isOccupied = false;
              if (lblBoard[countThis].getBackground() == Color.YELLOW) { yelMax ++; }
              if (lblBoard[countThis].getBackground() == Color.RED) { redMax ++; }  
              lblBoard[countThis].setText("");
              lblBoard[countThis].setBackground(Color.BLACK);
            }
            //End of the conditional for NOT OK to place a piece
          }
          //end of if game has not yet started
        }
        
        //End of button action if - for game started or not
      }
    }); //End of button action listener
    
    //black ones will not get warning
    if((x%2)==(y%2)){ 
      //row goes,,, 0 1 0 1 0 1 0 1 0 1, then 0 1 0 1 0 1 0 1 0 1
      //column goes 0 0 0 0 0 0 0 0 0 0, then 1 1 1 1 1 1 1 1 1 1
     lblBoard[(x*10)+y].setBackground(Color.WHITE); 
     //white ones will get warning
    } else {
     lblBoard[(x*10)+y].setBackground(Color.BLACK);  
    }
    
    if((y==0) || (y==9)) {//Column 1 and 10 of the board
      lblBoard[(x*10)+y].setBackground(Color.WHITE);
      lblBoard[(x*10)+y].setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
      switch (x) {
        case 0: lblBoard[(x*10)+y].setText("");
        break;
        case 1: lblBoard[(x*10)+y].setText("8");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 2: lblBoard[(x*10)+y].setText("7");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 3: lblBoard[(x*10)+y].setText("6");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 4: lblBoard[(x*10)+y].setText("5");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 5: lblBoard[(x*10)+y].setText("4");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 6: lblBoard[(x*10)+y].setText("3");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 7: lblBoard[(x*10)+y].setText("2");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 8: lblBoard[(x*10)+y].setText("1");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 9: lblBoard[(x*10)+y].setText("K");
        break;
        default: lblBoard[(x*10)+y].setText("X");
        break;
        }
    }
    if((x==0) || (x==9)){//Row 1 and 10 of the board
      lblBoard[(x*10)+y].setBackground(Color.WHITE);
      lblBoard[(x*10)+y].setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
    switch (y) {
        case 0: lblBoard[(x*10)+y].setText(""); 
        lblBoard[(x*10)+y].setBackground(Color.RED);
        //left top and left bottom will choose RED button
        //if bottom corner, show K
        if (x==9) {lblBoard[(x*10)+y].setText("K");}
        break;
        case 1: lblBoard[(x*10)+y].setText("A");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 2: lblBoard[(x*10)+y].setText("B");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 3: lblBoard[(x*10)+y].setText("C");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 4: lblBoard[(x*10)+y].setText("D");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 5: lblBoard[(x*10)+y].setText("E");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 6: lblBoard[(x*10)+y].setText("F");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 7: lblBoard[(x*10)+y].setText("G");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        case 8: lblBoard[(x*10)+y].setText("H");
        lblBoard[(x*10)+y].setEnabled(false);
        break;
        //right top and right bottom will choose YELLOW button
        //if bottom corner, show K
        case 9: lblBoard[(x*10)+y].setText("");
        lblBoard[(x*10)+y].setBackground(Color.YELLOW);
        if (x==9) {lblBoard[(x*10)+y].setText("K");}
        break;
        default: lblBoard[(x*10)+y].setText("X");
        //X should never happen **Testcase
        break;
        }

    }
    boardPanel.add(lblBoard[(x*10)+y]); 
   } 
  }
  //add action listener to buttons
  lblBoard[0].addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                colourSelect = 0;
                kingSelect = 0;
                //System.out.println(colourSelect + " " + kingSelect);
            }
  });
  lblBoard[9].addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                colourSelect = 1;
                kingSelect = 0;
                //System.out.println(colourSelect + " " + kingSelect);
            }
  });
  lblBoard[90].addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                colourSelect = 0;
                kingSelect = 1;
                //System.out.println(colourSelect + " " + kingSelect);
            }
  });
  lblBoard[99].addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                colourSelect = 1;
                kingSelect = 1;
                //System.out.println(colourSelect + " " + kingSelect);
            }
  });
//***Assignment 1*** Above is from the previous code
  
  
  
//outside of if statement so not affected by gameStart state  
  //Set up menu
  JPanel panel = new JPanel();
  panel.setBorder(new LineBorder(new Color(0,0,0)));
  panel.setBounds(520, 10, 170, 500); //menu size 180x500
  Checkers.add(panel); 
  panel.setLayout(null); 
   
  final JButton btnStandard = new JButton("STANDARD"); 
  btnStandard.setBounds(10, 10, 140, 30); 
  panel.add(btnStandard); 
  infoAddr3[0] = btnStandard;
   
  final JButton btnCustom = new JButton("CUSTOM"); 
  btnCustom.setBounds(10, 50, 140, 30); 
  panel.add(btnCustom);
  infoAddr3[1] = btnCustom;
  
  JButton btnQuit = new JButton("QUIT"); 
  btnQuit.setBounds(10, 90, 140, 30); 
  panel.add(btnQuit); 
  infoAddr3[2] = btnQuit;
  
  final JButton btnStart = new JButton("START"); 
  btnStart.setBounds(10, 130, 140, 30); 
  panel.add(btnStart); 
  infoAddr3[3] = btnStart;
  
  final JLabel info = new JLabel("Checkers!"); 
  info.setFont(new Font("Arial", Font.PLAIN, 15)); 
  info.setHorizontalAlignment(SwingConstants.CENTER); 
  info.setBounds(10, 180, 140, 30); 
  panel.add(info); 
  infoAddr[0] = new JLabel();
  infoAddr[0] = info;
  infoAddr2[0] = infoAddr[0];
  //infoAddr3[4] = info; //info is actually different data type
  
  JButton btnSave = new JButton("SAVE"); 
  btnSave.setBounds(10, 230, 140, 30); 
  panel.add(btnSave); 
  infoAddr3[4] = btnSave;
  
  final JButton btnLoad = new JButton("LOAD"); 
  btnLoad.setBounds(10, 270, 140, 30); 
  panel.add(btnLoad); 
  infoAddr3[5] = btnLoad;
  
  final JButton btnClear = new JButton("CLEAR"); 
  //this needs to be declared final to be accessed within class
  btnClear.setBounds(10, 310, 140, 30); 
  panel.add(btnClear); 
  infoAddr3[6] = btnClear;
  
  /*
  final JCheckBox boxComputer = new JCheckBox("vs. Computer");
  boxComputer.setBounds(10, 350, 140, 30);
  panel.add(boxComputer); */
  
  final JRadioButton redButton   = new JRadioButton("Red");
  final JRadioButton yelButton    = new JRadioButton("Yellow");
  final JRadioButton noButton = new JRadioButton("No", true);
  
  ButtonGroup bgroup = new ButtonGroup();
  bgroup.add(redButton);
  bgroup.add(yelButton);
  bgroup.add(noButton);
  
  final JPanel radioPanel = new JPanel();
  radioPanel.setLayout(new GridLayout(3, 1));
  radioPanel.add(redButton);
  radioPanel.add(yelButton);
  radioPanel.add(noButton);
  
  radioPanel.setBorder(BorderFactory.createTitledBorder(
                            BorderFactory.createEtchedBorder(), "AI's colour"));
  
  radioPanel.setBounds(10, 350, 140, 80);
  panel.add(radioPanel);
  
  final JLabel info2 = new JLabel("5 s");
  info2.setFont(new Font("Arial", Font.PLAIN, 15));
  info2.setHorizontalAlignment(SwingConstants.CENTER); 
  info2.setBounds(10, 440, 140, 30); 
  panel.add(info2);
  infoAddr2[1] = info2;
  
  //Set up action listnerers for buttons
  btnStandard.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                //System.out.println(colourSelect + "std " + kingSelect);
                Gamebody.clearBoard(lblBoard);
                Gamebody.standardBoard(lblBoard);

            }
  });
  btnCustom.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                //System.out.println(colourSelect + "cus " + kingSelect);
              btnClear.setText("Checkers");
                Gamebody.clearBoard(lblBoard);
            }
  });
  btnQuit.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                //System.out.println(colourSelect + "quit " + kingSelect);
                //int result = JOptionPane.showConfirmDialog((Component) e.getSource(),"Close this application?");
                int result = JOptionPane.showConfirmDialog((Component) e.getSource(), "Are you sure?","", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                  System.exit(0);
                } else if (result == JOptionPane.NO_OPTION) {
                  System.out.println("no quit");
                }
                //System.exit(0);
            }
  });
  btnStart.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                //System.out.println(colourSelect + "start " + kingSelect);
                
                
                
                //change turns
                if ((Gamebody.startGame(redMax,yelMax))== true){    
                  //if there is at least one red and one yellow, startgame returns true
                btnStart.setEnabled(false);
                btnCustom.setEnabled(false);
                btnStandard.setEnabled(false);
                btnLoad.setEnabled(false);
                btnClear.setEnabled(false);
                
                
                //can not put any more pieces
                yelMax = 0;
                redMax = 0;
                
                info.setText("Red's Turn");
                gameTurn = 0;
                
                
                //AI starts
                //computer play checked
                //boxComputer.setEnabled(false);
                //boxComputer.isSelected()
                radioPanel.setEnabled(false);
                redButton.setEnabled(false);
                yelButton.setEnabled(false);
                noButton.setEnabled(false);
                
                if (noButton.isSelected() != true) { 
                  //if no button is not selected, find out which colour computer should be
                  if (redButton.isSelected() == true) { 
                    Gamebody.gameTurnComp = 0;
                    //System.out.println("redBtn : " + Gamebody.gameTurnComp);
                  }
                  if (yelButton.isSelected() == true) { 
                    Gamebody.gameTurnComp = 1;
                    //System.out.println("yelBtn : " + Gamebody.gameTurnComp);
                  }
                  
                  if (Board.gameTurn == Gamebody.gameTurnComp) { 
                    Gamebody.runItAI(lblBoard, infoAddr); 
                  }
                  Gamebody.gameComputer = true;
                  //System.out.println("gameComp : " + boxComputer.isSelected());
                  //System.out.println("gameComp : " + Gamebody.gameComputer);
                }
                
                
                }
                else {
                info.setText("cannot start");
                }
                
//Start button is clicked, setting up board is terminated
                //Game state is 2 now
                //Each click on the box toggles lift/lay
                //First click will make piece disappear (lift), 
                //show available next location.
                
                //Second click will make piece show up (put down),
                //If original position, do not check, put down
                //If new position, check#1 #2 #3.
            }
  });
  //Save button
  btnSave.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                //System.out.println(colourSelect + "save " + kingSelect);
                //System.out.println(lblBoard[0].getBackground());
                Gamebody.saveBoard(lblBoard);
            }
  });
  //Load button
  btnLoad.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                //System.out.println(colourSelect + "load " + kingSelect);
                int loadMe3[] = new int[78];
                loadMe3 = Gamebody.loadBoard(); //this returns loadMe2 array 0-77 offset +11
                int calcOffset = 0;
                int anOffset = 11;
                Gamebody.clearBoard(lblBoard);
                for (int i = 0; i < 77; i++) {
                 // loadMe3[i] = //could use loadMe3.length() to calculate array size
                 //System.out.println(loadMe3[i]);
                  calcOffset = i + anOffset;
                  
                 if (lblBoard[calcOffset].getBackground() == Color.BLACK) { //legit blocks
                   switch(loadMe3[i]) {
                     case 1:
                       lblBoard[calcOffset].setBackground(Color.RED);
                       redMax --;
                       break;
                     case 2:
                       lblBoard[calcOffset].setBackground(Color.RED);
                       redMax --;
                       lblBoard[calcOffset].setText("K");
                       break;
                     case 3:
                       lblBoard[calcOffset].setBackground(Color.YELLOW);
                       yelMax --;
                       break;
                     case 4:
                       lblBoard[calcOffset].setBackground(Color.YELLOW);
                       yelMax --;
                       lblBoard[calcOffset].setText("K");
                       break;
                     default:
                       break;           
                   }//end of switch
                 }//end of legit blocks
                  
                }//end of for to restore
                
            }//end of Load btn action liscener
  });
  //Clear button
  btnClear.addActionListener(new ActionListener() {
    
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                //System.out.println(colourSelect + "clear " + kingSelect);
              if (btnClear.getText() != "CLEAR") {
                btnClear.setText("CLEAR");
                for (int i = 0; i < 7; i ++) {
                  infoAddr3[i].setEnabled(true);
                }
                info.setText("Checkers!");
                for (int i=0; i<100; i++) {
                  lblBoard[i].setEnabled(true);
                  lblBoard[i].isOccupied = false;
                }
                Gamebody.gameComputer = false;
                Gamebody.gameTurnComp = 2;
                Gamebody.gameStarted = false;
                
              }
              

              
              radioPanel.setEnabled(true);
              redButton.setEnabled(true);
              yelButton.setEnabled(true);
              noButton.setEnabled(true);
              Board.gameTurn = 2;
              Board.here = 0;
              Board.there = 0;
              
              Gamebody.clearBoard(lblBoard);
              //Gamebody.resetBoard(lblBoard);
            }
  });
  
  
  
 }

 
 public static void setredMax(int aNum){
  redMax = aNum;
 }
 
 public static void setyelMax(int aNum){
  yelMax = aNum;
 }
 
 public static int getredMax(){
  return redMax;
 }
 
 public static int getyelMax(){
  return yelMax;
 }
 
 public void BoardChangeName(){
  myName = "Name Changed-board";
 }
 
 public String myName(){
  return myName;
 }
 
}
