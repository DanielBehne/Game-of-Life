
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
import javax.swing.Timer;
//import javax.swing.JLabel;
//import java.util.Random;

public class MainFrame extends JPanel implements ActionListener {
    private final int CELL_SIZE = 25;

    private int x = 0;
    private int y = 0;

    private boolean[][] board = new boolean[32][32];

    private static JButton startButton;

    private static boolean gameOn = false;

    private Timer timer;

    public MainFrame() {
        startButton = new JButton("Start");
        startButton.setBounds(675, 25, 100, 50);
        startButton.setPreferredSize(new Dimension(100, 75));
        startButton.addActionListener(this);
        startButton.setBackground(Color.lightGray);
        startButton.setBorder(BorderFactory.createRaisedBevelBorder());
        // add(startButton);

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

        timer = new Timer(10, this);
    }

    /************* BUTTONS *************/
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            if (!gameOn) {
                startButton.setText("Stop");
                gameOn = true;
                timer.start();
            } else {
                startButton.setText("Start");
                gameOn = false;
                timer.stop();
            }

        } else if (e.getSource() == timer) {
            if (gameOn) {
                actionHelper();
                repaint();
            }
        }
    }

    public void actionHelper() {

        boolean[][] retArr = new boolean[board.length][board[0].length];
        for (int r = 1; r < board.length-1; r++) {
            for (int c = 1; c < board[0].length-1; c++) {

                /* actual game logic here eventually */
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
        // repaint();
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

}