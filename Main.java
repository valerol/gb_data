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
        // color
    }

    public boolean insert(int value){
        if(root == null){
            root = new Node();
            root.value = value;
            return true;
        }else{
            insert(root, value);
            //root = rebalance(root);
        }
        // root.color = BLACK
    }

    private boolean insert(Node node, int value){
        if(node.value == value){
            return false;
        }else{
            if(node.value < value){
                if(node.right != null){
                    insert(node.right, value);
                    // node.right = Rebalance(node.right);
                }else{
                    node.right = new Node();
                    node.right.value = value;
                    // node.color = RED
                    return true;
                }
            }else{
                if(node.left != null){
                    return insert(node.left, value);
                    // Rebalance();
                }else{
                    node.left = new Node();
                    node.left.value = value;
                    // node.color = RED
                    return true;
                }
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
        tree.insert(1);
        tree.insert(2);
        tree.insert(7);
        tree.insert(8);
        tree.insert(6);

        System.out.println(tree.find(7));
        System.out.println(tree.find(9));

    }
}