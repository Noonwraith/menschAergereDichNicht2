package Logic;

import Controls.Control;

import java.util.Scanner;

public class TestMainForLogic {
    public static void main(String[] args) {
        Board board = new Board();
        Control control = new Control(true);
        GameManager gameManager = new GameManager(4, control);

        int input = 0;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Player: "+gameManager.getCurrentPlayer());
            //System.out.println(board.toString());
            //System.out.println(gameManager.getNumbers());
            //gameManager.throwsDice();

            System.out.print("Input: ");
            input = scanner.nextInt();

            if(input == 99){
                gameManager.throwsDice(-1);
            }else{
                gameManager.clickOnPiece(input);
            }

            System.out.println("-------------");
        }
    }
}
