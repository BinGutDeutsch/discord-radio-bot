package de.bingutdeutsch.commands;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class MusicCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		channel.sendMessage(FMBot.instance.createMessage("Music", "Use \"b#help music\" for help", FMBot.RED))
				.queue();

	}

}
