package org;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.listener.InventoryListener;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.ChatMessageListener;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.ChatMessageEvent;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

import tasks.Heal;
import tasks.Node;
import tasks.Potions;

@ScriptMeta(desc = "Combat Script with Queue", developer = "Nulled", name = "Nulled Combat")
public class FirstScript extends Script implements RenderListener, ChatMessageListener {

	private final List<Node> open = new ArrayList<>();
	public static final List<Node> cache = new ArrayList<>();
	Npc npc;
	int killCount = 0;
	public static int slayer = 0;
	int startLevel = 0;
	int setTask = 6000 * 1000;
	int setAmount = 50;
	int left = 0;
	public static boolean enable_slayer = false;
	public static boolean enable_potions = false;
	boolean level_task = false;
	boolean amount_task = true;
	private int[] start_Experience = new int[5];
	private int[] start_Level = new int[5];
	private int[] current_Experience = new int[5];
	private int beginningXP;
	private long startTime;
	long runTime;
	private FontMetrics fontMetrics;
	int x = 10, y = 140;
	public static InventoryListener listener = new InventoryListener();

	int xpGained;
	public static boolean uiWait = true;
	Position pos;

	@Override
	public void onStart() {
		pos = Players.getLocal().getPosition();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sad ui = new sad();
					ui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		cache.add(new Heal());
		cache.add(new Potions("Super Strenght (4)"));
		startTime = System.currentTimeMillis();
		start_Experience[0] = Skills.getExperience(Skill.ATTACK);
		start_Experience[1] = Skills.getExperience(Skill.STRENGTH);
		start_Experience[2] = Skills.getExperience(Skill.DEFENCE);
		start_Experience[3] = Skills.getExperience(Skill.RANGED);
		start_Experience[4] = Skills.getExperience(Skill.MAGIC);
		start_Level[0] = Skills.getLevel(Skill.ATTACK);
		start_Level[1] = Skills.getLevel(Skill.STRENGTH);
		start_Level[2] = Skills.getLevel(Skill.DEFENCE);
		start_Level[3] = Skills.getLevel(Skill.RANGED);
		start_Level[4] = Skills.getLevel(Skill.MAGIC);
		beginningXP = Skills.getExperience(Skill.HITPOINTS);
		startLevel = Skills.getLevel(Skill.HITPOINTS);
	}

	@Override
	public int loop() {
		listener.setInventoryCount(Inventory.getCount());
		while (uiWait) {
			Time.sleep(50);
		}
		if (Players.getLocal().getPosition().distance(pos) > 20) {
			Movement.walkTo(pos);
		}
		int delay = Random.high(300, 500);
		if (!cache.isEmpty()) {
			open.clear();
			open.addAll(cache.stream().filter((Connect) -> Connect.validate()).collect(Collectors.toList()));
			if (!open.isEmpty()) {
				delay = getAvailableConnections().execute();
			}
		}

		return delay;
	}

	public Node getAvailableConnections() {
		Node node = null;
		if (!open.isEmpty()) {
			node = open.get(0);
			if (open.size() > 0) {
				for (Node active : open) {
					if (node.priority() < active.priority())
						node = active;
					killCount = node.getKills();
				}
			}
		}
		return node;
	}

