package Logic;

import Controls.Receive;

import java.util.Scanner;

public class TestMainForLogic {
    public static void main(String[] args) {
        Board board = new Board();
        Receive guiReceive = new Receive();
        GameManager gameManager = new GameManager(4,board, guiReceive);

        int input = 0;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Player: "+gameManager.getCurrentPlayer());
            System.out.println(board.toString());
            System.out.println(gameManager.getNumbers());
            //gameManager.throwsDice();

            System.out.print("Input: ");
            input = scanner.nextInt();

            if(input == 99){
                gameManager.throwsDice();
            }else{
                gameManager.clickOnPiece(input);
            }

            System.out.println("-------------");
        }
    }
}
