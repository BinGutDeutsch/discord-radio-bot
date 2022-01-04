package de.bingutdeutsch.commands.answer;

import de.bingutdeutsch.FMBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AnswerListener extends ListenerAdapter{

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String message = e.getMessage().getContentRaw();
		for (int i = 0; i < FMBot.instance.getAnswers().size(); i++) {
			if (FMBot.instance.getAnswers().get(i).getTrigger().equalsIgnoreCase(message)) {
				e.getChannel().sendMessage(FMBot.instance.getAnswers().get(i).getReply()).queue();
			}
		}
	}
	
}
