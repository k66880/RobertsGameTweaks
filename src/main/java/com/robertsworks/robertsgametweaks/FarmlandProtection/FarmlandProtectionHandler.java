package com.robertsworks.robertsgametweaks.FarmlandProtection;

import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;
import com.robertsworks.robertsgametweaks.Config.ModConfigCore;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID)
public class FarmlandProtectionHandler {
    @SubscribeEvent
    public static void onFarmlandTrample(BlockEvent.FarmlandTrampleEvent event) {
        if (ModConfigCore.enableFarmlandProtection) {
            Entity entity = event.getEntity();
            // 仅当实体是玩家时取消践踏事件
            if (entity instanceof Player) {
                event.setCanceled(true);
            }
        }
    }
}
