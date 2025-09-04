package com.robertsworks.robertsgametweaks.ToolTip;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class HarvestLevel {
    /** 获取指定挖掘工具的挖掘等级（1~4） */
    public static int getHarvestLevel(ItemStack stack) {
        // 方法1: 通过Tier系统获取（原版工具）
        if (stack.getItem() instanceof TieredItem) {
            Tier tier = ((TieredItem) stack.getItem()).getTier();
            return tier.getLevel() + 1;
        }
        
        // 方法2: 通过Forge的TierSortingRegistry获取（模组工具）
        // Optional<Integer> forgeLevel = TierSortingRegistry.getSortedTiers().stream()
        //     .filter(tier -> tier.getTag() != null && stack.is(tier.getTag()))
        //     .map(Tier::getLevel)
        //     .max(Integer::compare);
            
        // if (forgeLevel.isPresent()) {
        //     return forgeLevel.get();
        // }
        
        // 方法3: 通过NBT标签获取（自定义工具）
        if (stack.hasTag() && stack.getTag().contains("HarvestLevel")) {
            return stack.getTag().getInt("HarvestLevel") + 1;
        }
        
        // 方法4: 通过工具能力检测
        return detectMiningLevelByTesting(stack) + 1;
    }
    
    public static Component getHarvestLevelName(int level) {
        if (level == 1) return Component.translatable("tooltip.roberts_game_tweaks.harvest_level_1");
        if (level == 2) return Component.translatable("tooltip.roberts_game_tweaks.harvest_level_2");
        if (level == 3) return Component.translatable("tooltip.roberts_game_tweaks.harvest_level_3");
        if (level == 4) return Component.translatable("tooltip.roberts_game_tweaks.harvest_level_4");
        if (level == 5) return Component.translatable("tooltip.roberts_game_tweaks.harvest_level_5");
        return Component.translatable("tooltip.roberts_game_tweaks.harvest_level_0");
    }

    private static int detectMiningLevelByTesting(ItemStack stack) {
        // 通过测试工具对不同硬度方块的挖掘能力来判断等级
        if (canMineBlock(stack, Blocks.OBSIDIAN.defaultBlockState())) return 3;
        if (canMineBlock(stack, Blocks.DIAMOND_ORE.defaultBlockState())) return 2;
        if (canMineBlock(stack, Blocks.IRON_ORE.defaultBlockState())) return 1;
        if (canMineBlock(stack, Blocks.STONE.defaultBlockState())) return 0;
        return -1; // 无法挖掘石头
    }

    private static boolean canMineBlock(ItemStack stack, BlockState state) {
        return stack.isCorrectToolForDrops(state);
    }
}
