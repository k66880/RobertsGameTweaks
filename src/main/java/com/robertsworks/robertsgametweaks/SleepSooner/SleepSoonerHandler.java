package com.robertsworks.robertsgametweaks.SleepSooner;

import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID)
public class SleepSoonerHandler {
    // 配置参数
    private static final long SLEEP_TIME = 12541L;    // 默认睡眠时间（黄昏）
    private static final long ADVANCE_TIME = 1000L;    // 允许提前的时间范围（ticks）

    @SubscribeEvent
    public static void onRightClickBed(PlayerInteractEvent.RightClickBlock event) {
        var level = event.getLevel();
        if (level.isClientSide()) return;

        var pos = event.getPos();
        var state = level.getBlockState(pos);
        if (!state.is(BlockTags.BEDS)) return;
        
        var player = event.getEntity();


        // 获取当前游戏时间（0-23999）
        long currentTime = level.getDayTime() % 24000;
        
        // 计算允许跳过的起始时间
        long skipStartTime = (SLEEP_TIME - ADVANCE_TIME + 24000) % 24000;

        // 检查是否在允许的时间范围内
        boolean shouldSkip = false;
        
        if (skipStartTime < SLEEP_TIME) {
            // 正常范围（无跨天）
            shouldSkip = currentTime >= skipStartTime && currentTime < SLEEP_TIME;
        } else {
            // 跨天范围（处理午夜边界）
            shouldSkip = currentTime >= skipStartTime || currentTime < SLEEP_TIME;
        }
        
        if (shouldSkip) {
            // 设置新时间（当前天数 + 睡眠时间）
            long newTime = (level.getDayTime() / 24000) * 24000 + SLEEP_TIME;

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.setDayTime(newTime);
            }
            
            
            // 发送全局通知
            String message = player.getName().getString() + " 提前休息，夜幕降临！";
            notifyAllPlayers(level, Component.literal(message));
            
            // 取消原版行为
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }

        // // 检查点击的是否是床
        // if (!(state.getBlock() instanceof BedBlock)) {
        //     return;
        // }

        // // 只在服务器端处理
        // if (world.isClientSide) {
        //     return;
        // }

        // long worldTime = world.getDayTime() % 24000; // 获取当天时间（0-23999）
        // if (worldTime >= 12000 && worldTime < 12540) {
        //     // // 计算新时间（保留天数信息）
        //     // long totalTime = world.getDayTime();
        //     // long currentDay = totalTime / 24000;
        //     // long newTime = currentDay * 24000 + 12540;
        //     long newTime = 12540;

        //     // 使用ServerLevel的setDayTime方法设置时间
        //     if (world instanceof ServerLevel serverLevel) {
        //         serverLevel.setDayTime(newTime);
                
        //         // 通知玩家
        //         player.displayClientMessage(Component.literal("时间已快进至黎明！"), true);
                
        //         // 取消原版睡觉行为
        //         event.setCanceled(true);
        //     }
        // }
    }

    // 通知所有在线玩家
    private static void notifyAllPlayers(Level level, Component message) {
        if (level instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            server.getPlayerList().broadcastSystemMessage(message, false);
        }
    }
}
