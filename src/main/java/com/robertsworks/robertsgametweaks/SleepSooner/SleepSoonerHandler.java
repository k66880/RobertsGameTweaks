package com.robertsworks.robertsgametweaks.SleepSooner;

import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;
import com.robertsworks.robertsgametweaks.Config.ModConfigCore;
import com.robertsworks.robertsgametweaks.util.RGTHelper;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID)
public class SleepSoonerHandler {
    // 配置参数
    private static final long SLEEP_TIME = 12541L;    // 默认睡眠时间（黄昏）

    @SubscribeEvent
    public static void onRightClickBed(PlayerInteractEvent.RightClickBlock event) {
        var sleepingAdvanceTime = ModConfigCore.sleepingAdvanceTime;
        if (sleepingAdvanceTime != 0) {
            var level = event.getLevel();
            if (level.isClientSide()) return;
    
            var pos = event.getPos();
            var state = level.getBlockState(pos);
            if (!state.is(BlockTags.BEDS)) return;
            
            var player = event.getEntity();
    
    
            // 获取当前游戏时间（0-23999）
            long currentTime = level.getDayTime() % 24000;
            
            // 计算允许跳过的起始时间
            long skipStartTime = (SLEEP_TIME - sleepingAdvanceTime + 24000) % 24000;
    
            // 检查是否在允许的时间范围内
            boolean shouldSkip = false;
            if (skipStartTime < SLEEP_TIME)
                shouldSkip = currentTime >= skipStartTime && currentTime < SLEEP_TIME;
            
            if (shouldSkip) {
                // 设置新时间（当前天数 + 睡眠时间）
                long newTime = (level.getDayTime() / 24000) * 24000 + SLEEP_TIME;
                RGTHelper.setGameTime(level, newTime);
                
                // 发送全局通知
                var playerName = player.getName().getString();
                var message = Component.translatable("tooltip.roberts_game_tweaks.rest_early", playerName);
                RGTHelper.notifyAllPlayers(level, message);
                
                // 取消原版行为
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
