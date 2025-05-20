/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */package com.robertsworks.robertsgametweaks.ToolTip;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.robertsworks.robertsgametweaks.util.RGTHelper;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.PossibleEffect;
import net.minecraft.world.item.ItemStack;

public class FoodAttributes {
    private static final DecimalFormat df = new DecimalFormat("#.##");

    /** 当前有没有食物属性 */
    public boolean hasFoodProperties = false;
    /** 恢复饥饿值 */
    public int hunger = 0;
    /** 恢复饱和度 */
    public float saturation = 0;
    /** 附带效果 */
    public List<FoodEffect> effects = new ArrayList<>();

    public String formatHunger() {
        return df.format(hunger);
    }

    public String formatSaturation() {
        return df.format(saturation);
    }

    public static FoodAttributes LoadFromItemStack(ItemStack stack) {
        FoodAttributes res = new FoodAttributes();

        FoodProperties foodProperties = RGTHelper.getFoodProperties(stack.getItem());
        if (foodProperties != null) {
            res.hasFoodProperties = true;

            // 恢复饥饿值
            res.hunger = foodProperties.nutrition();

            // 恢复饱和度
            res.saturation = foodProperties.saturation();
            
            // 附带效果
            for (PossibleEffect possibleEffect : foodProperties.effects()) {
                FoodEffect foodEffect = FoodEffect.LoadFromPossibleEffect(possibleEffect);
                res.effects.add(foodEffect);
            }
        }

        return res;
    }
}
