/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */package com.robertsworks.robertsgametweaks.ToolTip;

import java.text.DecimalFormat;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

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
        
        var modifiers = stack.getDefaultAttributeModifiers().modifiers();
        for (ItemAttributeModifiers.Entry entry : modifiers) {
            var holder = entry.attribute();
            var attr = entry.modifier();
            if (holder.equals(Attributes.ARMOR)) {
                res.hasArmor = true;
                res.armor = attr.amount();
            }
            else if (holder.equals(Attributes.ARMOR_TOUGHNESS)) {
                res.hasArmorToughness = true;
                res.armorToughness = attr.amount();
            }
            else if (holder.equals(Attributes.KNOCKBACK_RESISTANCE)) {
                res.hasKnockbackResistance = true;
                res.knockbackResistance = attr.amount();
            }
        }

        // stack.getDefaultAttributeModifiers().forEach(EquipmentSlot., (holder, attr) -> {
        //     if (holder.equals(Attributes.ARMOR)) {
        //         res.hasArmor = true;
        //         res.armor = attr.amount();
        //     }
        //     else if (holder.equals(Attributes.ARMOR_TOUGHNESS)) {
        //         res.hasArmorToughness = true;
        //         res.armorToughness = attr.amount();
        //     }
        //     else if (holder.equals(Attributes.KNOCKBACK_RESISTANCE)) {
        //         res.hasKnockbackResistance = true;
        //         res.knockbackResistance = attr.amount();
        //     }
        // });

        return res;
    }
}
