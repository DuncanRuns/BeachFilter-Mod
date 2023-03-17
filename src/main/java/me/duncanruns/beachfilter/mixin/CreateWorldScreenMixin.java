package me.duncanruns.beachfilter.mixin;

import me.duncanruns.beachfilter.BeachFilterMod;
import me.duncanruns.beachfilter.FilteringScreen;
import me.duncanruns.beachfilter.SeedManager;
import me.duncanruns.beachfilter.runner.FilterResult;
import me.voidxwalker.autoreset.Atum;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {

    protected CreateWorldScreenMixin() {
        super(null);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void interruptCreationMixin(CallbackInfo info) {
        if (!Atum.isRunning) return;

        if (SeedManager.canTake()) {
            FilterResult filterResult = SeedManager.take();
            BeachFilterMod.setLastToken(filterResult.toToken());
            SeedManager.find();
            Atum.seed = Long.toString(filterResult.getWorldSeed());
        } else {
            SeedManager.find();
            client.openScreen(new FilteringScreen());
            info.cancel();
        }
    }
}
