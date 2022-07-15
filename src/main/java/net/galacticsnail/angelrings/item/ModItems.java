package net.galacticsnail.angelrings.item;

import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.galacticsnail.angelrings.AngelRings;
import net.galacticsnail.angelrings.item.custom.AngelRing;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item ANGEL_RING = registerItem("angel_ring",
            new AngelRing(new FabricItemSettings().group(ItemGroup.TOOLS), false));

    public static final Item ANGEL_RING_BREAKABLE = registerItem("angel_ring_breakable",
            new AngelRing(new FabricItemSettings().group(ItemGroup.TOOLS), true));
//
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(AngelRings.MOD_ID, name), item);
    }
    //TrinketItem(ANGEL_RING.Settings);

    //TrinketsApi.registerTrinket(ANGEL_RING, new AngelRing());

    public static void registerModItems() {
        AngelRings.LOGGER.info("Registering Mod Items for " + AngelRings.MOD_ID);
    }
}
