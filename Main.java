import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedReader bufferedReader = new BufferedReader(new FileReader("Movies.txt"));

        String line = bufferedReader.readLine();
        String[] lineArr;
        int ranking = 0;
        String title = null;
        int year = 0;
        int stockQuantity = 0;
        MovieTree movieTree = new MovieTree();
        while (line != null) {
            lineArr = line.split(",");
            for (int i = 0; i < lineArr.length; i++) {
                if (i == 0) ranking = Integer.parseInt(lineArr[i]);
                if (i == 1) title = lineArr[i];
                if (i == 2) year = Integer.parseInt(lineArr[i]);
                if (i == 3) stockQuantity = Integer.parseInt(lineArr[i]);
            }
            movieTree.addMovieNode(ranking, title, year, stockQuantity);
            line = bufferedReader.readLine();
        }
        char userInput = ' ';
        while (userInput != '6') {
            // Display menu
            System.out.println("======Main Menu======");
            System.out.println("1. Find a movie");
            System.out.println("2. Rent a movie");
            System.out.println("3. Print the inventory");
            System.out.println("4. Delete a movie");
            System.out.println("5. Count the movies");
            System.out.println("6. Quit\n");
            System.out.println("What would you like to do?");

            userInput = scanner.next().charAt(0);
            switch (userInput) {
                case '1':
                    scanner = new Scanner(System.in);
                    while (true) {
                        try {
                            System.out.print("Enter title: ");
                            String movieToFind = scanner.nextLine();
                            System.out.print("\n");
                            movieTree.findMovie(movieToFind);
                            System.out.println();
                            break;
                        }
                        catch (StringIndexOutOfBoundsException e) {
                            System.out.println("ERROR: You did not enter anything\n");
                        }
                    }
                    break;
                case '2':
                    scanner = new Scanner(System.in);
                    while (true) {
                        try {
                            System.out.println("Please enter the name of the movie you would like to rent:");
                            String movieToRent = scanner.nextLine();
                            movieTree.rentMovie(movieToRent);
                            System.out.println();
                            break;
                        }
                        catch (StringIndexOutOfBoundsException e) {
                            System.out.println("ERROR: You did not enter anything\n");
                        }
                    }
                    break;
                case '3':
                    movieTree.printMovieInventory();
                    break;
                case '4':
                    scanner = new Scanner(System.in);
                    while (true) {
                        try {
                            System.out.println("Please enter the name of the movie you would like to delete:");
                            String movieToDelete = scanner.nextLine();
                            movieTree.deleteMovieNode(movieToDelete);
                            System.out.println();
                            break;
                        }
                        catch (StringIndexOutOfBoundsException e) {
                            System.out.println("ERROR: You did not enter anything\n");
                        }
                    }
                    break;
                case '5':
                    movieTree.countMovieNodes();
                    break;
                case '6':
                    System.out.println("\nGoodbye!");
                    return;
            }
        }
    }
}
