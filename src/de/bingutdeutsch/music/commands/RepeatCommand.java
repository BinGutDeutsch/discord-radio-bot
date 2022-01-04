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

public class RepeatCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		message.delete().queue();
		Member self = g.getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();

			if (!selfVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Repeating track",
						"I need to be in a voice channel to repeat the track", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			GuildVoiceState mVoiceState = m.getVoiceState();
			if (!mVoiceState.inVoiceChannel()) {
				channel.sendMessage(FMBot.instance.createMessage("Repeating track",
						"You need to be in a voice channel to repeat the track", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}

			if (!mVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
				channel.sendMessage(FMBot.instance.createMessage("Repeating track",
						"You need to be in the same voice channel as me to repeat the track", FMBot.RED)).complete().delete()
						.queueAfter(3, TimeUnit.SECONDS);
				return;
			}
			
			GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManagers(g);
			boolean newRepeating = !musicManager.scheduler.repeating;
			musicManager.scheduler.repeating = newRepeating;
			if (newRepeating) {
				channel.sendMessage(FMBot.instance.createMessage("Repeating track",
						"The player has been set to **repeating**", FMBot.GREEN)).complete().delete()
						.queueAfter(5, TimeUnit.SECONDS);
			}
			else {
				channel.sendMessage(FMBot.instance.createMessage("Repeating track",
						"The player has been set to **not repeating**", FMBot.GREEN)).complete().delete()
						.queueAfter(5, TimeUnit.SECONDS);
			}
			
			
	}

	
	
}
