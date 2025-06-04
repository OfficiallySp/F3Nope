# F3Nope

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

F3Nope is designed to be version agnostic and can be easily backported to older Minecraft versions by:

1. Updating the `minecraft_version` and `yarn_mappings` in `gradle.properties`
2. Adjusting the `fabric_version` to match the target version
3. Updating any version-specific API calls in the mixins if necessary

The mod uses minimal Minecraft internals and should work across most modern Fabric-supported versions.

## Development

### Building from Source

1. Clone the repository
2. Run `./gradlew build`
3. Find the built `.jar` in `build/libs/`

### Backporting to Older Versions

To backport to an older Minecraft version:

1. Update `gradle.properties` with the target version details
2. Check [Fabric version compatibility](https://fabricmc.net/develop/)
3. Test the `DebugHud` mixin for any API changes
4. Rebuild and test

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
