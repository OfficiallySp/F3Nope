package com.f3nope.mixin.client;

import com.f3nope.F3Nope;
import com.f3nope.F3NopeConfig;
import com.f3nope.util.PlaceholderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugHud.class)
public class DebugHudMixin {

    @Shadow
    private MinecraftClient client;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(DrawContext context, CallbackInfo ci) {
        F3NopeConfig config = F3Nope.getConfig();

        // If vanilla debug should be hidden, cancel the original render
        if (config.hideVanillaDebug) {
            ci.cancel();

            // Only show custom text if enabled
            if (config.showCustomText) {
                renderCustomText(context, config);
            }
        }
    }

    private void renderCustomText(DrawContext context, F3NopeConfig config) {
        if (config.customTextLines.isEmpty()) {
            return;
        }

        int y = config.textY;
        int lineHeight = this.client.textRenderer.fontHeight + 1;

        for (String line : config.customTextLines) {
            if (line != null && !line.isEmpty()) {
                // Process placeholders before rendering
                String processedLine = PlaceholderUtil.replacePlaceholders(line, config, this.client);
                Text text = Text.literal(processedLine);

                if (config.textShadow) {
                    context.drawTextWithShadow(this.client.textRenderer, text, config.textX, y, config.textColor);
                } else {
                    context.drawText(this.client.textRenderer, text, config.textX, y, config.textColor, false);
                }

                y += lineHeight;
            }
        }
    }
    // Note: The shouldShowDebugHud method may not exist in all Minecraft versions
    // The render method injection above should be sufficient for most use cases
}
