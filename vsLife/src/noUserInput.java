
// import java.awt.Color;
//import java.awt.Font;
import java.awt.Graphics;
//import java.awt.Graphics2D;
// import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
// import javax.swing.BorderFactory;
// import javax.swing.JButton;
// import java.awt.event.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.util.Random;

public class noUserInput extends JPanel implements ActionListener {
    private Timer timer;

    static JLabel test;

    // private final int CELL_SIZE = 25;

    private int x = 0;
    private int y = 0;

    boolean[][] board = new boolean[32][32];

    private static boolean gameOn = false;

    public noUserInput() {

        for (int r = 0; r < 32; r++) {
            for (int c = 0; c < 32; c++) {
                board[r][c] = false;
            }
        }
        board[10][10] = true;
        board[11][11] = true;
        board[12][11] = true;
        board[12][10] = true;
        board[12][9] = true;

        timer = new Timer(500, this);
        timer.start();

    }

    /************* PAINTING METHODS *************/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawSquare(g);

    }

    private void drawGrid(Graphics g) {
        for (int i = 0; i < 32; i++) {
            g.drawLine(i, (25 * i + 25), 800, (25 * i + 25));
            g.drawLine((25 * i + 25), i, (25 * i + 25), 800);

        }
    }

    private void drawSquare(Graphics g) {
        for (int r = 0; r < 32; r++) {
            for (int c = 0; c < 32; c++) {
                if (board[r][c]) {
                    g.fillRect(r * 25, c * 25, 25, 25);
                }
            }
        }
    }

    /************* GAME LOGIC *************/
    // public void flow() {
    // boolean[][] retArr = new boolean[board.length][board[0].length];
    // for (int r = 0; r < board.length; r++) {
    // for (int c = 0; c < board[0].length; c++) {
    // retArr[r][c] = isLive(board, r, c);
    // }
    // }
    // board = retArr;
    // //repaint();
    // }

    // public boolean isLive(boolean[][] arr, int row, int col) {
    // int count = 0;
    // for (int r = row - 1; row < row + 2; row++) {
    // for (int c = col - 1; col < col + 2; col++) {
    // if (arr[r][c]) {
    // count++;
    // }
    // }
    // }

    // if (arr[row][col]) {
    // count--;
    // if (count < 2 || count > 3) {
    // return false;
    // }
    // if (count == 2 || count == 3) {
    // return true;
    // }
    // }
    // if (!arr[row][col]) {
    // if (count == 3) {
    // return true;
    // }
    // }
    // return false;
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean[][] retArr = new boolean[board.length][board[0].length];
        for (int r = 1; r < board.length-1; r++) {
            for (int c = 1; c < board[0].length-1; c++) {
                int count = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int w = -1; w <= 1; w++) {
                        if (board[r + i][c + w]) {
                            count++;
                        }
                    }
                }
                if (board[r][c]) {
                    count--;
                    if (count < 2 || count > 3) {
                        retArr[r][c] = false;
                    } else if (count == 2 || count == 3) {
                        retArr[r][c] = true;
                    }
                } else if (!board[r][c]) {
                    if (count == 3) {
                        retArr[r][c] = true;
                    }
                } else {
                    retArr[r][c] = false;
                }

                /* random test */
                // Random rand = new Random();
                // retArr[r][c] = rand.nextBoolean();

                /* invert */
                // retArr[r][c] = !retArr[r][c];

                /* checkers */
                // if (r % 2 == 0 && c % 2 == 0) {
                // retArr[r][c] = true;
                // }

                // print for testing
                for (boolean b : retArr[r]) {
                    System.out.println(b);
                }
            }
        }

        // retArr[7][7] = true;
        board = retArr;

        repaint();
    }

    /************* MAIN METHOD *************/
    public static void main(String[] args) {
        JFrame frame = new JFrame("noUserInput");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        noUserInput mainFrame = new noUserInput();
        frame.add(mainFrame);
        // mainFrame.add(test);
        frame.setVisible(true);
        // mainFrame.flow();
        // mainFrame.repaint();
        // try {
        // Thread.sleep(150);
        // String[] a = new String[1];
        // main(a);
        // } catch (InterruptedException ex) {}
    }
}