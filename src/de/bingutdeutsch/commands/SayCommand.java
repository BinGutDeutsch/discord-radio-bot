package de.bingutdeutsch.commands;

import java.util.concurrent.TimeUnit;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SayCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g,Member m, TextChannel channel, Message message) {
		if (m.getUser().getName().equalsIgnoreCase("BinGutDeutsch")) {
			message.delete().queue();
			String msg = "";
			String [] args = message.getContentRaw().substring(2).split(" ");
			System.out.println("test 1");
			TextChannel channel2 = null;
			try {
				channel2 = message.getMentionedChannels().get(0);
			} catch (IndexOutOfBoundsException e1) {
				m.getUser().openPrivateChannel().queue(channelP -> {
					channelP.sendMessage("Benutz b#say [Channel] [Message]").queue();;
				});
			}

			for (int i = 2; i < args.length; i++) {
				msg+=args[i]+" ";
			}
			channel2.sendTyping().queue();
			msg = msg.substring(0,msg.length()-1);
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			channel2.sendMessage(FMBot.instance.createMessage(msg)).queue();
		}
	}

}
