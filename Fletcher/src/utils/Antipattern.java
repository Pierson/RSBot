package utils;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Item;

import pFletcher.Fletcher;

public class Antipattern extends MethodProvider{
    
    public Antipattern(MethodContext arg0) {
	super(arg0);
    }
    
    public void antiban() {
	int anti = Random.nextInt(0, 10);
	switch(anti) {
	
	case 1:
	    Fletcher.status = "Adjusting Camera Angle";
	    ctx.camera.setAngle(Random.nextInt(5, 150));
	    sleep(800, 1500);
	    break;
	    
	case 2:
	    Fletcher.status = "Setting Camera Pitch: " +ctx.camera.getPitch();
	    ctx.camera.setPitch(Random.nextInt(45, 80));
	    sleep(800, 1700);
	    break;
	    
	case 3: 
	    Fletcher.status = "Random Mouse Movement";
	    ctx.mouse.move(Random.nextInt(5, 200), Random.nextInt(40, 400));
	    sleep(900, 1600);
	    ctx.mouse.move(Random.nextInt(3, 500), Random.nextInt(5, 400));
	    break;
	    
	case 4:
	    Fletcher.status = "Random Script Rest";
	    sleep(1000, 4000);
	    break;
	    
	case 5:
	    for(GameObject object : ctx.objects.select().first()) {
		if(object.isOnScreen()) {
		    Fletcher.status = "Hovering " +object.getName();
		    object.hover();
		    sleep(750, 1500);
		}
	    }
	    break;
	    
	case 6:
	    for(Item i : ctx.backpack.select().first()) {
		if(i != null) {
		    Fletcher.status = "Hovering " +i.getName();
		    i.hover();
		    sleep(500, 1400);
		}
	    }
	    break;
	}
    }
}
