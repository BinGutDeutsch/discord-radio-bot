package de.bingutdeutsch.music.commands;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import de.bingutdeutsch.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		message.delete().queue();
		Member self = g.getSelfMember();
		String[] args = message.getContentRaw().substring(2).split(" ");
		GuildVoiceState selfVoiceState = self.getVoiceState();

		if (args.length >= 2) {
			if (!selfVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Play",
						"I need to be in a voice channel to play music", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			GuildVoiceState mVoiceState = m.getVoiceState();
			if (!mVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Play",
						"You need to be in a voice channel to play music", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			if (!mVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
				channel.sendMessage(FMBot.instance.createMessage("Play",
						"You need to be in the same voice channel as me to play music", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			String link = "";
			if (args.length == 2 && args[1].length()>=15) {
				link = args[1];
			} else {
				for (int i = 1; i < args.length; i++) {
					link += args[i] + " ";
				}
			}

			if (!isUrl(link)) {
				link = "ytsearch: " + link;
			}
			PlayerManager.getINSTANCE().loadAndPlay(channel, link);
		}
	}

	private boolean isUrl(String url) {
		try {
			new URI(url);
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}

}
