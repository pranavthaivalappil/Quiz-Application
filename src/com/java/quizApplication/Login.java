package com.java.quizApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

	JButton rules, back;
	JTextField tfname;

	// Custom JButton with rounded corners
	class RoundedButton extends JButton {
		public RoundedButton(String text) {
			super(text);
			setContentAreaFilled(false);
			setFocusPainted(false);
			setBorderPainted(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			if (getModel().isPressed()) {
				g2.setColor(new Color(25, 118, 210));
			} else if (getModel().isRollover()) {
				g2.setColor(new Color(42, 162, 255));
			} else {
				g2.setColor(new Color(33, 150, 243));
			}
			
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
			
			g2.setColor(getForeground());
			g2.setFont(getFont());
			int textWidth = g2.getFontMetrics().stringWidth(getText());
			int textHeight = g2.getFontMetrics().getHeight();
			g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight / 2) / 2);
			g2.dispose();
		}
	}

	Login() {
		// Initialize database on startup
		DatabaseManager.initDatabase();
		
		// Modern gradient background
		setContentPane(new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				
				// Gradient background
				java.awt.GradientPaint gradient = new java.awt.GradientPaint(
					0, 0, new Color(240, 242, 247),
					0, getHeight(), new Color(255, 255, 255)
				);
				g2d.setPaint(gradient);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		});
		setLayout(null);

		// Main title with modern styling
		JLabel heading = new JLabel("QuizMaster");
		heading.setBounds(450, 50, 350, 60);
		heading.setFont(new Font("Segoe UI", Font.BOLD, 48));
		heading.setForeground(new Color(33, 150, 243));
		add(heading);

		// Subtitle
		JLabel subtitle = new JLabel("Test Your Knowledge");
		subtitle.setBounds(480, 110, 300, 30);
		subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		subtitle.setForeground(new Color(96, 125, 139));
		add(subtitle);

		// Quiz image with border
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpeg"));
		JLabel image = new JLabel(i1);
		image.setBounds(50, 40, 350, 350);
		image.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createRaisedBevelBorder(),
			BorderFactory.createLoweredBevelBorder()
		));
		add(image);

		// Name input label
		JLabel name = new JLabel("Enter Your Name");
		name.setBounds(480, 170, 300, 25);
		name.setFont(new Font("Segoe UI", Font.BOLD, 20));
		name.setForeground(new Color(62, 39, 35));
		add(name);

		// Modern text field
		tfname = new JTextField();
		tfname.setBounds(480, 210, 300, 45);
		tfname.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		tfname.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createRaisedBevelBorder(),
			BorderFactory.createEmptyBorder(10, 15, 10, 15)
		));
		tfname.setBackground(Color.WHITE);
		add(tfname);

		// Start button with modern styling
		rules = new RoundedButton("START QUIZ");
		rules.setBounds(480, 290, 140, 45);
		rules.setFont(new Font("Segoe UI", Font.BOLD, 16));
		rules.setForeground(Color.WHITE);
		rules.addActionListener(this);
		rules.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(rules);

		// Exit button
		back = new RoundedButton("EXIT");
		back.setBounds(640, 290, 140, 45);
		back.setFont(new Font("Segoe UI", Font.BOLD, 16));
		back.setForeground(Color.WHITE);
		back.addActionListener(this);
		back.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(back);

		// Footer text
		JLabel footer = new JLabel("© 2024 QuizMaster - Challenge Yourself!");
		footer.setBounds(450, 420, 350, 20);
		footer.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		footer.setForeground(new Color(158, 158, 158));
		add(footer);

		setSize(850, 550);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("QuizMaster - Login");
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == rules) {
			String name = tfname.getText().trim();
			if (name.equals("")) {
				JOptionPane.showMessageDialog(this, 
					"Please enter your name to continue!", 
					"Name Required", 
					JOptionPane.WARNING_MESSAGE);
			} else {
				// Save user to database and get user ID
				String email = name.toLowerCase() + "@quiz.app"; // Simple email generation
				int userId = DatabaseManager.saveUser(name, email);
				
				if (userId != -1) {
					setVisible(false);
					new Rules(name, userId); // Pass userId to Rules
				} else {
					JOptionPane.showMessageDialog(this, "Database error. Please try again.");
				}
			}
		} else if (ae.getSource() == back) {
			int choice = JOptionPane.showConfirmDialog(this, 
				"Are you sure you want to exit?", 
				"Exit Confirmation", 
				JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		new Login();
	}
}