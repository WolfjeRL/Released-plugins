package com.example.chatexample;

import com.google.common.primitives.Ints;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import org.pf4j.Extension;

import javax.inject.Inject;
import java.util.Arrays;

@Extension
@PluginDescriptor(
	name = "Chat example",
	description = "Chat example",
	type = PluginType.MISCELLANEOUS
)
@Slf4j
public class ChatExamplePlugin extends Plugin
{
	// Injects our config
	@Inject
	private ChatExampleConfig config;

	// Provides our config
	@Provides
	ChatExampleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ChatExampleConfig.class);
	}

	@Override
	protected void startUp()
	{
		// runs on plugin startup
		log.info("Plugin started");

		// example how to use config items
		if (config.chatexample())
		{
			// do stuff
			log.info("The value of 'config.example()' is ${config.example()}");
		}
	}

	@Override
	protected void shutDown()
	{
		// runs on plugin shutdown
		log.info("Plugin stopped");
	}

	@Subscribe
	private void onGameTick(GameTick gameTick)
	{
		// runs every gametick
		log.info("Gametick");
	}

		private final int[] QUESTLIST_WIDGET_IDS = new int[]
				{
						WidgetInfo.QUESTLIST_FREE_CONTAINER.getId(),
						WidgetInfo.QUESTLIST_MEMBERS_CONTAINER.getId(),
						WidgetInfo.QUESTLIST_MINIQUEST_CONTAINER.getId(),
				};

		private static final String MENUOP_QUESTNAVIGATOR = "Quest navigator";

		@Inject
		private Client client;

		@Inject
		private ChatMessageManager chatMessageManager;

		@Subscribe
		private void onMenuEntryAdded(MenuEntryAdded event)
		{
			int widgetID = event.getParam1();
			if (Ints.contains(QUESTLIST_WIDGET_IDS, widgetID) && "Read Journal:".equals(event.getOption()))
			{
				MenuEntry[] menuEntries = client.getMenuEntries();

				MenuEntry newMenuEntry = createSlayermusiqOptionMenuEntry(event);
				menuEntries = Arrays.copyOf(menuEntries, menuEntries.length + 1);
				menuEntries[menuEntries.length - 1] = newMenuEntry;

				client.setMenuEntries(menuEntries);
			}
		}

//		@Subscribe
//		private void onMenuOptionClicked(MenuOptionClicked ev)
//		{
//			if (ev.getMenuOpcode() == MenuOpcode.RUNELITE && ev.getOption().equals(MENUOP_QUESTNAVIGATOR))
//			{
//				ev.consume();
//				String quest = Text.removeTags(ev.getTarget());
//				QuestGuideLinks.tryOpenGuide(quest, chatMessageManager);
//			}
//		}

		private MenuEntry createSlayermusiqOptionMenuEntry(MenuEntryAdded event)
		{
			int widgetIndex = event.getParam0();
			int widgetID = event.getParam1();

			MenuEntry menuEntry = new MenuEntry();
			menuEntry.setTarget(event.getTarget());
			menuEntry.setOption(MENUOP_QUESTNAVIGATOR);
			menuEntry.setParam0(widgetIndex);
			menuEntry.setParam1(widgetID);
			menuEntry.setOpcode(MenuOpcode.RUNELITE.getId());

			return menuEntry;
		}

	}