package de.bingutdeutsch.listener;



import java.util.concurrent.TimeUnit;

import de.bingutdeutsch.FMBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String message = event.getMessage().getContentDisplay();
		if (message.startsWith(FMBot.instance.prefix)) {
			String [] args = message.substring(2).split(" ");
			if (args.length >0) {
				if(!FMBot.instance.getCmdMan().perform(event.getGuild(), args[0], event.getMember(), event.getChannel(), event.getMessage())) {
					event.getChannel().sendMessage("> Dieses Befehl wurde nicht gefunden").complete().delete().queueAfter(5, TimeUnit.SECONDS);
				}
				
			}
		}
		
	}
	

}
