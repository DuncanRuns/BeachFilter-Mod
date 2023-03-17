package me.duncanruns.beachfilter;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class FilteringScreen extends Screen {
    public FilteringScreen() {
        super(new LiteralText("Filtering Seeds..."));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        renderBackground(matrices);
        this.drawCenteredString(matrices, this.textRenderer, "Filtering Seeds...", width / 2, height / 3, 0xFFFFFF);
    }

    @Override
    public void tick() {
        if (SeedManager.canTake()) {
            client.openScreen(new CreateWorldScreen(null));
        }
    }
}
