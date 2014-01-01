package tasks.fletching;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Item;

import pFletcher.Fletcher;

import utils.Node;

public class Interaction extends Node {

    public Interaction(MethodContext arg0) {
	super(arg0);
    }
    
    final int string = 1777;
    
    @Override
    public boolean validate() {
	final Component select = ctx.widgets.get(1179, 27);
	final Component fletch = ctx.widgets.get(1370, 26);
	return (!ctx.backpack.select().id(Fletcher.log.getLog()).isEmpty() 
		|| ctx.backpack.select().id(string).count() == 14)
		&& !ctx.bank.isOpen()
		&& !select.isOnScreen()
		&& !fletch.isOnScreen();
    }

    @Override
    public void execute() {
	Item log = ctx.backpack.select().id(Fletcher.log.getLog()).poll();
	Item bowString = ctx.backpack.select().id(string).poll();
	final Component select = ctx.widgets.get(1179, 27);
	final Component obstruction = ctx.widgets.get(1432, 1);
	
	Fletcher.status = "Clicking Item";
	if(obstruction.isOnScreen()) {
	    ctx.mouse.move(Random.nextInt(5, 400), Random.nextInt(5, 600));
	}
	if(log.interact("Craft") || bowString.interact("String")) {
	    Condition.wait(new Callable<Boolean>() {
		@Override
		public Boolean call() {
		    return select.isOnScreen();    
		}
	    }, 100, 10);
	}
    }
}