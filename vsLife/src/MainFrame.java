import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.BorderLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JPanel {
    private final int CELL_SIZE = 25;
    
    private int x = 0;
    private int y = 0;
    
    private boolean[][] board = new boolean[32][32];
    
    JButton startButton;
    

    public MainFrame() {
        startButton = new JButton();
        startButton.setBounds(100,100,100,100);
        
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
                x = e.getX()/CELL_SIZE;
                y = e.getY()/CELL_SIZE;
                if (board[x][y]) {
                    board[x][y] = false;
                } else {
                    board[x][y] = true;
                }
                // Repaint the panel to trigger the paintComponent method
                repaint();
            }
        });

        this.add(startButton);

    }
    
    public void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setSize(500,500);
        setVisible(true);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Graphics2D g2d = (Graphics2D) g;
        drawGrid(g);
        
        drawSquare(g);

    }




/*************PAINTING METHODS*************/    
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
                    g.fillRect(r*25, c*25, 25, 25);
                }
            }
        }
    }





/*************MAIN METHOD*************/    
    public static void main(String[] args) {
        JFrame frame = new JFrame("example");
        MainFrame mainFrame = new MainFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.add(mainFrame);
        frame.setVisible(true);
    }
}