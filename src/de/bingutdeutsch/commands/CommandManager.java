package de.bingutdeutsch.commands;

import java.util.concurrent.ConcurrentHashMap;

import de.bingutdeutsch.commands.answer.AnswerCommand;
import de.bingutdeutsch.commands.types.ServerCommand;
import de.bingutdeutsch.music.commands.JoinCommand;
import de.bingutdeutsch.music.commands.LeaveCommand;
import de.bingutdeutsch.music.commands.PlayCommand;
import de.bingutdeutsch.music.commands.QueueCommand;
import de.bingutdeutsch.music.commands.RepeatCommand;
import de.bingutdeutsch.music.commands.SkipCommand;
import de.bingutdeutsch.music.commands.SongCommand;
import de.bingutdeutsch.music.commands.StopCommand;
import de.bingutdeutsch.music.commands.VolumeCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {

	public ConcurrentHashMap<String, ServerCommand> commands;

	public CommandManager() {
		this.commands = new ConcurrentHashMap<String, ServerCommand>();
		this.commands.put("clear", new ClearCommand());
		this.commands.put("say", new SayCommand());
		this.commands.put("help", new HelpCommand());
		this.commands.put("answer", new AnswerCommand());
		this.commands.put("join",new JoinCommand());
		this.commands.put("play", new PlayCommand());
		this.commands.put("stop", new StopCommand());
		this.commands.put("skip", new SkipCommand());
		this.commands.put("song", new SongCommand());
		this.commands.put("queue", new QueueCommand());
		this.commands.put("repeat", new RepeatCommand());
		this.commands.put("leave", new LeaveCommand());
		this.commands.put("paste", new PasteCommand());
		this.commands.put("volume", new VolumeCommand());
		this.commands.put("music", new MusicCommand());
	}

	public boolean perform(Guild g, String command, Member m, TextChannel channel, Message message) {
		ServerCommand cmd;
		if ((cmd = this.commands.get(command.toLowerCase())) != null) {
			cmd.performCommand(g, m, channel, message);
			return true;
		}
		
		return false;
	}
}
