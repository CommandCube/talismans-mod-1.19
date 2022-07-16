package net.galacticsnail.angelrings.item.custom;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class Aglet extends TrinketItem {

    public float speedMult = 1;
    public int agletIndex = -1;
    private ItemStack _itemstack;
    private SlotReference _slot;
    private LivingEntity _player;
    private UUID _uuid;
    public Aglet(Settings settings, float speedMult, int agletIndex) {
        super(settings);
        this.agletIndex = agletIndex;
        this.speedMult = speedMult - 1;
        TrinketsApi.registerTrinket(this, this);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        this._itemstack = stack; this._slot = slot; this._player = entity; this._uuid = uuid;
        Multimap<EntityAttribute, EntityAttributeModifier> map = super.getModifiers(stack, slot, entity, uuid);
        // Multiplies movement speed by speedMult
        map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid, "angelrings:movement_speed", speedMult, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        // If the player has access to ring slots, this will give them an extra one
        //SlotAttributes.addSlotModifier(map, "hand/ring", uuid, 1, EntityAttributeModifier.Operation.ADDITION);
        return map;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (agletIndex == 0) {
            tooltip.add(Text.translatable("item.angelrings.aglet.tooltip"));
        } else if (agletIndex == 1) {
            tooltip.add(Text.translatable("item.angelrings.red_aglet.tooltip"));
        } else if (agletIndex == 2) {
            tooltip.add(Text.translatable("item.angelrings.green_aglet.tooltip"));
        } else if (agletIndex == 3) {
            tooltip.add(Text.translatable("item.angelrings.blue_aglet.tooltip"));
        } else if (agletIndex == 4) {
            tooltip.add(Text.translatable("item.angelrings.super_aglet.tooltip"));
        }
    }

    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PlayerEntity player = (PlayerEntity) entity;
        World mcWorld = player.getWorld();
        //if the instance of the object has isDurable set to true, the item will drain xp to fly
        if (agletIndex == 2) {
            //player.sendMessage(player.getBlockY());
            player.setNoGravity(false);
            if (player.isOnGround()) {
                //checks to see if it should update speed based on grass blocks
                BlockPos testBlock = new BlockPos(player.getPos().x, player.getPos().y - 1, player.getPos().z);
                if (getBlock(testBlock, mcWorld).getTranslationKey().equals("block.minecraft.grass_block")) {
                    //Player standing on grass block
                    speedMult = 0.3f;
                    getModifiers(_itemstack,  _slot, _player, _uuid);
                } else {
                    //Player standing on non grass block
                    speedMult = 0f;
                    getModifiers(_itemstack,  _slot, _player, _uuid);
                }
                //player.sendMessage(Text.literal(getBlock(testBlock, mcWorld).getTranslationKey()));
            }

        }

        

    }

    private Block getBlock(BlockPos pos, World world) {
        BlockState bs = world.getBlockState(pos);
        Block block = bs.getBlock();
        return block;
    }
}
