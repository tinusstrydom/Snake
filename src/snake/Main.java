package snake;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame{
	//Variables & Constants
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
		//Set size
		setSize(300, 300);
		//
		setResizable(false);
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
