package utils;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;

public abstract class Node extends MethodProvider {
    
    public Node(final MethodContext arg0) {
	super(arg0);
    }
    
    public abstract boolean validate();
    
    public abstract void execute();
}
