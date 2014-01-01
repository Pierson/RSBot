package tasks.fletching;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Item;

import pFletcher.Fletcher;

import utils.Node;

public class BoltTipping extends Node {

    public BoltTipping(MethodContext arg0) {
	super(arg0);
    }

    @Override
    public boolean validate() {
	final Component fletch = ctx.widgets.get(1370, 26);
	return !ctx.backpack.select().id(Fletcher.bolt.getBolt()).isEmpty()
		&& !ctx.backpack.select().id(314).isEmpty() 
		&& !ctx.bank.isOpen()
		&& !fletch.isOnScreen();
    }

    @Override
    public void execute() {
	final Item bolt = ctx.backpack.select().id(Fletcher.bolt.getBolt()).poll();
	final Item feather = ctx.backpack.select().id(314).poll();
	
	Fletcher.status = "Clicking " +Fletcher.bolt.toString();
	if(bolt.interact("Use")) {
	    if(feather.interact("Use"));
	    sleep(500);
	}
    }
}
