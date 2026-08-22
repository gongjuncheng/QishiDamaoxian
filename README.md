# 骑士大冒险（QishiDamaoxian）

> 一款基于 **HTML5 Canvas** 的横屏动作小游戏，复刻自 Scratch 原作《峡谷骑士大冒险》。
> 操控骑士在沙漠峡谷中走位、释放技能击退敌人，维持生命值并冲击更高分数。

![Platform](https://img.shields.io/badge/platform-Android-brightgreen)
![Build APK](https://github.com/gongjuncheng/QishiDamaoxian/actions/workflows/build-apk.yml/badge.svg)
![Version](https://img.shields.io/badge/version-1.2.0-blue)
![License](https://img.shields.io/badge/license-MIT-yellow)
![Engine](https://img.shields.io/badge/engine-HTML5%20Canvas-orange)

## 📸 游戏截图

| 游戏画面 | 骑士素材 |
| --- | --- |
| <img src="Picture/original/Screenshot_20260819_212638_edit_35046427395164-removebg-preview.png" width="320" alt="游戏截图"> | <img src="Picture/original/mmexport1787146227623-removebg-preview.png" width="180" alt="骑士"> |

## ✨ 特性

- **零依赖纯前端**：单个 `index.html` 即可运行，无需构建工具。
- **Canvas 渲染 + DOM UI**：游戏场景用 `<canvas>` 绘制，HUD / 摇杆 / 技能按钮为 DOM 层。
- **虚拟摇杆操控**：支持鼠标拖拽与触摸，移动顺滑。
- **双主动技能**：飞剑突刺（`thrust`）、范围旋斩（`spin`）。
- **战斗反馈**：碰撞扣血、击杀得分、随时间的动态难度递进。
- **一键打包 APK**：基于 Apache Cordova，横屏全屏。
- **CI 自动构建**：推送 `main` 即由 GitHub Actions 产出 `app-debug.apk` 产物。

## 🎮 玩法与操作

- **移动**：按住屏幕左下角虚拟摇杆并拖动，骑士朝摇杆方向位移。
- **技能 1 · 飞剑突刺**：点击/触摸技能按钮，向前方投掷飞剑，命中敌人 `+1` 分。
- **技能 2 · 旋斩**：点击/触摸技能按钮，以骑士为中心释放范围剑气，按击杀数 `+= killCount` 计分。
- **生命值 `hp`**：初始 `5`，被敌人碰撞 `hp -= 1`；`hp <= 0` 时游戏结束。
- **得分 `score`**：随击杀累积，用于刷新高分。
- **动态难度**：随 `gameTime` 推进，敌人速度 `currentSpeed`、最大数量 `maxEnemies`、生成频率 `spawnRate` 逐步提升。

> 说明：当前版本为鼠标 / 触摸操控，未绑定键盘；敌人、技能与难度参数均可在 `HTML/index.html` 顶部常量区调整。

## 🏗 技术架构

| 维度 | 方案 |
| --- | --- |
| 渲染 | HTML5 `<canvas>` 2D（`ctx.drawImage` 绘制背景 / 敌人 / 骑士 / 剑） |
| 游戏循环 | `requestAnimationFrame` 驱动 `gameLoop()` → `gameUpdate()` + `gameRender()` |
| 输入 | DOM 摇杆（`mousedown`/`touchstart` → `handleJoy()`）+ 技能按钮点击事件 |
| 碰撞检测 | 玩家与敌人矩形重叠判定，命中即 `hp -= 1` 并移除敌人 |
| 资源 | 背景 / 骑士 / 敌人 / 剑 等图片以外链形式加载（见下「资源说明」） |
| 打包 | Apache Cordova 12 + `android@12.0.0`，横屏、全屏 |
| 持续集成 | GitHub Actions `build-apk.yml` 自动构建 debug APK |

核心函数：`startGame()` · `gameUpdate()` · `gameRender()` · `gameLoop()` · `triggerSkill()` · `updateUI()` · `loadImages()` · `handleJoy()` · `releaseJoy()`。

## 📂 目录结构

```
QishiDamaoxian/
├── .github/
│   ├── workflows/
│   │   └── build-apk.yml          # GitHub Actions：自动构建 Android APK
│   ├── ISSUE_TEMPLATE/            # 问题 / 功能建议模板
│   └── PULL_REQUEST_TEMPLATE.md   # PR 模板
├── HTML/
│   └── index.html                 # 游戏本体（Canvas 实现，单文件）
├── Picture/
│   ├── link.md                    # 图片资源说明
│   └── original/                  # 原始素材与截图（png / jpg）
├── docs/
│   └── DESIGN.md                  # 游戏设计与需求原案
├── config.xml                     # Cordova 配置（包名 / 横屏 / SDK 版本）
├── LICENSE                        # MIT 许可证
├── .gitignore
├── CONTRIBUTING.md
├── CHANGELOG.md
└── README.md
```

## 🚀 本地运行

游戏为纯静态页面，任选其一：

```bash
# 方式一：直接用浏览器打开
#   打开 HTML/index.html 即可

# 方式二：本地静态服务器（推荐，避免个别浏览器对 file:// 的限制）
python3 -m http.server 8000
# 然后浏览器访问 http://localhost:8000/HTML/index.html
```

> 资源图片目前以图床外链形式加载，运行时需联网；如需离线，请将 `HTML/index.html` 中的外链替换为 `Picture/original/` 下的本地图片（见 `docs/DESIGN.md` 资源清单）。

## 📦 构建 Android APK

### 方式一：推送即构建（推荐）

将改动推送到 `main` 分支，GitHub Actions 会自动执行 `build-apk.yml` 并上传产物 `knight-adventure-apk`（含 `app-debug.apk`）。

### 方式二：本地构建

```bash
npm install -g cordova@12
cordova create app com.qishi.knightadventure KnightAdventure
rm -rf app/www/*
cp -r HTML/* app/www/          # 拷贝游戏源码
cp config.xml app/             # 拷贝 Cordova 配置
cd app
cordova platform add android@12.0.0
cordova build android          # 产物：platforms/android/app/build/outputs/apk/debug/app-debug.apk
```

构建环境要求（CI 已固化）：Node 20、Java 17（Temurin）、Gradle 8.x、Android SDK `android-33` + `build-tools;33.0.2`。

> iOS 当前未配置（Cordova 需 macOS runner 与签名证书）；如需要可迁移至 Capacitor 并补充 iOS 平台，见路线图。

## 🗺 路线图

- [ ] 图片资源本地化，去除图床外链依赖，支持离线运行
- [ ] 增加键盘（WASD / 方向键）操控映射
- [ ] 增加音效与背景音乐
- [ ] 增加关卡 / BOSS 与存档（最高分持久化）
- [ ] iOS 支持（Capacitor + 签名）

## 🤝 贡献

欢迎 Issue / PR！提交前请阅读 [CONTRIBUTING.md](CONTRIBUTING.md)，并使用本仓库的 Issue / PR 模板。

## 📄 许可证

[MIT](LICENSE) © 2026 zhangqizheng

## 🙏 资源说明

游戏美术素材（背景、骑士、敌人、剑）来源于截图去背处理，原始文件见 `Picture/original/`，外链引用清单见 `Picture/link.md`。
