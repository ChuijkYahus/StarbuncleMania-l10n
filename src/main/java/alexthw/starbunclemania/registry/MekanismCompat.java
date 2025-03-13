package alexthw.starbunclemania.registry;

import alexthw.starbunclemania.StarbuncleMania;
import mekanism.common.base.IChemicalConstant;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;
import net.neoforged.bus.api.IEventBus;

public class MekanismCompat {

    public static final ChemicalDeferredRegister GASES = new ChemicalDeferredRegister(StarbuncleMania.MODID);
    public static final DeferredChemical<?> SOURCE_GAS = GASES.register(new IChemicalConstant() {
        @Override
        public String getName() {
            return "source_gas";
        }

        @Override
        public int getColor() {
            return 0xDF9B13FB;
        }

        @Override
        public float getTemperature() {
            return 300;
        }

        @Override
        public float getDensity() {
            return 0.001F;
        }

        @Override
        public int getLightLevel() {
            return 0;
        }
    });

    public static void register(IEventBus bus) {
        GASES.register(bus);
    }

}
