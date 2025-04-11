package com.java.quizApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class profileUpdate extends JFrame implements ActionListener {

	private JLabel nameLabel, emailLabel, passwordLabel, confirmPasswordLabel;
	private JTextField nameTextField, emailTextField;
	private JPasswordField passwordField, confirmPasswordField;
	private JButton updateButton, cancelButton;

	profileUpdate(String name) {

		setTitle("Profile and Password Update");
		setBounds(400, 150, 600, 500);
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);

		nameLabel = new JLabel("Name:");
		nameLabel.setBounds(50, 50, 100, 30);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setBounds(200, 50, 300, 30);
		nameTextField.setText(name);
		add(nameTextField);

		emailLabel = new JLabel("Email:");
		emailLabel.setBounds(50, 100, 100, 30);
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(emailLabel);

		emailTextField = new JTextField();
		emailTextField.setBounds(200, 100, 300, 30);
		add(emailTextField);

		passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(50, 150, 100, 30);
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(200, 150, 300, 30);
		add(passwordField);

		confirmPasswordLabel = new JLabel("Confirm Password:");
		confirmPasswordLabel.setBounds(50, 200, 150, 30);
		confirmPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(confirmPasswordLabel);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(200, 200, 300, 30);
		add(confirmPasswordField);

		updateButton = new JButton("Update");
		updateButton.setBounds(200, 270, 100, 30);
		updateButton.setBackground(new Color(30, 144, 255));
		updateButton.setForeground(Color.WHITE);
		updateButton.addActionListener(this);
		add(updateButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(350, 270, 100, 30);
		cancelButton.setBackground(new Color(30, 144, 255));
		cancelButton.setForeground(Color.WHITE);
		cancelButton.addActionListener(this);
		add(cancelButton);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == updateButton) {
			String name = nameTextField.getText();
			String email = emailTextField.getText();
			String password = new String(passwordField.getPassword());
			String confirmPassword = new String(confirmPasswordField.getPassword());

			if (name.trim().equals("") || email.trim().equals("") || password.trim().equals("")
					|| confirmPassword.trim().equals("")) {
				JOptionPane.showMessageDialog(this, "Please fill all the fields.");
			} else if (!password.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(this, "Passwords do not match.");
			} else {
				// Code to update profile and password goes here
				JOptionPane.showMessageDialog(this, "Profile and password updated successfully.");
				setVisible(false);
				new Quiz(name);
			}
		} else if (ae.getSource() == cancelButton) {
			setVisible(false);
		}
	}

	public static void main(String[] args) {
		String name = "User";
		new profileUpdate(name);
	}
}
