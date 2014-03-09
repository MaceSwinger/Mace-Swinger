package com.maceswinger.gui;

import org.lwjgl.util.vector.Vector4f;

import com.maceswinger.client.GameClient;
import com.maceswinger.gui.components.GuiButtonArray;
import com.maceswinger.gui.components.GuiString;
import com.maceswinger.utils.Font;

public class GuiMainMenu extends Gui {

	private GuiButtonArray buttons;
	private GuiString text;

	public GuiMainMenu(GameClient game, int width, int height) {
		super(game, width, height);
		buttons = new GuiButtonArray(0, this);
		text = new GuiString(1, this);
		// text.addString(text.new Text(0, "beefdrippinggames 2014", 75,
		// Game.HEIGHT - 35));
		buttons.addButton(buttons.new Button(0, "     start", 80, 300, 300, 50));
		buttons.addButton(buttons.new Button(1, "   something else", 80, 200,
				300, 50));
		buttons.addButton(buttons.new Button(2, "   quit game", 80, 100, 300,
				50));
	}

	@Override
	public void render() {
		text.render();
		buttons.render();
		// this.renderImage(1, 100, 100, 200, 200);
		Font.drawString("Main", 100, 460, 1f, new Vector4f(0, 0, 1, 1));
		Font.drawString("Menu", 100, 380, 1f, new Vector4f(0, 0, 1, 1));
		Font.draw8bitString("wooah", 400, 360, 6f, new Vector4f(0, 0, 1, 1));
		super.render();
	}

	@Override
	public void tick(int ticks) {
		tick++;
		super.tick(ticks);
		if (tick < 20)
			return;
		buttons.tick(ticks);
		text.tick(ticks);

	}

	@Override
	public void guiActionPerformed(int elementId, int action) {
		switch (elementId) {
		case 0:
			game.startGame();
			game.closeGui();
			break;
		case 1:
			break;
		case 2:
			GameClient.exit(0);
			break;
		}

	}

}
