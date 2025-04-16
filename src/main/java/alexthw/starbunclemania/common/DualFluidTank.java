package alexthw.starbunclemania.common;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class DualFluidTank implements IFluidHandler {
    protected final FluidTank[] tanks = new FluidTank[2];

    public DualFluidTank(int capacity1, int capacity2) {
        this(capacity1, capacity2, fs -> true, fs -> true);
    }

    public DualFluidTank(int capacity1, int capacity2, Predicate<FluidStack> validator1, Predicate<FluidStack> validator2) {
        tanks[0] = new FluidTank(capacity1, validator1) {
            @Override
            protected void onContentsChanged() {
                contentsChangedCallback();
            }
        };
        tanks[1] = new FluidTank(capacity2, validator2) {
            @Override
            protected void onContentsChanged() {
                contentsChangedCallback();
            }
        };
    }

    public void contentsChangedCallback() {
    }

    @Override
    public int getTanks() {
        return tanks.length;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        validateTankIndex(tank);
        return tanks[tank].getFluid().copy(); // return copy to obey interface contract
    }

    @Override
    public int getTankCapacity(int tank) {
        validateTankIndex(tank);
        return tanks[tank].getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        validateTankIndex(tank);
        return tanks[tank].isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, @NotNull FluidAction action) {
        if (resource.isEmpty()) return 0;

        for (FluidTank tank : tanks) {
            if (tank.isFluidValid(resource)) {
                int filled = tank.fill(resource, action);
                if (filled > 0) return filled;
            }
        }

        return 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, @NotNull FluidAction action) {
        if (resource.isEmpty()) return FluidStack.EMPTY;

        for (FluidTank tank : tanks) {
            FluidStack drained = tank.drain(resource, action);
            if (!drained.isEmpty()) return drained;
        }

        return FluidStack.EMPTY;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action) {
        for (FluidTank tank : tanks) {
            FluidStack drained = tank.drain(maxDrain, action);
            if (!drained.isEmpty()) return drained;
        }

        return FluidStack.EMPTY;
    }

    private void validateTankIndex(int index) {
        if (index < 0 || index >= tanks.length) {
            throw new IndexOutOfBoundsException("Tank index out of bounds: " + index);
        }
    }

    public CompoundTag writeToNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
        CompoundTag tank0Tag = new CompoundTag();
        CompoundTag tank1Tag = new CompoundTag();

        tanks[0].writeToNBT(lookupProvider, tank0Tag);
        tanks[1].writeToNBT(lookupProvider, tank1Tag);

        nbt.put("Tank0", tank0Tag);
        nbt.put("Tank1", tank1Tag);

        return nbt;
    }

    public DualFluidTank readFromNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
        if (nbt.contains("Tank0")) {
            tanks[0].readFromNBT(lookupProvider, nbt.getCompound("Tank0"));
        }

        if (nbt.contains("Tank1", Tag.TAG_COMPOUND)) {
            tanks[1].readFromNBT(lookupProvider, nbt.getCompound("Tank1"));
        }

        return this;
    }


    public boolean isEmpty() {
        return tanks[0].getFluid().isEmpty() && tanks[1].getFluid().isEmpty();
    }

}
