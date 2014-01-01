package utils.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.Skills;

public class ProgressBar {
    
    MethodContext ctx;
    public ProgressBar(MethodContext ctx) {
        this.ctx = ctx;
    }
    
    public void drawProgressBar(Graphics2D g1, final int x, final int y, final int width, final int height, final Color background, final Color main, final Color progress, final int arcWidth, final int arcHeight, int percentage) {
        
        Graphics2D g = (Graphics2D) g1;
        percentage = getPercentToNextLevel(Skills.FLETCHING);
        final GradientPaint first = new GradientPaint(x, y, Color.ORANGE, x, y + height, main);
        final GradientPaint second = new GradientPaint(x, y, Color.ORANGE, x, y + height, progress);
        
        g.setPaint(first);
        g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        g.setPaint(second);
        g.fillRoundRect(x, y, (int) (width * (percentage / 100.0D)), height, arcWidth, arcHeight);
        g.setStroke(new BasicStroke(2));
        g.setColor(background);
        g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
        g.setFont(new Font("Arial", 10, Font.ITALIC));
    }
    
    public int getPercentToNextLevel(final int index) {
        if (index > SKILL_NAMES.length - 1) {
                return -1;
        }
        final int lvl = ctx.skills.getRealLevel(index);
        return getPercentToLevel(index, lvl + 1);
}

    public int getPercentToLevel(final int index, final int endLvl) {
        if (index > SKILL_NAMES.length - 1) {
            return -1;
        }
        final int lvl = ctx.skills.getRealLevel(index);
        if (lvl == 99 || endLvl > 99) {
            return 100;
        }
        final int xpTotal = Skills.XP_TABLE[endLvl] - Skills.XP_TABLE[lvl];
        if (xpTotal == 0) {
            return 0;
        }
        final int xpDone = ctx.skills.getExperience(index) - Skills.XP_TABLE[lvl];
        return 100 * xpDone / xpTotal;
    }

    public final String[] SKILL_NAMES = {
            "attack", "defence",
            "strength", "constitution", "range", "prayer", "magic", "cooking",
            "woodcutting", "fletching", "fishing", "firemaking", "crafting",
            "smithing", "mining", "herblore", "agility", "thieving", "slayer",
            "farming", "runecrafting", "hunter", "construction", "summoning",
            "dungeoneering", "-unused-" };
}