/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.util;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SingleItemSlot extends Slot {
    public SingleItemSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        // 仅当槽位为空时才允许放置
        return this.getItem().isEmpty();
    }

    @Override
    public int getMaxStackSize() {
        // 最大堆叠数为1
        return 1;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        // 无论放入什么物品都限制为1
        return 1;
    }

    @Override
    public void set(ItemStack stack) {
        // 确保放入的堆叠数量不超过1
        super.set(stack.copyWithCount(1));
    }
}
