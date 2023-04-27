package me.duncanruns.beachfilter.mixin;

import me.duncanruns.beachfilter.BeachFilterMod;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {
    protected LevelLoadingScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = {@At("TAIL")}, locals = LocalCapture.CAPTURE_FAILSOFT)
    public void atum_modifyString(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci, String ignored, long l, int i, int j) {
        if (BeachFilterMod.shouldRun()) {
            this.drawCenteredString(matrices, this.textRenderer, "Beach Seed", i, j - 4 - 50, 16777215);
        }
    }
}
