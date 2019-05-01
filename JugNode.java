package AI_Assignment1;

import java.util.ArrayList;

//Referenced from example code and lecture slides

public class JugNode {
    JugState state;
    JugNode parent;
    private int steps;


    public JugNode(JugState state, JugNode parent, int cost) {
        this.state = state;
        this.parent = parent;
        this.steps = cost;
    }


    public JugNode(JugState state) {
        this(state,null,0);
    }


    public int getSteps() {
        return steps;
    }


    public String toString() {
        return "JugNode:" + state + " ";
    }


    public static JugNode findNodeWithState(ArrayList<JugNode> nodeList, JugState gs) {
        for (JugNode n : nodeList) {
            if (gs.sameState(n.state)) return n;
        }
        return null;
    }
}
