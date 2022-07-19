import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ticTacToe extends JFrame implements ActionListener {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ticTacToe t = new ticTacToe();
	}

	public static int BOARD_SIZE = 3;

	public static enum GameStatus {
		Incomplete, X_wins, O_wins, Tie
	}

	private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];

	boolean X_Turn = true;

	public ticTacToe() {
		super.setTitle("TicTacToe");
		super.setSize(800, 800);
		GridLayout grid = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		super.setLayout(grid);
		Font font = new Font("Comic Sans", 1, 150);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				JButton button = new JButton("");
				buttons[row][col] = button;
				button.setFont(font);
				button.addActionListener(this);
				super.add(button);
			}
		}
		super.setResizable(false);
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		makeMove(clickedButton);
		GameStatus gs = this.getGameStatus();
		if (gs == GameStatus.Incomplete)
			return;

		declareWinner(gs);

		int choice = JOptionPane.showConfirmDialog(this, "Do you want to restart the game?");
		if (choice == JOptionPane.YES_OPTION) {
			for (int row = 0; row < BOARD_SIZE; row++) {
				for (int col = 0; col < BOARD_SIZE; col++) {
					buttons[row][col].setText("");
				}
			}
			X_Turn = true;
		} else {
			super.dispose();
		}

	}

	private GameStatus getGameStatus() {
		String text1 = "", text2 = "";
		int row = 0, col = 0;
		while (row < BOARD_SIZE) {
			col = 0;
			while (col < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row][col + 1].getText();
				if (!text1.equals(text2) || text1.length() == 0) {
					break;
				}
				col++;
			}
			if (col == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.X_wins;
				} else {
					return GameStatus.O_wins;
				}
			}
			row++;
		}

		// text inside cols
		col = 0;
		while (col < BOARD_SIZE) {
			row = 0;
			while (row < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row + 1][col].getText();
				if (!text1.equals(text2) || text1.length() == 0) {
					break;
				}
				row++;
			}
			if (row == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.X_wins;
				} else {
					return GameStatus.O_wins;
				}
			}
			col++;
		}

		// test in diagonal 1

		row = 0;
		col = 0;
		while (row < BOARD_SIZE - 1 && col < BOARD_SIZE - 1) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row + 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0) {
				break;
			}
			row++;
			col++;
		}

		if (row == BOARD_SIZE - 1) {
			if (text1.equals("X")) {
				return GameStatus.X_wins;
			} else {
				return GameStatus.O_wins;
			}
		}

		// test in diagonal 2
		row = BOARD_SIZE - 1;
		col = 0;
		while (row > 0 && col < BOARD_SIZE - 1) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row - 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0) {
				break;
			}
			row--;
			col++;
		}

		if (row == 0) {
			if (text1.equals("X")) {
				return GameStatus.X_wins;
			} else {
				return GameStatus.O_wins;
			}
		}

		String txt = "";
		for (row = 0; row < BOARD_SIZE; row++) {
			for (col = 0; col < BOARD_SIZE; col++) {
				txt = buttons[row][col].getText();
				if (txt.length() == 0) {
					return GameStatus.Incomplete;
				}
			}
		}

		return GameStatus.Tie;
	}

	private void declareWinner(GameStatus gs) {
		if (gs == GameStatus.X_wins) {
			JOptionPane.showMessageDialog(this, "X Wins");
		} else if (gs == GameStatus.O_wins) {
			JOptionPane.showMessageDialog(this, "O Wins");
		} else {
			JOptionPane.showMessageDialog(this, "It's a tie !");
		}
	}

	private void makeMove(JButton clickedButton) {
		String btntext = clickedButton.getText();
		if (btntext.length() > 0) {
			JOptionPane.showMessageDialog(this, "Invalid Move");
		} else {
			if (X_Turn) {
				clickedButton.setText("X");
			} else {
				clickedButton.setText("O");
			}
			X_Turn = !X_Turn;
		}
	}

}