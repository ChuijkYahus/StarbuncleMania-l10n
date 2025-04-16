package alexthw.starbunclemania.starbuncle.item;

import alexthw.starbunclemania.StarbuncleMania;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.goal.carbuncle.StarbyTransportBehavior;
import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import com.hollingsworth.arsnouveau.common.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.HashMap;
import java.util.Map;

public class RRobinItemTransport extends StarbyTransportBehavior {

    public static final ResourceLocation TRANSPORT_ID = ResourceLocation.fromNamespaceAndPath(StarbuncleMania.MODID, "starby_robin_item_transport");

    public RRobinItemTransport(Starbuncle entity, CompoundTag tag) {
        super(entity, tag);
    }

    @Override
    public ResourceLocation getRegistryName() {
        return TRANSPORT_ID;
    }

    private final Map<Item, Integer> itemRoundRobinIndex = new HashMap<>();

    @Override
    public boolean isPickupDisabled() {
        return true;
    }

    @Override
    public BlockPos getValidStorePos(ItemStack stack) {
        return getValidStorePos(stack, true);
    }

    public BlockPos getValidStorePos(ItemStack stack, boolean advanceIndex) {
        if (TO_LIST.isEmpty() || stack.isEmpty())
            return null;

        BlockPos returnPos = null;
        Item item = stack.getItem();
        int size = TO_LIST.size();
        int startIndex = itemRoundRobinIndex.getOrDefault(item, 0);
        ItemScroll.SortPref foundPref = ItemScroll.SortPref.INVALID;
        int winningIndex = -1;

        for (int i = 0; i < size; i++) {
            int index = (startIndex + i) % size;
            BlockPos b = TO_LIST.get(index);
            ItemScroll.SortPref pref = sortPrefForStack(b, stack);

            if (pref.ordinal() > foundPref.ordinal()) {
                foundPref = pref;
                returnPos = b;
                winningIndex = index;

                if (foundPref == ItemScroll.SortPref.HIGHEST) break;
            }
        }

        if (advanceIndex && returnPos != null) {
            itemRoundRobinIndex.put(item, (winningIndex + 1) % size);
        }

        return returnPos;
    }


    /**
     * Returns the maximum stack size an inventory can accept for a particular stack.
     * Does all needed validity checks without advancing the round-robin state.
     */
    public int getMaxTake(ItemStack stack) {
        BlockPos validStorePos = getValidStorePos(stack, false); // peek mode
        if (validStorePos == null) return -1;

        IItemHandler handler = getItemCapFromTile(validStorePos, FROM_DIRECTION_MAP.get(validStorePos.hashCode()));
        if (handler == null)
            return -1;

        try {
            // assumption: if validStorePos is not null, TO_LIST is not empty

            // try to take evenly from all slots, based on the item's max stack size
            int maxEvenTake = Math.ceilDiv(stack.getMaxStackSize(), TO_LIST.size());

            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack handlerStack = handler.getStackInSlot(i);
                // if the slot is empty, we can take as much as we want
                if (handlerStack.isEmpty()) {
                    return Math.clamp(handler.getSlotLimit(i), 1, maxEvenTake);
                } else if (ItemUtil.canStack(handlerStack, stack)) {
                    // if the slot is not empty, we can only take as much as the slot can hold
                    int originalCount = stack.getCount();
                    ItemStack simStack = handler.insertItem(i, stack, true); // simulate insert
                    int maxRoom = originalCount - simStack.getCount();
                    if (maxRoom > 0) {
                        return Math.min(maxRoom, Math.clamp(handler.getSlotLimit(i), 1, maxEvenTake));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error while getting max take" + e);
        }

        return -1;
    }

    public boolean isPositionValidTake(BlockPos p) {
        if (p == null || !level.isLoaded(p)) return false;

        Direction face = FROM_DIRECTION_MAP.get(p.hashCode());
        IItemHandler iItemHandler = getItemCapFromTile(p, face);

        if (iItemHandler == null) return false;

        for (int j = 0; j < iItemHandler.getSlots(); j++) {
            ItemStack stack = iItemHandler.getStackInSlot(j);
            if (!stack.isEmpty() && getValidStorePos(stack, false) != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canGoToBed() {
        return isBedPowered() || (getValidTakePos() == null && (starbuncle.getHeldStack().isEmpty() || getValidStorePos(starbuncle.getHeldStack(), false) == null));
    }

}
