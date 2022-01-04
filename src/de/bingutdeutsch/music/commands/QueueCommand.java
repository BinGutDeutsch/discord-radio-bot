package de.bingutdeutsch.music.commands;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import de.bingutdeutsch.music.GuildMusicManager;
import de.bingutdeutsch.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class QueueCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		message.delete().queue();
		GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManagers(g);
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

		if (queue.isEmpty()) {
			channel.sendMessage(FMBot.instance.createMessage("Queue", "The queue is currently empty", FMBot.RED))
					.complete().delete().queueAfter(3, TimeUnit.SECONDS);
			return;
		}
		int trackCount = Math.min(queue.size(), 10);
		List<AudioTrack> tracklist = new ArrayList<AudioTrack>(queue);

		EmbedBuilder eb = new EmbedBuilder();

		eb.setTitle("Current Queue");
		eb.setDescription("Current queue for the bot");
		eb.setColor(new Color(FMBot.ORANGE));
		eb.setTimestamp(OffsetDateTime.now());
		eb.setFooter("BinGutDeutschFM",
				"https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
		eb.setThumbnail("https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
		eb.setAuthor(m.getUser().getName(), "https://pvp.fyi/del", m.getUser().getAvatarUrl());
		// eb.addBlankField(false);
		for (int i = 0; i < trackCount; i++) {
			eb.addField(i+1+") " + tracklist.get(i).getInfo().title + " - " + tracklist.get(i).getInfo().author+"["+formatTime(tracklist.get(i).getDuration())+"]",
					"(" + tracklist.get(i).getInfo().uri + ")", false);
		}
		
		if (tracklist.size()>trackCount) {
			eb.addField("","And "+String.valueOf(tracklist.size()-trackCount+" more..."),false);
		}
		channel.sendMessage(eb.build()).queue();
		

	}

	private String formatTime(long timeInMillis) {
		long hours = timeInMillis/TimeUnit.HOURS.toMillis(1);
		long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
		long seconsds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
		
		return String.format("%02d:%02d:%02d", hours,minutes,seconsds);
	}

}
