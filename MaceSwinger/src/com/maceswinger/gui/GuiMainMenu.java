package com.maceswinger.gui;

import org.lwjgl.util.vector.Vector4f;

import com.maceswinger.client.GameClient;
import com.maceswinger.gui.components.GuiButtonArray;
import com.maceswinger.gui.components.GuiString;
import com.maceswinger.items.Item;
import com.maceswinger.items.ItemMace;
import com.maceswinger.mods.Mod;
import com.maceswinger.mods.ModuleLoader;
import com.maceswinger.utils.Font;

public class GuiMainMenu extends Gui {

	private GuiButtonArray buttons;
	private GuiString text;

	public GuiMainMenu(GameClient game, int width, int height) {
		super(game, width, height);
		buttons = new GuiButtonArray(0, this);
		text = new GuiString(1, this);
		buttons.addButton(buttons.new Button(0, "     start", 80, 300, 300, 50));
		buttons.addButton(buttons.new Button(1, "   show loaded mods", 80, 200,
				300, 50));
		buttons.addButton(buttons.new Button(2, "   test maces", 80, 120,
				300, 50));
		buttons.addButton(buttons.new Button(3, "   quit game", 80, 100, 50,
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
			System.out.println("Loaded Mods: ");
			for(Mod m: ModuleLoader.mods){
				System.out.println(m.name+" -\t"+m.desc);
			}
			break;
		case 2:
			Item mace = ItemMace.createMace();
			System.out.println(mace.getName());
			break;
		case 3:
			GameClient.exit(0);
			break;
		}

	}

}
