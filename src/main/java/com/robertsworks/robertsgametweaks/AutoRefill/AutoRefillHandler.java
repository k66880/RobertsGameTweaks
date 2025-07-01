package com.robertsworks.robertsgametweaks.AutoRefill;

import java.util.HashMap;
import java.util.Map;

import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;
import com.robertsworks.robertsgametweaks.Config.ModConfigCore;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID)
public class AutoRefillHandler {
    // 存储玩家最后手持物品的槽位信息（Key: 玩家, Value: 槽位+物品类型）
    private static final Map<Player, SlotInfo> lastUsedInfo = new HashMap<>();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onPlayerInteract(PlayerInteractEvent.RightClickItem event) {
        RobertsGameTweaksMod.LOGGER.info("触发onPlayerInteract事件");
        if (ModConfigCore.enableAutoRefill) {
            if (event.getSide().isServer()) {
                Player player = event.getEntity();
                int slot = player.getInventory().selected;
                lastUsedInfo.put(player, new SlotInfo(slot, event.getItemStack().getItem()));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        RobertsGameTweaksMod.LOGGER.info("触发onItemUseFinish事件");
        if (ModConfigCore.enableAutoRefill) {
            if (event.getEntity() instanceof Player player) {
                int slot = player.getInventory().selected;
                lastUsedInfo.put(player, new SlotInfo(slot, event.getItem().getItem()));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onItemDestroyed(PlayerDestroyItemEvent event) {
        RobertsGameTweaksMod.LOGGER.info("触发onItemDestroyed事件");
        if (ModConfigCore.enableAutoRefill) {
            Player player = event.getEntity();
            int slot = player.getInventory().selected;
            lastUsedInfo.put(player, new SlotInfo(slot, event.getOriginal().getItem()));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side.isServer()) {
            Player player = event.player;
            SlotInfo info = lastUsedInfo.get(player);

            if (info != null) {
                // 检查记录的槽位是否为空
                ItemStack currentStack = player.getInventory().getItem(info.slot);
                if (currentStack.isEmpty()) {
                    // 在背包中搜索同类型物品
                    for (int i = 0; i < player.getInventory().items.size(); i++) {
                        // 跳过快捷栏本身（0-8为快捷栏）
                        if (i == info.slot || (i >= 0 && i < 9)) continue;

                        ItemStack stack = player.getInventory().items.get(i);
                        if (!stack.isEmpty() && stack.getItem() == info.item) {
                            // 移动物品到快捷栏
                            player.getInventory().items.set(info.slot, stack.copy());
                            player.getInventory().items.set(i, ItemStack.EMPTY);
                            break;
                        }
                    }
                }
                // 清除记录（无论是否补充成功）
                lastUsedInfo.remove(player);
            }
        }
    }

    // 存储槽位和物品信息
    static class SlotInfo {
        final int slot;
        final Item item;

        SlotInfo(int slot, Item item) {
            this.slot = slot;
            this.item = item;
        }
    }
}
