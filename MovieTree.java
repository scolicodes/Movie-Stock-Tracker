import java.util.ArrayList;

public class MovieTree
{
    private class MovieNodeBST{
        private static class MovieNodeLL{
            public int ranking;
            public String title;
            public int year;
            public int quantity;
            public MovieNodeLL next;
            public MovieNodeLL(){}
            public MovieNodeLL(int in_ranking, String in_title, int in_year, int
                    in_quantity) {
                ranking = in_ranking;
                title = in_title;
                year = in_year;
                quantity = in_quantity;
                next = null;
            }
        }
        public char letter;
        public MovieNodeBST parent;
        public MovieNodeBST leftChild;
        public MovieNodeBST rightChild;
        public MovieNodeLL head;
        public MovieNodeBST(){}
        public MovieNodeBST(char in_letter) {
            letter = in_letter;
            parent = null;
            leftChild = null;
            rightChild = null;
            head = null;
        }
    }
    public MovieNodeBST root;
    public MovieTree() {
        // Your code here
    }
    public void printMovieInventory() {
        // Your code here
        printMovieInventory(root);
    }
    private int countMovieNodes(MovieNodeBST node) {
        if (node == null) {
            return 0;
        }
        else {
            return countMovieNodes(node.leftChild) + countLLNodes(node) + countMovieNodes(node.rightChild);
        }
    }
    public void countMovieNodes() {
        System.out.println("\nTree contains: " + countMovieNodes(root) + " movies." + "\n");
    }
    private int countLLNodes(MovieNodeBST key) {
        if (key.head == null) {
            return 0;
        }
        else {
            int counter = 0;
            MovieNodeBST.MovieNodeLL currNode = key.head;
            while (currNode != null) {
                counter += 1;
                currNode = currNode.next;
            }
            return counter;
        }

    }
    public void deleteMovieNode(String title) {
        MovieNodeBST key = searchBST(root, title);
        if (key == null || searchLL(key.head, title) == null) {
            System.out.println("\nMovie not found");
        }
        else {
            System.out.println("\nDeleting: " + title);
            if (key.head.title.equals(title)) {
                key.head = key.head.next;
            }
            else {
                MovieNodeBST.MovieNodeLL currNode = key.head;
                while (currNode != null && currNode.next != null) {
                    if (currNode.next.title.equals(title)) {
                        currNode.next = currNode.next.next;
                    }
                    currNode = currNode.next;
                }
            }
            if (key.head == null) {
                root = DeleteAll(root, key.letter);
            }
        }
    }
    private MovieNodeBST addKeyToBST(MovieNodeBST node, MovieNodeBST newNode) {
        if (node == null) {
            return newNode;
        }
        else if (newNode.letter < node.letter) {
            node.leftChild = addKeyToBST(node.leftChild, newNode);
        }
        else if (newNode.letter > node.letter) {
            node.rightChild = addKeyToBST(node.rightChild, newNode);
        }
        return node;
    }
    private void addNodeToLL(MovieNodeBST key, int ranking, String title, int releaseYear, int quantity)  {
        MovieNodeBST.MovieNodeLL currLLNode = null;
        MovieNodeBST.MovieNodeLL nodeToAdd = new MovieNodeBST.MovieNodeLL(ranking, title, releaseYear, quantity);
        if (key.head == null) {
            key.head = nodeToAdd;
        }
        else {
            nodeToAdd.next = null;
            currLLNode = key.head;
            while (currLLNode.next != null) {
                currLLNode = currLLNode.next;
            }
            currLLNode.next = nodeToAdd;
        }
    }
    public void addMovieNode(int ranking, String title, int releaseYear, int quantity) {
        // Your code here
        MovieNodeBST key = searchBST(root, title);
        // If the initial letter of the title is not in the BST, then insert BST node containing
        // that letter
        if (key == null) {
            key = new MovieNodeBST(title.charAt(0));
            root = addKeyToBST(root, key);
        }
        // Append movie data to end of linked list to which the BST node points
        addNodeToLL(key, ranking, title, releaseYear, quantity);

    }
    public void findMovie(String title) {
        MovieNodeBST key = searchBST(root, title);
        if (key == null || searchLL(key.head, title) == null) {
            System.out.println("Movie not found.");
        }
        else {
            MovieNodeBST.MovieNodeLL foundMovie = searchLL(key.head, title);
            System.out.print("Movie Info:\n");
            System.out.print("===========\n");
            System.out.print("Ranking: " + foundMovie.ranking + "\n");
            System.out.print("Title: " + foundMovie.title + "\n");
            System.out.print("Year: " + foundMovie.year + "\n");
            System.out.print("Quantity: " + foundMovie.quantity + "\n");
        }

    }
    public void rentMovie(String title) {
        MovieNodeBST key = searchBST(root, title);
        if (key == null || searchLL(key.head, title) == null) {
            System.out.println("\nMovie not found.");
        }
        else {
            MovieNodeBST.MovieNodeLL foundMovie = searchLL(key.head, title);
            foundMovie.quantity -= 1;

            System.out.println("\nMovie has been rented.\n");
            System.out.print("Movie Info:\n");
            System.out.print("===========\n");
            System.out.print("Ranking:" + foundMovie.ranking + "\n");
            System.out.print("Title:" + foundMovie.title + "\n");
            System.out.print("Year:" + foundMovie.year + "\n");
            System.out.print("Quantity:" + foundMovie.quantity + "\n");

            if (foundMovie.quantity == 0) {
                deleteMovieNode(title);
            }
        }
    }
    private MovieNodeBST DeleteAll(MovieNodeBST node, char keyToDelete) {
        //use this for the post-order traversal deletion of the tree
        if (node == null) {
            return null;
        }
        else if (node.letter == keyToDelete) {
            // Node to delete has 2 children
            if (node.leftChild != null && node.rightChild != null) {
                MovieNodeBST largestNode = treeMaximum(node.leftChild);
                if (node.parent == null) { // Deleting root
                    MovieNodeBST temp = node.rightChild;
                    node = node.leftChild;
                    largestNode.rightChild = temp;
                }
                else { // not deleting root
                    if (node == node.parent.leftChild) {
                        node.parent.leftChild = node.leftChild;
                    }
                    else {
                        node.parent.rightChild = node.leftChild;
                    }
                    largestNode.rightChild = node.rightChild; // ?
                }
            }
            else if (node.leftChild != null || node.rightChild != null) {
                // Node to delete has one child
                if (node.parent == null) { // Deleting root
                    if (node.leftChild != null) {
                        node = node.leftChild;
                    }
                    else {
                        node = node.rightChild;
                    }
                }
                else {
                    if (node == node.parent.leftChild) {
                        if (node.leftChild != null) {
                            node.parent.leftChild = node.leftChild;
                        }
                        else {
                            node.parent.leftChild = node.rightChild;
                        }
                    }
                    else {
                        if (node.leftChild != null) {
                            node.parent.rightChild = node.leftChild;
                        }
                        else {
                            node.parent.leftChild = node.rightChild;
                        }
                    }
                }
            }
            else {
                if (node.parent == null) { // Deleting root
                   node = null;
                }
                else {
                    if (node == node.parent.leftChild) {
                        node.parent.leftChild = null;
                    }
                    else {
                        node.parent.rightChild = null;
                    }
                }
            }
        }
        else if (keyToDelete > node.letter) {
            node.rightChild = DeleteAll(node.rightChild, keyToDelete);
        }
        else {
            node.leftChild = DeleteAll(node.leftChild, keyToDelete);
        }
        return node;
    }
    private void printMovieInventory(MovieNodeBST node) {
        if (node != null) {
            printMovieInventory(node.leftChild);
            printTitleAndQuantity(node);
            printMovieInventory(node.rightChild);
        }
    }
    public void printTitleAndQuantity(MovieNodeBST node) {
        // Prints movie titles and quantity available
        String initial = String.valueOf(node.letter);
        MovieNodeBST key = searchBST(node, initial);
        if (key == null) {
            System.out.println("There are no movies that start with letter: " + initial);
        }
        else{
            MovieNodeBST.MovieNodeLL currNode = key.head;
            while (currNode != null) {
                System.out.print("Title: " + currNode.title + "\n");
                System.out.println("Quantity: " + currNode.quantity + "\n");
                currNode = currNode.next;
            }
        }
    }
    public void printLL(MovieNodeBST node, String initial) {
        MovieNodeBST key = searchBST(node, initial);
        if (key == null) {
            System.out.println("There are no movies that start with letter: " + initial);
        }
        else{
            MovieNodeBST.MovieNodeLL currNode = key.head;
            while (currNode != null) {
                System.out.print(currNode.ranking + " ");
                System.out.print(currNode.title + " ");
                System.out.print(currNode.year + " ");
                System.out.print(currNode.quantity + "\n");
                currNode = currNode.next;
            }
        }
    }
    private MovieNodeBST searchBST(MovieNodeBST node, String title){
        //use this recursive function to find a reference to a node in the BST,
        //given a MOVIE TITLE
        char initial = title.charAt(0);
        if (node == null || node.letter == initial) {
            return node;
        }
        else if (initial < node.letter) {
            return searchBST(node.leftChild, title);
        }
        else {
            return searchBST(node.rightChild, title);
        }
    }
    private MovieNodeBST.MovieNodeLL searchLL(MovieNodeBST.MovieNodeLL head, String title) {
        //use this to return a pointer to a node in a linked list,
        //given a MOVIE TITLE and the head of the linked list
        MovieNodeBST.MovieNodeLL currNode = head;
        while (currNode != null) {
            if (currNode.title.equals(title)) {
                return currNode;
            }
            currNode = currNode.next;
        }
        return null;
    }
    private MovieNodeBST treeMaximum(MovieNodeBST node) {
        //use this to find the left most leaf node of the BST,
        //you'll need this in the delete function
        MovieNodeBST currNode = node;
        while (currNode.rightChild != null) {
            currNode = currNode.rightChild;
        }
        return currNode;
    }
    public void printKeys(MovieNodeBST node) {
        if (node != null) {
            printKeys(node.leftChild);
            System.out.println(node.letter);
            printKeys(node.rightChild);
        }
    }

}