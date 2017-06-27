import java.util.*;

/**
 * Created by neil on 6/25/17.
 */
public class vaccuum {

    public static Node worldStart = new Node(false, false, false, false, 1);
    public static final Node cutoffNode = new Node(false, false, false, false, 0);
    public static final Node failure = new Node(false, false, false, false, -1);


    public static class Node {

        private boolean sq_one;
        private boolean sq_two;
        private boolean sq_three;
        private boolean sq_four;
        private int vac_location;
        Node parent;


        //default constructor.  Creates initial world state
        public Node(boolean sq_one, boolean sq_two, boolean sq_three, boolean sq_four, int vac_location) {
            this.sq_one = sq_one;
            this.sq_two = sq_two;
            this.sq_three = sq_three;
            this.sq_four = sq_four;
            this.vac_location = 1;
        }

        //use inputs to create altered world states
        public Node(Node input) {
            this.sq_one = input.sq_one;
            this.sq_two = input.sq_two;
            this.sq_three = input.sq_three;
            this.sq_four = input.sq_four;
            this.vac_location = input.vac_location;
            this.parent = input;
        }

        //do movement
        public void move_right() {
            if (vac_location == 1) {
                vac_location = 4;
            } else if (vac_location == 2) {
                vac_location = 3;
            }
        }

        public void move_left() {
            if (vac_location == 3) {
                vac_location = 2;
            } else if (vac_location == 4) {
                vac_location = 1;
            }
        }

        public void move_down() {
            if (vac_location == 2) {
                vac_location = 1;
            } else if (vac_location == 3) {
                vac_location = 4;
            }
        }

        public void move_up() {
            if (vac_location == 1) {
                vac_location = 2;
            } else if (vac_location == 4) {
                vac_location = 3;
            }
        }

        public void clean() {
            if (vac_location == 1) {
                sq_one = true;
            } else if (vac_location == 2) {
                sq_two = true;
            } else if (vac_location == 3) {
                sq_three = true;
            } else if (vac_location == 4) {
                sq_four = true;
            }
        }

        //GOAL TEST
        public boolean goal_test() {
            if (this.sq_one == true && this.sq_two == true && this.sq_three ==true && this.sq_four == true) {
                return true;
            } else {
                return false;
            }
        }

        public String toString() {
            return this.sq_one + " " + this.sq_two + " " + this.sq_three + " " + this.sq_four;
        }


        public Node recursive_search(Node input_node, int limit) {

            boolean cutoffOccurred;

            if (input_node.goal_test() == true) {
                return input_node;
            } else if (limit == 0) {
                return cutoffNode;
            } else {
                cutoffOccurred = false;

                List<Node> action_list = new ArrayList<>();

                //for each action, create node with said action, feed back to recur call
                Node clean_node = new Node(input_node);
                Node up_node    = new Node(input_node);
                Node right_node = new Node(input_node);
                Node down_node  = new Node(input_node);
                Node left_node  = new Node(input_node);

                //do said actions on each created node
                clean_node.clean();
                up_node.move_up();
                right_node.move_right();
                down_node.move_down();
                left_node.move_left();

                action_list.add(clean_node);
                action_list.add(up_node);
                action_list.add(right_node);
                action_list.add(down_node);
                action_list.add(left_node);


                for (Node current_node : action_list) {
                    Node result_node = recursive_search(current_node, limit -1);

                    if (result_node.goal_test() == true) {
                        return result_node;
                    }
                    if (result_node == cutoffNode) {
                        cutoffOccurred = true;
                    } else if (result_node != null) {
                        return clean_node; //return clean node b/c final action will always be clean
                    } else if (cutoffOccurred = true) {
                        return cutoffNode;
                    } else {
                        return failure;
                    }
                }

            }

            if (cutoffOccurred == true) {
                return cutoffNode;
            } else {
                return failure;
            }
        }

    }

    public static void main(String[] args) {


        int limit = 10;

        System.out.println(worldStart.vac_location);
        System.out.println(worldStart.sq_one);
        System.out.println(worldStart.sq_two);
        System.out.println(worldStart.sq_three);
        System.out.println(worldStart.sq_four);

        Node final_node = (worldStart.recursive_search(worldStart, limit));
        System.out.println(final_node.vac_location);
        System.out.println(final_node.sq_one);
        System.out.println(final_node.sq_two);
        System.out.println(final_node.sq_three);
        System.out.println(final_node.sq_four);
    }
}
