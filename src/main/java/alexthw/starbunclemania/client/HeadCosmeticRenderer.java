package alexthw.starbunclemania.client;

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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import static net.minecraft.world.item.ItemDisplayContext.HEAD;

public class HeadCosmeticRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!(slotContext.entity() instanceof Player player)) return;
        // render the item model over the entity's head
        matrixStack.pushPose();
        matrixStack.scale(.65F, -.65F, -.65F);
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        // rotate on Y axis based on player's head rotation
        matrixStack.mulPose(Axis.YP.rotationDegrees(-netHeadYaw));
        // rotate on X axis based on player's head rotation
        matrixStack.mulPose(Axis.XP.rotationDegrees(-headPitch));
        matrixStack.translate(0, (player.isCrouching() ? 1.4 : 2) - player.getEyeHeight(), 0);
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, HEAD, light, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer, player.level(), 0);
        matrixStack.popPose();

    }
}
