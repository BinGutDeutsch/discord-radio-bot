package de.bingutdeutsch.commands.answer;

import java.awt.Color;
import java.time.OffsetDateTime;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class AnswerCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {

		//channel.sendMessage(FMBot.instance.createMessage("Answer", "The answer command is currently not working", FMBot.RED));
		
		String[] args = message.getContentRaw().substring(2).split(" ");
		if (args.length == 1) {
			channel.sendMessage(FMBot.instance.createMessage("Answer", "Use \"b#help answer\" for help", FMBot.RED))
					.queue();
		} else if (args.length == 2) {
			if (args[1].equalsIgnoreCase("list")) {
				channel.sendMessage(showList(g,  m, channel,message)).queue();
			}
		}
		else if(args.length == 3) {
			if (args[1].equalsIgnoreCase("remove")) {
				for (int i = 0; i < FMBot.instance.getAnswers().size(); i++) {
					if (FMBot.instance.getAnswers().get(i).getTrigger().equalsIgnoreCase(args[2])) {
						FMBot.instance.getAnswers().remove(i);
						channel.sendMessage(FMBot.instance.createMessage("Answer", "automatic answer successfully deleted", FMBot.GREEN)).queue();
						break;
					}
				}
			}
		}
		else if(args.length >= 4) {
			if (args[1].equalsIgnoreCase("add")) {
				String reply = "";
				for (int i = 3; i < args.length; i++) {
					reply +=args[i]+" ";
				}
				AutomaticAnswer aa = new AutomaticAnswer(args[2], reply);
				FMBot.instance.getAnswers().add(aa);
				channel.sendMessage(FMBot.instance.createMessage("Answer", "automatic answer successfully created", FMBot.GREEN)).queue();
			}
		}
	}
	
	private MessageEmbed showList(Guild g, Member m, TextChannel channel, Message message) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Helpmenu :musical_note:");
		eb.setDescription("Here you can find help to the automatic answers b#answer");
		eb.setColor(new Color(10577160));
		eb.setTimestamp(OffsetDateTime.now());
		eb.setFooter("BinGutDeutschFM",
				"https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
		eb.setThumbnail(
				"https://cdn.discordapp.com/avatars/800737515098865704/e234dbd2e0a319999532ba84c4dbbc27.png");
		eb.setAuthor(m.getUser().getName(), "https://pvp.fyi/del", m.getUser().getAvatarUrl());
		for (int i = 0; i < FMBot.instance.getAnswers().size(); i++) {
			eb.addField("Trigger: "+FMBot.instance.getAnswers().get(i).getTrigger(), "Answer: "+FMBot.instance.getAnswers().get(i).getReply(), false);
		}
		
		return eb.build();
	}
	}

