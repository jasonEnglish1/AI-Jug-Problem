package AI_Assignment1;

import java.util.ArrayList;

//Referenced from example code and lecture slides

public class JugState {
    static int[] capacity = new int[3];
    private int[] content = new int[3];     // implements the variables that are required in a object of type JugState
    static int[] start_state = new int[3];
    static int[] goal = new int[3];
    static final int jug_amount = 3;

    public JugState(int x, int y, int z) { //constructor for a JugState that only requires the content
        content[0] = x;
        content[1] = y;
        content[2] = z;
    }

    public JugState(int capX, int capY, int capZ, int x, int y, int z, int a, int b, int c) { //constructor for the initial JugState
        this(x, y, z);                                                              //detailing the initial state , goal state and capacity of the jugs
        int[] item = {x,y,z};
        start_state = item;
        capacity[0] = capX;
        capacity[1] = capY;
        capacity[2] = capZ;
        goal[0] = a;
        goal[1] = b;
        goal[2] = c;
    }

    public int getContent(int j) {  //returns the content at jug j
        return content[j];
    }

    public String toString() {      //overrides the toString method for JugState objects
        String s = "[ ";
        for (int c : this.content) s =  s  + c + ' ' ;
        return s + "]";
    }

    private void setContent(int j, int volume) {
        content[j] = volume;            //sets the content at jug j to the amount volume
    }

    public int getCapacity(int j) {         //returns the capacity at position j
        return capacity[j];
    }


    public JugState cloneState() {      //creates a clone of the JugState object
        return new JugState(content[0], content[1], content[2]);
    }

    public boolean Goal() {     // checks the current JugState to see if it equals the goal state
        for (int j = 0; j < jug_amount; j++) {
            if (this.getContent(j) != goal[j]) return false;
        }
        return true;
    }

    public boolean sameState(JugState gs) {     //checks if two JugStates are the same
        for (int j = 0; j < jug_amount; j++) {
            if (this.getContent(j) != gs.getContent(j)) {
                return false;       //different states
            }
        }
        return true; //same states
    }

    public JugState decantJugs(int source, int dest) {  //attempts decants from a source jug to a destination jug
        if (source == dest) { //checks if the two jugs you are pouring from and to are the same
            System.out.println("WARNING: Attempt to decant from and to same jug: " + source);  //gives an error output
            return this; //returns state without changes
        } else if (content[source] == 0) { // source jug is empty
            return this;
        } else if (content[dest] == capacity[dest]) { // destination jug is at capacity
            return this;
        } else { //if jugs meet requirements to pour
            int remainingVolume = capacity[dest] - content[dest]; //finds volume of destination jug available
            int newDestContent, newSourceContent;
            if (remainingVolume >= content[source]) { //if there is space to pour all of source into destination
                newSourceContent = 0;  //source is empty
                newDestContent = content[dest] + content[source]; //destination is full
            } else { //if there is more liquid in source than space available in destination
                newDestContent = capacity[dest];  //destination becomes full
                newSourceContent = content[source] - remainingVolume;  //source becomes equal to amount left after filling destination
            }
            JugState newState = this.cloneState();  // create a copy of the current state
            newState.setContent(source, newSourceContent); //update the contents of the state that have changed
            newState.setContent(dest, newDestContent);
            return newState;  //return a JugState object which contains the updated values after decanting
        }
    }
    public ArrayList<JugState> decantJugs(JugState newState) {      // iterates through all six possible decant moves
        ArrayList<JugState> moves = new ArrayList<>();
        for (int i = 0; i < 3 ;i++){for (int j = 0; j < 3;j++){
            if (i!=j){
                moves.add(newState.decantJugs(i,j));  //adds new JugStates to a list of JugStates
            }
        }
        }
        return moves;
    }
}





