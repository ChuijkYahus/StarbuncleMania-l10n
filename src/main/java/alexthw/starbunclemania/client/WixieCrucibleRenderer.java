package alexthw.starbunclemania.client;

import alexthw.eidolon_repraised.client.ClientRegistry;
import alexthw.eidolon_repraised.client.particle.Particles;
import alexthw.eidolon_repraised.registries.EidolonParticles;
import alexthw.starbunclemania.common.block.wixie_stations.CrucibleWixieCauldronTile;
import alexthw.starbunclemania.registry.ModRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import static alexthw.eidolon_repraised.common.tile.CrucibleTileRenderer.STIRRER_TEXTURE;

public class WixieCrucibleRenderer implements BlockEntityRenderer<CrucibleWixieCauldronTile> {
    private final ModelPart stirrer;
    private static final RandomSource random = RandomSource.createNewThreadLocalInstance();

    public WixieCrucibleRenderer(BlockEntityRendererProvider.Context pContext) {
        this.stirrer = Minecraft.getInstance().getEntityModels().bakeLayer(ClientRegistry.CRUCIBLE_STIRRER_LAYER).getChild("stirrer");
    }

    public void render(CrucibleWixieCauldronTile tile, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, STIRRER_TEXTURE);
        if (!tile.getLevel().getBlockState(tile.getBlockPos().above()).isFaceSturdy(tile.getLevel(), tile.getBlockPos().above(), Direction.DOWN)) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5, 0.625, 0.5);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(45.0f));
            matrixStackIn.translate(0.0, -0.0, 0.125);
            this.stirrer.xRot = 0.3926991f;
            this.stirrer.render(matrixStackIn, bufferIn.getBuffer(RenderType.entitySolid(STIRRER_TEXTURE)), combinedLightIn, combinedOverlayIn);
            matrixStackIn.popPose();
        }

        if (tile.hasSource) {
            TextureAtlasSprite water = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(IClientFluidTypeExtensions.of(ModRegistry.SOURCE_FLUID.get()).getStillTexture());
            VertexConsumer builder = bufferIn.getBuffer(RenderType.translucentMovingBlock());
            Matrix4f mat = matrixStackIn.last().pose();
            int color = IClientFluidTypeExtensions.of(ModRegistry.SOURCE_FLUID.get()).getTintColor();
            int r = FastColor.ARGB32.red(color);
            int g = FastColor.ARGB32.green(color);
            int b = FastColor.ARGB32.blue(color);
            int a = FastColor.ARGB32.alpha(color);

            builder.addVertex(mat, 0.125F, 0.75F, 0.125F).setColor(r, g, b, 192).setUv(water.getU(0.125F), water.getV(0.125F)).setLight(combinedLightIn).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(mat, 0.125F, 0.75F, 0.875F).setColor(r, g, b, 192).setUv(water.getU(0.875F), water.getV(0.125F)).setLight(combinedLightIn).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(mat, 0.875F, 0.75F, 0.875F).setColor(r, g, b, 192).setUv(water.getU(0.875F), water.getV(0.875F)).setLight(combinedLightIn).setNormal(0.0F, 1.0F, 0.0F);
            builder.addVertex(mat, 0.875F, 0.75F, 0.125F).setColor(r, g, b, 192).setUv(water.getU(0.125F), water.getV(0.875F)).setLight(combinedLightIn).setNormal(0.0F, 1.0F, 0.0F);

            if (tile.getLevel().getGameTime() % 10 == 0) {
                for (int i = 0; i < 2; ++i) {
                    Particles.create(EidolonParticles.BUBBLE_PARTICLE.get()).setScale(0.05F).setLifetime(10).addVelocity(0.0, 0.015625, 0.0).setColor(0.25f, 0.5f, 1.0f).setAlpha(1.0F, 0.75F).spawn(tile.getLevel(), (double) tile.getBlockPos().getX() + 0.125 + 0.75 * (double) random.nextFloat(), (double) tile.getBlockPos().getY() + 0.6875, (double) tile.getBlockPos().getZ() + 0.125 + 0.75 * (double) random.nextFloat());
                    if (random.nextInt(8) == 0) {
                        Particles.create(EidolonParticles.STEAM_PARTICLE.get()).setAlpha(0.0625F, 0.0F).setScale(0.375F, 0.125F).setLifetime(80).randomOffset(0.375, 0.125).randomVelocity(0.012500000186264515, 0.02500000037252903).addVelocity(0.0, 0.05000000074505806, 0.0).setColor(1.0f, 1.0f, 1.0f).spawn(tile.getLevel(), (double) tile.getBlockPos().getX() + 0.5, (double) tile.getBlockPos().getY() + 0.625, (double) tile.getBlockPos().getZ() + 0.5);
                    }
                }
            }
        }

    }
}


