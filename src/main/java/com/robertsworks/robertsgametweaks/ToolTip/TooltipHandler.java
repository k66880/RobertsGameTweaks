/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.ToolTip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;
import com.robertsworks.robertsgametweaks.Config.ModConfigCore;
import com.robertsworks.robertsgametweaks.util.RGTHelper;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID, value = Dist.CLIENT)
public class TooltipHandler {
    private final static Set<String> HIDE_KEYS = Set.of(
        "item.modifiers.mainhand",
        "item.modifiers.offhand",
        "item.modifiers.chest",
        "item.modifiers.feet",
        "item.modifiers.head",
        "item.modifiers.legs",
        
        "attribute.name.generic.attack_damage",
        "attribute.name.generic.attack_knockback",
        "attribute.name.generic.attack_speed",

        "attribute.name.generic.armor",
        "attribute.name.generic.armor_toughness",
        "attribute.name.generic.knockback_resistance"
    );

    // @SubscribeEvent(priority = EventPriority.LOWEST)
	// public static void gatherTooltips(RenderTooltipEvent.GatherComponents event)
	// {
    //     ItemStack stack = event.getItemStack();
    //     Item item = stack.getItem();
    //     if (item instanceof DiggerItem || item instanceof SwordItem || item instanceof ArmorItem) {
    //         var elements = event.getTooltipElements();
    //         var iterator = elements.iterator();
    //         while (iterator.hasNext()) {
    //             var line = iterator.next();
    //             if (isVanillaTooltips(line))
    //                 iterator.remove();
    //         }
    //     }
    // }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        // 移除原版工具、武器、防具信息
        removeVanillaTooltips(tooltip);
        
