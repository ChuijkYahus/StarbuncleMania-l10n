package alexthw.starbunclemania.mixin;

import alexthw.starbunclemania.common.block.fluids.MixinFluidProvider;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.block.tile.ModdedTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobJarTile.class)
public abstract class MobJarCapability extends ModdedTile implements MixinFluidProvider {

    public MobJarCapability(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Unique
    public final int starbuncleMania$capacity = 10000;

    @Unique
    public final FluidTank starbuncleMania$tank = new FluidTank(starbuncleMania$capacity) {
        protected void onContentsChanged() {
            MobJarCapability.this.updateBlock();
            MobJarCapability.this.setChanged();
        }
    };

    public IFluidHandler getStarbuncleMania$tank() {
        return starbuncleMania$tank;
    }


    @Inject(method = "saveAdditional", at = @At("HEAD"))
    public void starbuncleMania$saveAdditional(CompoundTag tag, HolderLookup.Provider pRegistries, CallbackInfo ci) {
        if (!starbuncleMania$tank.isEmpty()) {
            starbuncleMania$tank.writeToNBT(pRegistries, tag);
        }
    }

    @Inject(method = "loadAdditional", at = @At("HEAD"))
    public void starbuncleMania$loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries, CallbackInfo ci) {
        starbuncleMania$tank.readFromNBT(pRegistries, pTag);
    }

}
