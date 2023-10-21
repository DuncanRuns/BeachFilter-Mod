package me.duncanruns.beachfilter.mixin;

import me.duncanruns.beachfilter.BeachFilterMod;
import me.duncanruns.beachfilter.FilteringScreen;
import me.duncanruns.beachfilter.SeedManager;
import me.duncanruns.beachfilter.runner.FilterResult;
import me.voidxwalker.autoreset.Atum;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {

    private boolean doTheThing = false;

    @Shadow
    private TextFieldWidget levelNameField;

    protected CreateWorldScreenMixin() {
        super(null);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void interruptCreationMixin(CallbackInfo info) {
        if (!BeachFilterMod.shouldRun()) return;
        doTheThing = true;
        BeachFilterMod.LOGGER.info("Interrupting world creation...");

        if (SeedManager.canTake()) {
            BeachFilterMod.LOGGER.info("Seed is ready, generating...");
            FilterResult filterResult = SeedManager.take();
            BeachFilterMod.setLastToken(filterResult.toToken());
            SeedManager.find();
            Atum.seed = String.valueOf(filterResult.getWorldSeed()); // We tell atum to do it because it's too hard to work around that
        } else {
            BeachFilterMod.LOGGER.info("Seed is not ready, filtering...");
            SeedManager.find();
            Atum.hotkeyState = Atum.HotkeyState.PRE_WORLDGEN;
            client.openScreen(new FilteringScreen());
            info.cancel();
        }
    }

    @Inject(method = "createLevel", at = @At("HEAD"))
    private void renameWorld(CallbackInfo info) {
        if (doTheThing) {
            this.levelNameField.setText(this.levelNameField.getText().replace("Set", "Beach"));
        }
    }

    @Inject(method = "createLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;method_29607(Ljava/lang/String;Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/util/registry/RegistryTracker$Modifiable;Lnet/minecraft/world/gen/GeneratorOptions;)V", shift = At.Shift.BEFORE))
    private void hideTheAtumSeed(CallbackInfo ci) throws IOException {
        if (doTheThing) {
            Atum.seed = "";
            Atum.saveProperties();
        }
    }
}
