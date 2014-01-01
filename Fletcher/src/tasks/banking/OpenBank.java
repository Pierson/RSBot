package tasks.banking;

import org.powerbot.script.methods.MethodContext;
import pFletcher.Fletcher;

import utils.Node;

public class OpenBank extends Node {
    
    private Fletcher fletch;

    public OpenBank(MethodContext arg0, Fletcher fletch) {
	super(arg0);
	this.fletch = fletch;
    }
    
    private boolean containsItem() {
	if(!fletch.stringBow) {
	    return ctx.backpack.select().id(Fletcher.log.getLog()).isEmpty();
	} else { 
	    return ctx.backpack.select().id(1777).isEmpty();
	}
    }

    @Override
    public boolean validate() {
	return ctx.bank.isOnScreen() 
		&& !ctx.bank.isOpen()
		&& (containsItem() || ctx.backpack.select().isEmpty())
		&& ctx.backpack.select().id(314).isEmpty();
    }

    @Override
    public void execute() {
	Fletcher.status = "Finding Bank";
	if(!ctx.bank.isOnScreen()) {
	    ctx.camera.turnTo(ctx.bank.getNearest());
	    ctx.movement.stepTowards(ctx.bank.getNearest());
	} else {
	    Fletcher.status = "Opening Bank";
	    if(ctx.bank.open()) {	    
	    }
	}
    }
}