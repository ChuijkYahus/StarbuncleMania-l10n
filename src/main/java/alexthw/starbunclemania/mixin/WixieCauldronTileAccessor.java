package alexthw.starbunclemania.mixin;

import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = WixieCauldronTile.class, remap = false)
public interface WixieCauldronTileAccessor {

    @Accessor
    ItemStack getStackBeingCrafted();

}
