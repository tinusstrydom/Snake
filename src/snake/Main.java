package snake;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame{
	//create new board class
	public Board board = new Board();
	
	//Constructor Method
	public Main() {
		initUI();
	}
	public void initUI() {
		//add board to the initial UI
		add(board);
		//set title for frame
		setTitle("Snake");
		//Set if frame is resizable by user
		setResizable(false);
		//call pack method to fit the 
		//preferred size and layout of subcomponents
		//***important head might not work correctly with collision of bottom and right borders
		pack();
		//set location on screen(null centers it)
		setLocationRelativeTo(null);
		//set default for close button of frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//Main Method
	public static void main(String[] args) {
		//EventQueue call blocks until pending events 
		//have been processed
		EventQueue.invokeLater(() -> {
			Main main = new Main();
			main.setVisible(true);
		});
	}
}
