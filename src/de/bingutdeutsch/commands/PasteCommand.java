package de.bingutdeutsch.commands;

import java.util.concurrent.TimeUnit;

import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;
import org.menudocs.paste.PasteHost;

import de.bingutdeutsch.FMBot;
import de.bingutdeutsch.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PasteCommand implements ServerCommand {

	private PasteClient client = new PasteClientBuilder().setUserAgent("BinGutDeutschFM Bot").setDefaultExpiry("10m")
			.setPasteHost(PasteHost.MENUDOCS).build();

	@Override
	public void performCommand(Guild g, Member m, TextChannel channel, Message message) {
		message.delete().queue();
		String[] args = message.getContentRaw().substring(2).split(" ");
		if (args.length <= 2) {
			channel.sendMessage(FMBot.instance.createMessage("Paste", "Missing arguments", FMBot.RED)).complete()
					.delete().queueAfter(3, TimeUnit.SECONDS);
			return;
		}

		String language = args[1];
		String body = "";
		String contentRaw = message.getContentRaw();
		int index = contentRaw.indexOf(language)+language.length();
		body = contentRaw.substring(index).trim();

		client.createPaste(language, body).async(

				(id) -> channel.sendMessage(FMBot.instance.createMessage("Paste",
						"Successfully pasted: https://paste.menudocs.org/paste/" + id, FMBot.GREEN)).queue()

		);

	}

}
