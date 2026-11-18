# EduTube — Educational & Anti-Brainrot YouTube Plugin for PipePipe

[![CI](https://github.com/InfinityLoop1308/PipePipe/actions/workflows/ci.yml/badge.svg)](https://github.com/InfinityLoop1308/PipePipe)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)
[![PipePipe Compatible](https://img.shields.io/badge/PipePipe-Plugin%20v1.0-brightgreen.svg)](https://github.com/InfinityLoop1308/PipePipe/releases)

**EduTube** is a distraction-free, academic streaming service plugin for [PipePipe](https://github.com/InfinityLoop1308/PipePipe/releases) (the plugin-enabled NewPipe fork). It curates YouTube specifically for university lectures, full college courses, STEM tutorials, computer science bootcamps, and deep humanities analyses while **strictly eradicating entertainment clickbait, YouTube Shorts, and brain-rot memes**.

---

## 🎯 The Problem EduTube Solves

Standard YouTube, default NewPipe, and mobile media clients are engineered for engagement maximization:
* **The Brain-rot Epidemic:** Feeds flooded with sensationalized click-traps, prank videos, streaming drama, reaction mashups, and Gen-Z/Alpha meme loops (*Skibidi, Gyatt, Rizz, Kai Cenat, Fanum Tax*).
* **The Shorts Trap:** Micro-dopamine vertical video loops designed for addictive doom-scrolling.
* **Search Polluting:** Searching for academic topics like "Calculus" or "Python" returns entertainment reaction videos, 30-second shorts, and low-effort summaries instead of structured university curricula.

### What EduTube Delivers
* 🎓 **100% Educational & Academic Content:** Only structured university curricula (MIT, Stanford, Harvard, Yale, Oxford, Cambridge, Berkeley, NPTEL, Khan Academy, freeCodeCamp, etc.) and verified science & tech authorities.
* 🛡️ **Multi-Layered Anti-Brainrot Filter:** Automatically strips out brain-rot slang, prank culture, clickbait capitalization, emoji abuse, and celebrity gossip.
* 🚫 **Shorts & Micro-Content Elimination:** Enforces a minimum duration threshold ($> 3$ minutes) and discards `/shorts/` streams entirely.
* 📚 **Curated Academic Kiosks:** Instant access to 5 academic categories right on the home screen.
* ⚡ **Native 4K/1080p Playback:** Seamlessly leverages PipePipe's built-in player with DASH streams, high-speed chunk loading, subtitles, video chapters, background play, and offline downloads.
* 🤫 **Distraction-Free Environment:** Comments are permanently disabled to prevent endless scrolling and preserve deep study focus.

---

## 🏛️ Built-in Curated Kiosks

When switching to **EduTube** inside PipePipe, you will see 5 curated categories instead of YouTube's entertainment trending tab:

| Kiosk | Description & Highlights |
| :--- | :--- |
| **🎓 Featured University Courses** *(Default)* | Master university courses: **MIT 18.06 Linear Algebra** (Prof. Strang), **Harvard CS50**, **Stanford CS229 Machine Learning** (Prof. Andrew Ng), **MIT 6.006 Algorithms**, **Yale Psychology**, **Harvard Justice**. |
| **💻 Computer Science & Tech** | Full bootcamps: **freeCodeCamp** (Python, C++, Rust, Go), **NeetCode DSA**, **Hussein Nasser Distributed Systems**, **The Cherno C++**, **ByteByteGo System Design**, **Ben Eater 8-bit Architecture**. |
| **📐 Mathematics & Physics** | Deep visual mathematics and theoretical physics: **3Blue1Brown** (Linear Algebra, Calculus, Neural Networks), **Prof. Leonard Calculus 1-3**, **PBS Space Time**, **StatQuest**, **Numberphile**, **Veritasium**. |
| **🔬 Engineering & Applied Sciences** | Mechanical, civil, electrical & aerospace engineering: **Real Engineering**, **Practical Engineering**, **SmarterEveryDay**, **NileRed Chemistry**, **Two Minute Papers**. |
| **📚 Humanities & Philosophy** | Deep historical documentaries & philosophy: **Fall of Civilizations**, **CrashCourse World History & Economics**, **Academy of Ideas** (Jung, Nietzsche, Stoicism), **Overly Sarcastic Productions**. |

---

## ⚙️ Configurable Plugin Settings

EduTube provides in-app settings configurable directly inside **PipePipe Settings → Plugins → EduTube**:

1. **Strict Academic Mode** *(Default: Enabled)*:
   * When enabled, videos must either originate from verified universities/educators or contain explicit academic course keywords (*lecture, course, derivation, proof, curriculum, algorithms, thermodynamics, etc.*).
2. **Block All YouTube Shorts** *(Default: Enabled)*:
   * Completely filters out short-form vertical videos and `/shorts/` links.
3. **Minimum Video Duration** *(Default: `180` seconds / 3 minutes)*:
   * Discards micro-videos to ensure only comprehensive lectures and deep tutorials are displayed.
4. **Block Clickbait Sensationalism** *(Default: Enabled)*:
   * Discards titles with excessive uppercase characters ($> 65\%$), consecutive exclamation/question mark spam (`???`, `!!!`), or clickbait emoji spam (😱, 🚨, 🤯, 💀).
5. **Custom Blocked Keywords**:
   * Add any comma-separated list of words or topics you wish to ban permanently from search and feeds.

---

## 📥 How to Install in PipePipe

### Step 1: Install PipePipe
Download and install the latest release of **PipePipe** from the official releases page:
👉 **[PipePipe Releases](https://github.com/InfinityLoop1308/PipePipe/releases)**

### Step 2: Add EduTube Repository or Install Plugin
1. Open PipePipe and navigate to **Settings → Plugins**.
2. Ensure **Enable plugins** is toggled **ON**.
3. Tap **Add repository** and paste the raw repository index URL:
   ```text
   https://raw.githubusercontent.com/<YOUR_GITHUB_USERNAME>/pipepipe-edutube-plugin/main/build/repo/index.json
   ```
4. Find **EduTube** in the available plugins list and tap **Install**.
5. Restart PipePipe.

### Step 3: Start Learning Distraction-Free
1. Tap the service switcher in the top navigation or menu drawer.
2. Select **EduTube**.
3. Browse curated university tracks, search for academic topics, and watch lectures with 0% entertainment interference!

---

## 🛠️ Building & Packaging from Source

### Prerequisites
* JDK 25 or JDK 17 with Foojay toolchain resolver (Gradle handles download automatically).
* Linux / macOS / Windows / Termux PRoot.

### Build Steps

```bash
# 1. Clone the repository
git clone https://github.com/<YOUR_GITHUB_USERNAME>/pipepipe-edutube-plugin.git
cd pipepipe-edutube-plugin

# 2. Run unit tests
./gradlew test

# 3. Build the .ppp plugin package
./gradlew assembleRepo

# 4. Generate signing key (first time only)
./gradlew :tools:genKey

# 5. Sign the package and create index.json
./gradlew :tools:index
```

The output artifacts will be ready in `build/repo/`:
* `pipepipe-edutube-plugin-1.ppp` (The compiled, dexed, and packaged plugin)
* `index.json` (The signed Ed25519 plugin repository index)

---

## 🏗️ Project Architecture

```
pipepipe-edutube-plugin/
├── build.gradle                  # Gradle build script with R8 pure-Java dexer & desugaring
├── settings.gradle               # Root project settings
├── gradle.properties             # Runtime versions matched with PipePipeExtractor
├── .github/workflows/
│   ├── ci.yml                    # Automated compilation and unit testing on PR/Push
│   └── release.yml               # Automated packaging and GitHub Release deployment
├── src/main/resources/
│   ├── plugin.json               # PipePipe Plugin Manifest with settings & metadata
│   └── icon.png                  # Service icon tinted by PipePipe
└── src/main/java/org/pipepipe/plugin/edutube/
    ├── EduTubePlugin.java        # Entry point implementing PipePipePlugin
    ├── EduTubeService.java       # StreamingService with YouTube delegation & kiosks
    ├── filter/
    │   ├── AntiBrainrotFilter.java     # Lexicon blacklist, shorts blocker, clickbait detector
    │   └── EducationalClassifier.java  # Institutional whitelist & curriculum evaluator
    ├── data/
    │   └── CuratedCourses.java         # Pre-indexed master playlists & lectures
    ├── kiosk/
    │   ├── EduKioskExtractor.java      # 5 academic kiosk feeds
    │   └── EduKioskLinkHandlerFactory.java
    ├── search/
    │   ├── EduSearchExtractor.java     # YouTube search wrapper with anti-brainrot filtering
    │   └── EduSearchQueryHandlerFactory.java
    ├── stream/
    │   └── EduStreamLinkHandlerFactory.java # YouTube stream delegation handler
    ├── channel/
    │   ├── EduChannelExtractor.java    # Academic channel wrapper
    │   └── EduChannelLinkHandlerFactory.java
    └── playlist/
        ├── EduPlaylistExtractor.java   # Academic course playlist wrapper
        └── EduPlaylistLinkHandlerFactory.java
```

---

## 📜 License

This project is licensed under the **GNU General Public License v3.0 (GPL-3.0)** — adhering to the open-source spirit of NewPipe and PipePipe. See [LICENSE](LICENSE) for details.
