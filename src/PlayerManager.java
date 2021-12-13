import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PlayerManager {
    BinarySearchTree<Player> bst;
    HashMap<Integer, ArrayList<Player>> hash;

    public PlayerManager() {
        bst = new BinarySearchTree<>();
        hash = new HashMap<>();
    }

    public void addNewPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new player's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new player's age: ");
        int age = scanner.nextInt();
        System.out.printf("Name: %s Age: %d\n", name, age);
        Player player = new Player(name, age);
        bst.insert(player);
        if (!hash.containsKey(age)) {
            ArrayList<Player> list = new ArrayList<>();
            list.add(player);
            hash.put(age, list);
        } else {
            hash.get(age).add(player);
        }
        System.out.printf("Player '%s' created.\n", name);
    }

    public Player findPlayerByName(String name) {
        Player finder = new Player(name, 0);
        return bst.find(finder);
    }
}
