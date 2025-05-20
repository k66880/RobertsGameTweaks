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

    private static final ForgeConfigSpec.BooleanValue SHOW_DURABILITY = BUILDER
        .comment("Whether to show the durability info")
        .define("showDurability", true);

    private static final ForgeConfigSpec.BooleanValue SHOW_TOOLTIPS_FOR_WEAPONS = BUILDER
        .comment("Whether to show the tooltips for weapons")
        .define("showTooltipsForWeapons", true);

    private static final ForgeConfigSpec.BooleanValue SHOW_TOOLTIPS_FOR_ARMORS = BUILDER
        .comment("Whether to show the tooltips for armors")
        .define("showTooltipsForArmors", true);

    private static final ForgeConfigSpec.BooleanValue SHOW_TOOLTIPS_FOR_FOODS = BUILDER
        .comment("Whether to show the tooltips for foods")
        .define("showTooltipsForFoods", true);

    private static final ForgeConfigSpec.BooleanValue REMOVE_VANILLA_TOOLTIPS = BUILDER
        .comment("Whether to remove the vanilla tooltips")
        .define("removeVanillaTooltips", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    /** 是否启用“玩家在攻击冷却时无法攻击” */
    public static boolean banAttackWhenCooldown;

    /** 是否启用“玩家在吃饱的状态下可以继续吃” */
    public static boolean couldHaveFoodWhenFull;

    /** 是否启用“增加雪球、鸡蛋、末影珍珠、蜂蜜瓶、旗帜、盔甲架堆叠上限至64” */
    public static boolean increaseMaxStackSizeTo64;

    /** 是否启用“增加附魔书堆叠上限至16” */
    public static boolean increaseMaxStackSizeForEnchantedBooks;

    /** 是否启用“增加普通药水、喷溅药水、滞留药水堆叠上限至16” */
    public static boolean increaseMaxStackSizeForPotions;

    /** 是否启用“增加鞍、船、矿车、床堆叠上限至16” */
    public static boolean increaseMaxStackSizeForOthers;

    /** 是否显示耐久度信息 */
    public static boolean showDurability;

    /** 是否显示武器的属性信息 */
    public static boolean showTooltipsForWeapons;

    /** 是否显示防具的属性信息 */
    public static boolean showTooltipsForArmors;

    /** 是否显示食物的属性信息 */
    public static boolean showTooltipsForFoods;

    /** 是否移除原版游戏的武器、工具、防具属性信息显示 */
    public static boolean removeVanillaTooltips;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        banAttackWhenCooldown = BAN_ATTACK_WHEN_COOLDOWN.get();

        couldHaveFoodWhenFull = COULD_HAVE_FOOD_WHEN_FULL.get();

        increaseMaxStackSizeTo64 = INCREASE_MAX_STACK_SIZE_TO_64.get();
        increaseMaxStackSizeForEnchantedBooks = INCREASE_MAX_STACK_SIZE_TO_16_FOR_ENCHANTED_BOOKS.get();
        increaseMaxStackSizeForPotions = INCREASE_MAX_STACK_SIZE_TO_16_FOR_POTIONS.get();
        increaseMaxStackSizeForOthers = INCREASE_MAX_STACK_SIZE_TO_16_FOR_OTHERS.get();

        showDurability = SHOW_DURABILITY.get();
        showTooltipsForWeapons = SHOW_TOOLTIPS_FOR_WEAPONS.get();
        showTooltipsForArmors = SHOW_TOOLTIPS_FOR_ARMORS.get();
        showTooltipsForFoods = SHOW_TOOLTIPS_FOR_FOODS.get();
        removeVanillaTooltips = REMOVE_VANILLA_TOOLTIPS.get();
    }
}
