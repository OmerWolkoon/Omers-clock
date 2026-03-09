package com.omer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import eu.midnightdust.lib.config.MidnightConfig;

public class OmersClockClient implements ClientModInitializer {
    private static KeyMapping toggleKey;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void onInitializeClient() {
        // Register Toggle Key (Default: K)
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "Toggle omers-clock",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                KeyMapping.Category.MISC
        ));

        MidnightConfig.init("omers-clock", OmersClockConfig.class);

        // Register commands
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            // /clock-toggle
            dispatcher.register(ClientCommandManager.literal("clock-toggle")
                    .executes(context -> {
                        OmersClockConfig.visible = !OmersClockConfig.visible;
                        context.getSource().sendFeedback(Component.literal("Real World Clock: " + (OmersClockConfig.visible ? "Enabled" : "Disabled"))
                                .withStyle(OmersClockConfig.visible ? ChatFormatting.AQUA : ChatFormatting.GRAY));
                        return 1;
                    })
            );

            // /clock-config (Fixed with thread scheduling)
            dispatcher.register(ClientCommandManager.literal("clock-config")
                    .executes(context -> {
                        Minecraft client = Minecraft.getInstance();
                        client.execute(() -> {
                            client.setScreen(MidnightConfig.getScreen(client.screen, "omers-clock"));
                        });
                        return 1;
                    })
            );
        });

        // Handle Key Press to toggle visibility
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                OmersClockConfig.visible = !OmersClockConfig.visible;
                if (client.player != null) {
                    client.player.displayClientMessage(Component.literal("Real World Clock: " + (OmersClockConfig.visible ? "Enabled" : "Disabled"))
                            .withStyle(OmersClockConfig.visible ? ChatFormatting.AQUA : ChatFormatting.GRAY), true);
                }
            }
        });

        // Register the HUD Renderer
        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> {
            Minecraft client = Minecraft.getInstance();
            if (client.options.hideGui || client.level == null || !OmersClockConfig.visible) return;

            renderClock(guiGraphics, client);
        });
    }

    private void renderClock(GuiGraphics graphics, Minecraft client) {
        Font font = client.font;
        String realTime = LocalDateTime.now().format(TIME_FORMATTER);
        Component fullText = Component.literal(realTime);
        
        int textColor = 0xFFFFFFFF;
        try {
            String hex = OmersClockConfig.color;
            if (hex.startsWith("#")) hex = hex.substring(1);
            textColor = (int) Long.parseLong((hex.length() == 6 ? "FF" : "") + hex, 16);
        } catch (Exception ignored) {}
        
        int width = font.width(realTime);
        int x = OmersClockConfig.posX;
        int y = OmersClockConfig.posY;
        int padding = 5;

        int alphaValue = (int) (OmersClockConfig.backgroundOpacity * 2.55);
        int finalBgColor = (alphaValue << 24); 

        // Draw background FIRST
        if (OmersClockConfig.showBackground) {
            graphics.fill(x - padding, y - padding, x + width + padding, y + font.lineHeight + padding, finalBgColor);
        }

        // Draw text ON TOP
        graphics.drawString(font, fullText, x, y, textColor, false);
    }
}