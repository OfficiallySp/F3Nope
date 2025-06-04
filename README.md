# F3Nope

[![Minecraft Versions](https://img.shields.io/badge/Minecraft-1.20.2--1.21.5+-brightgreen?style=flat-square)](https://github.com/OfficiallySp/f3nope/releases)
[![Java](https://img.shields.io/badge/Java-17%20|%2021-orange?style=flat-square)](https://github.com/OfficiallySp/f3nope)
[![GitHub Actions](https://img.shields.io/github/actions/workflow/status/OfficiallySp/f3nope/build.yml?style=flat-square)](https://github.com/OfficiallySp/f3nope/actions)
[![License](https://img.shields.io/badge/License-MPL--2.0-blue?style=flat-square)](https://github.com/OfficiallySp/f3nope/blob/main/LICENSE)

A lightweight Fabric mod that completely removes the F3 debug menu and replaces it with custom text of your choosing.

## Features

- **Hide Vanilla Debug Info**: Strip away all default F3 information
- **Custom Text Display**: Show your own text instead of debug data
- **Fully Configurable**: Toggle features and edit text via commands or config file
- **Live Editing**: Change settings in-game without restarting
- **Version Agnostic**: Designed to be easily backportable to older Minecraft versions

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/installer/)
2. Download [Fabric API](https://modrinth.com/mod/fabric-api)
3. Place the F3Nope `.jar` file in your `mods` folder
4. Launch Minecraft

## Usage

### In-Game Commands

All commands require OP level 2 (operator privileges).

#### Basic Commands
- `/f3nope` - Show current configuration
- `/f3nope save` - Save current configuration to file
- `/f3nope reload` - Reload configuration from file

#### Toggle Settings
- `/f3nope toggle hideVanillaDebug <true|false>` - Hide/show vanilla debug info
- `/f3nope toggle showCustomText <true|false>` - Enable/disable custom text display

#### Text Management
- `/f3nope text add <text>` - Add a new line of custom text
- `/f3nope text clear` - Remove all custom text lines
- `/f3nope text list` - Show all current text lines

#### Positioning & Styling
- `/f3nope position <x> <y>` - Set text position (in pixels)
- `/f3nope color <hexColor>` - Set text color (e.g., FF0000 for red)

### Configuration File

The mod creates a configuration file at `config/f3nope.json` with the following options:

```json
{
  "hideVanillaDebug": true,
  "showCustomText": true,
  "customTextLines": [
    "F3Nope Mod",
    "Debug info hidden",
    "Configure with /f3nope"
  ],
  "textX": 10,
  "textY": 10,
  "textColor": 16777215,
  "textShadow": true
}
```

#### Configuration Options

- `hideVanillaDebug`: Whether to hide the vanilla F3 debug information
- `showCustomText`: Whether to display custom text when F3 is pressed
- `customTextLines`: Array of text lines to display
- `textX`, `textY`: Position of the text on screen (pixels from top-left)
- `textColor`: Text color as an integer (use hex converter or commands)
- `textShadow`: Whether to render text with shadow for better visibility

## Examples

### Hide F3 Completely
```
/f3nope toggle hideVanillaDebug true
/f3nope toggle showCustomText false
```

### Custom Server Info
```
/f3nope text clear
/f3nope text add "Welcome to MyServer!"
/f3nope text add "Version: 1.21.5"
/f3nope text add "Players Online: Check Tab"
/f3nope position 10 10
/f3nope color 00FF00
```

### Minimalist Display
```
/f3nope text clear
/f3nope text add "F3 Disabled"
/f3nope position 10 10
/f3nope color FFFFFF
```

## Version Compatibility

F3Nope supports **every major Minecraft version** that Fabric supports, from **1.14.4** to **1.21.5+**:

### **Automatic Multi-Version Builds**

GitHub Actions automatically builds F3Nope for the following versions:

- 1.14.4 (Village and Pillage)
- 1.15.2 (Buzzier Bees)
- 1.16.5 (Nether Update)
- 1.17.1 (Caves and Cliffs 1)
- 1.18.2 (Caves and Cliffs 2)
- 1.19.4 (Wild Update)
- 1.20.2 (Trails and Tales)
- 1.20.4 (Bats and Pots)
- 1.20.6 (Armored Paws)
- 1.21.1 (Tricky Trials)
- 1.21.3 (Bundles of Bravery)
- 1.21.4 (Garden Awakens)
- 1.21.5 (Spring to life)

### **Installation**

1. Go to our [Releases page](https://github.com/OfficiallySp/f3nope/releases)
2. Download the JAR file matching your Minecraft version
3. Install the corresponding Fabric Loader and Fabric API versions
4. Drop both JARs into your `mods` folder


## Development

### Building from Source

1. Clone the repository
2. Run `./gradlew build`
3. Find the built `.jar` in `build/libs/`


#### **Manual Building**
```bash
# Build for current version
./gradlew build

# Build for specific version
./gradlew build -PmcVersion=1.21.1

# See .github/workflows/build.yml for all supported versions
```

#### **Adding New Minecraft Versions**
1. Update the matrix in `.github/workflows/build.yml`
2. Add version info: `minecraft_version`, `yarn_mappings`, `loader_version`, `fabric_version`, `java`
3. Test build locally, then push to trigger CI

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
