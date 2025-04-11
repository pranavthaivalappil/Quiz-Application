package com.java.quizApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Rules extends JFrame implements ActionListener {

	String name;
	JButton start, back;

	Rules(String name) {
		this.name = name;
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);

		JLabel heading = new JLabel("Welcome " + name + " to the Quizz");
		heading.setBounds(50, 120, 700, 30);
		heading.setFont(new Font("Tohoma", Font.BOLD, 28));
		heading.setForeground(new Color(30, 144, 254));
		add(heading);

		JLabel rules = new JLabel();
		rules.setBounds(70, 120, 700, 350);
		rules.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rules.setText("<html>" + "1. Correct answer on the first try - 3 points" + "<br><br>"
				+ "2. Correct answer on the second try - 2 points" + "<br><br>"
				+ "3. Correct answer on the third try - 1 point" + "<br><br>" + "4. Wrong answer - 0 points"
				+ "<br><br>" + "5. Skipped question - 0 points" + "<br><br>" + "<html>");
		add(rules);

		back = new JButton("Back");
		back.setBounds(90, 500, 100, 30);
		back.setBackground(new Color(30, 144, 254));
		back.setForeground(Color.WHITE);
		back.addActionListener(this);
		add(back);

		start = new JButton("Start");
		start.setBounds(230, 500, 100, 30);
		start.setBackground(new Color(30, 144, 254));
		start.setForeground(Color.WHITE);
		start.addActionListener(this);
		add(start);

		setSize(500, 650);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == start) {
			setVisible(false);
			new profileUpdate(name);
		} else {
			setVisible(false);
			new Login();
		}
	}

	public static void main(String[] args) {
		new Rules("User");
	}
}