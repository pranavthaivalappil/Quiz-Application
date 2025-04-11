package com.java.quizApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

	JButton rules, back;
	JTextField tfname;

	Login() {
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpeg"));
		JLabel image = new JLabel(i1);
		image.setBounds(40, 25, 400, 400);
		add(image);

		JLabel heading = new JLabel("Quiz Game");
		heading.setBounds(495, 60, 300, 45);
		heading.setFont(new Font("Tahoma", Font.BOLD, 40));
		heading.setForeground(new Color(30, 144, 254));
		add(heading);

		JLabel name = new JLabel("Enter your name");
		name.setBounds(530, 135, 300, 20);
		name.setFont(new Font("Mongolian Baiti", Font.BOLD, 18));
		name.setForeground(new Color(30, 144, 254));
		add(name);

		tfname = new JTextField();
		tfname.setBounds(455, 200, 300, 25);
		tfname.setFont(new Font("Times New Roman", Font.BOLD, 20));
		add(tfname);

		rules = new JButton("Start");
		rules.setBounds(455, 270, 120, 25);
		rules.setBackground(new Color(30, 144, 254));
		rules.setForeground(Color.WHITE);
		rules.addActionListener(this);
		add(rules);

		back = new JButton("Exit");
		back.setBounds(635, 270, 120, 25);
		back.setBackground(new Color(30, 144, 254));
		back.setForeground(Color.WHITE);
		back.addActionListener(this);
		add(back);

		setSize(800, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == rules) {
			String name = tfname.getText();
			if (name.equals("")) {
				JOptionPane.showMessageDialog(this, "Please enter your name.");
			} else {
				setVisible(false);
				new Rules(name);
			}
		} else if (ae.getSource() == back) {
			setVisible(false);
		}
	}

	public static void main(String[] args) {
		new Login();
	}
}