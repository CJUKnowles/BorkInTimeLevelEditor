package com.turruc;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.turruc.engine.Window;
import com.turruc.game.GameManager;
import com.turruc.game.entities.BuildEnt;
import com.turruc.game.entities.CameraFollow;

@SuppressWarnings("serial")
public class LevelPicker extends JFrame{

	public static LevelPicker instance;

	JLabel currentTile;
	JLabel currentTileImage;
	ArrayList<String> tiles = new ArrayList<String>();

	public LevelPicker(Window window) {
		/*
		jframe = new JFrame();
		jframe.setLayout(new GridLayout());
		jframe.setAlwaysOnTop(true);

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		 */
		instance = this;
		JPanel contentPane;
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 312, 999);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlShadow);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Level Editor");
		setResizable(false);
		setSize(312,window.getFrame().getHeight());
		setLocation(window.getFrame().getWidth() - 8, 0);


		File file = new File("");

		JLabel lblCurrentBlock = new JLabel("Drawing Tile: ");
		lblCurrentBlock.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		lblCurrentBlock.setBounds(10, 11, 117, 23);
		contentPane.add(lblCurrentBlock);

		JTextArea txtrTest = new JTextArea();
		txtrTest.setText("test");
		txtrTest.setBounds(20, 119, 253, 199);
		contentPane.add(txtrTest);

		

		JTextPane textPane = new JTextPane();
		textPane.setBounds(129, 666, 64, 23);
		contentPane.add(textPane);

		JLabel lblWidth = new JLabel("Width: 5");
		lblWidth.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		lblWidth.setBounds(20, 667, 89, 19);
		contentPane.add(lblWidth);

		JLabel lblHeight = new JLabel("Height: 5");
		lblHeight.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		lblHeight.setBounds(20, 697, 84, 25);
		contentPane.add(lblHeight);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(129, 699, 64, 23);
		contentPane.add(textPane_1);

		JButton btnSetWidth = new JButton("Set");
		btnSetWidth.setBounds(218, 666, 65, 23);
		btnSetWidth.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				GameManager.gm.updateLevel();

			}
		});
		contentPane.add(btnSetWidth);
		
		JButton btnSetHeight = new JButton("Set");
		btnSetHeight.setBounds(218, 699, 65, 23);
		btnSetHeight.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				GameManager.gm.updateLevel();

			}
		});
		contentPane.add(btnSetHeight);

		currentTile = new JLabel("Current Tile");
		currentTile.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		currentTile.setBounds(119, 11, 117, 23);
		contentPane.add(currentTile);

		JButton btnSave = new JButton("Save to File");
		btnSave.setBounds(10, 926, 117, 23);
		btnSave.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				GameManager.gm.exportToImage("level");
			}
		});
		contentPane.add(btnSave);


		JButton btnSaveAndRun = new JButton("Save and Run");
		btnSaveAndRun.setBounds(156, 926, 117, 23);
		btnSaveAndRun.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				GameManager.gm.exportToImage("level");
				GameManager.gm.runMainGame();

			}
		});
		contentPane.add(btnSaveAndRun);

		JLabel lblTileSet = new JLabel("Tile Set");
		lblTileSet.setFont(new Font("Gill Sans MT", Font.BOLD, 16));
		lblTileSet.setBounds(109, 341, 75, 19);
		contentPane.add(lblTileSet);

		final JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setIcon(new ImageIcon(file.getAbsolutePath() + "\\resources\\dirtTileset.png"));
		lblNewLabel.setBounds(84, 437, 128, 128);
		contentPane.add(lblNewLabel);

		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(getTileSets(true)));
		comboBox.setBounds(13, 364, 276, 32);
		comboBox.setToolTipText("");
		comboBox.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				for(String s : getTileSets(false)) {
					if(s.endsWith(comboBox.getSelectedItem().toString())) {
						comboBox.setToolTipText(s);
						break;
					}
				}
				lblNewLabel.setIcon(new ImageIcon(comboBox.getToolTipText()));
				GameManager.updateTileSet("/" + comboBox.getSelectedItem().toString());

			}
		});
		contentPane.add(comboBox);


		JLabel lblProperties = new JLabel("Selected Tile: ");
		lblProperties.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		lblProperties.setBounds(10, 59, 117, 19);
		contentPane.add(lblProperties);

		JLabel lblMapSize = new JLabel("Map Size");
		lblMapSize.setFont(new Font("Gill Sans MT", Font.BOLD, 16));
		lblMapSize.setBounds(118, 635, 89, 19);
		contentPane.add(lblMapSize);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.DARK_GRAY);
		separator.setForeground(Color.DARK_GRAY);
		separator.setBounds(10, 623, 276, 1);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.DARK_GRAY);
		separator_1.setBackground(Color.DARK_GRAY);
		separator_1.setBounds(10, 329, 276, 1);
		contentPane.add(separator_1);

		currentTileImage = new JLabel("");
		currentTileImage.setBackground(Color.WHITE);
		currentTileImage.setVerticalAlignment(SwingConstants.TOP);
		currentTileImage.setIcon(new ImageIcon(file.getAbsolutePath() + "\\resources\\turretHead.png"));
		currentTileImage.setBounds(241, 6, 32, 32);
		contentPane.add(currentTileImage);

		JLabel label_1 = new JLabel("Collision Block");
		label_1.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		label_1.setBounds(119, 55, 117, 23);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(file.getAbsolutePath() + "\\resources\\resourceBall.png"));
		label_2.setVerticalAlignment(SwingConstants.TOP);
		label_2.setBackground(Color.WHITE);
		label_2.setBounds(241, 46, 32, 32);
		contentPane.add(label_2);



		contentPane.validate();
		contentPane.repaint();
	}

	private String[] getTileSets(boolean justName){
		tiles.clear();
		tiles = new ArrayList<String>();

		findFile("set.png", new File(System.getProperty("user.dir") + File.separator + "resources"), justName);

		String[] ret = new String[tiles.size()];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = tiles.get(i);
		}
		return ret;
	}

	private void findFile(String name,File file, boolean justName){
		File[] list = file.listFiles();
		if(list!=null)
			for (File fil : list){
				if (fil.isDirectory()){
					findFile(name,fil, justName);
				}else if (fil.getName().contains(name)){
					if(!tiles.contains(fil.getAbsolutePath())) {
						if(justName){
							tiles.add(fil.getName());
						}else {
							tiles.add(fil.getAbsolutePath());
						}

					}

				}
			}
	}

	public static void exit() {
		LevelPicker.instance.dispatchEvent(new WindowEvent(LevelPicker.instance, WindowEvent.WINDOW_CLOSING));
	}

	public static void update() {

		BuildEnt current = BuildEnt.values()[CameraFollow.selection];

		LevelPicker.instance.currentTile.setText(current.getName());

		File file = new File("");
		LevelPicker.instance.currentTileImage.setIcon(
				new ImageIcon(file.getAbsolutePath() + "\\resources\\" + current.getPath()));
		if(current == BuildEnt.MANABALL) {
			LevelPicker.instance.currentTileImage.setVerticalAlignment(SwingConstants.BOTTOM);
		}else {
			LevelPicker.instance.currentTileImage.setVerticalAlignment(SwingConstants.TOP);
		}


	}
}
