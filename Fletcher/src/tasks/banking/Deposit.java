package tasks.banking;

import org.powerbot.script.methods.MethodContext;

import pFletcher.Fletcher;

import utils.Node;

public class Deposit extends Node {
    
   private Fletcher fletch;

    public Deposit(MethodContext arg0, Fletcher fletch) {
	super(arg0);
	this.fletch = fletch;
    }
    
    private boolean lacksItem() {
	if(fletch.stringBow) {
	    return ctx.backpack.select().id(1777).isEmpty();
	} else {
	    return ctx.backpack.select().id(Fletcher.log.getLog()).isEmpty();
	}
    }
    

    @Override
    public boolean validate() {
	return ctx.bank.isOpen() && lacksItem() && !ctx.backpack.select().isEmpty();
    }

    @Override
    public void execute() {
	if(ctx.bank.depositInventory()) {
	    Fletcher.status = "Depositing Inventory";
	}
    }
}
