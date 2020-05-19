package com.example.questnavigator;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import org.pf4j.Extension;

import javax.inject.Inject;

@Extension
@PluginDescriptor(
	name = "Quest navigator",
	description = "Quest navigator",
	type = PluginType.MISCELLANEOUS
)
@Slf4j
public class QuestNavigatorPlugin extends Plugin
{
	// Injects our config
	@Inject
	private QuestNavigatorConfig config;

	// Provides our config
	@Provides
	QuestNavigatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(QuestNavigatorConfig.class);
	}

	@Override
	protected void startUp()
	{
		// runs on plugin startup
		log.info("Plugin started");

		// example how to use config items
		if (config.example())
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
}