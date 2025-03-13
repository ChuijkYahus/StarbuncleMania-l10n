package alexthw.starbunclemania.registry;

import alexthw.starbunclemania.StarbuncleMania;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MekanismCompat {

    public static final DeferredRegister<Chemical> GASES = DeferredRegister.create(MekanismAPI.CHEMICAL_REGISTRY_NAME, StarbuncleMania.MODID);
    public static final DeferredHolder<Chemical, Chemical> SOURCE_GAS = GASES.register("source_gas", () -> new Chemical(ChemicalBuilder.builder().tint(0xDF9B13FB)));

    public static void register(IEventBus bus) {
        GASES.register(bus);
    }

}
