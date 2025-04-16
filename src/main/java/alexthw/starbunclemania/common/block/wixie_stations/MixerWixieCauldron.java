package alexthw.starbunclemania.common.block.wixie_stations;

import com.hollingsworth.arsnouveau.common.block.WixieCauldron;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.jetbrains.annotations.NotNull;

public class MixerWixieCauldron extends WixieCauldron {

    public MixerWixieCauldron() {
        super();
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public WixieCauldronTile newBlockEntity(BlockPos pos, BlockState state) {
        return new MixerWixieCauldronTile(pos, state);
    }

    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (stack.getCapability(Capabilities.FluidHandler.ITEM) != null || stack.getItem() instanceof BucketItem) {
            if (worldIn.getBlockEntity(pos) instanceof MixerWixieCauldronTile be && be.bucketInteract(player, handIn)) {
                return ItemInteractionResult.sidedSuccess(worldIn.isClientSide());
            }
        }
        return super.useItemOn(stack, state, worldIn, pos, player, handIn, hit);
    }
}
