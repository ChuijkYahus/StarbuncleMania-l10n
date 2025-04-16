package alexthw.starbunclemania.common.block.wixie_stations;

import com.hollingsworth.arsnouveau.common.block.WixieCauldron;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StonecutterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class StonecutterWixieCauldron extends WixieCauldron {

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(StonecutterBlock.FACING);
    }

    @Override
    public WixieCauldronTile newBlockEntity(BlockPos pos, BlockState state) {
        return new StonecutterWixieCauldronTile(pos, state);
    }

    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

}
