package com.maceswinger.gui;

import org.lwjgl.opengl.GL11;

import com.maceswinger.client.GameClient;
import com.maceswinger.gui.components.GuiTexturedButtonArray;
import com.maceswinger.mods.Mod;
import com.maceswinger.mods.ModuleLoader;
import com.maceswinger.utils.Easing;
import com.maceswinger.utils.EnumTextures;

public class GuiMainMenu extends Gui {

	private GuiTexturedButtonArray buttons;
	

	public GuiMainMenu(GameClient game, int width, int height) {
		super(game, width, height);
		buttons = new GuiTexturedButtonArray(0, this);
		buttons.addButton(buttons.new TexturedButton(0, EnumTextures.Play.getID(),EnumTextures.PlayT.getID(), 500, 360, 400,80));
		buttons.addButton(buttons.new TexturedButton(1, EnumTextures.Settings.getID(), EnumTextures.SettingsT.getID(), 500, 280,
				400, 80));
		buttons.addButton(buttons.new TexturedButton(2, EnumTextures.Mods.getID(), EnumTextures.ModsT.getID(), 500, 190,
				400, 80));
		buttons.addButton(buttons.new TexturedButton(3, EnumTextures.Quit.getID(), EnumTextures.QuitT.getID(), 500, 110, 400,
				80));
	}
	
	float a=1.00f;
	@Override
	public void render() {
		GL11.glColor4f(1,1,1,1);
		a= Easing.elasticIn(tick, 0.00f, a, 2000);
		this.renderImage(EnumTextures.BG_Sky.getID(), GameClient.width, GameClient.height, 0, 0);
		this.fillScreen(0,0,1,a);
		this.renderImage(EnumTextures.BG_Ground.getID(), GameClient.width, GameClient.height, 0, 0);
		buttons.render();
		this.renderImage(EnumTextures.LeftHand.getID(), GameClient.width, GameClient.height, 0, 0);
		this.renderImage(EnumTextures.Legs.getID(), GameClient.width, GameClient.height, 0, 0);
		this.renderImage(EnumTextures.Torso.getID(), GameClient.width, GameClient.height, 0, 0);//make it a button?
		this.renderImage(EnumTextures.Logo.getID(), GameClient.width, GameClient.height, 0, 0);
		this.renderImage(EnumTextures.Vignette.getID(), GameClient.width, GameClient.height, 0, 0);
		super.render();
	}

	@Override
	public void tick(int ticks) {
		tick++;
		super.tick(ticks);
		if (tick < 20)
			return;
		buttons.tick(ticks);


	}

	@Override
	public void guiActionPerformed(int elementId, int action) {
		switch (elementId) {
		case 0:
			game.startGame();
			game.closeGui();
			break;
		case 2:
			System.out.println("Loaded Mods: ");
			for(Mod m: ModuleLoader.mods){
				System.out.println(m.name+" -\t"+m.desc);
			}
			break;
		
		case 3:
			GameClient.exit(0);
			break;
		}

	}

}
