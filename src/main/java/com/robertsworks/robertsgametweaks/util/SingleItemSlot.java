/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.util;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SingleItemSlot extends Slot {
    public SingleItemSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.getItem() == Items.ENCHANTED_BOOK) {
            if (!this.getItem().isEmpty()) return false;
        }
        return super.mayPlace(stack);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        if (stack.getItem() == Items.ENCHANTED_BOOK)
            return 1;
        else
            return super.getMaxStackSize();
    }

    @Override
    public void set(ItemStack stack) {
        if (stack.getItem() == Items.ENCHANTED_BOOK)
            super.set(stack.copyWithCount(1));
        else
            super.set(stack);
    }
}
