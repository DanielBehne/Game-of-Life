import java.awt.Color;
//import java.awt.Font;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.*;

public class MainFrame extends JPanel implements ActionListener {
    private final int CELL_SIZE = 25;

    private int x = 0;
    private int y = 0;

    private boolean[][] board = new boolean[32][32];

    private static JButton startButton;

    private static boolean gameOn = false;

    public MainFrame() {
        startButton = new JButton("Start");
        startButton.setBounds(675, 25, 100, 50);
        startButton.setPreferredSize(new Dimension(100, 75));
        startButton.addActionListener(this);
        startButton.setBackground(Color.lightGray);
        startButton.setBorder(BorderFactory.createRaisedBevelBorder());
        //add(startButton);

        for (int r = 0; r < 32; r++) {
            for (int c = 0; c < 32; c++) {
                board[r][c] = false;
            }
        }

        // Add a MouseListener to capture mouse clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Update the coordinates where the mouse was clicked
                x = e.getX() / CELL_SIZE;
                y = e.getY() / CELL_SIZE;
                if (board[x][y]) {
                    board[x][y] = false;
                } else {
                    board[x][y] = true;
                }
                // Repaint the panel to trigger the paintComponent method
                repaint();
            }
        });

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
        }
        for (int i = 0; i < 32; i++) {
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

    /************* BUTTONS *************/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startButton.setText("Stop");
            gameOn = true;
            flow(board);
            while (gameOn) {
                boolean[][] newBoard = flow(board);
                board = newBoard;
                repaint();
                // try {
                // Thread.sleep(250);
                // repaint();
                // }
                // catch(InterruptedException er) {
                // er.printStackTrace();
                // }
            }
        }
    }

    /************* MAIN METHOD *************/
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        MainFrame mainFrame = new MainFrame();
        frame.add(mainFrame);
        mainFrame.add(startButton);
        frame.setVisible(true);
    }

    /************* GAME LOGIC *************/
    public boolean[][] flow(boolean[][] arr) {
        boolean[][] retArr = new boolean[arr.length][arr[0].length];
        for (int r = 0; r < arr.length; r++) {
            for (int c = 0; c < arr[0].length; c++) {
                retArr[r][c] = isLive(arr, r, c);
            }
        }
        return retArr;
    }

    public boolean isLive(boolean[][] arr, int row, int col) {
        int count = 0;
        for (int r = row - 1; row < row + 2; row++) {
            for (int c = col - 1; col < col + 2; col++) {
                if (r == row && c == col) {
                    continue;
                }
                if (arr[r][c]) {
                    count++;
                }
            }
        }

        if (arr[row][col]) {
            if (count < 2 || count > 3) {
                return false;
            }
            if (count == 2 || count == 3) {
                return true;
            }
        }
        if (!arr[row][col]) {
            if (count == 3) {
                return true;
            }
        }
        return false;
    }
}