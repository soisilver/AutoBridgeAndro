package com.autobridge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "autobridgelite", name = "AutoBridge Lite", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class AutoBridgeLite {

    private boolean enabled = false;
    private final Minecraft mc = Minecraft.getMinecraft();
    private int jumpCooldown = 0;

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        Keybinds.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        if (Keybinds.TOGGLE.isPressed()) {
            enabled = !enabled;
            mc.thePlayer.addChatMessage(
                new ChatComponentText("§a[AutoBridge] " + (enabled ? "ON" : "OFF")));
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (!enabled || mc.thePlayer == null || mc.theWorld == null) return;

        ItemStack held = mc.thePlayer.getHeldItem();
        if (held == null || !(held.getItem() instanceof ItemBlock)) return;

        BlockPos below = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.5, mc.thePlayer.posZ);
        if (mc.theWorld.isAirBlock(below)) {
            mc.thePlayer.rotationPitch = 85f;

            if (jumpCooldown == 0) {
                mc.thePlayer.jump();
                jumpCooldown = 4;
            } else {
                jumpCooldown--;
            }

            mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld,
                    held, below, EnumFacing.UP, 0.5F, 0.5F, 0.5F);
        }
    }
}