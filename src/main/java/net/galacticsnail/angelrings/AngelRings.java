package net.galacticsnail.angelrings;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.galacticsnail.angelrings.item.ModItems;
import net.galacticsnail.angelrings.item.custom.Aglet;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AngelRings implements ModInitializer {
	public static final String MOD_ID = "angelrings";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	private static final Identifier ZOMBIE_LOOT_TABLE_ID = new Identifier("minecraft", "entities/zombie");
	private static final Identifier AGLET_LOOT_TABLE_ID = new Identifier(MOD_ID, "entities/aglet");
	@Override
	public void onInitialize() {

		ModItems.registerModItems();

		updateLootTables();
	}

	private void updateLootTables() {
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && ZOMBIE_LOOT_TABLE_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(LootTableEntry.builder(AGLET_LOOT_TABLE_ID));

				tableBuilder.pool(poolBuilder);
			}
		});
	}
}
