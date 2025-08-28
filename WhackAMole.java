
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class WhackAMole {
    int boardWidth = 600;
    int boardLength = 600;

    JFrame frame = new JFrame("Whack a Mole");
    JLabel textLabel = new JLabel();
    JLabel scoreBoard = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon marioIcon;
    ImageIcon tryAgain;

    JButton currMarioTileOne;
    JButton currMarioTileTwo;
    JButton currMoleTile;
    JButton reset;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setMarioTimerOne;
    Timer setMarioTimerTwo;
    int score = 0;
    int highScore;

    WhackAMole () {
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardLength);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.LEFT);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        scoreBoard.setFont(new Font("Arial", Font.BOLD, 20));
        scoreBoard.setHorizontalAlignment(JLabel.RIGHT);
        scoreBoard.setText("HighScore: " + highScore);
        scoreBoard.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        scorePanel.add(scoreBoard);
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(scoreBoard, BorderLayout.SOUTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        //boardPanel.setBackground(Color.black);
        frame.add(boardPanel);

        //marioIcon = new ImageIcon(getClass().getResource("./Mario.png"));
        Image marioImg = new ImageIcon(getClass().getResource("./Mario.png")).getImage();
        marioIcon = new ImageIcon(marioImg.getScaledInstance(130, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./Mole-removebg-preview.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image againImg = new ImageIcon(getClass().getResource("./tryAgain.png")).getImage();
        tryAgain = new ImageIcon(againImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        score = 0;

        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            //tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (tile == currMoleTile) {
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    }
                    else if (tile == currMarioTileOne || tile == currMarioTileTwo) {
                        textLabel.setText("Game Over: " + Integer.toString(score));
                        setMoleTimer.stop();
                        setMarioTimerOne.stop();
                        setMarioTimerTwo.stop();
                        if (score > highScore) {
                            highScore = score;
                            scoreBoard.setText("High Score: " + highScore);
                        }
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);
                        }
                        board[4].setEnabled(true);
                        board[4].setIcon(null);
                        currMoleTile = null;
                        currMarioTileOne = null;
                        currMarioTileTwo = null;
                        tile = board[4];
                        reset = tile;
                        reset.setIcon(tryAgain);
                    }
                    else if (tile == reset) {
                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(true);
                            board[i].setIcon(null);
                        }
                        setMarioTimerOne.start();
                        setMoleTimer.start();
                        setMarioTimerTwo.start();
                        score = 0;
                        textLabel.setText("Score: 0");
                        reset = null;
                    }
                    
                }
            });
        }

        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //gets rid of the mole from the current tile
                if (currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }
                
                //randomly picks a tile to put the mole
                int num = random.nextInt(9);
                JButton tile = board[num];

                //makes sure the tile isnt occupied 
                if (currMarioTileOne == tile || currMarioTileTwo == tile) return;

                //sets the mole img onto the tile
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });
        
        setMarioTimerOne = new Timer(800, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //gets rid of the mario from the current tile
                if (currMarioTileOne != null) {
                    currMarioTileOne.setIcon(null);
                    currMarioTileOne = null;
                }
                
                //randomly picks a tile to put mario
                int num = random.nextInt(9);
                JButton tile = board[num];

                //stops the code if the tiles occupied 
                if (currMoleTile == tile || currMarioTileTwo == tile) return;

                //sets the mario img onto the tile
                currMarioTileOne = tile;
                currMarioTileOne.setIcon(marioIcon);
            }
        });
        
        setMarioTimerTwo = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //gets rid of the mario from the current tile
                if (currMarioTileTwo != null) {
                    currMarioTileTwo.setIcon(null);
                    currMarioTileTwo = null;
                }
                
                //randomly picks a tile to put mario
                int num = random.nextInt(9);
                JButton tile = board[num];

                //stops the code if the tiles occupied 
                if (currMoleTile == tile || currMarioTileOne == tile) return;

                //sets the mario img onto the tile
                currMarioTileTwo = tile;
                currMarioTileTwo.setIcon(marioIcon);
            }
        });

        setMarioTimerOne.start();
        setMarioTimerTwo.start();
        setMoleTimer.start();
        frame.setVisible(true);
    }
}
