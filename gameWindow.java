import javax.swing.*;
import java.awt.*;

/**
 * @author Philipp Klein
 * @version 1.0
 * @since 08.04.2023
 */

public class gameWindow extends JFrame {

    // Tells the gameWindow, where the computer moved.
    // Central access: gameLogic.java

    public static String computerMove = "";

    // Saves the difficulty of being used in the game logic

    public static String difficultyStatus = "---";

    // We need to add new components here, for the 'reset' method and overall access.

    private final JButton a1 = new JButton("X");
    private final JButton a2 = new JButton("X");
    private final JButton a3 = new JButton("X");
    private final JButton b1 = new JButton("X");
    private final JButton b2 = new JButton("X");
    private final JButton b3 = new JButton("X");
    private final JButton c1 = new JButton("X");
    private final JButton c2 = new JButton("X");
    private final JButton c3 = new JButton("X");
    private final JButton reset = new JButton("Reset");

    JLabel a1_text = new JLabel("X");
    JLabel a2_text = new JLabel("X");
    JLabel a3_text = new JLabel("X");
    JLabel b1_text = new JLabel("X");
    JLabel b2_text = new JLabel("X");
    JLabel b3_text = new JLabel("X");
    JLabel c1_text = new JLabel("X");
    JLabel c2_text = new JLabel("X");
    JLabel c3_text = new JLabel("X");

    // Creating the difficulty selector.

    private JComboBox<String> difficultySelector;

    public gameWindow() {

       // Initializing the difficulty selector

        difficultySelector = new JComboBox<>(new String[] {"---","Easy","Normal","Hard"});
        difficultySelector.setBounds(405,3,100,20);

        // Difficulty selector ActionListener

        difficultySelector.addActionListener(e -> updateDifficultyStatus(difficultySelector.getSelectedItem()));

        // JButtons customization :

        reset.setBounds(200,410,100,20);
        reset.setVisible(false);

        a1.setBounds(100,100,100,100);

        a2.setBounds(200,100,100,100);

        a3.setBounds(300,100,100,100);

        b1.setBounds(100,200,100,100);

        b2.setBounds(200,200,100,100);

        b3.setBounds(300,200,100,100);

        c1.setBounds(100,300,100,100);

        c2.setBounds(200,300,100,100);

        c3.setBounds(300,300,100,100);

        // JLabels customization :

        JLabel message = new JLabel("Please select a difficulty!");
        message.setBounds(170,50,160,20);
        message.setForeground(Color.RED);
        message.setVisible(true);

        JLabel difficulty = new JLabel("Difficulty:");
        difficulty.setBounds(345,0,70,20);

        JLabel version = new JLabel("Ver. 1.0.0");
        version.setBounds(4,0,80,20);

        JLabel copyright = new JLabel("Copyright (c) Philipp Klein");
        copyright.setBounds(170,440,170,20);

        a1_text.setBounds(145,145,10,10);

        a2_text.setBounds(245,145,10,10);

        a3_text.setBounds(345,145,10,10);

        b1_text.setBounds(145,245,10,10);

        b2_text.setBounds(245,245,10,10);

        b3_text.setBounds(345,245,10,10);

        c1_text.setBounds(145,345,10,10);

        c2_text.setBounds(245,345,10,10);

        c3_text.setBounds(345,345,10,10);

        // Add new ActionListeners here :

        difficultySelector.addActionListener(e -> {
            message.setVisible(difficultySelector.getSelectedItem().equals("---"));
        });

        // When pressing the reset button,
        // the 'reset' method will be called.

        reset.addActionListener(e -> reset());

        // If a field button is pressed and the move is validated, the difficulty selector will be disabled,
        // the JButton will disappear, and the game logic will be told to realize the move of the player and let the computer play.
        // At the end, the game window will update, to show the movement by the computer.

        a1.addActionListener(e -> {
            if ( validateMove() ) {
                difficultySelector.setEnabled(false);
                a1.setVisible(false);
                gameLogic.move(0,0);
                realizeComputerMove();
            }
        });

        a2.addActionListener(e -> {
            if ( validateMove() ) {
                difficultySelector.setEnabled(false);
                a2.setVisible(false);
                gameLogic.move(1,0);
                realizeComputerMove();
            }
        });

        a3.addActionListener(e -> {
            if ( validateMove() ) {
                difficultySelector.setEnabled(false);
                a3.setVisible(false);
                gameLogic.move(2,0);
                realizeComputerMove();
            }
        });

        b1.addActionListener(e -> {
            if ( validateMove() ) {
                difficultySelector.setEnabled(false);
                b1.setVisible(false);
                gameLogic.move(0,1);
                realizeComputerMove();
            }
        });

        b2.addActionListener(e -> {
            if ( validateMove() ) {
                difficultySelector.setEnabled(false);
                b2.setVisible(false);
                gameLogic.move(1,1);
                realizeComputerMove();
            }
        });

        b3.addActionListener(e -> {
            if ( validateMove() ) {
                difficultySelector.setEnabled(false);
                b3.setVisible(false);
                gameLogic.move(2,1);
                realizeComputerMove();
            }
        });

        c1.addActionListener(e -> {
            if ( validateMove() ) {
                difficultySelector.setEnabled(false);
                c1.setVisible(false);
                gameLogic.move(0,2);
                realizeComputerMove();
            }
        });

        c2.addActionListener(e -> {
            if ( validateMove() ) {
                difficultySelector.setEnabled(false);
                c2.setVisible(false);
                gameLogic.move(1,2);
                realizeComputerMove();
            }
        });

        c3.addActionListener(e -> {
            if ( validateMove() ) {
                difficultySelector.setEnabled(false);
                c3.setVisible(false);
                gameLogic.move(2,2);
                realizeComputerMove();
            }
        });

        // Customize the JFrame:

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TicTacToe");
        this.setSize(500,500);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Adding the components

        this.add(a1);
        this.add(a2);
        this.add(a3);
        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(c1);
        this.add(c2);
        this.add(c3);
        this.add(a1_text);
        this.add(a2_text);
        this.add(a3_text);
        this.add(b1_text);
        this.add(b2_text);
        this.add(b3_text);
        this.add(c1_text);
        this.add(c2_text);
        this.add(c3_text);
        this.add(difficultySelector);
        this.add(difficulty);
        this.add(copyright);
        this.add(version);
        this.add(message);
        this.add(reset);

    }

