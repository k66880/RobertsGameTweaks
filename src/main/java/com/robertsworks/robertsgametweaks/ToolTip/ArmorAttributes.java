/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.ToolTip;

import java.text.DecimalFormat;

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
            var slot = armorItem.getSlot();
            
            res.hasArmor = true;
            res.armor = material.getDefenseForSlot(slot);
            
            res.hasArmorToughness = true;
            res.armorToughness = material.getToughness();
            
            res.knockbackResistance = material.getKnockbackResistance();
            res.hasKnockbackResistance = res.knockbackResistance > 0;
        }

        if (stack.getItem() instanceof HorseArmorItem horseArmorItem) {
            res.hasArmor = true;
            res.armor = horseArmorItem.getProtection();
        }

        return res;
    }
}
