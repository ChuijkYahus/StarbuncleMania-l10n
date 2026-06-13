package alexthw.starbunclemania.common.block.fluids;

import alexthw.starbunclemania.Configs;
import alexthw.starbunclemania.registry.ModRegistry;
import com.alexthw.sauce.common.fluid.AbstractTankTile;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.entity.EntityFollowProjectile;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.AttachmentsRegistry;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

import static com.alexthw.sauce.registry.ModRegistry.SOURCE_FLUID;
import static com.alexthw.sauce.registry.ModRegistry.SOURCE_FLUID_TYPE;

public class SourceCondenserTile extends AbstractTankTile implements GeoBlockEntity, ITickable, IWandable {

    public boolean disabled = false;

    public SourceCondenserTile(BlockPos pos, BlockState state) {
        super(ModRegistry.SOURCE_CONDENSER_TILE.get(), pos, state);
        tank.setValidator((stack) -> stack.getFluid().getFluidType() == SOURCE_FLUID_TYPE.get());
    }

    public static final FluidStack tester = new FluidStack(SOURCE_FLUID.get(), 1000);

    public float getFluidPercentage() {
        return (float) super.getFluidAmount() / capacity;
    }

    @Override
    public void tick() {
        if (level instanceof ServerLevel server && level.getGameTime() % 40 == 0) {

            if (!this.tank.isEmpty() && this.tank.getFluidAmount() >= 1000) {
                IFluidHandler handler = level.getCapability(Capabilities.FluidHandler.BLOCK, getBlockPos().below(), Direction.UP);
                if (handler != null && handler.fill(tester, IFluidHandler.FluidAction.SIMULATE) > 100) {
                    int drain = handler.fill(tester, IFluidHandler.FluidAction.EXECUTE);
                    this.tank.drain(drain, IFluidHandler.FluidAction.EXECUTE);
                }
            }

            if (this.tank.fill(tester, IFluidHandler.FluidAction.SIMULATE) == 1000 && !disabled) {
                var sp = this.getLinkedSourceProvider();
                if (sp != null) {
                    var cap = this.level.getCapability(CapabilityRegistry.SOURCE_CAPABILITY, sp.first(), sp.second().orElse(null));
                    if (cap == null) {
                        this.setLinkedSourceProvider(null);
                        return;
                    }

                    if (cap.extractSource(Configs.SOURCE_TO_FLUID.get(), true) < Configs.SOURCE_TO_FLUID.get()) {
                        return;
                    }

                    cap.extractSource(Configs.SOURCE_TO_FLUID.get(), false);
                    EntityFollowProjectile.spawn(server, sp.first(), this.worldPosition);
                    this.tank.fill(tester, IFluidHandler.FluidAction.EXECUTE);
                } else if (SourceUtil.takeSourceMultipleWithParticles(getBlockPos(), level, 6, Configs.SOURCE_TO_FLUID.get()) != null) {
                    this.tank.fill(tester, IFluidHandler.FluidAction.EXECUTE);
                }
            }
        }
        updateBlock();
    }


    final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "rotate_controller", 0, e -> e.setAndContinue(RawAnimation.begin().thenLoop("floating"))));
        data.add(new AnimationController<>(this, "float_controller", 0, e -> e.setAndContinue(RawAnimation.begin().thenLoop("rotation"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Nullable
    public Pair<BlockPos, Optional<Direction>> getLinkedSourceProvider() {
        return this.getExistingDataOrNull(AttachmentsRegistry.LINKED_SOURCE_PROVIDER);
    }

    public void setLinkedSourceProvider(@Nullable Pair<BlockPos, Optional<Direction>> sourceProvider) {
        if (sourceProvider == null) {
            this.removeData(AttachmentsRegistry.LINKED_SOURCE_PROVIDER);
            return;
        }

        this.setData(AttachmentsRegistry.LINKED_SOURCE_PROVIDER, sourceProvider);
    }

    public void setLinkedSourceProvider(BlockPos pos, @Nullable Direction face) {
        this.setLinkedSourceProvider(Pair.of(pos, Optional.ofNullable(face)));
    }

    @Override
    public IWandable.Result onLastConnection(@Nullable GlobalPos storedPos, @Nullable Direction face, @Nullable LivingEntity storedEntity, Player playerEntity) {
        if (this.level == null || storedPos == null) {
            return IWandable.Result.FAIL;
        }

        ResourceKey<Level> dim = storedPos.dimension();
        if (!this.level.dimension().equals(dim)) {
            PortUtil.sendMessageNoSpam(playerEntity, Component.translatable("ars_nouveau.connections.dimension_mismatch"));
            return IWandable.Result.FAIL;
        }

        var diff = this.worldPosition.subtract(storedPos.pos());
        if (Math.abs(diff.getX()) > 10 || Math.abs(diff.getY()) > 10 || Math.abs(diff.getZ()) > 10) {
            PortUtil.sendMessageNoSpam(playerEntity, Component.translatable("ars_nouveau.connection.range", 10));
            return IWandable.Result.FAIL;
        }

        var cap = this.level.getCapability(CapabilityRegistry.SOURCE_CAPABILITY, storedPos.pos(), face);
        if (cap == null || cap.getMaxExtract() <= 0) {
            return IWandable.Result.FAIL;
        }

        var sp = this.getLinkedSourceProvider();
        if (sp != null && sp.first().equals(storedPos.pos()) && sp.second().equals(Optional.ofNullable(face))) {
            PortUtil.sendMessageNoSpam(playerEntity, Component.translatable("ars_nouveau.connections.remove"));
            this.setLinkedSourceProvider(null);
            return IWandable.Result.SUCCESS;
        }

        this.setData(AttachmentsRegistry.LINKED_SOURCE_PROVIDER, Pair.of(storedPos.pos(), Optional.ofNullable(face)));
        PortUtil.sendMessageNoSpam(playerEntity, Component.translatable("starbunclemania.connections.condenser.take_from", storedPos.pos().toShortString()));

        return IWandable.Result.SUCCESS;
    }
}
