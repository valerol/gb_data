import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

class HashMap{
    class Entity{
        int key; // Key -> HashCode() - > Index
        int value;
    }
    class Basket{ // List
        Node head;
        class Node{
            Entity entity;
            Node next;
        }

        public boolean insert(Entity entity){
            Node node = new Node();
            node.entity = entity;

            if(head == null){
                head = node;
                return true;
            }else{
                Node cur = head;
                while(cur != null){
                    if(cur.entity.key == entity.key){
                        return false;
                    }
                    if(cur.next == null){
                        cur.next = node;
                        return true;
                    }
                    cur = cur.next;
                }
            }
            return false;
        }
    }

    public static int INIT_SIZE = 16;
    Basket[] baskets;

    public HashMap(){
        this(INIT_SIZE);
    }

    public HashMap(int size){
        baskets = new Basket[size];
    }

    int getIndex(int key){
        //return key.hashCode() % baskets.length;
        return key % baskets.length;
    }

    public boolean insert(int key, int value){
        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        int index = getIndex(key);

        Basket basket = baskets[index];

        if(basket == null){
            basket = new Basket();
            baskets[index] = basket;
        }

        return basket.insert(entity);
    }
}

class BinaryTree{
    Node root;
    class Node{
        int value;
        Node left;
        Node right;
        boolean isRed = false;

        public void print() {
            print("", this, false);
        }

        public void print(String prefix, Node n, boolean isLeft) { // Добавила визуализацию дерева, для собственной же наглядности
            if (n != null) {
                System.out.println(prefix + (n.isRed ? "\u001B[31m" : "\u001B[30m") + (isLeft ? "|-- " : "\\-- ") + n.value);
                prefix += "    ";
                if (n.right != null && n.right.value != 0) {
                    print(prefix, n.right, false);
                }
                if (n.left != null && n.left.value != 0) {
                    print(prefix, n.left, true);
                }
            }
        }
    }

    public void Rebalance(Node node, Node parent) {
        if (node.right != null && node.right.isRed) { // Если красный правый ребенок - разворачиваем в сторону родителя
            if (parent.value > node.value) { // по часовой
                int tmp = parent.value;
                parent.value = node.right.value;
                insert(parent, tmp, false);
            } else { // против часовой
                int tmp = parent.value;
                parent.value = node.value;
                node.value = node.right.value;
                insert(node, tmp, false);
            }
            node.right = new Node(); // Перемещенную ноду обнуляем
        } 
        // Если у красной ноды есть красный потомок - разворачиваем в сторону ноды
        if (node.isRed && node.left != null && node.left.isRed) { // по часовой
            int tmp = node.left.value;
            node.right.value = node.value;
            node.left = new Node();
            node.value = tmp;
        } else if (node.isRed && node.right != null && node.right.isRed) { // против часовой
            int tmp = node.right.value;
            node.left.value = node.value;
            node.right = new Node();
            node.value = tmp;
        }
    }

    public boolean insert(int value){
        if (root == null){
            root = new Node();
            root.value = value;
            return true;
        } else{
            insert(root, value, true);
            //root = rebalance(root);
            // Не поняла, что надо ребалансировать, когда у нас еще только корень есть?
        }
        root.isRed = false;
        return false;
    }

    private boolean insert(Node node, int value, boolean isNew) { // isNew - чтобы не окрашивать ноды, перемещаемые в ходе ребалансировки
        if (node.value == value) {
            return false;
        } else {
            System.out.println("node: " + node.value + ", value: " + value);
            if (node.value < value) {
                if (node.right != null && node.right.value != 0) {
                    insert(node.right, value, isNew);
                    //this.root.print("before rebalance", this.root, false);
                    Rebalance(node.right, node);
                    //this.root.print("after  rebalance", this.root, false);
                } else {
                    node.right = new Node();
                    node.right.value = value;
                    node.right.isRed = isNew ? true : false; // node.color = RED
                }
                return true;
            } else {
                if (node.left != null && node.left.value != 0) {
                    insert(node.left, value, isNew);
                    //this.root.print("before", this.root, false);
                    Rebalance(node.left, node);
                    //this.root.print("after", this.root, false);
                } else {
                    node.left = new Node();
                    node.left.value = value;
                    node.left.isRed = isNew ? true : false; // node.color = RED
                }
                return true;
            }
        }
    }

    public boolean find(int value){
        return find(root, value);
    }

    private boolean find(Node node, int value){
        if(node == null){
            return false;
        }
        if(node.value == value){
            return true;
        }

        if(node.value < value){
            return find(node.right, value);
        }else{
            return find(node.left, value);
        }
    }
}
public class Main {
    public static void main(String[] args) {

//        HashMap map = new HashMap();
//
//        map.insert(1, 2);
//        map.insert(3, 4);
//        map.insert(17, 6);

        BinaryTree tree = new BinaryTree();

        tree.insert(5);
        tree.insert(3);
        tree.insert(4);
        tree.insert(6);
        tree.insert(1);
        tree.insert(2);
        tree.insert(7);
        tree.insert(8);
        tree.insert(15);
        tree.insert(120);
        tree.insert(1500);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);

        // System.out.println(tree.find(7));
        // System.out.println(tree.find(9));
        tree.root.print(" ", tree.root, false);
    }

}