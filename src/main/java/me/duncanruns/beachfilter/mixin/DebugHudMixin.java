package me.duncanruns.beachfilter.mixin;

import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {

    @ModifyVariable(method = "renderRightText", at = @At("STORE"), ordinal = 0)
    private List<String> lateModifyRightText(List<String> strings) {
        for (int i = strings.size() - 1; i >= 0; i--) {
            if (strings.get(i).equals("Resetting a random seed")) {
                strings.set(i, "Resetting a beach seed");
                break;
            }
        }
        return strings;
    }
}
