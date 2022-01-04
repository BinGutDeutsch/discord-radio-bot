package de.bingutdeutsch.music.commands;

import java.util.concurrent.TimeUnit;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements ServerCommand{

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		message.delete().queue();
		Member self = g.getSelfMember();
		GuildVoiceState selfVoiceState =self.getVoiceState();
		if (selfVoiceState.inVoiceChannel()) {
			channel.sendMessage(FMBot.instance.createMessage("Join", "I'm already in a voice channel" , FMBot.RED)).complete().delete().queueAfter(3, TimeUnit.SECONDS);
			return;
		}
		if (!m.getVoiceState().inVoiceChannel()) {
			channel.sendMessage(FMBot.instance.createMessage("Join", "You need to be in a voice channel" , FMBot.RED)).complete().delete().queueAfter(3, TimeUnit.SECONDS);
			return;
		}
		AudioManager am = g.getAudioManager();
		VoiceChannel vc = m.getVoiceState().getChannel();
		
		am.openAudioConnection(vc);
		channel.sendMessage(FMBot.instance.createMessage("Join", "Connecting to "+m.getVoiceState().getChannel().getName()+" ..." , FMBot.GREEN)).queue();
		
	}

}
