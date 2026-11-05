package com.example.nosprint;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 服务端疾跑拦截监听器。
 *
 * <p>由 Forge 在 mod 构造期通过 {@link Mod.EventBusSubscriber} 自动注册到 FORGE 事件总线，
 * 因此主类里不需要（也不应该）再手动 {@code MinecraftForge.EVENT_BUS.register(...)}。
 *
 * <p>本类只有静态方法，Forge 注册的是 {@code Class} 对象，
 * 而 {@code EventBus.register(Class)} 只收集 {@code public static} 且带 {@code @SubscribeEvent} 的方法。
 */
@Mod.EventBusSubscriber(modid = NoSprint.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class SprintGuard
{
    /** 纯静态监听器，禁止实例化。 */
    private SprintGuard()
    {
    }

    /**
     * 玩家 tick 回调。{@code PlayerTickEvent} 每个 tick 触发两次（{@link TickEvent.Phase#START} 与
     * {@link TickEvent.Phase#END}），这里只在 END 阶段处理。
     *
     * <p>原因：START 早于本 tick 的移动与状态更新，此时清除疾跑后，同一 tick 内后续的原版逻辑
     * （移动、饥饿等状态判定）仍可能把 sprinting 重新置回 true；END 位于 tick 收尾，
     * 清除后能一直保持到下一次状态同步，拦截更稳定。
     */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        // 配置关闭时完全放行（默认 true，即禁止疾跑）。
        if (!NoSprintConfig.DISABLE_SPRINTING.get())
            return;

        if (event.phase != TickEvent.Phase.END)
            return;

        // 只处理逻辑服务端玩家：ServerPlayer 只存在于逻辑服务端。
        // 不能用 FMLEnvironment.dist 判断——那是「物理端」（客户端 / 专用服务器）：
        // 单人游戏时物理端是 CLIENT，但集成服务器里同样有 ServerPlayer 在跑；
        // 用 instanceof 才能同时覆盖专用服务器、单人存档和 LAN。
        if (!(event.player instanceof ServerPlayer player))
            return;

        // 创造模式与旁观模式不限制。
        if (player.isCreative() || player.isSpectator())
            return;

        // 疾跑中则取消；setSprinting(false) 会同步冲刺状态给客户端。
        if (player.isSprinting())
            player.setSprinting(false);
    }
}
