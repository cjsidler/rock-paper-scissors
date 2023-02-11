package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String[] options;

        // Greet user
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        System.out.printf("Hello, %s%n", playerName);

        // Get user's score from rating.txt if it exists
        String pathToFile = "rating.txt";
        File file = new File(pathToFile);
        int playerScore = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String playerAndScore = fileScanner.nextLine();
                String[] splitPlayerAndScore = playerAndScore.split(" ");
                if (splitPlayerAndScore[0].equals(playerName)) {
                    playerScore = Integer.parseInt(splitPlayerAndScore[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Get custom options (if any) and start the game
        String customOptionLine = scanner.nextLine();

        if (customOptionLine.isEmpty()) {
            // options = new String[]{"rock", "scissors", "paper"};
            options = new String[]{"rock", "paper", "scissors"};
        } else {
            options = customOptionLine.split(",");
        }

        System.out.println("Okay, let's start");

        while (true) {
            String playerChoice = scanner.nextLine();
            String computerChoice = options[random.nextInt(options.length)];

            if (playerChoice.equals("!exit")) {
                System.out.println("Bye!");
                return;
            }
            if (playerChoice.equals("!rating")) {
                System.out.printf("Your rating: %s%n", playerScore);
                continue;
            }
            if (playerChoice.equals(computerChoice)) {
                System.out.printf("There is a draw (%s)%n", playerChoice);
                playerScore += 50;
            } else {
                // Find the index of the player's selection if it exists in the options array
                int playerChoiceIndex = -1;
                for (int i = 0; i < options.length; i++) {
                    if (options[i].equals(playerChoice)) {
                        playerChoiceIndex = i;
                        break;
                    }
                }

                // Determine if player choice a non-existing choice
                if (playerChoiceIndex == -1) {
                    System.out.println("Invalid input");
                    break;
                }

                // Build array of opponent's possible selections
                String[] opponentOptions = new String[options.length - 1];
                int oppOptionIndex = 0;
                while (oppOptionIndex < opponentOptions.length) {
                    playerChoiceIndex++;
                    if (playerChoiceIndex >= options.length) {
                        playerChoiceIndex = 0;
                    }
                    opponentOptions[oppOptionIndex] = options[playerChoiceIndex];
                    oppOptionIndex++;
                }

                // Find the index of the computers choice in the opponentOptions array
                int computerChoiceIndex = 0;
                while (computerChoiceIndex < opponentOptions.length) {
                    if (opponentOptions[computerChoiceIndex].equals(computerChoice)) {
                        break;
                    }
                    computerChoiceIndex++;
                }

                // If it is in the first half, computer loses
                // If it is in the second half, computer wins
                int halfMark = opponentOptions.length / 2;
                if (computerChoiceIndex >= halfMark) {
                    System.out.printf("Well done. The computer chose %s and failed%n", opponentOptions[computerChoiceIndex]);
                    playerScore += 100;
                } else {
                    System.out.printf("Sorry, but the computer chose %s%n", opponentOptions[computerChoiceIndex]);
                }
            }
        }

    }
}
