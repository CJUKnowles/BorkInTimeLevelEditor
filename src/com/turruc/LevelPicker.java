package com.turruc;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.turruc.engine.Window;
import com.turruc.game.entities.BuildEnt;
import com.turruc.game.entities.CameraFollow;

public class LevelPicker {

	private static JFrame jframe;
	private static JLabel selectedItem;
	
	public LevelPicker(Window window) {
		jframe = new JFrame();
		jframe.setLayout(new GridLayout());
		jframe.setAlwaysOnTop(true);
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setTitle("Level Editor");
		jframe.setSize(300,400);
		jframe.setLocation(jframe.getX() + window.getFrame().getWidth(), window.getCanvas().getHeight() - jframe.getHeight());

		JLabel selected = new JLabel("Selected: ");
		selectedItem = new JLabel("");
		JButton button = new JButton("Increase Size");

		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
			}
		});

		
		jframe.add(button);
		jframe.add(selected);
		jframe.add(selectedItem);
		

		jframe.setVisible(true);
	}
	
	public static void exit() {
		jframe.dispatchEvent(new WindowEvent(jframe, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void update() {
			selectedItem.setText(BuildEnt.values()[CameraFollow.selection].getName());
	}
}
