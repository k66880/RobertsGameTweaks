/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.ItemStack;

import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;
import com.robertsworks.robertsgametweaks.Config.ModConfigCore;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.SaddleItem;
import net.minecraftforge.event.GatherComponentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemStackModifier {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItem(GatherComponentsEvent.Item itemEvent) {
        Item item = itemEvent.getOwner();
        var size = getMaxStackSize(item);
        if (size != -1)
            setMaxStackSize(itemEvent, size);
    }

    private static int getMaxStackSize(Item item) {
        if (ModConfigCore.increaseMaxStackSizeTo64) {
            // 雪球
            if (item == Items.SNOWBALL) return 64;
            // 鸡蛋
            if (item == Items.EGG) return 64;
            // 末影珍珠
            if (item == Items.ENDER_PEARL) return 64;
            // 蜂蜜瓶
            if (item == Items.HONEY_BOTTLE) return 64;
            // 旗帜
            if (item instanceof BannerItem) return 64;
            // 盔甲架
            if (item == Items.ARMOR_STAND) return 64;
        }

        if (ModConfigCore.increaseMaxStackSizeForEnchantedBooks) {
            // 附魔书
            if (item == Items.ENCHANTED_BOOK) return 16;
        }

        if (ModConfigCore.increaseMaxStackSizeForPotions) {
            // 药水
            if (item == Items.POTION) return 16;
            // 喷溅药水
            if (item == Items.SPLASH_POTION) return 16;
            // 滞留药水
            if (item == Items.LINGERING_POTION) return 16;
        }
        
        if (ModConfigCore.increaseMaxStackSizeForOthers) {
            // 鞍
            if (item instanceof SaddleItem) return 16;
            // 船
            if (item instanceof BoatItem) return 16;
            // 矿车
            if (item instanceof MinecartItem) return 16;
            // 床
            if (item instanceof BedItem) return 16;
        }

        return -1;
    }

    private static void setMaxStackSize(GatherComponentsEvent.Item itemEvent, int size) {
        itemEvent.register(DataComponents.MAX_STACK_SIZE, size);
    }
}
