package de.bingutdeutsch.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClearCommand implements ServerCommand {

	@Override
	public void performCommand(Guild g,Member m, TextChannel channel, Message message) {
		if (m.hasPermission(channel, Permission.MESSAGE_MANAGE)) {
			message.delete().queue();
			String [] args = message.getContentDisplay().split(" ");
			if (args.length == 2) {
				
				try {
					int amount = Integer.parseInt(args[1]);
					channel.purgeMessages(get(channel, amount));
					channel.sendTyping().queue();
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					channel.sendMessage(FMBot.instance.createMessage("Clear",amount+ " Messages were deleted.",FMBot.RED)).complete().delete().queueAfter(5, TimeUnit.SECONDS);
					return;
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}			
			}
		}
		
		
	}
	
	public List<Message> get(MessageChannel channel, int amount){
		List<Message> messages = new ArrayList<>();
		int i = amount+1;
		
		for(Message msg: channel.getIterableHistory().cache(false)) {
			if (!msg.isPinned()) {
				messages.add(msg);
				if (--i <=0) break;
			}
			
		}
		
		return messages;
	}

}
