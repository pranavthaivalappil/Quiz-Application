package com.java.quizApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Score extends JFrame implements ActionListener {

	Score(String name, int score) {
		setBounds(400, 150, 750, 550);
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		setLocationRelativeTo(null);
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/score.png"));
		Image i2 = i1.getImage().getScaledInstance(300, 250, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel image = new JLabel(i3);
		image.setBounds(50, 150, 300, 250);
		add(image);

		JLabel heading = new JLabel("Thank you " + name + " for playing the Quiz");
		heading.setBounds(165, 40, 700, 70);
		heading.setFont(new Font("Tahoma", Font.PLAIN, 26));
		add(heading);

		JLabel lblscore = new JLabel("Your score is " + score);
		lblscore.setBounds(420, 180, 300, 30);
		lblscore.setFont(new Font("Tahoma", Font.PLAIN, 26));
		if (score < 50) {
			lblscore.setForeground(Color.RED);
		} else {
			lblscore.setForeground(Color.GREEN);
		}
		add(lblscore);

		JButton submit = new JButton("Play Again");
		submit.setBounds(380, 270, 120, 30);
		submit.setBackground(new Color(30, 144, 255));
		submit.setForeground(Color.WHITE);
		submit.addActionListener(this);
		add(submit);

		JButton logout = new JButton("Logout");
		logout.setBounds(510, 270, 120, 30);
		logout.setBackground(new Color(30, 144, 255));
		logout.setForeground(Color.WHITE);
		logout.addActionListener(this);
		add(logout);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Logout")) {
			setVisible(false);
			dispose();
			System.exit(0);
		} else {
			new Login();
		}
	}

	public static void main(String[] args) {
		new Score("User", 0);
	}
}
