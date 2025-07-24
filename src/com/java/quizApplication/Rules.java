package com.java.quizApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Rules extends JFrame implements ActionListener {

	String name;
	int userId; // Add userId field
	JButton start, back;

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

	Rules(String name, int userId) {
		this.name = name;
		this.userId = userId;
		
		// Modern gradient background
		setContentPane(new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				
				java.awt.GradientPaint gradient = new java.awt.GradientPaint(
					0, 0, new Color(240, 245, 255),
					0, getHeight(), new Color(255, 255, 255)
				);
				g2d.setPaint(gradient);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		});
		setLayout(null);

		// Welcome heading
		JLabel heading = new JLabel("Welcome " + name + "!");
		heading.setBounds(70, 40, 450, 50);
		heading.setFont(new Font("Segoe UI", Font.BOLD, 36));
		heading.setForeground(new Color(33, 150, 243));
		add(heading);

		// Subtitle
		JLabel subtitle = new JLabel("Ready to test your Java knowledge?");
		subtitle.setBounds(70, 90, 450, 30);
		subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		subtitle.setForeground(new Color(96, 125, 139));
		add(subtitle);

		// Rules panel with card styling - INCREASED HEIGHT
		JPanel rulesPanel = new JPanel();
		rulesPanel.setBounds(50, 140, 500, 450);  // Increased from 350 to 450
		rulesPanel.setBackground(Color.WHITE);
		rulesPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createRaisedBevelBorder(),
			BorderFactory.createEmptyBorder(30, 30, 30, 30)
		));
		rulesPanel.setLayout(null);
		add(rulesPanel);

		// Rules title
		JLabel rulesTitle = new JLabel("Quiz Rules & Instructions");
		rulesTitle.setBounds(20, 20, 400, 35);
		rulesTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
		rulesTitle.setForeground(new Color(62, 39, 35));
		rulesPanel.add(rulesTitle);

		// Using JTextArea with JScrollPane for better text display
		JTextArea rules = new JTextArea();
		rules.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		rules.setForeground(new Color(62, 39, 35));
		rules.setBackground(Color.WHITE);
		rules.setEditable(false);
		rules.setLineWrap(true);
		rules.setWrapStyleWord(true);
		rules.setText(
			"📝 QUIZ FORMAT:\n" +
			"   • 10 multiple-choice questions about Java programming\n" +
			"   • 15 seconds per question (timer countdown)\n" +
			"   • Only one correct answer per question\n" +
			"   • Questions cover various Java concepts\n\n" +
			
			"🏆 SCORING SYSTEM:\n" +
			"   • Correct answer: +10 points\n" +
			"   • Wrong answer: 0 points\n" +
			"   • No answer (timeout): 0 points\n" +
			"   • Maximum possible score: 100 points\n\n" +
			
			"⚡ IMPORTANT NOTES:\n" +
			"   • Timer automatically moves to the next question\n" +
			"   • You cannot go back to previous questions\n" +
			"   • Make sure to select an answer before time runs out\n" +
			"   • Review your answers before final submission\n" +
			"   • Take your time to read each question carefully\n\n" +
			
			"🎯 TIPS FOR SUCCESS:\n" +
			"   • Read each question completely before answering\n" +
			"   • Don't panic if you don't know an answer\n" +
			"   • Trust your first instinct when in doubt\n" +
			"   • Stay calm and focused throughout the quiz\n\n" +
			
			"Good luck and have fun testing your Java knowledge!"
		);

		// Add scroll pane for the rules text
		JScrollPane scrollPane = new JScrollPane(rules);
		scrollPane.setBounds(20, 70, 440, 350);  // Increased height for more content
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		rulesPanel.add(scrollPane);

		// Back button - MOVED DOWN
		back = new RoundedButton("← BACK");
		back.setBounds(120, 620, 120, 45);  // Moved from y=520 to y=620
		back.setFont(new Font("Segoe UI", Font.BOLD, 16));
		back.setForeground(Color.WHITE);
		back.addActionListener(this);
		back.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(back);

		// Start button - MOVED DOWN
		start = new RoundedButton("START QUIZ →");
		start.setBounds(260, 620, 150, 45);  // Moved from y=520 to y=620
		start.setFont(new Font("Segoe UI", Font.BOLD, 16));
		start.setForeground(Color.WHITE);
		start.addActionListener(this);
		start.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(start);

		// Footer - MOVED DOWN
		JLabel footer = new JLabel("Best of luck with your quiz, " + name + "!");
		footer.setBounds(150, 690, 300, 20);  // Moved from y=590 to y=690
		footer.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		footer.setForeground(new Color(158, 158, 158));
		add(footer);

		setSize(600, 800);  // Increased height from 700 to 800
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("QuizMaster - Rules & Instructions");
		setResizable(false);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == start) {
			setVisible(false);
			new profileUpdate(name, userId); // Pass userId to profileUpdate
		} else {
			setVisible(false);
			new Login();
		}
	}

	public static void main(String[] args) {
		new Rules("User", 1); // Pass a dummy userId for now
	}
}