package net.paperfish.kelsadv.mixin;

import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AdvancementWidget.class)
public class AdvancementWidgetMixin {
//    @Final
//    @Mutable
    private static int WINDOW_HEIGHT = 140 * 2; // double the vanilla height

    @ModifyConstant(method = {"drawTooltip"}, constant = @Constant(intValue = 113))
    public int swapHeight(int in) {
        return ((WINDOW_HEIGHT - 27) + 16);
    }
}
