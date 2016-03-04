package com.dyn.instructor.proxy;

import org.lwjgl.input.Keyboard;

import com.dyn.instructor.gui.Home;
import com.dyn.server.ServerMod;
import com.dyn.server.packets.PacketDispatcher;
import com.dyn.server.packets.server.RequestUserlistMessage;
import com.rabbit.gui.GuiFoundation;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

public class Client implements Proxy {

	private KeyBinding teacherKey;

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {

		if ((Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
			return;
		}
		if (ServerMod.opped && this.teacherKey.isPressed()) {
			PacketDispatcher.sendToServer(new RequestUserlistMessage());
			GuiFoundation.proxy.display(new Home());
		}
	}

	@Override
	public void init() {
		FMLCommonHandler.instance().bus().register(this);

		this.teacherKey = new KeyBinding("key.toggle.teacherui", Keyboard.KEY_K, "key.categories.toggle");

		ClientRegistry.registerKeyBinding(this.teacherKey);
	}
}