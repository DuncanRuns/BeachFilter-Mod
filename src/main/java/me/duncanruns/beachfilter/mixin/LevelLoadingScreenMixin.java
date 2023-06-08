package me.duncanruns.beachfilter.mixin;

import me.duncanruns.beachfilter.BeachFilterMod;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {
    protected LevelLoadingScreenMixin(Text title) {
        super(title);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/LevelLoadingScreen;drawCenteredString(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"))
    private void drawMoreText(LevelLoadingScreen instance, TextRenderer textRenderer, String str, int centerX, int y, int color) {
        this.drawCenteredString(textRenderer, str, centerX, y, color);
        if (BeachFilterMod.shouldRun()) {
            this.drawCenteredString(textRenderer, "Beach Seed", centerX, y - 20, color);
        }
    }
}
