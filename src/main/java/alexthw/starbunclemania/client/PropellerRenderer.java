package alexthw.starbunclemania.client;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleSparkleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Random;

import static net.minecraft.world.item.ItemDisplayContext.HEAD;

public class PropellerRenderer extends HeadCosmeticRenderer{

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float period = 40;
        float maxSpeed = 1500;

        if (!(slotContext.entity() instanceof Player player)) return;
        // render the item model over the entity's head
        matrixStack.pushPose();
        matrixStack.scale(.65F, -.65F, -.65F);
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        // rotate on Y axis based on player's head rotation
        matrixStack.mulPose(Axis.YP.rotationDegrees(-netHeadYaw));
        // rotate on X axis based on player's head rotation
        matrixStack.mulPose(Axis.XP.rotationDegrees(-headPitch));
        // if the player is midair, make the propeller spin
        matrixStack.mulPose(Axis.YP.rotationDegrees(player.onGround() ? 0 : (float) (Math.sin(ageInTicks / period) * maxSpeed)));
        // if the player is crouching, move down a bit
        matrixStack.translate(0, (player.isCrouching() ? 1.4 : 2) - player.getEyeHeight(), 0);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer, player.level(), 0);
        matrixStack.popPose();
        Random rand = ParticleUtil.r;
        Vec3 particlePos = player.position();
        float offsetY = player.isCrouching() ? 0.4f : 1f;
        float roteAngle = (float) (rand.nextFloat() * Math.PI * 2);
        if (rand.nextInt(5) == 0) {
            for (int i = 0; i < 5; i++) {
                player.level().addParticle(ParticleSparkleData.createData(new ParticleColor(52, 255, 36), 0.1f, 60),
                        particlePos.x() + Math.cos(roteAngle), particlePos.y() + 0.5 + offsetY, particlePos.z() + Math.sin(roteAngle),
                        0, 0, 0);
            }

        }
    }
}
