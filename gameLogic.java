/**
 * @author Philipp Klein
 * @version 1.0
 * @since 08.12.2023
 */

public class gameLogic {

    // Game Board
    private static char[][] board = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};

    // Movement variables to determine, how close the player is to win.
    private static byte a_horizontal,b_horizontal,c_horizontal,a1_vertical,a2_vertical,a3_vertical,a1_cross,c1_cross = 0;

    // Is it the players turn?
    public static boolean myTurn = true;

    // Total amount of moves per game.
    public static int moves = 0;

    // Stops the game if the computer/player has won, or if it's a tie.
    public static boolean gameIsOver = false;

    /**
     * This method checks if the player or computer has won.
     * Each player is represented by their symbol: 'X' or 'O'.
     *
     * @param c Player symbol
     * @return status of a win for the player
     */

    public static boolean hasWon(char c) {
                // Vertical Check
        return  ((board[0][0] == c) && (board[0][1] == c) && (board[0][2] == c)) ||
                ((board[1][0] == c) && (board[1][1] == c) && (board[1][2] == c)) ||
                ((board[2][0] == c) && (board[2][1] == c) && (board[2][2] == c)) ||
                // Horizontal Check
                ((board[0][0] == c) && (board[1][0] == c) && (board[2][0] == c)) ||
                ((board[0][1] == c) && (board[1][1] == c) && (board[2][1] == c)) ||
                ((board[0][2] == c) && (board[1][2] == c) && (board[2][2] == c)) ||
                // Cross-Check
                ((board[0][0] == c) && (board[1][1] == c) && (board[2][2] == c)) ||
                ((board[0][2] == c) && (board[1][1] == c) && (board[2][0] == c));
    }

    /**
     * This method resets the movement variables, the game board,
     * the gameIsOver to 'false',the total moves to 0 and myTurn to 'true'.
     */

    public static void reset() {
        a_horizontal = b_horizontal = c_horizontal = a1_vertical = a2_vertical = a3_vertical = a1_cross = c1_cross = 0;
        board = new char[][] {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        gameIsOver = false;
        moves = 0;
        myTurn = true;
    }

    /**
     * This method realizes the player's movements on the board.
     * It also updates the movement variables, in respect to the x and y coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */

    public static void move(int x, int y) {
        // Saving the move to the board and negate myTurn, because it's the computers turn
        board[y][x] = 'X';
        myTurn = false;
        ++moves;
        // Updating the movement variables for Y
        switch (y) {
            case 0:
                ++a_horizontal;
                break;
            case 1:
                ++b_horizontal;
                break;
            case 2:
                ++c_horizontal;
                break;
            default:
                break;
        }
        // Updating the movement variables for X
        switch (x) {
            case 0:
                if ( 0 == y ) ++a1_cross;
                if ( 2 == y ) ++c1_cross;
                ++a1_vertical;
                break;
            case 1:
                if ( 1 == y ) { ++a1_cross; ++c1_cross; }
                ++a2_vertical;
                break;
            case 2:
                if ( 0 == y ) ++c1_cross;
                if ( 2 == y ) ++a1_cross;
                ++a3_vertical;
            default:
                break;
        }
        if ( hasWon('X') || moves == 9 ) {
            // If the player has won or the game is finished.
            gameIsOver = true;
            moves = 9;
        } else if ( gameWindow.difficultyStatus.equals("Easy") && !gameIsOver ) {
            // If the difficulty is set to 'Easy'. 50% randomMove 50% controlled movements
            if ( new java.util.Random().nextBoolean() ) {
                makeRandomMove();
            } else {
                limitPlayerMovement();
            }
        } else if ( gameWindow.difficultyStatus.equals("Normal") && !gameIsOver ) {
            // If the difficulty is set to 'Normal'.
            if ( new java.util.Random().nextBoolean() ) {
                limitPlayerMovement();
            } else {
                makeWinningMoveIfPossible();
            }
        } else if ( !gameIsOver ) {
            // If the difficulty is set to 'Hard'.
            makeWinningMoveIfPossible();
        }
    }

    /**
     * This method will fill the last spot in a horizontal line.
     *
     * @param line y-coordinate, between 0 and 2.
     */

    private static void fillHorizontal(int line) {
        for ( byte x = 0 ; x < 3 ; ++x ) {
            if ( board[line][x] == ' ' ) {
                // Making the move and telling it to the game window, to mark the spot
                board[line][x] = 'O';
                gameWindow.computerMove = ( 0 == line ? "a" : ( 1 == line ? "b" : "c" ) ) + ( x + 1 );
                // Resetting the movements variables
                resetMovementVariables(line,x);
                break;
            }
        }
    }

    /**
     * This method will fill the last spot in a vertical line.
     *
     * @param line x-coordinate, between 0 and 2.
     */

    private static void fillVertical(int line) {
        for ( byte y = 0 ; y < 3 ; ++y ) {
            if ( board[y][line] == ' ' ) {
                board[y][line] = 'O';
                gameWindow.computerMove = ( 0 == y ? "a" : ( 1 == y ? "b" : "c" ) ) + ( line + 1 );
                // Resetting the movements variables
                resetMovementVariables(y,line);
                break;
            }
        }
    }

    /**
     * This method will fill the last spot in a cross line. Since there are
     * only two crosses available, they will be differentiated between 0 and 1.
     *
     * @param cross cross-code, either 0 or 1.
     */

    private static void fillCross(int cross) {
        // This method will fill the last spot in a cross line, to
        // prevent the player from winning. Since there are only two
        // crosses available, they will be differentiated between 0 and 1.
        if ( 0 == cross ) {
            // If cross code is 0, the a1_cross is meant
            for ( byte x = 0 ; x < 3 ; ++x ) {
                if ( board[x][x] == ' ' ) {
                    board[x][x] = 'O';
                    gameWindow.computerMove = ( 0 == x ? "a1" : ( 1 == x ? "b2" : "c3" ) );
                    resetMovementVariables(x,x);
                    break;
                }
            }
        } else {
            // The only cross left, is the c1_cross
            for ( byte x = 0 ; x < 3 ; ++x ) {
                if ( board[2 - x][x] == ' ' ) {
                    board[2 - x][x] = 'O';
                    gameWindow.computerMove = ( 0 == x ? "c1" : ( 1 == x ? "b2" : "a3" ) );
                    resetMovementVariables(2 - x,x);
                    break;
                }
            }
        }
    }

    /**
     * This method interprets the moving variables. If a variable
     * exceeds 2, the computer makes a placement in that line, to
     * prevent the player from winning in the next move.
     */

    private static void opponentPossibleWinInterfere() {
        if ( 1 == moves && ( gameWindow.difficultyStatus.equals("Hard") ) ) {
            firstMove();
        } else if ( 2 == a_horizontal ) {
            fillHorizontal(0);
        } else if ( 2 == b_horizontal ) {
            fillHorizontal(1);
        } else if ( 2 == c_horizontal ) {
            fillHorizontal(2);
        } else if ( 2 == a1_vertical ) {
            fillVertical(0);
        } else if ( 2 == a2_vertical ) {
            fillVertical(1);
        } else if ( 2 == a3_vertical ) {
            fillVertical(2);
        } else if ( 2 == a1_cross ) {
            fillCross(0);
        } else if ( 2 == c1_cross ) {
            fillCross(1);
        } else {
            // If no danger was found, the computer will limit the player's movements.
            limitPlayerMovement();
        }
    }

    /**
     * This method 'resets' the moving variables. Depending on the placement of the computer
     * the variables will be decreased, so the computer won't need to look at that line again (no danger left).
     * It can even help to detect lines, where the computer could win in the next move.
     *
     * @param y y-coordinate
     * @param x x-coordinate
     */

    private static void resetMovementVariables(int y, int x) {
        // This method reverts the effect of the move method, so that
        // the 'opponentPossibleWinInterfere' method doesn't fire at the same line again.

        // Updating the movement variables for Y
        switch (y) {
            case 0:
                --a_horizontal;
                break;
            case 1:
                --b_horizontal;
                break;
            case 2:
                --c_horizontal;
                break;
            default:
                break;
        }
        // Updating the movement variables for X
        switch (x) {
            case 0:
                if ( 0 == y ) --a1_cross;
                if ( 2 == y ) --c1_cross;
                --a1_vertical;
                break;
            case 1:
                if ( 1 == y ) { --a1_cross; --c1_cross; }
                --a2_vertical;
                break;
            case 2:
                if ( 0 == y ) --c1_cross;
                if ( 2 == y ) --a1_cross;
                --a3_vertical;
            default:
                break;
        }
    }

    /**
     * With this method, if no direct danger is found by the 'opponentPossibleWinInterfere' method,
     * the computer will make his moves, where the player's moves will be limited by the highest amount.
     * Such spots e.g. are the corners and the middle of the board.
     */

    private static void limitPlayerMovement() {
        if ( board[0][0] == 'X' && board[2][2] == 'X' ) {
            board[2][1] = 'O';
            resetMovementVariables(2, 1);
            gameWindow.computerMove = "c2";
        } else if ( board[1][1] == ' ' ) {
            board[1][1] = 'O';
            resetMovementVariables(1, 1);
            gameWindow.computerMove = "b2";
        } else if ( board[0][0] == ' ' ) {
            board[0][0] = 'O';
            resetMovementVariables(0,0);
            gameWindow.computerMove = "a1";
        } else if ( board[2][2] == ' ' ) {
            board[2][2] = 'O';
            resetMovementVariables(2,2);
            gameWindow.computerMove = "c3";
        } else if ( board[0][2] == ' ' ) {
            board[0][2] = 'O';
            resetMovementVariables(0,2);
            gameWindow.computerMove = "a3";
        } else if ( board[2][0] == ' ' ) {
            board[2][0] = 'O';
            resetMovementVariables(2,0);
            gameWindow.computerMove = "c1";
        } else {
            makeRandomMove();
        }
    }

    /**
     * As stated before, the movement variables can tell if, in the next move,
     * the computer could win the game. In this method, if a movement variable == -2,
     * the computer will make the move in that line.
     *
     * Why -2? If the player made a move in that line, the 'move' method would
     * increase the variable, thus being -1. If the computer makes the move, the
     * variable will be decreased. So we have two 'O' in one line.
     */

    private static void makeWinningMoveIfPossible() {
        if ( -2 == a_horizontal ) {
            fillHorizontal(0);
        } else if ( -2 == b_horizontal ) {
            fillHorizontal(1);
        } else if ( -2 == c_horizontal ) {
            fillHorizontal(2);
        } else if ( -2 == a1_vertical ) {
            fillVertical(0);
        } else if ( -2 == a2_vertical ) {
            fillVertical(1);
        } else if ( -2 == a3_vertical ) {
            fillVertical(2);
        } else if ( -2 == a1_cross ) {
            fillCross(0);
        } else if ( -2 == c1_cross ) {
            fillCross(1);
        } else {
            opponentPossibleWinInterfere();
        }
    }

    /**
     * This method makes a random move.
     */

    private static void makeRandomMove() {
        byte x = (byte) new java.util.Random().nextInt(3);
        byte y = (byte) new java.util.Random().nextInt(3);
        while ( board[y][x] == 'X' || board[y][x] == 'O' ) {
            x = (byte) new java.util.Random().nextInt(3);
            y = (byte) new java.util.Random().nextInt(3);
        }
        board[y][x] = 'O';
        resetMovementVariables(y,x);
        gameWindow.computerMove = ( 0 == y ? "a" : ( 1 == y ? "b" : "c" ) ) + ( x + 1 );
    }

    /**
     * This method places the first move on the board. The first move is the most
     * critical, especially if the difficulty is set high.
     */

    private static void firstMove() {
        if ( board[0][0] == 'X' || board[2][0] == 'X' || board[0][2] == 'X'|| board[2][2] == 'X' ) {
            // If the player placed at a corner
            resetMovementVariables(1,1);
            gameWindow.computerMove = "b2";
            board[1][1] = 'O';
        } else if ( board[2][1] == 'X' ) {
            // If the player placed at C2
            resetMovementVariables(2,0);
            gameWindow.computerMove = "c1";
            board[2][0] = 'O';
        } else if ( board[0][1] == 'X' || board[1][0] == 'X' ) {
            // If the placer placed at A2 or B1
            resetMovementVariables(0,0);
            gameWindow.computerMove = "a1";
            board[0][0] = 'O';
        } else {
            // Any other case
            resetMovementVariables(0,2);
            gameWindow.computerMove = "a3";
            board[0][2] = 'O';
        }
    }

}
