package tasks.interfaces;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;

import pFletcher.Fletcher;

import utils.Node;

public class SelectWhichHandler extends Node {

    public SelectWhichHandler(MethodContext arg0) {
	super(arg0);
    }
    
    final Component select = ctx.widgets.get(1179, 27);
    final Component cut = ctx.widgets.get(1179, 33);

    @Override
    public boolean validate() {
	return select.isOnScreen();
    }

    @Override
    public void execute() {
	if(cut.click()) {
	    Fletcher.status = "Selecting Interface";
	    Condition.wait(new Callable<Boolean>() {
		@Override
		public Boolean call() {
		    return !select.isOnScreen();    
		}
	    }, 100, 10);
	}
    }
}
