/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.Anvil;

import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;
import com.robertsworks.robertsgametweaks.Config.ModConfigCore;
import com.robertsworks.robertsgametweaks.util.SingleItemSlot;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AnvilSlotHandler {
    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        if (ModConfigCore.increaseMaxStackSizeForEnchantedBooks) {
            if (event.getContainer() instanceof AnvilMenu anvilMenu) {
                replaceSlot(anvilMenu, 0);
                replaceSlot(anvilMenu, 1);
            }
        }
    }

    private static void replaceSlot(AnvilMenu menu, int slotIndex) {
        Slot oldSlot = menu.slots.get(slotIndex);
        Slot newSlot = new SingleItemSlot(oldSlot.container, slotIndex, oldSlot.x, oldSlot.y);
        menu.slots.set(slotIndex, newSlot);
    }
}