        // 添加mod提示信息
        List<Component> newLines = new ArrayList<>();
        addDurabilityInfo(stack, newLines);
        addAttackInfo(stack, newLines);
        addArmorInfo(stack, newLines);
        addFoodInfo(stack, newLines);
        tooltip.addAll(1, newLines);
    }

    // 添加耐久信息
    private static void addDurabilityInfo(ItemStack stack, List<Component> lines) {
        if (!ModConfigCore.showDurability) return;
        int max = stack.getMaxDamage();
        if (max > 0) {
            // 物品无法破坏
            if (RGTHelper.isUnbreakable(stack)) {
                MutableComponent value = Component.translatable("tooltip.roberts_game_tweaks.unbreakable").withStyle(ChatFormatting.GREEN);
                lines.add(makeAttributeLine(AttributeType.DURABILITY, "tooltip.roberts_game_tweaks.durability", value));
            }
            else {
                MutableComponent value = Component.empty();
                int rest = max - stack.getDamageValue();
                if (rest == max)
                    value.append(Component.literal(Integer.toString(rest)).withStyle(ChatFormatting.GREEN));
                else if (rest <= 10 && max > 10)
                    value.append(Component.literal(Integer.toString(rest)).withStyle(ChatFormatting.RED));
                else
                    value.append(Component.literal(Integer.toString(rest)).withStyle(ChatFormatting.WHITE));
                value.append(Component.literal("/" + Integer.toString(max)).withStyle(ChatFormatting.WHITE));

                lines.add(makeAttributeLine(AttributeType.DURABILITY, "tooltip.roberts_game_tweaks.durability", value));
            }
        }
    }

    // 添加攻击信息
    private static void addAttackInfo(ItemStack stack, List<Component> lines) {
        if (!ModConfigCore.showTooltipsForTools) return;

        AttackAttributes attackAttributes = AttackAttributes.LoadFromItemStack(stack);
        if (attackAttributes.hasMainHandDamage)
            lines.add(makeAttributeLine(AttributeType.ATTACK, "tooltip.roberts_game_tweaks.attack", attackAttributes.formatMainHandDamage()));
        if (attackAttributes.hasMainHandAttackSpeed)
            lines.add(makeAttributeLine(AttributeType.ATTACK, "tooltip.roberts_game_tweaks.attack_speed", attackAttributes.formatMainHandAttackSpeed()));
        if (attackAttributes.hasMainHandAttackKnockback)
            lines.add(makeAttributeLine(AttributeType.ATTACK, "tooltip.roberts_game_tweaks.attack_knockback", attackAttributes.formatMainHandAttackKnockback()));
            
        if (attackAttributes.hasOffHandDamage)
            lines.add(makeAttributeLine(AttributeType.ATTACK, "tooltip.roberts_game_tweaks.attack_offhand", attackAttributes.formatOffHandDamage()));
        if (attackAttributes.hasOffHandAttackSpeed)
            lines.add(makeAttributeLine(AttributeType.ATTACK, "tooltip.roberts_game_tweaks.attack_speed_offhand", attackAttributes.formatOffHandAttackSpeed()));
        if (attackAttributes.hasOffHandAttackKnockback)
            lines.add(makeAttributeLine(AttributeType.ATTACK, "tooltip.roberts_game_tweaks.attack_knockback_offhand", attackAttributes.formatOffHandAttackKnockback()));
    }

    // 添加护甲信息
    private static void addArmorInfo(ItemStack stack, List<Component> lines) {
        if (!ModConfigCore.showTooltipsForArmors) return;

        ArmorAttributes armorAttributes = ArmorAttributes.LoadFromItemStack(stack);
        if (armorAttributes.hasArmor)
            lines.add(makeAttributeLine(AttributeType.ARMOR, "tooltip.roberts_game_tweaks.armor", armorAttributes.formatArmor()));
        if (armorAttributes.hasArmorToughness && armorAttributes.armorToughness > 0)
            lines.add(makeAttributeLine(AttributeType.ARMOR, "tooltip.roberts_game_tweaks.armor_toughness", armorAttributes.formatArmorToughness()));
        if (armorAttributes.hasKnockbackResistance)
            lines.add(makeAttributeLine(AttributeType.ARMOR, "tooltip.roberts_game_tweaks.knockback_resistance", armorAttributes.formAtknockbackResistance()));
    }

    // 添加食物信息
    private static void addFoodInfo(ItemStack stack, List<Component> lines) {
        if (!ModConfigCore.showTooltipsForFoods) return;

        FoodAttributes foodAttributes = FoodAttributes.LoadFromItemStack(stack);
        if (foodAttributes.hasFoodProperties) {
            lines.add(makeAttributeLine(AttributeType.FOOD, "tooltip.roberts_game_tweaks.restore_hunger", foodAttributes.formatHunger()));
            lines.add(makeAttributeLine(AttributeType.FOOD, "tooltip.roberts_game_tweaks.restore_hunger", foodAttributes.formatSaturation()));
            for (FoodEffect effect : foodAttributes.effects)
                lines.add(effect.makeLine());
        }
    }

    private static Component makeAttributeLine(AttributeType attributeType, String nameKey, Component value) {
        MutableComponent line = Component.empty();
        ChatFormatting dotColor;
        switch (attributeType) {
            case DURABILITY:
                dotColor = ChatFormatting.DARK_GREEN;
                break;
            case ATTACK:
                dotColor = ChatFormatting.BLUE;
                break;
            case ARMOR:
                dotColor = ChatFormatting.DARK_AQUA;
                break;
            case FOOD:
                dotColor = ChatFormatting.GOLD;
                break;
            default:
                dotColor = ChatFormatting.GRAY;
                break;
        }
        line.append(Component.literal("◆ ").withStyle(dotColor));
        line.append(Component.translatable(nameKey));
        line.append(value);
        return line;
    }
    private static Component makeAttributeLine(AttributeType attributeType, String nameKey, String value) {
        ChatFormatting valueColor;
        switch (attributeType) {
            case DURABILITY:
                valueColor = ChatFormatting.GREEN;
                break;
            case ATTACK:
                valueColor = ChatFormatting.BLUE;
                break;
            case ARMOR:
                valueColor = ChatFormatting.AQUA;
                break;
            case FOOD:
                valueColor = ChatFormatting.GOLD;
                break;
            default:
                valueColor = ChatFormatting.WHITE;
                break;
        }
        return makeAttributeLine(attributeType, nameKey, Component.literal(value).withStyle(valueColor));
    }

    // 移除原版属性相关提示
    public static void removeVanillaTooltips(List<Component> tooltip) {
        if (!ModConfigCore.removeVanillaTooltips) return;
        Iterator<Component> iterator = tooltip.iterator();
        while (iterator.hasNext()) {
            Component line = iterator.next();
            if (isVanillaTooltips(line)) {
                iterator.remove();
            }
        }
    }

    // private static boolean isVanillaTooltips(Either<FormattedText, TooltipComponent> either) {
    //     FormattedText text = either.left().get();
    //     if (text instanceof Component component)
    //         return isVanillaTooltips(component);
    //     return false;
    // }

    private static boolean isVanillaTooltips(Component component) {
        if (component.getContents() instanceof TranslatableContents translatable) {
            String key = translatable.getKey();

            if (HIDE_KEYS.contains(key))
                return true;

            Object[] args = translatable.getArgs();
            for (Object object : args) {
                if (object instanceof Component carg) {
                    if (isVanillaTooltips(carg))
                        return true;
                }
            }
        }

        for (Component sibling : component.getSiblings()) {
            if (isVanillaTooltips(sibling))
                return true;
        }
        return false;
    }

    private static enum AttributeType {
        DURABILITY,
        ATTACK,
        ARMOR,
        FOOD
    }
}
