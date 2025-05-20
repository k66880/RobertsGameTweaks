/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.Food;

import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;
import com.robertsworks.robertsgametweaks.Config.ModConfigCore;
import com.robertsworks.robertsgametweaks.util.RGTHelper;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID)
public class FoodEventHandler {

    @SubscribeEvent
    public static void onPlayerRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (ModConfigCore.couldHaveFoodWhenFull) {
            Player player = event.getEntity();
            ItemStack stack = event.getItemStack();
            Item item = stack.getItem();

            if (!RGTHelper.isEdible(item)) return;

            // 当饱食度已满时
            if (player.getFoodData().getFoodLevel() >= 20) {
                InteractionHand hand = event.getHand();

                // 强制触发进食动作
                player.startUsingItem(hand);

                // 确认玩家已进入进食状态
                if (player.isUsingItem()) {
                    event.setCanceled(true);
                    event.setCancellationResult(InteractionResult.CONSUME);
                }
            }
        }
    }
}
