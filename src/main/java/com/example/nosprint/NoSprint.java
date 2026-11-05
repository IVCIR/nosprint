package com.example.nosprint;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NoSprint.MODID)
public class NoSprint
{
    /** 必须与 gradle.properties 的 mod_id、mods.toml 的 modId 一致。 */
    public static final String MODID = "nosprint";

    private static final Logger LOGGER = LogUtils.getLogger();

    public NoSprint(FMLJavaModLoadingContext context)
    {
        // mod 生命周期事件总线；疾跑拦截由 SprintGuard 上的 @Mod.EventBusSubscriber 自动注册，
        // 这里不再手动注册到 MinecraftForge.EVENT_BUS。
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        // COMMON 配置：生成 config/nosprint-common.toml
        context.registerConfig(ModConfig.Type.COMMON, NoSprintConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("[No Sprint] common setup complete");
    }
}
