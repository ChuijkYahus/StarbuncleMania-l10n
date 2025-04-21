package alexthw.starbunclemania.client;

import alexthw.starbunclemania.common.DualFluidTank;
import alexthw.starbunclemania.common.block.wixie_stations.MixerWixieCauldronTile;
import alexthw.starbunclemania.registry.ModRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import static alexthw.starbunclemania.client.JarRenderer.renderFluid;

public class WixieMixerRenderer implements BlockEntityRenderer<MixerWixieCauldronTile> {

    private static final Vector3f LIQUID_DIMENSIONS = new Vector3f(12 / 16f, 9.5f / 16f, 1 / 16f); //Width, Height, y0

    private static final Vector3f SOURCE_DIMENSIONS = new Vector3f(0.6f, 0.2f, 0.7f);

    public WixieMixerRenderer(BlockEntityRendererProvider.Context ignored) {
    }

    @Override
    public void render(@NotNull MixerWixieCauldronTile blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        //render fluid
        if (!blockEntity.converted) return;
        DualFluidTank tank = blockEntity.tank;
        for (int i = 0; i < tank.getTanks(); i++) {
            poseStack.pushPose();
            poseStack.translate(0, 0, -0.05 + 0.6 * i);
            poseStack.scale(1, 1, 0.5f);
            FluidStack fluid = tank.getFluidInTank(i);
            if (!fluid.isEmpty()) {
                renderFluid((float) fluid.getAmount() / tank.getTankCapacity(i), IClientFluidTypeExtensions.of(fluid.getFluid()).getTintColor(fluid),
                        fluid.getFluid().getFluidType().getLightLevel(), IClientFluidTypeExtensions.of(fluid.getFluid()).getStillTexture(),
                        poseStack, bufferSource, packedLight, true, LIQUID_DIMENSIONS);
            }
            poseStack.popPose();
        }

        if (blockEntity.hasSource) {
            Fluid fluid = ModRegistry.SOURCE_FLUID.get();
            renderFluid(1, IClientFluidTypeExtensions.of(fluid).getTintColor(),
                    fluid.getFluidType().getLightLevel(), IClientFluidTypeExtensions.of(fluid).getStillTexture(),
                    poseStack, bufferSource, packedLight, true, SOURCE_DIMENSIONS);
        }
    }

}
