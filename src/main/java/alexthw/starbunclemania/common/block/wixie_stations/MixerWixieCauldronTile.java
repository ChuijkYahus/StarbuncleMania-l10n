package alexthw.starbunclemania.common.block.wixie_stations;

import alexthw.starbunclemania.common.DualFluidTank;
import alexthw.starbunclemania.mixin.WixieCauldronTileAccessor;
import alexthw.starbunclemania.registry.ModRegistry;
import alexthw.starbunclemania.wixie.WaterMilkRecipeWrapper;
import com.hollingsworth.arsnouveau.api.recipe.MultiRecipeWrapper;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class MixerWixieCauldronTile extends WixieCauldronTile {

    public DualFluidTank tank = new DualFluidTank(10000, 10000,
            fs -> fs.getFluid() == Fluids.WATER,
            fs -> fs.getFluid() == NeoForgeMod.MILK.get()) {

        @Override
        public void contentsChangedCallback() {
            MixerWixieCauldronTile.this.setChanged();
            MixerWixieCauldronTile.this.updateBlock();
        }
    };

    boolean hasMilk;
    boolean hasWater;

    public MixerWixieCauldronTile(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return ModRegistry.MIXER_WIXIE_CAULDRON_TILE.get();
    }

    static FluidStack milkTester = new FluidStack(NeoForgeMod.MILK.get(), 1000);
    static FluidStack waterTester = new FluidStack(Fluids.WATER, 1000);

    public MultiRecipeWrapper getRecipesForStack(ItemStack stack) {
        hasMilk = tank.drain(milkTester, IFluidHandler.FluidAction.SIMULATE).getAmount() >= 1000;
        hasWater = tank.drain(waterTester, IFluidHandler.FluidAction.SIMULATE).getAmount() >= 1000;
        return WaterMilkRecipeWrapper.fromStack(stack, level, hasMilk, hasWater);
    }

    @Override
    public void onCraftingComplete() {
        super.onCraftingComplete();

        if (level == null || level.isClientSide) {
            return;
        }

        ItemStack stack = ((WixieCauldronTileAccessor) this).getStackBeingCrafted();
        if (getRecipesForStack(stack) instanceof WaterMilkRecipeWrapper wrapper) {
            if (wrapper.needWater) {
                tank.drain(waterTester, IFluidHandler.FluidAction.EXECUTE);
            }
            if (wrapper.needMilk) {
                tank.drain(milkTester, IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider pRegistries) {
        super.saveAdditional(tag, pRegistries);
        if (!tank.isEmpty()) {
            tank.writeToNBT(pRegistries, tag);
        }
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag pTag, HolderLookup.@NotNull Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        tank.readFromNBT(pRegistries, pTag);
    }

    public boolean bucketInteract(Player player, InteractionHand hand) {
        return FluidUtil.interactWithFluidHandler(player, hand, tank);
    }

}
