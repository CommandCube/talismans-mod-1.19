package net.galacticsnail.angelrings.item.custom;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import dev.emi.trinkets.api.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AngelRing extends TrinketItem {
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


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!isDurable) {
            //Uses lang file to specify tooltip to allow additional languages
            tooltip.add(Text.translatable("item.angelrings.angel_ring.tooltip"));
        } else {
            tooltip.add(Text.translatable("item.angelrings.angel_ring_breakable.tooltip"));
        }
    }

    //@Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PlayerEntity player = (PlayerEntity) entity;
        //if the instance of the object has isDurable set to true, the item will drain xp to fly
        if (isDurable && player.getAbilities().flying) {
            //will take additional durability off if player is sprinting
            float experienceDamage = 0;
            if (player.isSprinting()) {
                experienceDamage = 1.4f/50;
            } else {
                experienceDamage = 1f/50;
            }
            if (player.experienceProgress < experienceDamage && player.experienceLevel > 0) {
                player.experienceLevel --;
                player.experienceProgress = 1f;
            } else if (player.experienceProgress > experienceDamage) {
                player.experienceProgress -= experienceDamage;
            } else if (player.experienceProgress < experienceDamage && player.experienceLevel <= 0) {
                player.experienceProgress = 0f;
                if (!player.getAbilities().creativeMode && !player.isSpectator()) {
                    player.getAbilities().allowFlying = false;
                    player.getAbilities().flying = false;
                    player.sendAbilitiesUpdate();

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
