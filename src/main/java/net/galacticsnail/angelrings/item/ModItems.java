package net.galacticsnail.angelrings.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.galacticsnail.angelrings.AngelRings;
import net.galacticsnail.angelrings.item.custom.AngelRing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item ANGEL_RING = registerItem("angel_ring",
            new AngelRing(new FabricItemSettings().group(ItemGroup.TOOLS)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(AngelRings.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AngelRings.LOGGER.info("Registering Mod Items for " + AngelRings.MOD_ID);
    }
}
