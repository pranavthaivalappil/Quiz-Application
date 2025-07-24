package com.java.quizApplication;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

public class Quiz extends JFrame implements ActionListener {

	String questions[][] = new String[10][5];
	String answers[][] = new String[10][2];
	String useranswers[][] = new String[10][1];
	JLabel qno, question, progress;  // Added progress as class field
	JRadioButton opt1, opt2, opt3, opt4;
	ButtonGroup groupoptions;
	JButton next, submit, lifeline;
	JPanel questionPanel, optionsPanel;

	public static int timer = 15;
	public static int ans_given = 0;
	public static int count = 0;
	public static int score = 0;

	String name;
	int userId; // Add userId field

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
			
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
			
			g2.setColor(getForeground());
			g2.setFont(getFont());
			int textWidth = g2.getFontMetrics().stringWidth(getText());
			int textHeight = g2.getFontMetrics().getHeight();
			g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight / 2) / 2);
			g2.dispose();
		}
	}

	Quiz(String name, int userId) {
		this.name = name;
		this.userId = userId;
		
		// Load questions from database
		loadQuestionsFromDatabase();

		setBounds(50, 0, 1400, 900);
		setSize(1400, 720);
		
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
		setLocationRelativeTo(null);
		setTitle("QuizMaster - Test Your Knowledge");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Header with image
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/quiz.jpg"));
		JLabel image = new JLabel(i1);
		image.setBounds(0, 0, 1400, 200);
		add(image);

		// Welcome message
		JLabel welcome = new JLabel("Welcome " + name + "! Answer the questions below:");
		welcome.setBounds(50, 220, 800, 35);
		welcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
		welcome.setForeground(new Color(33, 150, 243));
		add(welcome);

		// Question panel with card styling
		questionPanel = new JPanel();
		questionPanel.setBounds(50, 270, 1000, 100);
		questionPanel.setBackground(Color.WHITE);
		questionPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createRaisedBevelBorder(),
			BorderFactory.createEmptyBorder(20, 20, 20, 20)
		));
		questionPanel.setLayout(null);
		add(questionPanel);

		qno = new JLabel();
		qno.setBounds(20, 30, 50, 40);
		qno.setFont(new Font("Segoe UI", Font.BOLD, 28));
		qno.setForeground(new Color(33, 150, 243));
		questionPanel.add(qno);

		question = new JLabel();
		question.setBounds(80, 30, 900, 40);
		question.setFont(new Font("Segoe UI", Font.PLAIN, 22));
		question.setForeground(new Color(62, 39, 35));
		questionPanel.add(question);

		// Initialize questions and answers
		initializeQuestions();

		// Options panel with modern styling
		optionsPanel = new JPanel();
		optionsPanel.setBounds(50, 390, 1000, 250);
		optionsPanel.setBackground(Color.WHITE);
		optionsPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createRaisedBevelBorder(),
			BorderFactory.createEmptyBorder(20, 20, 20, 20)
		));
		optionsPanel.setLayout(null);
		add(optionsPanel);

		// Modern radio buttons
		opt1 = createStyledRadioButton();
		opt1.setBounds(30, 20, 900, 40);
		optionsPanel.add(opt1);

		opt2 = createStyledRadioButton();
		opt2.setBounds(30, 70, 900, 40);
		optionsPanel.add(opt2);

		opt3 = createStyledRadioButton();
		opt3.setBounds(30, 120, 900, 40);
		optionsPanel.add(opt3);

		opt4 = createStyledRadioButton();
		opt4.setBounds(30, 170, 900, 40);
		optionsPanel.add(opt4);

		groupoptions = new ButtonGroup();
		groupoptions.add(opt1);
		groupoptions.add(opt2);
		groupoptions.add(opt3);
		groupoptions.add(opt4);

		// Control buttons
		next = new RoundedButton("NEXT QUESTION");
		next.setBounds(1100, 390, 200, 50);
		next.setFont(new Font("Segoe UI", Font.BOLD, 16));
		next.setForeground(Color.WHITE);
		next.addActionListener(this);
		next.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(next);

		submit = new RoundedButton("SUBMIT QUIZ");
		submit.setBounds(1100, 460, 200, 50);
		submit.setFont(new Font("Segoe UI", Font.BOLD, 16));
		submit.setForeground(Color.WHITE);
		submit.addActionListener(this);
		submit.setEnabled(false);
		submit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		add(submit);

		// Progress indicator
		progress = new JLabel("Question 1 of 10");
		progress.setBounds(1100, 530, 200, 30);
		progress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		progress.setForeground(new Color(96, 125, 139));
		add(progress);

		start(count);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JRadioButton createStyledRadioButton() {
		JRadioButton rb = new JRadioButton();
		rb.setBackground(Color.WHITE);
		rb.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		rb.setForeground(new Color(62, 39, 35));
		rb.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		return rb;
	}

	private void initializeQuestions() {
		questions[0][0] = "Which is used to find and fix bugs in the Java programs.?";
		questions[0][1] = "JVM";
		questions[0][2] = "JDB";
		questions[0][3] = "JDK";
		questions[0][4] = "JRE";

		questions[1][0] = "What is the return type of the hashCode() method in the Object class?";
		questions[1][1] = "int";
		questions[1][2] = "Object";
		questions[1][3] = "long";
		questions[1][4] = "void";

		questions[2][0] = "Which package contains the Random class?";
		questions[2][1] = "java.util package";
		questions[2][2] = "java.lang package";
		questions[2][3] = "java.awt package";
		questions[2][4] = "java.io package";

		questions[3][0] = "An interface with no fields or methods is known as?";
		questions[3][1] = "Runnable Interface";
		questions[3][2] = "Abstract Interface";
		questions[3][3] = "Marker Interface";
		questions[3][4] = "CharSequence Interface";

		questions[4][0] = "In which memory a String is stored, when we create a string using new operator?";
		questions[4][1] = "Stack";
		questions[4][2] = "String memory";
		questions[4][3] = "Random storage space";
		questions[4][4] = "Heap memory";

		questions[5][0] = "Which of the following is a marker interface?";
		questions[5][1] = "Runnable interface";
		questions[5][2] = "Remote interface";
		questions[5][3] = "Readable interface";
		questions[5][4] = "Result interface";

		questions[6][0] = "Which keyword is used for accessing the features of a package?";
		questions[6][1] = "import";
		questions[6][2] = "package";
		questions[6][3] = "extends";
		questions[6][4] = "export";

		questions[7][0] = "In java, jar stands for?";
		questions[7][1] = "Java Archive Runner";
		questions[7][2] = "Java Archive";
		questions[7][3] = "Java Application Resource";
		questions[7][4] = "Java Application Runner";

		questions[8][0] = "Which of the following is a mutable class in java?";
		questions[8][1] = "java.lang.StringBuilder";
		questions[8][2] = "java.lang.Short";
		questions[8][3] = "java.lang.Byte";
		questions[8][4] = "java.lang.String";

		questions[9][0] = "Which of the following option leads to the portability and security of Java?";
		questions[9][1] = "Bytecode is executed by JVM";
		questions[9][2] = "The applet makes the Java code secure and portable";
		questions[9][3] = "Use of exception handling";
		questions[9][4] = "Dynamic binding between objects";

		answers[0][1] = "JDB";
		answers[1][1] = "int";
		answers[2][1] = "java.util package";
		answers[3][1] = "Marker Interface";
		answers[4][1] = "Heap memory";
		answers[5][1] = "Remote interface";
		answers[6][1] = "import";
		answers[7][1] = "Java Archive";
		answers[8][1] = "java.lang.StringBuilder";
		answers[9][1] = "Bytecode is executed by JVM";
	}

	// Load questions from database instead of hardcoded questions
	private void loadQuestionsFromDatabase() {
		String[][] dbQuestions = DatabaseManager.getRandomQuestions();
		String[] dbAnswers = DatabaseManager.getRandomAnswers();
		
		// Copy database questions to existing array structure
		for (int i = 0; i < Math.min(dbQuestions.length, 10); i++) {
			if (dbQuestions[i].length >= 5) {
				questions[i][0] = dbQuestions[i][0]; // question
				questions[i][1] = dbQuestions[i][1]; // option1
				questions[i][2] = dbQuestions[i][2]; // option2
				questions[i][3] = dbQuestions[i][3]; // option3
				questions[i][4] = dbQuestions[i][4]; // option4
			}
		}
		
		// Copy answers
		for (int i = 0; i < Math.min(dbAnswers.length, 10); i++) {
			answers[i][1] = dbAnswers[i];
		}
	}

	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == next) {
			repaint();
			opt1.setEnabled(true);
			opt2.setEnabled(true);
			opt3.setEnabled(true);
			opt4.setEnabled(true);

			ans_given = 1;
			if (groupoptions.getSelection() == null) {
				useranswers[count][0] = "";
			} else {
				useranswers[count][0] = groupoptions.getSelection().getActionCommand();
			}

			if (count == 8) {
				next.setEnabled(false);
				submit.setEnabled(true);
			}

			count++;
			start(count);
		} else if (ae.getSource() == submit) {
			ans_given = 1;
			if (groupoptions.getSelection() == null) {
				useranswers[count][0] = "";
			} else {
				useranswers[count][0] = groupoptions.getSelection().getActionCommand();
			}

			for (int i = 0; i < useranswers.length; i++) {
				if (useranswers[i][0].equals(answers[i][1])) {
					score += 10;
				} else {
					score += 0;
				}
			}
			
			// Save quiz result to database
			DatabaseManager.saveQuizAttempt(userId, score);
			
			setVisible(false);
			new Score(name, score, userId); // Pass userId to Score
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		String time = "Time Remaining: " + timer + "s";
		g.setColor(new Color(244, 67, 54));
		g.setFont(new Font("Segoe UI", Font.BOLD, 18));
		int textXPosition = 1100;
		g.drawString(time, textXPosition, 350);

		if (timer > 0) {
			g.drawString(time, textXPosition, 350);
		} else {
			g.setColor(new Color(244, 67, 54));
			g.drawString("Time's Up!", textXPosition, 350);
		}

		timer--;

		try {
			Thread.sleep(1000);
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ans_given == 1) {
			ans_given = 0;
			timer = 15;
		} else if (timer < 0) {
			timer = 15;
			opt1.setEnabled(true);
			opt2.setEnabled(true);
			opt3.setEnabled(true);
			opt4.setEnabled(true);

			if (count == 8) {
				next.setEnabled(false);
				submit.setEnabled(true);
			}
			if (count == 9) {
				if (groupoptions.getSelection() == null) {
					useranswers[count][0] = "";
				} else {
					useranswers[count][0] = groupoptions.getSelection().getActionCommand();
				}

				for (int i = 0; i < useranswers.length; i++) {
					if (useranswers[i][0].equals(answers[i][1])) {
						score += 10;
					} else {
						score += 0;
					}
				}
				setVisible(false);
				new Score(name, score, userId); // Pass userId to Score
			} else {
				if (groupoptions.getSelection() == null) {
					useranswers[count][0] = "";
				} else {
					useranswers[count][0] = groupoptions.getSelection().getActionCommand();
				}
				count++;
				start(count);
				setLocationRelativeTo(null);
			}
		}
	}

	public void start(int count) {
		qno.setText("Q" + (count + 1) + ".");
		question.setText(questions[count][0]);
		opt1.setText(questions[count][1]);
		opt1.setActionCommand(questions[count][1]);

		opt2.setText(questions[count][2]);
		opt2.setActionCommand(questions[count][2]);

		opt3.setText(questions[count][3]);
		opt3.setActionCommand(questions[count][3]);

		opt4.setText(questions[count][4]);
		opt4.setActionCommand(questions[count][4]);

		groupoptions.clearSelection();
		progress.setText("Question " + (count + 1) + " of 10");
	}

	public static void main(String[] args) {
		new Quiz("User", 1); // Assuming userId 1 for now
	}
}