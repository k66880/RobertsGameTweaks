/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.ToolTip;

import java.text.DecimalFormat;

import com.google.common.collect.Multimap;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;

public class ArmorAttributes {
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public boolean hasArmor = false;
    public boolean hasArmorToughness = false;
    public boolean hasKnockbackResistance = false;

    public double armor = 0;
    public double armorToughness = 0;
    public double knockbackResistance = 0;

    public String formatArmor() {
        return "+" + df.format(armor);
    }

    public String formatArmorToughness() {
        return "+" + df.format(armorToughness);
    }

    public String formAtknockbackResistance() {
        return "+" + df.format(knockbackResistance * 10);
    }

    public static ArmorAttributes LoadFromItemStack(ItemStack stack) {
        ArmorAttributes res = new ArmorAttributes();
        
        if (stack.getItem() instanceof ArmorItem armorItem) {
            ArmorMaterial material = armorItem.getMaterial();
            ArmorItem.Type type = armorItem.getType();
            
            res.hasArmor = true;
            res.armor = material.getDefenseForType(type);
            
            res.hasArmorToughness = true;
            res.armorToughness = material.getToughness();
            
            Multimap<Attribute, AttributeModifier> attributes = stack.getAttributeModifiers(armorItem.getEquipmentSlot());
            if (attributes.containsKey(Attributes.KNOCKBACK_RESISTANCE)) {
                res.hasKnockbackResistance = true;
                res.knockbackResistance = getAttributeValue(attributes, Attributes.KNOCKBACK_RESISTANCE);
            }
        }

        if (stack.getItem() instanceof HorseArmorItem horseArmorItem) {
            res.hasArmor = true;
            res.armor = horseArmorItem.getProtection();
        }

        return res;
    }

    private static double getAttributeValue(Multimap<Attribute, AttributeModifier> attributes, Attribute attribute) {
        return attributes.get(attribute).stream()
                .mapToDouble(AttributeModifier::getAmount)
                .sum();
    }
}
