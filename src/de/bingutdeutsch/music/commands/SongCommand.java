package de.bingutdeutsch.music.commands;

import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import de.bingutdeutsch.music.GuildMusicManager;
import de.bingutdeutsch.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SongCommand implements ServerCommand{

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		message.delete().queue();
		Member self = g.getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();

			if (!selfVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Now playing",
						"I need to be in a voice channel to skip the track", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			GuildVoiceState mVoiceState = m.getVoiceState();
			if (!mVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Now playing",
						"You need to be in a voice channel to skip the track", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			if (!mVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
				channel.sendMessage(FMBot.instance.createMessage("Now playing",
						"You need to be in the same voice channel as me to skip the track", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}
			
			GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManagers(g);
			AudioPlayer audioPlayer = musicManager.audioPlayer;
			AudioTrack track = audioPlayer.getPlayingTrack(); 
			
			if (track== null) {
				channel.sendMessage(FMBot.instance.createMessage("Now playing",
						"There is currently no track playing", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}
			channel.sendMessage(FMBot.instance.createMessage("Now playing",
					track.getInfo().title+" by "+track.getInfo().author+ " (Link: "+track.getInfo().uri+")", FMBot.ORANGE)).queue();
			
		
	}

}
