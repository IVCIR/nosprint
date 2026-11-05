package com.example.nosprint;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * No Sprint 的配置定义。
 *
 * <p>只有一项 {@code disableSprinting}，默认 {@code true}（禁止疾跑）。
 * 该文件由 {@code NoSprint} 构造器通过
 * {@code context.registerConfig(ModConfig.Type.COMMON, NoSprintConfig.SPEC)} 注册，
 * 生成位置为 {@code config/nosprint-common.toml}。
 */
public final class NoSprintConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    /** 是否禁止玩家疾跑。默认 true。 */
    public static final ForgeConfigSpec.BooleanValue DISABLE_SPRINTING = BUILDER
            .comment("是否禁止玩家疾跑")
            .define("disableSprinting", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    private NoSprintConfig()
    {
    }
}
