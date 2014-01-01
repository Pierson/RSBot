package tasks.banking;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.Bank.Amount;

import pFletcher.Fletcher;

import utils.Node;

public class Withdraw extends Node {
    
    private Fletcher fletch;

    public Withdraw(MethodContext arg0, Fletcher fletch) {
	super(arg0);
	this.fletch = fletch;
    }
    
    private void withdrawBow() {
	if(fletch.longBow) {
	    if(ctx.backpack.select().id(Fletcher.log.getLongBow()).count() != 14) {
		ctx.bank.withdraw(Fletcher.log.getLongBow(), 14);
	    }
	} else {
	    if(ctx.backpack.select().id(Fletcher.log.getBow()).count() != 14) {
		ctx.bank.withdraw(Fletcher.log.getBow(), 14);
	    }
	}
    }
    
    private boolean containsItem() {
	if(!fletch.stringBow) {
	    return ctx.backpack.select().id(Fletcher.log.getLog()).count() == 28;
	} else 
	    return (ctx.backpack.select().id(Fletcher.log.getBow()).count() == 14 || ctx.backpack.select().id(Fletcher.log.getLongBow()).count() == 14)
		    && ctx.backpack.select().id(1777).count() == 14;
    }
    
    

    @Override
    public boolean validate() {
	return ctx.bank.isOpen() && ctx.backpack.select().isEmpty();
    }

    @Override
    public void execute() {
	// PLEASE NOTE SLEEPING IS FOR ADDITIONAL "RANDOMNESS" AND HUMAN-LIKE BEHAVIOUR \\
	if(fletch.stringBow) {
	    if(ctx.backpack.select().id(1777).count() != 14) {
		Fletcher.status = "Withdrawing BowString";
		ctx.bank.withdraw(1777, 14);
		sleep(500, 1250);
	    }
	    Fletcher.status = "Withdrawing Bows";
	    withdrawBow();
	    sleep (300, 1000); 
	} else {
	    Fletcher.status = "Withdrawing Logs";
	    if(ctx.backpack.select().id(Fletcher.log.getLog()).count() != 28) {
		ctx.bank.withdraw(Fletcher.log.getLog(), Amount.ALL);
		sleep(500, 1250);
	    }
	}
	if(containsItem()) {
	    ctx.bank.close();
	}
    }
}
	    
