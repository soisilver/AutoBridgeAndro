package com.autobridge;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {
    public static final KeyBinding TOGGLE =
        new KeyBinding("key.autobridge.toggle", Keyboard.KEY_R, "key.categories.autobridge");

    public static void init() {
        ClientRegistry.registerKeyBinding(TOGGLE);
    }
}