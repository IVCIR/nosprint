# No Sprint

服务端模组：禁止玩家疾跑。不依赖任何前置模组，仅需安装在服务端（客户端可不装）。

- Minecraft: 1.20.1
- Forge: 47.4.28（推荐版）
- Java: 17
- modid: `nosprint`
- 包名: `com.example.nosprint`

## 结构

```
src/main/java/com/example/nosprint/
  NoSprint.java      主类，@Mod，只负责 mod 生命周期
  SprintGuard.java   FORGE 总线监听器，每 tick 末尾清除疾跑
src/main/resources/
  META-INF/mods.toml modid / 版本 / 依赖声明（displayTest=IGNORE_SERVER_VERSION）
  pack.mcmeta        pack_format 15
```

## 行为

`SprintGuard.onPlayerTick` 在 `TickEvent.Phase.END` 阶段：

1. 只处理 `ServerPlayer`（专用服务器、单人集成服务器、LAN 都覆盖）；
2. 跳过创造模式与旁观模式；
3. 若 `isSprinting()` 则 `setSprinting(false)`。

## 配置

首次启动生成 `config/nosprint-common.toml`：

```toml
#是否禁止玩家疾跑
disableSprinting = true
```

改为 `false` 并重启服务端即恢复疾跑（默认 `true`）。

## 构建

```powershell
.\gradlew build
```

产物：`build\libs\nosprint-1.0.0.jar`，放入服务端 `mods/` 目录即可。

## 开发运行

```powershell
.\gradlew runServer    # 首次需在 run\eula.txt 里把 eula 改为 true
.\gradlew runClient
```

## 已知限制

客户端按住疾跑键会持续重发 `START_SPRINTING`，服务端每 tick 清除一次；客户端本地预测可能出现轻微速度回弹。
彻底消除需在网络层拦截 `ServerboundPlayerCommandPacket`（START_SPRINTING），当前版本未实现。
