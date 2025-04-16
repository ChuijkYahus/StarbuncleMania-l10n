package alexthw.starbunclemania.mixin;

import alexthw.starbunclemania.Configs;
import alexthw.starbunclemania.common.block.fluids.MixinFluidProvider;
import com.hollingsworth.arsnouveau.client.renderer.tile.MobJarRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static alexthw.starbunclemania.client.JarRenderer.renderFluid;

@Mixin(MobJarRenderer.class)
public class MobJarRendererMixin {

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"))
    public void starbuncleMania$renderHead(BlockEntity par1, float par2, PoseStack par3, MultiBufferSource par4, int par5, int par6, CallbackInfo ci) {
        par3.pushPose();
    }

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("TAIL"))
    public void starbuncleMania$renderTail(BlockEntity par1, float par2, PoseStack par3, MultiBufferSource par4, int par5, int par6, CallbackInfo ci) {
        par3.popPose();
        if (!(par1 instanceof MixinFluidProvider tile) || !Configs.MOB_JAR_RENDER.get()) {
            return;
        }
        var LIQUID_DIMENSIONS = new Vector3f(12 / 16f, 10f / 16f, 1 / 16f);
        IFluidHandler tank = tile.getStarbuncleMania$tank();
        FluidStack fluidHolder = tank.getFluidInTank(0);
        if (!fluidHolder.isEmpty()) {
            renderFluid((float) fluidHolder.getAmount() / 2 / tank.getTankCapacity(0), IClientFluidTypeExtensions.of(fluidHolder.getFluid()).getTintColor(fluidHolder),
                    fluidHolder.getFluid().getFluidType().getLightLevel(), IClientFluidTypeExtensions.of(fluidHolder.getFluid()).getStillTexture(),
                    par3, par4, par5, true, LIQUID_DIMENSIONS);
        }

    }
}
