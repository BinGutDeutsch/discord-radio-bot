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

public class StopCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		Member self = g.getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();

			if (!selfVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Stop",
						"I need to be in a voice channel to stop the music", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			GuildVoiceState mVoiceState = m.getVoiceState();
			if (!mVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Stop",
						"You need to be in a voice channel to stop the music", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			if (!mVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
				channel.sendMessage(FMBot.instance.createMessage("Stop",
						"You need to be in the same voice channel as me to stop the music", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}
			
			GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManagers(g);
			musicManager.scheduler.player.stopTrack();
			musicManager.scheduler.queue.clear();
			channel.sendMessage(FMBot.instance.createMessage("Stopped the player","The player has been stopped and the queue has been cleared", FMBot.ORANGE)).queue();

		

	}
}
