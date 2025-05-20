/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */package com.robertsworks.robertsgametweaks.ToolTip;

import java.text.DecimalFormat;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class AttackAttributes {
    private static final double BASE_ATTACK_DAMAGE = 1.0;
    private static final double BASE_ATTACK_SPEED = 4.0;
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public boolean hasMainHandDamage = false;
    public boolean hasMainHandAttackSpeed = false;
    public boolean hasMainHandAttackKnockback = false;

    public boolean hasOffHandDamage = false;
    public boolean hasOffHandAttackSpeed = false;
    public boolean hasOffHandAttackKnockback = false;

    public double mainHandDamage = 0;
    public double mainHandAttackSpeed = 0;
    public double mainHandAttackKnockback = 0;
    
    public double offHandDamage = 0;
    public double offHandAttackSpeed = 0;
    public double offHandAttackKnockback = 0;

    public String formatMainHandDamage() {
        return df.format(BASE_ATTACK_DAMAGE + mainHandDamage);
    }

    public String formatMainHandAttackSpeed() {
        return df.format(BASE_ATTACK_SPEED + mainHandAttackSpeed);
    }

    public String formatMainHandAttackKnockback() {
        return "+" + df.format(mainHandAttackKnockback);
    }

    public String formatOffHandDamage() {
        return df.format(BASE_ATTACK_DAMAGE + offHandDamage);
    }

    public String formatOffHandAttackSpeed() {
        return df.format(BASE_ATTACK_SPEED + offHandAttackSpeed);
    }

    public String formatOffHandAttackKnockback() {
        return "+" + df.format(offHandAttackKnockback);
    }

    public static AttackAttributes LoadFromItemStack(ItemStack stack) {
        AttackAttributes res = new AttackAttributes();

        stack.forEachModifier(EquipmentSlot.MAINHAND, (holder, attr) -> {
            if (holder.equals(Attributes.ATTACK_DAMAGE)) {
                res.hasMainHandDamage = true;
                res.mainHandDamage = attr.amount();
            }
            else if (holder.equals(Attributes.ATTACK_SPEED)) {
                res.hasMainHandAttackSpeed = true;
                res.mainHandAttackSpeed = attr.amount();
            }
            else if (holder.equals(Attributes.ATTACK_KNOCKBACK)) {
                res.hasMainHandAttackKnockback = true;
                res.mainHandAttackKnockback = attr.amount();
            }
        });

        stack.forEachModifier(EquipmentSlot.OFFHAND, (holder, attr) -> {
            if (holder.equals(Attributes.ATTACK_DAMAGE)) {
                res.hasOffHandDamage = true;
                res.offHandDamage = attr.amount();
            }
            else if (holder.equals(Attributes.ATTACK_SPEED)) {
                res.hasOffHandAttackSpeed = true;
                res.offHandAttackSpeed = attr.amount();
            }
            else if (holder.equals(Attributes.ATTACK_KNOCKBACK)) {
                res.hasOffHandAttackKnockback = true;
                res.offHandAttackKnockback = attr.amount();
            }
        });

        return res;
    }
}
