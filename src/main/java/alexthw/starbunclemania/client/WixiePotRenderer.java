package alexthw.starbunclemania.client;

import alexthw.starbunclemania.common.block.wixie_stations.FarmerPotWixieCauldronTile;
import com.alexthw.sauce.registry.ModRegistry;
import com.hollingsworth.arsnouveau.client.renderer.tile.ArsGeoBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import static alexthw.starbunclemania.client.JarRenderer.renderFluid;

public class WixiePotRenderer extends ArsGeoBlockRenderer<FarmerPotWixieCauldronTile> {

    public WixiePotRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new WixieCookingPotModel());
    }

    public static final Vector3f LIQUID_DIMENSIONS = new Vector3f(0.7F, 0.07F, 0.7F); //Width, Height, y0

    @Override
    public void actuallyRender(PoseStack poseStack, FarmerPotWixieCauldronTile tile, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
        super.actuallyRender(poseStack, tile, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
        poseStack.pushPose();
        poseStack.translate(-0.5, 0, -0.5);
        if (tile.hasSource) {
            Fluid fluid = ModRegistry.SOURCE_FLUID.get();
            renderFluid(1, IClientFluidTypeExtensions.of(fluid).getTintColor(),
                    fluid.getFluidType().getLightLevel(), IClientFluidTypeExtensions.of(fluid).getStillTexture(),
                    poseStack, bufferSource, packedLight, true, LIQUID_DIMENSIONS);
        }
        poseStack.popPose();
    }

}
