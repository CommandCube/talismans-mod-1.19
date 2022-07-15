package net.galacticsnail.angelrings.item.custom;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import dev.emi.trinkets.api.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.UUID;

public class AngelRing extends TrinketItem {
//    public AngelRing(Settings settings) {
//        super(settings);
//    }
    public boolean isDurable;
    public AngelRing(Settings settings, boolean isDurable) {
        super(settings);
        this.isDurable = isDurable;
        TrinketsApi.registerTrinket(this, this);
    }

//    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
//        Multimap<EntityAttribute, EntityAttributeModifier> map = super.getModifiers(stack, slot, entity, uuid);
//        // +10% movement speed
//        map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uuid, "angelrings:movement_speed", 0.1, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
//        // If the player has access to ring slots, this will give them an extra one
//        SlotAttributes.addSlotModifier(map, "hand/ring", uuid, 1, EntityAttributeModifier.Operation.ADDITION);
//        return map;
//    }


    //@Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PlayerEntity player = (PlayerEntity) entity;
        //if the instance of the object has isDurable set to true, the item will eventually break
        if (isDurable && player.getAbilities().flying) {
            //will take additional durability off if player is sprinting
            if (player.isSprinting()) {
                //will damage ring 2% of time
                if (Math.random() < 0.02) {
                    stack.damage(2, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.LEGS));
                }
            }
            //logic for when player is not sprint flying
            else {
                if (Math.random() < 0.02) {
                    stack.damage(1, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.LEGS));
                }
            }

        }
    }

    //@Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PlayerEntity player = (PlayerEntity) entity;
        player.getAbilities().allowFlying = true;
    }

    //@Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PlayerEntity player = (PlayerEntity) entity;
        //Will not disable flight if player in creative
        if (!player.getAbilities().creativeMode && !player.isSpectator()) {
            player.getAbilities().allowFlying = false;
            player.getAbilities().flying = false;
            player.sendAbilitiesUpdate();
        }
    }

}