    /**
     * This method interprets the 'computerMove' variable,
     * deactivates the JButton, which represents that field
     * and switches the text.
     */

    public void realizeComputerMove() {
        switch ( computerMove ) {
            case "a1":
                a1.setVisible(false);
                a1_text.setText("O");
                break;
            case "a2":
                a2.setVisible(false);
                a2_text.setText("O");
                break;
            case "a3":
                a3.setVisible(false);
                a3_text.setText("O");
                break;
            case "b1":
                b1.setVisible(false);
                b1_text.setText("O");
                break;
            case "b2":
                b2.setVisible(false);
                b2_text.setText("O");
                break;
            case "b3":
                b3.setVisible(false);
                b3_text.setText("O");
                break;
            case "c1":
                c1.setVisible(false);
                c1_text.setText("O");
                break;
            case "c2":
                c2.setVisible(false);
                c2_text.setText("O");
                break;
            case "c3":
                c3.setVisible(false);
                c3_text.setText("O");
                break;
            default:
                break;

        }
        if ( gameLogic.hasWon('O') || gameLogic.moves == 9 ) {
            // If the computer has won the game is over and the
            // 'reset' button will be available.
            gameLogic.gameIsOver = true;
            reset.setVisible(true);
        } else {
            // If the computer didn't win.
            gameLogic.myTurn = true;
            ++gameLogic.moves;
        }
    }

    /**
     * This method resets the game window by showing all buttons again,
     * resetting all the texts, the 'computerMove' variable and the game logic.
     */

    private void reset() {
        difficultySelector.setEnabled(true);
        reset.setVisible(false);
        a1_text.setText("X");
        a2_text.setText("X");
        a3_text.setText("X");
        b1_text.setText("X");
        b2_text.setText("X");
        b3_text.setText("X");
        c1_text.setText("X");
        c2_text.setText("X");
        c3_text.setText("X");
        a1.setVisible(true);
        a2.setVisible(true);
        a3.setVisible(true);
        b1.setVisible(true);
        b2.setVisible(true);
        b3.setVisible(true);
        c1.setVisible(true);
        c2.setVisible(true);
        c3.setVisible(true);
        computerMove = "";
        gameLogic.reset();
    }

    /**
     * Updates the difficultyStatus, when changed
     *
     * @param o difficulty
     */

    private void updateDifficultyStatus(Object o) {
        difficultyStatus = (String) o;
    }

    /**
     * Checks if an acceptable difficulty was selected and if it's my (player) turn.
     *
     * @return validation
     */

    private boolean validateMove() {
        return !(difficultyStatus.equals("---")) && gameLogic.myTurn && !gameLogic.gameIsOver;
    }

}
