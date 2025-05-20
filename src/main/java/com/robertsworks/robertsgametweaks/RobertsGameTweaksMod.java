/**
 * Copyright (c) 2025 Robert Wu
 * 
 * MIT License
 */
package com.robertsworks.robertsgametweaks;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.robertsworks.robertsgametweaks.Config.ModConfigCore;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RobertsGameTweaksMod.MODID)
public class RobertsGameTweaksMod {
    public static final String MODID = "roberts_game_tweaks";

    public static final Logger LOGGER = LogUtils.getLogger();

    public RobertsGameTweaksMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigCore.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        
    }
}
