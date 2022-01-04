package de.bingutdeutsch.commands;

import java.awt.Color;
import java.time.OffsetDateTime;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {

		EmbedBuilder eb = new EmbedBuilder();
		

		String[] args = message.getContentRaw().substring(2).split(" ");
		if (args.length == 1) {
			eb.setTitle("Helpmenu :musical_note:");
			eb.setDescription("Here you can find help to every command");
			eb.setColor(new Color(FMBot.ORANGE));
			eb.setTimestamp(OffsetDateTime.now());
			eb.setFooter("BinGutDeutschFM",
					"https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
			eb.setThumbnail(
					"https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
			eb.setAuthor(m.getUser().getName(), "https://pvp.fyi/del", m.getUser().getAvatarUrl());
			eb.addField("Prefix", "b#", true);
			eb.addBlankField(false);
			eb.addField("help", "Shows this menu", false);
			eb.addField("music", "Shows the helpmenu for music", false);
			eb.addField("clear [amount]", "Deletes messages", false);
			// eb.addField("setup","Setting up the bot", false);
			eb.addField("answer", "Adding an automatic answer", false);
			eb.addField("paste", "Creates pastebin", false);
			
			channel.sendMessage(eb.build()).queue();
		}
		
		//external Helpmenus (b#help answer)
		if (args.length ==2) {
			switch (args[1]) {
			case "answer":
				answerHelp(channel,m,message);
				break;
			case "music":
				musicHelp(channel, m, message);
				break;
			default:
				break;
			}
		}
		

	}
	
	
	public void musicHelp(TextChannel channel, Member m, Message msg) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Helpmenu :musical_note:");
		eb.setDescription("Here can you find all music commands");
		eb.setColor(new Color(FMBot.ORANGE));
		eb.setTimestamp(OffsetDateTime.now());
		eb.setFooter("BinGutDeutschFM",
				"https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
		eb.setThumbnail(
				"https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
		eb.setAuthor(m.getUser().getName(), "https://pvp.fyi/del", m.getUser().getAvatarUrl());
		eb.addBlankField(false);
		eb.addField("join", "joins into your channel", false);
		eb.addField("leave", "leaves your channel", false);
		eb.addField("play", "play [url]", false);
		eb.addField("queue", "shows the current queue", false);
		eb.addField("repeat", "turns repeating on/off", false);
		eb.addField("skip", "skips the current song", false);
		eb.addField("song", "shows the current song", false);
		eb.addField("stop", "stops the song and clears the queue", false);
		eb.addField("volume", "changes the volume", false);
		channel.sendMessage(eb.build()).queue();
	}
	
	public void answerHelp(TextChannel channel, Member m, Message msg) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Helpmenu :musical_note:");
		eb.setDescription("Here can you find all automatic answers");
		eb.setColor(new Color(FMBot.ORANGE));
		eb.setTimestamp(OffsetDateTime.now());
		eb.setFooter("BinGutDeutschFM",
				"https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
		eb.setThumbnail(
				"https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
		eb.setAuthor(m.getUser().getName(), "https://pvp.fyi/del", m.getUser().getAvatarUrl());
		eb.addBlankField(false);
		eb.addField("add", "answer add [trigger] [answer]", false);
		eb.addField("remove", "answer remove [trigger]", false);
		eb.addField("list", "answer list", false);
		
		channel.sendMessage(eb.build()).queue();
		
	}

}
