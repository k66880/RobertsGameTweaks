/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.Config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfigCore
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue BAN_ATTACK_WHEN_COOLDOWN = BUILDER
        .comment("Whether to enable \"player cannot attack during attack cooldown\"")
        .define("banAttackWhenCooldown", true);

    private static final ForgeConfigSpec.BooleanValue COULD_HAVE_FOOD_WHEN_FULL = BUILDER
        .comment("Whether to enable \"player still could have food when full\"")
        .define("couldHaveFoodWhenFull", true);

    private static final ForgeConfigSpec.BooleanValue ENABLE_AUTO_REFILL = BUILDER
        .comment("Whether to enable \"refill the item after used or placed\"")
        .define("enableAutoRefill", true);

    private static final ForgeConfigSpec.BooleanValue ENABLE_FARMLAND_PROTECTION = BUILDER
        .comment("Whether to enable \"protect the farmland when player jumping\"")
        .define("enableFarmlandProtection", true);

    private static final ForgeConfigSpec.IntValue SLEEPING_ADVANCE_TIME = BUILDER
        .comment("The ticks that allowing players to sleep earlier (20Ticks = 1Second)")
        .defineInRange("sleepingAdvanceTime", 1200, 0, 6000);

    private static final ForgeConfigSpec.BooleanValue INCREASE_MAX_STACK_SIZE_TO_64 = BUILDER
        .comment("Whether to enable \"increase the max stack size to 64 for snowball, egg, ender pearl, honey bottle, banners and armor stand\"")
        .define("increaseMaxStackSizeTo64", true);

    private static final ForgeConfigSpec.BooleanValue INCREASE_MAX_STACK_SIZE_TO_16_FOR_ENCHANTED_BOOKS = BUILDER
        .comment("Whether to enable \"increase the max stack size to 16 for enchanted books\"")
        .define("increaseMaxStackSizeForEnchantedBooks", true);

    private static final ForgeConfigSpec.BooleanValue INCREASE_MAX_STACK_SIZE_TO_16_FOR_POTIONS = BUILDER
        .comment("Whether to enable \"increase the max stack size to 16 for potions\"")
        .define("increaseMaxStackSizeForPotions", true);

    private static final ForgeConfigSpec.BooleanValue INCREASE_MAX_STACK_SIZE_TO_16_FOR_OTHERS = BUILDER
        .comment("Whether to enable \"increase the max stack size to 16 for saddles, boats, minecarts, beds\"")
        .define("increaseMaxStackSizeForOthers", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    /** 是否启用“玩家在攻击冷却时无法攻击” */
    public static boolean banAttackWhenCooldown;

    /** 是否启用“玩家在吃饱的状态下可以继续吃” */
    public static boolean couldHaveFoodWhenFull;

    /** 是否启用“当手持物品被使用或放置后若背包存在同物品则自动填充” */
    public static boolean enableAutoRefill;

    /** 是否启用“当玩家在耕地上跳跃时保护耕地” */
    public static boolean enableFarmlandProtection;

    /** 允许玩家提前睡觉的游戏时刻数 */
    public static long sleepingAdvanceTime;

    /** 是否启用“增加雪球、鸡蛋、末影珍珠、蜂蜜瓶、旗帜、盔甲架堆叠上限至64” */
    public static boolean increaseMaxStackSizeTo64;

    /** 是否启用“增加附魔书堆叠上限至16” */
    public static boolean increaseMaxStackSizeForEnchantedBooks;

    /** 是否启用“增加普通药水、喷溅药水、滞留药水堆叠上限至16” */
    public static boolean increaseMaxStackSizeForPotions;

    /** 是否启用“增加鞍、船、矿车、床堆叠上限至16” */
    public static boolean increaseMaxStackSizeForOthers;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        banAttackWhenCooldown = BAN_ATTACK_WHEN_COOLDOWN.get();

        couldHaveFoodWhenFull = COULD_HAVE_FOOD_WHEN_FULL.get();

        enableAutoRefill = ENABLE_AUTO_REFILL.get();

        enableFarmlandProtection = ENABLE_FARMLAND_PROTECTION.get();

        sleepingAdvanceTime = (long)SLEEPING_ADVANCE_TIME.get();

        increaseMaxStackSizeTo64 = INCREASE_MAX_STACK_SIZE_TO_64.get();
        increaseMaxStackSizeForEnchantedBooks = INCREASE_MAX_STACK_SIZE_TO_16_FOR_ENCHANTED_BOOKS.get();
        increaseMaxStackSizeForPotions = INCREASE_MAX_STACK_SIZE_TO_16_FOR_POTIONS.get();
        increaseMaxStackSizeForOthers = INCREASE_MAX_STACK_SIZE_TO_16_FOR_OTHERS.get();
    }
}
