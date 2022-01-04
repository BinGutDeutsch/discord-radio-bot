package de.bingutdeutsch.music;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.bingutdeutsch.FMBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class PlayerManager {
	private static PlayerManager INSTANCE;

	private final Map<Long, GuildMusicManager> musicManagers;
	private final AudioPlayerManager audioPlayerManager;

	public PlayerManager() {
		this.musicManagers = new HashMap<Long, GuildMusicManager>();
		this.audioPlayerManager = new DefaultAudioPlayerManager();

		AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
		AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
	}

	public GuildMusicManager getMusicManagers(Guild guild) {
		return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
			final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);
			guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

			return guildMusicManager;
		});
	}

	public void loadAndPlay(TextChannel channel, String trackUrl) {
		GuildMusicManager musicManager = this.getMusicManagers(channel.getGuild());
		this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

			@Override
			public void trackLoaded(AudioTrack track) {
				musicManager.scheduler.queue(track);
				channel.sendMessage(FMBot.instance.createMessage("Song added to queue",
						track.getInfo().title + " - " + track.getInfo().author, FMBot.ORANGE)).queue();
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				if (trackUrl.toLowerCase().startsWith("ytsearch:")) {
					AudioTrack track = playlist.getTracks().get(0);
					musicManager.scheduler.queue(track);
					channel.sendMessage(FMBot.instance.createMessage("Song added to queue",
							track.getInfo().title + " - " + track.getInfo().author, FMBot.ORANGE)).queue();
				} else {
					List<AudioTrack> tracks = playlist.getTracks();
					for (AudioTrack track : tracks) {
						musicManager.scheduler.queue(track);
					}
					channel.sendMessage(FMBot.instance.createMessage("Songs added to queue",
							String.valueOf(tracks.size()) + " from Playlist - " + playlist.getName(), FMBot.ORANGE))
							.queue();
				}
			}

			@Override
			public void noMatches() {
				channel.sendMessage(FMBot.instance.createMessage("Play", "Song could not be found", FMBot.RED)).queue();

			}

			@Override
			public void loadFailed(FriendlyException exception) {
				channel.sendMessage(FMBot.instance.createMessage("Play", "Song could not be loaded", FMBot.RED)).queue();
			}
		});
	}

	public static PlayerManager getINSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new PlayerManager();
		}
		return INSTANCE;
	}
}
