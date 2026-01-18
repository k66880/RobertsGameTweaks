/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.util;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RGTHelper {
    /** 判断指定的物品是否可食用 */
    public static boolean isEdible(Item item) {
        return getFoodProperties(item) != null;
    }

    /** 获取指定的物品的食物属性 */
    @SuppressWarnings("deprecation")
    public static FoodProperties getFoodProperties(Item item) {
        return item.getFoodProperties();
    }

    /**
     * 向全体玩家发送一个通知
     * @param level 要通知的世界
     * @param message 要通知的内容
     * @return 返回是否通知成功
     */
    public static boolean notifyAllPlayers(Level level, Component message) {
        try {
            if (level instanceof ServerLevel serverLevel) {
                MinecraftServer server = serverLevel.getServer();
                server.getPlayerList().broadcastSystemMessage(message, false);
                return true;
            }
        } catch (Exception e) {}
        return false;
    }

    /**
     * 设置游戏时间
     * @param level 要设置的世界
     * @param ticks 要设置的时间（tick数，20Tick = 1Second）
     * @return
     */
    public static boolean setGameTime(Level level, long ticks) {
        try {
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.setDayTime(ticks);
                return true;
            }
        } catch (Exception e) {}
        return false;
    }

    /**
     * 克隆一个ItemStack，堆叠数量与原ItemStack相同
     * @param stack 要克隆的ItemStack
     * @return
     */
    public static ItemStack clonItemStack(ItemStack stack) {
        return clonItemStack(stack, stack.getCount());
    }

    /**
     * 克隆一个ItemStack
     * @param stack 要克隆的ItemStack
     * @param count 新的ItemStack的堆叠数量
     * @return
     */
    public static ItemStack clonItemStack(ItemStack stack, int count) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        var newStack = stack.copy();
        newStack.setCount(count);
        return newStack;
    }
}
