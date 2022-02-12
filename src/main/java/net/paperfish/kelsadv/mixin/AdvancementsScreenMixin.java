package net.paperfish.kelsadv.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementsScreen.class)
public class AdvancementsScreenMixin extends Screen {

    @Final @Mutable
    private static int WINDOW_WIDTH = 252 * 2; // double the vanilla width
    @Final @Mutable
    private static int WINDOW_HEIGHT = 140 * 2; // double the vanilla height

    protected AdvancementsScreenMixin(Text title) {
        super(title);
    }


    @ModifyConstant(method = {"mouseClicked", "render"}, constant = @Constant(intValue = 252))
    public int swapWidth(int in) {
        return WINDOW_WIDTH;
    }
    @ModifyConstant(method = {"mouseClicked", "render"}, constant = @Constant(intValue = 140))
    public int swapHeight(int in) {
        return WINDOW_HEIGHT;
    }


    @ModifyConstant(method = "drawAdvancementTree", constant = @Constant(intValue = 234))
    public int swapTreeWidth(int in) {
        return (WINDOW_WIDTH - 18);
    }
    @ModifyConstant(method = "drawAdvancementTree", constant = @Constant(intValue = 113))
    public int swapTreeHeight(int in) {
        return (WINDOW_HEIGHT - 27);
    }

    @Redirect(method = "drawWindow", at=@At(value = "INVOKE", target = "net/minecraft/client/gui/screen/advancement/AdvancementsScreen.drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"))
    public void disableDefaultDraw(AdvancementsScreen advancementsScreen, MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
        // do nothing :b
    }

    @Inject(method = "drawWindow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/advancement/AdvancementsScreen;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V")
    )
    public void sliceRender(MatrixStack matrices, int x, int y, CallbackInfo ci) {
        // top-left corner
        this.drawTexture(matrices, x, y, 0, 0, 16, 32);
        // top-right corner
        this.drawTexture(matrices, x + WINDOW_WIDTH - 24, y, 228, 0, 24, 32);

        int xTiling = (WINDOW_WIDTH - 18) / 16 - 1;

        for (int i = 0; i < xTiling; i++) {
            // top bar
            this.drawTexture(matrices, x+16+(i*(16)), y, 16, 0, 16, 32);
            // bottom bar
            this.drawTexture(matrices, x+16+(i*(16)), y + WINDOW_HEIGHT - 16, 16, 108, 16, 32);
        }

        // side bars
        int yTiling = (WINDOW_HEIGHT - 27) / 16;

        for (int i = 0; i < yTiling; i++) {
            // middle-left bar
            this.drawTexture(matrices, x, y+32+(i*(16)), 0, 32, 16, 16);
            // middle-right bar
            this.drawTexture(matrices, x + WINDOW_WIDTH - 24, y+32+(i*(16)), 228, 32, 24, 16);
        }

//        // middle-left bar
//        this.drawTexture(matrices, x, y+32, 0, 32, 16, WINDOW_HEIGHT - (32*2));
//        // middle-right bar
//        this.drawTexture(matrices, x + WINDOW_WIDTH - 16, y+32, 236, 32, 16, WINDOW_HEIGHT - (32*2));

        // bottom-left corner
        this.drawTexture(matrices, x, y + WINDOW_HEIGHT - 8, 0, 116, 16, 24);
        // bottom-right corner
        this.drawTexture(matrices, x + WINDOW_WIDTH - 24, y + WINDOW_HEIGHT - 8, 228, 116, 24, 24);
    }
}
