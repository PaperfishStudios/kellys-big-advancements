package net.paperfish.kelsadv.mixin;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AdvancementTab.class)
public class AdvancementTabMixin extends DrawableHelper {

    @Final
    @Mutable
    private static int WINDOW_WIDTH = 252 * 2; // double the vanilla width
    @Final @Mutable
    private static int WINDOW_HEIGHT = 140 * 2; // double the vanilla height

    @ModifyConstant(method = {"render", "drawWidgetTooltip"}, constant = @Constant(intValue = 234))
    public int swapWidth(int in) {
        return (WINDOW_WIDTH - 18);
    }
    @ModifyConstant(method = {"render", "drawWidgetTooltip"}, constant = @Constant(intValue = 113))
    public int swapHeight(int in) {
        return ((WINDOW_HEIGHT - 27) + 16);
    }

    @ModifyConstant(method = {"move"}, constant = @Constant(intValue = 234))
    public int scrollWidth(int in) {
        return ((WINDOW_WIDTH - 18));
    }
    @ModifyConstant(method = {"move"}, constant = @Constant(intValue = 113))
    public int scrollHeight(int in) {
        return (((WINDOW_HEIGHT - 27) + 16));
    }

    @ModifyConstant(method = {"render"}, constant = @Constant(intValue = 117))
    public int swapTreeWidth(int in) {
        return ((WINDOW_WIDTH / 2) + 9);
    }
    @ModifyConstant(method = {"render"}, constant = @Constant(intValue = 56))
    public int swapTreeHeight(int in) {
        return ((WINDOW_HEIGHT / 2));
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 15))
    private int getXBgTiling(int in) { return (WINDOW_WIDTH - 18) / 16 + 1; }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 8))
    private int getYBgTiling(int in) { return (WINDOW_HEIGHT - 27) / 16 + 2; }
}
