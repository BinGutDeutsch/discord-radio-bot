package de.bingutdeutsch.music.commands;

import java.util.concurrent.TimeUnit;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import de.bingutdeutsch.music.GuildMusicManager;
import de.bingutdeutsch.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class VolumeCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		message.delete().queue();
		Member self = g.getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		String[] args = message.getContentRaw().substring(2).split(" ");
		if (args.length == 2) {
			if (!selfVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Volume",
						"I need to be in a voice channel to manage the volume", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			GuildVoiceState mVoiceState = m.getVoiceState();
			if (!mVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Volume",
						"You need to be in a voice channel to manage the volume", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			if (!mVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
				channel.sendMessage(FMBot.instance.createMessage("Volume",
						"You need to be in the same voice channel as me to manage the volume", FMBot.RED)).complete()
						.delete().queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManagers(g);
			try {
				int volume = Integer.parseInt(args[1]);
				musicManager.audioPlayer.setVolume(volume);
				channel.sendMessage(FMBot.instance.createMessage("Volume",
						"Volume set to "+volume, FMBot.GREEN)).complete()
						.delete().queueAfter(5, TimeUnit.SECONDS);
			} catch (NumberFormatException e) {
				channel.sendMessage(FMBot.instance.createMessage("Volume",
						"Missing arguments \"b#volume [volume]\"", FMBot.RED)).complete()
						.delete().queueAfter(3, TimeUnit.SECONDS);
			}
			
			
		}
		else {
			channel.sendMessage(FMBot.instance.createMessage("Volume",
					"Missing arguments \"b#volume [volume]\"", FMBot.RED)).complete()
					.delete().queueAfter(5, TimeUnit.SECONDS);
		}
	}

}