	@Override
	public void onStop() {
		try {
			URL submit = new URL("http://localhost/signature/" + "/update.php?=name=" + Script.getRSPeerUser()
					+ "&time=" + runTime + "&exp=" + xpGained + "&premium=" + "VIP");
			URLConnection con = submit.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			final BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void checkSlayer() {
		if (enable_slayer) {
			if (slayer == 0) {
				for (Item item : Inventory.getItems()) {
					if (Inventory.contains("Enchanted gem")) {
						item.interact("Check");
						enable_slayer = false;
					}
				}
			}
		}
	}

	@Override
	public void notify(RenderEvent gg) {
		Graphics g = gg.getSource();
		int currentXp = Skills.getExperience(Skill.HITPOINTS);
		int getAttack = Skills.getExperience(Skill.ATTACK);
		int getStrenght = Skills.getExperience(Skill.STRENGTH);
		int getDefence = Skills.getExperience(Skill.DEFENCE);
		int getRanged = Skills.getExperience(Skill.RANGED);
		int getMagic = Skills.getExperience(Skill.MAGIC);
		runTime = System.currentTimeMillis() - startTime;
		xpGained = currentXp - beginningXP;
		current_Experience[0] = getAttack - start_Experience[0];
		current_Experience[1] = getStrenght - start_Experience[1];
		current_Experience[2] = getDefence - start_Experience[2];
		current_Experience[3] = getRanged - start_Experience[3];
		current_Experience[4] = getMagic - start_Experience[4];
		if (xpGained != 0) {
			drawText(g, "Experience Gained: " + xpGained, x, y = (y + 20), Color.RED);
		}
		if (current_Experience[0] != 0) {
			drawText(g, "Gained Attack Exp: " + current_Experience[0], x, y = (y + 20), Color.RED);
		}
		if (current_Experience[1] != 0) {
			drawText(g, "Gained Strenght Exp: " + current_Experience[1], x, y = (y + 20), Color.RED);
		}
		if (current_Experience[2] != 0) {
			drawText(g, "Gained Defence Exp: " + current_Experience[2], x, y = (y + 20), Color.RED);
		}
		if (current_Experience[3] != 0) {
			drawText(g, "Gained Range Exp: " + current_Experience[3], x, y = (y + 20), Color.RED);
		}
		if (current_Experience[4] != 0) {
			drawText(g, "Gained Magic Experience: " + current_Experience[4], x, y + (y + 20), Color.RED);
		}
		drawText(g, "Elapsed Time: " + formatTime(runTime), x, y + (y + 20), Color.RED);
		drawText(g, "KC: " + killCount, x, y + (y + 20), Color.RED);
		if (enable_slayer) {
			drawText(g, "Slayer: disabled" + slayer, x, y + (y + 20), Color.red);
		}
		if (!level_task) {
			drawText(g, "Task Level: " + setTask + " current: " + startLevel, x, y + (y + 20), Color.RED);
		}
		if (!amount_task) {
			drawText(g, "Task amount: " + setAmount + " current: " + left, x, y + (y + 20), Color.RED);
		}
	}

	public void drawText(Graphics g, String text, int x, int y, Color c) {
		Graphics2D g2 = (Graphics2D) g;
		Color color3 = new Color(51, 51, 51, 205);
		Font font1 = new Font("Arial", 0, 12);
		g.setFont(font1);
		fontMetrics = g.getFontMetrics();
		Rectangle textBox = new Rectangle(x, y - g.getFont().getSize(),
				(int) fontMetrics.getStringBounds(text, g).getWidth() + 8,
				(int) fontMetrics.getStringBounds(text, g).getHeight() + 6);
		Paint defaultPaint = (Paint) g2.getPaint();
		g2.setPaint(new RadialGradientPaint(
				new Point.Double(textBox.x + textBox.width / 2.0D, textBox.y + textBox.height / 2.0D),
				(float) (textBox.getWidth() / 2.0D), new float[] { 0.5F, 1.0F },
				new Color[] { new Color(color3.getRed(), color3.getGreen(), color3.getBlue(), 175),
						new Color(0.0F, 0.0F, 0.0F, 0.8F) }));
		g.fillRect(textBox.x, textBox.y + 12, textBox.width, textBox.height);
		g2.setPaint((java.awt.Paint) defaultPaint);
		g.setColor(Color.BLACK);
		g.drawRect(textBox.x, textBox.y + 12, textBox.width, textBox.height);
		g.setColor(c);
		g.drawString(text, x + 4, y + 15);
		for (int i = 0; i < text.length(); i++) {
			if (Character.isDigit(text.charAt(i))) {
				g.setColor(new Color(255, 255, 255));
				g.drawString("" + text.charAt(i), x + fontMetrics.stringWidth(text.substring(0, i)) + 4, y + 15);
			}
		}
	}

	public final String formatTime(final long ms) {
		long s = ms / 1000, m = s / 60, h = m / 60;
		s %= 60;
		m %= 60;
		h %= 24;
		return String.format("%02d:%02d:%02d", h, m, s);
	}

	@Override
	public void notify(ChatMessageEvent m) {
		int msg = 0;
		if (m.getMessage().contains("You're assigned to kill ")) {
			msg = Integer.parseInt(m.getMessage().replaceAll("\\D+", ""));
		}
		slayer = msg;

	}

}
