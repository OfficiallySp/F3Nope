package com.f3nope;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class F3NopeCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerF3NopeCommand(dispatcher);
        });
    }

    private static void registerF3NopeCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("f3nope")
            .requires(source -> source.hasPermissionLevel(2)) // Requires OP level 2
            .then(CommandManager.literal("toggle")
                .then(CommandManager.argument("setting", StringArgumentType.string())
                    .suggests((context, builder) -> {
                        builder.suggest("hideVanillaDebug");
                        builder.suggest("showCustomText");
                        builder.suggest("enablePlaceholders");
                        builder.suggest("showFps");
                        builder.suggest("showPing");
                        builder.suggest("showDateTime");
                        builder.suggest("showVersions");
                        return builder.buildFuture();
                    })
                    .then(CommandManager.argument("value", BoolArgumentType.bool())
                        .executes(F3NopeCommands::executeToggle))))
            .then(CommandManager.literal("text")
                .then(CommandManager.literal("add")
                    .then(CommandManager.argument("line", StringArgumentType.greedyString())
                        .executes(F3NopeCommands::executeAddText)))
                .then(CommandManager.literal("clear")
                    .executes(F3NopeCommands::executeClearText))
                .then(CommandManager.literal("list")
                    .executes(F3NopeCommands::executeListText)))
            .then(CommandManager.literal("position")
                .then(CommandManager.argument("x", IntegerArgumentType.integer(0))
                    .then(CommandManager.argument("y", IntegerArgumentType.integer(0))
                        .executes(F3NopeCommands::executeSetPosition))))
            .then(CommandManager.literal("color")
                .then(CommandManager.argument("hexColor", StringArgumentType.string())
                    .executes(F3NopeCommands::executeSetColor)))
            .then(CommandManager.literal("placeholders")
                .executes(F3NopeCommands::executePlaceholderHelp))
            .then(CommandManager.literal("reload")
                .executes(F3NopeCommands::executeReload))
            .then(CommandManager.literal("save")
                .executes(F3NopeCommands::executeSave))
            .executes(F3NopeCommands::executeInfo));
    }

    private static int executeToggle(CommandContext<ServerCommandSource> context) {
        String setting = StringArgumentType.getString(context, "setting");
        boolean value = BoolArgumentType.getBool(context, "value");
        F3NopeConfig config = F3Nope.getConfig();

        switch (setting) {
            case "hideVanillaDebug":
                config.hideVanillaDebug = value;
                context.getSource().sendFeedback(() -> Text.literal("Hide vanilla debug: " + value), false);
                break;
            case "showCustomText":
                config.showCustomText = value;
                context.getSource().sendFeedback(() -> Text.literal("Show custom text: " + value), false);
                break;
            case "enablePlaceholders":
                config.enablePlaceholders = value;
                context.getSource().sendFeedback(() -> Text.literal("Enable placeholders: " + value), false);
                break;
            case "showFps":
                config.showFps = value;
                context.getSource().sendFeedback(() -> Text.literal("Show FPS placeholder: " + value), false);
                break;
            case "showPing":
                config.showPing = value;
                context.getSource().sendFeedback(() -> Text.literal("Show ping placeholder: " + value), false);
                break;
            case "showDateTime":
                config.showDateTime = value;
                context.getSource().sendFeedback(() -> Text.literal("Show date/time placeholders: " + value), false);
                break;
            case "showVersions":
                config.showVersions = value;
                context.getSource().sendFeedback(() -> Text.literal("Show version placeholders: " + value), false);
                break;
            default:
                context.getSource().sendError(Text.literal("Unknown setting: " + setting));
                return 0;
        }

        config.save();
        return 1;
    }

    private static int executeAddText(CommandContext<ServerCommandSource> context) {
        String line = StringArgumentType.getString(context, "line");
        F3NopeConfig config = F3Nope.getConfig();
        config.customTextLines.add(line);
        config.save();

        context.getSource().sendFeedback(() -> Text.literal("Added text line: " + line), false);
        return 1;
    }

    private static int executeClearText(CommandContext<ServerCommandSource> context) {
        F3NopeConfig config = F3Nope.getConfig();
        config.customTextLines.clear();
        config.save();

        context.getSource().sendFeedback(() -> Text.literal("Cleared all custom text lines"), false);
        return 1;
    }

    private static int executeListText(CommandContext<ServerCommandSource> context) {
        F3NopeConfig config = F3Nope.getConfig();
        context.getSource().sendFeedback(() -> Text.literal("Custom text lines:"), false);

        for (int i = 0; i < config.customTextLines.size(); i++) {
            final int index = i;
            context.getSource().sendFeedback(() -> Text.literal((index + 1) + ". " + config.customTextLines.get(index)), false);
        }

        return 1;
    }

    private static int executeSetPosition(CommandContext<ServerCommandSource> context) {
        int x = IntegerArgumentType.getInteger(context, "x");
        int y = IntegerArgumentType.getInteger(context, "y");
        F3NopeConfig config = F3Nope.getConfig();
        config.textX = x;
        config.textY = y;
        config.save();

        context.getSource().sendFeedback(() -> Text.literal("Set text position to: " + x + ", " + y), false);
        return 1;
    }

        private static int executeSetColor(CommandContext<ServerCommandSource> context) {
        String hexColor = StringArgumentType.getString(context, "hexColor");
        try {
            // Remove # if present
            if (hexColor.startsWith("#")) {
                hexColor = hexColor.substring(1);
            }

            int color = Integer.parseInt(hexColor, 16);
            F3NopeConfig config = F3Nope.getConfig();
            config.textColor = color;
            config.save();

            final String finalHexColor = hexColor;
            context.getSource().sendFeedback(() -> Text.literal("Set text color to: #" + finalHexColor), false);
            return 1;
        } catch (NumberFormatException e) {
            context.getSource().sendError(Text.literal("Invalid hex color: " + hexColor));
            return 0;
        }
    }

    private static int executeReload(CommandContext<ServerCommandSource> context) {
        F3Nope.getConfig().reload();
        context.getSource().sendFeedback(() -> Text.literal("Reloaded F3Nope configuration"), false);
        return 1;
    }

    private static int executeSave(CommandContext<ServerCommandSource> context) {
        F3Nope.getConfig().save();
        context.getSource().sendFeedback(() -> Text.literal("Saved F3Nope configuration"), false);
        return 1;
    }

    private static int executePlaceholderHelp(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("=== Available Placeholders ==="), false);
        context.getSource().sendFeedback(() -> Text.literal("%fps% - Current FPS"), false);
        context.getSource().sendFeedback(() -> Text.literal("%ping% - Current ping (multiplayer only)"), false);
        context.getSource().sendFeedback(() -> Text.literal("%time% - Current time (HH:mm:ss)"), false);
        context.getSource().sendFeedback(() -> Text.literal("%date% - Current date (yyyy-MM-dd)"), false);
        context.getSource().sendFeedback(() -> Text.literal("%mc_version% - Minecraft version"), false);
        context.getSource().sendFeedback(() -> Text.literal("%fabric_version% - Fabric Loader version"), false);
        context.getSource().sendFeedback(() -> Text.literal("%mod_version% - F3Nope mod version"), false);
        context.getSource().sendFeedback(() -> Text.literal("Example: \"FPS: %fps% | Ping: %ping%ms\""), false);
        return 1;
    }

    private static int executeInfo(CommandContext<ServerCommandSource> context) {
        F3NopeConfig config = F3Nope.getConfig();
        context.getSource().sendFeedback(() -> Text.literal("=== F3Nope Configuration ==="), false);
        context.getSource().sendFeedback(() -> Text.literal("Hide vanilla debug: " + config.hideVanillaDebug), false);
        context.getSource().sendFeedback(() -> Text.literal("Show custom text: " + config.showCustomText), false);
        context.getSource().sendFeedback(() -> Text.literal("Enable placeholders: " + config.enablePlaceholders), false);
        context.getSource().sendFeedback(() -> Text.literal("Show FPS: " + config.showFps), false);
        context.getSource().sendFeedback(() -> Text.literal("Show ping: " + config.showPing), false);
        context.getSource().sendFeedback(() -> Text.literal("Show date/time: " + config.showDateTime), false);
        context.getSource().sendFeedback(() -> Text.literal("Show versions: " + config.showVersions), false);
        context.getSource().sendFeedback(() -> Text.literal("Text position: " + config.textX + ", " + config.textY), false);
        context.getSource().sendFeedback(() -> Text.literal("Text color: #" + Integer.toHexString(config.textColor)), false);
        context.getSource().sendFeedback(() -> Text.literal("Custom text lines: " + config.customTextLines.size()), false);
        context.getSource().sendFeedback(() -> Text.literal("Use '/f3nope placeholders' to see available placeholders"), false);
        return 1;
    }
}
