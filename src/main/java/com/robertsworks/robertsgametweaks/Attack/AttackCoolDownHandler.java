/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks.Attack;

import com.robertsworks.robertsgametweaks.RobertsGameTweaksMod;
import com.robertsworks.robertsgametweaks.Config.ModConfigCore;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RobertsGameTweaksMod.MODID, value = Dist.CLIENT)
public class AttackCoolDownHandler {

    @SubscribeEvent
    public static void onPlayerAttackInput(InputEvent.InteractionKeyMappingTriggered event) {
        if (ModConfigCore.banAttackWhenCooldown) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || !event.isAttack()) return;

            // 获取攻击冷却进度，1.0表示冷却完成
            float attackStrength = mc.player.getAttackStrengthScale(0.0F);

            if (attackStrength < 1.0F) {
                // 取消事件以阻止攻击动作和服务端数据包发送
                event.setCanceled(true);
                // 阻止手部摆动动画
                event.setSwingHand(false);
            }
        }
    }
}
