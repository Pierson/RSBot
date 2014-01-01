package tasks.interfaces;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;

import data.Option;

import pFletcher.Fletcher;

import utils.Antipattern;
import utils.Node;

public class FletchHandler extends Node {
    
    private Fletcher fletch;

    public FletchHandler(MethodContext arg0, Fletcher fletch) {
	super(arg0);
	random = new Antipattern(ctx);
	this.fletch = fletch;
    }
    
    private Antipattern random;
    public static Option opt = Option.NONE;
    
    private boolean isFletching() {
	return ctx.players.local().getAnimation() == 1248
		|| ctx.players.local().getAnimation() == 6678
		|| ctx.players.local().getAnimation() == 6685
		|| ctx.players.local().getAnimation() == 19516;
    }

    @Override
    public boolean validate() {
	final Component fletch = ctx.widgets.get(1370, 26);
	return fletch.isOnScreen();
    }

    @Override
    public void execute() {
	Fletcher.status = "Selecting " +opt.toString();
	if(!fletch.stringBow) {
	    Component bow = ctx.widgets.get(1371, 44).getChild(fletch.widget);
	    if(bow.click()) {
		sleep(300, 1500);
	    }
	}
	    Fletcher.status = "Pressing Button";
	    final Component button = ctx.widgets.get(1370, 20);
	    if(button.click()) {
		random.antiban();
		sleep(1000, 2000);
	    }
	    Fletcher.status = "Fletching/Stringing";
	    Condition.wait(new Callable<Boolean>() {
		@Override
		public Boolean call() {
		    return !isFletching();    
		}
	    }, 3500, 15);
    }
}