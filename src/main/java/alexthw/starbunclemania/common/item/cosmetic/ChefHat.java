package alexthw.starbunclemania.common.item.cosmetic;

import com.hollingsworth.arsnouveau.api.entity.IDecoratable;
import com.hollingsworth.arsnouveau.api.item.ICosmeticItem;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarStarbuncle;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarWixie;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ChefHat extends PlayerCurioCosmetic implements ICosmeticItem {

    public ChefHat(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity interactionTarget, @NotNull InteractionHand usedHand) {
        if (interactionTarget instanceof IDecoratable deco && canWear(interactionTarget)) {
            deco.setCosmeticItem(stack.split(1));
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }

    static final Vec3 wixieTransl = new Vec3(0, 0.68, 0.15);

    static final Vec3 starbScale = new Vec3(0.9, 0.8, 0.9);
    static final Vec3 starbTransl = new Vec3(0, 0.3, 0.02);

    @Override
    public Vec3 getTranslations(LivingEntity entity) {
        return switch (entity) {
            case FamiliarWixie ignored -> wixieTransl;
            case null, default -> starbTransl;
        };
    }

    @Override
    public Vec3 getScaling(LivingEntity entity) {
        return switch (entity) {
            case FamiliarWixie ignored -> defaultScaling;
            case null, default -> starbScale;
        };
    }

    @Override
    public boolean canWear(LivingEntity entity) {
        return entity instanceof FamiliarWixie || entity instanceof FamiliarStarbuncle || entity instanceof Starbuncle;
    }

    @Override
    public String getBone(LivingEntity entity) {
        return entity instanceof FamiliarWixie ? "hat" : "head";
    }

}
