package com.java.quizApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Score extends JFrame implements ActionListener {

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

	Score(String name, int score, int userId) {
		setBounds(400, 150, 850, 650);
		
		// Get user's best score from database
		int bestScore = DatabaseManager.getUserBestScore(userId);

		// Modern gradient background
		setContentPane(new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				
				// Different gradient based on score
				java.awt.GradientPaint gradient;
				if (score >= 70) {
					gradient = new java.awt.GradientPaint(
						0, 0, new Color(232, 245, 233),
						0, getHeight(), new Color(255, 255, 255)
					);
				} else if (score >= 50) {
					gradient = new java.awt.GradientPaint(
						0, 0, new Color(255, 248, 225),
						0, getHeight(), new Color(255, 255, 255)
					);
				} else {
					gradient = new java.awt.GradientPaint(
						0, 0, new Color(255, 235, 238),
						0, getHeight(), new Color(255, 255, 255)
					);
				}
				g2d.setPaint(gradient);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		});
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("QuizMaster - Results");
		setResizable(false);

		// Result header
		String resultText = score >= 70 ? "Excellent Work!" : score >= 50 ? "Good Job!" : "Keep Trying!";
		String emoji = score >= 70 ? "🏆" : score >= 50 ? "👍" : "💪";
		
		JLabel resultHeader = new JLabel(emoji + " " + resultText);
		resultHeader.setBounds(250, 50, 400, 60);
		resultHeader.setFont(new Font("Segoe UI", Font.BOLD, 42));
		if (score >= 70) {
			resultHeader.setForeground(new Color(76, 175, 80));
		} else if (score >= 50) {
			resultHeader.setForeground(new Color(255, 152, 0));
		} else {
			resultHeader.setForeground(new Color(244, 67, 54));
		}
		add(resultHeader);

		// Thank you message
		JLabel heading = new JLabel("Thank you " + name + " for playing QuizMaster!");
		heading.setBounds(150, 120, 600, 40);
		heading.setFont(new Font("Segoe UI", Font.PLAIN, 24));
		heading.setForeground(new Color(62, 39, 35));
		add(heading);

		// Score display panel
		JPanel scorePanel = new JPanel();
		scorePanel.setBounds(200, 190, 450, 200);
		scorePanel.setBackground(Color.WHITE);
		scorePanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createRaisedBevelBorder(),
			BorderFactory.createEmptyBorder(30, 30, 30, 30)
		));
		scorePanel.setLayout(null);
		add(scorePanel);

		// Score image
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/score.png"));
		Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel image = new JLabel(i3);
		image.setBounds(30, 40, 120, 120);
		add(image);

		// Your Score label
		JLabel scoreTitle = new JLabel("Your Final Score");
		scoreTitle.setBounds(40, 20, 300, 30);
		scoreTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
		scoreTitle.setForeground(new Color(96, 125, 139));
		scorePanel.add(scoreTitle);

		// Score value
		JLabel lblscore = new JLabel(score + "/100");
		lblscore.setBounds(40, 60, 200, 60);
		lblscore.setFont(new Font("Segoe UI", Font.BOLD, 48));
		if (score >= 70) {
			lblscore.setForeground(new Color(76, 175, 80));
		} else if (score >= 50) {
			lblscore.setForeground(new Color(255, 152, 0));
		} else {
			lblscore.setForeground(new Color(244, 67, 54));
		}
		scorePanel.add(lblscore);

		// Show best score if different
		if (bestScore > score) {
			JLabel bestScoreLabel = new JLabel("Best: " + bestScore + "/100");
			bestScoreLabel.setBounds(40, 135, 200, 25);  // Moved down from 125 to 135
			bestScoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			bestScoreLabel.setForeground(new Color(76, 175, 80));
			scorePanel.add(bestScoreLabel);
		} else if (score > 0 && score == bestScore) {
			JLabel newBestLabel = new JLabel("🎉 New Best Score!");
			newBestLabel.setBounds(40, 135, 200, 25);  // Moved down from 125 to 135
			newBestLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
			newBestLabel.setForeground(new Color(76, 175, 80));
			scorePanel.add(newBestLabel);
		}

		// Performance message - moved down to avoid overlap
		String performance;
		if (score >= 90) performance = "Outstanding! 🌟";
		else if (score >= 70) performance = "Excellent work! 🎉";
		else if (score >= 50) performance = "Good effort! 👏";
		else performance = "Keep practicing! 📚";
		
		JLabel performanceLabel = new JLabel(performance);
		performanceLabel.setBounds(40, 160, 300, 25);  // Moved down from 130 to 160
		performanceLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		performanceLabel.setForeground(new Color(96, 125, 139));
		scorePanel.add(performanceLabel);

		// Statistics
		int correct = score / 10;
		int incorrect = 10 - correct;
		
		JLabel stats = new JLabel("<html>✅ Correct: " + correct + "/10<br>❌ Incorrect: " + incorrect + "/10<html>");
		stats.setBounds(270, 60, 150, 80);
		stats.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		stats.setForeground(new Color(96, 125, 139));
		scorePanel.add(stats);

		// Action buttons
		JButton submit = new RoundedButton("🔄 PLAY AGAIN");
		submit.setBounds(200, 430, 180, 50);
		submit.setFont(new Font("Segoe UI", Font.BOLD, 16));
		submit.setForeground(Color.WHITE);
		submit.addActionListener(this);
		submit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(submit);

		JButton logout = new RoundedButton("🚪 EXIT");
		logout.setBounds(400, 430, 140, 50);
		logout.setFont(new Font("Segoe UI", Font.BOLD, 16));
		logout.setForeground(Color.WHITE);
		logout.addActionListener(this);
		logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(logout);

		// Footer message
		JLabel footer = new JLabel("Thanks for using QuizMaster! Come back anytime to test your knowledge.");
		footer.setBounds(150, 520, 600, 25);
		footer.setFont(new Font("Segoe UI", Font.ITALIC, 14));
		footer.setForeground(new Color(158, 158, 158));
		add(footer);

		// Motivational quote based on performance
		String quote = "";
		if (score >= 80) quote = "\"Excellence is not a skill, it's an attitude.\" - Ralph Marston";
		else if (score >= 60) quote = "\"Success is the sum of small efforts repeated day in and day out.\"";
		else quote = "\"Every expert was once a beginner. Keep learning!\"";
		
		JLabel quoteLabel = new JLabel("<html><center>" + quote + "</center></html>");
		quoteLabel.setBounds(100, 550, 650, 40);
		quoteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		quoteLabel.setForeground(new Color(158, 158, 158));
		add(quoteLabel);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().contains("EXIT")) {
			setVisible(false);
			dispose();
			System.exit(0);
		} else {
			setVisible(false);
			new Login();
		}
	}

	public static void main(String[] args) {
		new Score("User", 75, 1); // Assuming userId 1 for testing
	}
}
