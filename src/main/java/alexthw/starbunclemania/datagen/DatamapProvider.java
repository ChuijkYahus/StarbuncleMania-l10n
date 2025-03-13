package alexthw.starbunclemania.datagen;

import alexthw.starbunclemania.registry.MekanismCompat;
import mekanism.api.datamaps.chemical.attribute.ChemicalFuel;
import mekanism.common.registries.MekanismDataMapTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DatamapProvider extends DataMapProvider {
    protected DatamapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.@NotNull Provider provider) {
        builder(MekanismDataMapTypes.INSTANCE.chemicalFuel()).add(MekanismCompat.SOURCE_GAS, new ChemicalFuel(10, 200), false);
    }
}
