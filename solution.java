package AI_Assignment1;

//Referenced from example code and lecture slides

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static AI_Assignment1.JugState.capacity;
import static AI_Assignment1.JugState.goal;
import static AI_Assignment1.JugState.start_state;


public class solution {

    ArrayList<JugNode> unexplored = new ArrayList<JugNode>();
    ArrayList<JugNode> explored = new ArrayList<JugNode>();
    JugNode rootNode;


    public solution(int x, int y, int z) {
        try {   //trys to get the inputs
            Scanner sc = new Scanner(System.in);        //gets the inputs for the jug capacities
            System.out.println("Input jug capacities with spaces between each int ");
            int jugCap1 = sc.nextInt();
            int jugCap2 = sc.nextInt();
            int jugCap3 = sc.nextInt();

            System.out.println("Input goal state with spaces between each int ");    //gets the inputs for the goal state
            int goal1 = sc.nextInt();
            int goal2 = sc.nextInt();
            int goal3 = sc.nextInt();
            JugState initialState = new JugState(jugCap1, jugCap2, jugCap3, x, y, z, goal1, goal2, goal3);
            rootNode = new JugNode(initialState);
        } catch (Exception e){  //if there is an error it outputs the error message below
            System.out.println("Error: " + e);
            System.out.println("Please check your inputs are all correct");
        }
    }


    public void printSolution(JugNode n, PrintWriter writer) { //iterates through the JugNode parent nodes to find the steps to the solution
        if (n.parent != null) {                     //starting at the goal state and working its way back up
            printSolution(n.parent, writer);
        }
        writer.println(n.state);
    }


    public void reportSolution(JugNode n, PrintWriter writer, JugState gs ) { //generates the outputs when a solution is found
        writer.println("A Solution is possible");
        writer.println("Capacities: " +"\n" + "[ " + gs.getCapacity(0) + " " + gs.getCapacity(1) + " " + gs.getCapacity(2) + " ]");
        writer.println("Solution Steps: ");
        printSolution(n, writer);
        writer.println(n.getSteps() + " Moves Required");
        writer.println("Number of Nodes Expanded: " + this.explored.size());
        writer.println("Number of Nodes Unexpanded: " + this.unexplored.size());
        writer.println();
    }



    public void solve(PrintWriter writer) { //solves the jug problem
        unexplored.add(rootNode);
        while (unexplored.size() > 0) { //whiles there are still nodes to expand
            JugNode n = unexplored.get(0);  // create a node with the information of the game state you are about to expand
            unexplored.remove(0); //removes it from the unexplored list
            explored.add(n);  // adds to the explored node list


            if (n.state.Goal()) { //if it is the goal state
                reportSolution(n, writer, n.state ); //output the solution
                return;

            } else {

                ArrayList<JugState> moveList = n.state.decantJugs(n.state); // creates an ArrayList of JugStates called moveList, with the outputs of the decant function for that state

                for (JugState gs : moveList) { //for each states in the ArrayList of states

                    if ((JugNode.findNodeWithState(unexplored, gs) == null) &&
                            (JugNode.findNodeWithState(explored, gs) == null)) { // if the state isn't already in explored or unexplored

                        if (gs.getContent(0) <= gs.getCapacity(0) && gs.getContent(1) <= gs.getCapacity(1) && gs.getContent(2) <= gs.getCapacity(2)) {  //and the contents are not larger than the capacities
                            int newCost = n.getSteps() + 1; //create the cost to get to the new node
                            JugNode newNode = new JugNode(gs, n, newCost); //create a new node with the current game state, parent node and cost
                            unexplored.add(newNode); //add the new node to the unexplored list
                        }
                    }
                }
            }

        }
        writer.println("No solution possible");  //generates the output when no solution is found
        writer.println("Capacities: " + "\n" + '[' + ' ' + (capacity[0]) + ' ' + (capacity[1]) + ' ' + (capacity[2]) + ' ' + ']');
        writer.println("Initial State: " + "\n" + '[' + ' ' + (start_state[0]) + ' ' + (start_state[1]) + ' ' + (start_state[2]) + ' ' + ']');
        writer.println("Goal State: " + "\n" + '[' + ' ' + (goal[0]) + ' ' + (goal[1]) + ' ' + (goal[2]) + ' ' + ']');
        writer.println("No solution ");
        writer.println("Number of Nodes Expanded: " + this.explored.size());
        writer.println("Number of Nodes Unexpanded: " + this.unexplored.size());
        writer.println();
    }


    public static void main(String[] args) throws Exception {
        try { //attepmts to create a JugState and output the results
            Scanner sc = new Scanner(System.in); //creates a scanner object
            System.out.println("Would you like to implement a game state? y/n");
            String choice = sc.next();
            File outFile = new File("output.txt");
            PrintWriter writer = new PrintWriter(outFile); //creates an output object, which outputs to a file named output.txt
            while (choice.charAt(0) == 'y') { //checks to see if the user wishes to keep trying new jug problems before printing to a file
                System.out.println("Input the initial jug state with spaces between each int");
                int jugStart1 = sc.nextInt();
                int jugStart2 = sc.nextInt();
                int jugStart3 = sc.nextInt();
                solution problem = new solution(jugStart1, jugStart2, jugStart3); //generates the new jug problem
                problem.solve(writer);      //solves the jug problem
                System.out.println("Would you like to implement a game state? y/n");
                choice = sc.next();
            }
            writer.close(); //closes the file that is being written to
        } catch (Exception e){ //if an error is found in the input, this error message is output
            System.out.println("Error: " + e);
            System.out.println("Please check your inputs are all correct");
        }



    }
}

