package pFletcher;

import utils.Node;
import utils.graphics.ProgressBar;
import gui.Gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Hud;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.util.Random;
import org.powerbot.script.util.Timer;

import data.Bolt;
import data.Log;

@SuppressWarnings("deprecation")
@Manifest(name = "pFletch AIO", description = "Your AIO Fletching Experience. Supports cutting, stringing, and adding bolt tips!", version = 1.0, topic = 0)
public class Fletcher extends PollingScript implements PaintListener, MessageListener {
    
    public boolean stringBow;
    public boolean longBow;
    public boolean paint;
    
    public int widget;
    private int cut = 0;
    private int startExp;
    private int fletchExp;
    private int realLvl;
    private int startLvl;
    private long startTime;
    
    public static String status = "Starting Up...";
    public String selected = "Selecting...";
    public String bow = "";
    private String runTime;
    
    public final ArrayList<Node> nodes = new ArrayList<Node>();
    public static Log log = Log.NONE;
    public static Bolt bolt = Bolt.NONE;
    

    private ProgressBar pb;
    public Fletcher fletch;
    
    @Override
    public void start() {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		new Gui(ctx, Fletcher.this);
	    }
	});
	startExp = ctx.skills.getExperience(Skills.FLETCHING);
	startLvl = ctx.skills.getLevel(Skills.FLETCHING);
	pb = new ProgressBar(ctx);
	startTime = System.currentTimeMillis();
    }
    
    public int perHour(int value) {
	return (int) ((value) * 3600000D / (System.currentTimeMillis() - startTime));
    }

    @Override
    public void repaint(Graphics g1) {
	Graphics2D g = (Graphics2D) g1;
	
	if(paint) {
	    final BufferedImage paint = downloadImage("http://i.imgur.com/6Jkuuqe.png");
	    realLvl = ctx.skills.getLevel(Skills.FLETCHING);
	    fletchExp = ctx.skills.getExperience(Skills.FLETCHING) - startExp;
	    int gain = realLvl - startLvl; 
	    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    runTime = Timer.format(getRuntime());
	    
	    g.drawImage(paint, 2, 447, null); 
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    g.drawString("" +runTime, 148, 523);
	    g.drawString("" +realLvl +" ("+gain+")", 350, 558);
	    g.drawString("" +fletchExp +" ("+perHour(fletchExp)+")", 410, 523);
	    g.drawString("" +cut +" ("+perHour(cut)+")", 400, 590);
	    g.drawString("" +status, 110, 554);
	    g.drawString("" +selected +" "+bow, 103, 590);
	    g.drawString(+pb.getPercentToNextLevel(Skills.FLETCHING) +"%", 500, 515);
	    pb.drawProgressBar(g, 440, 490, 130, 10, Color.BLACK, Color.RED, Color.ORANGE, 10, 10, pb.getPercentToNextLevel(Skills.FLETCHING));

	    // Mouse + Background \\
	    g.setColor(Color.WHITE);
	    g.setStroke(new BasicStroke(2));
	    g.drawLine(ctx.mouse.getLocation().x, ctx.mouse.getLocation().y - 5, ctx.mouse.getLocation().x, ctx.mouse.getLocation().y + 5);
	    g.drawLine(ctx.mouse.getLocation().x - 5, ctx.mouse.getLocation().y, ctx.mouse.getLocation().x + 5, ctx.mouse.getLocation().y);
	}
    }

    @Override
    public int poll() {
	if(ctx.hud.isOpen(Hud.Window.BACKPACK)) {
	    ctx.hud.open(Hud.Window.BACKPACK);
	}
	if(!ctx.hud.isVisible(Hud.Window.BACKPACK)) {
	    ctx.hud.view(Hud.Window.BACKPACK);
	}
	for (Node node : nodes) {
	    if(node.validate()) {
		node.execute();
		return Random.nextInt(250, 375);
	    }
	}
	return 0;
    }
    
    @Override
    public void messaged(MessageEvent arg0) {
        final String logsCut = arg0.getMessage().toLowerCase();
        if (logsCut.contains("you carefully cut") || logsCut.contains("you add a")) {
            cut++;
        }
        if(logsCut.contains("you attach")) {
            cut = cut+10;
        }
    }
}