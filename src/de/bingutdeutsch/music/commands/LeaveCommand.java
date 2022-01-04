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
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand implements ServerCommand{

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		message.delete().queue();
		Member self = g.getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();

			if (!selfVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Leave",
						"I need to be in a voice channel to leave the channel", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			GuildVoiceState mVoiceState = m.getVoiceState();
			if (!mVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Leave",
						"You need to be in a voice channel to leave the channel", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			if (!mVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
				channel.sendMessage(FMBot.instance.createMessage("Leave",
						"You need to be in the same voice channel as me to leave the channel", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}
			GuildMusicManager musicManager =PlayerManager.getINSTANCE().getMusicManagers(g);
			musicManager.scheduler.repeating = false;
			musicManager.scheduler.queue.clear();
			musicManager.audioPlayer.stopTrack();
			
			AudioManager am = g.getAudioManager();
			am.closeAudioConnection();
			
			channel.sendMessage(FMBot.instance.createMessage("Leave",
					"I left the voice channel", FMBot.GREEN)).complete().delete()
					.queueAfter(3, TimeUnit.SECONDS);
		
	}

}
