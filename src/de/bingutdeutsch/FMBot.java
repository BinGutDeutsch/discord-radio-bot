package de.bingutdeutsch;



import java.awt.Color;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import de.bingutdeutsch.commands.CommandManager;
import de.bingutdeutsch.commands.answer.AnswerListener;
import de.bingutdeutsch.commands.answer.AutomaticAnswer;
import de.bingutdeutsch.listener.CommandListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;



public class FMBot {

	public final static int ORANGE = 10577160;
	public final static int RED = 9765652;
	public final static int GREEN = 8311585;
	
	public String version = "1.0.1";
	public static FMBot instance;
	public JDABuilder builder;
	public ShardManager shardMan;
	public  String prefix = "b#";
	private CommandManager cmdMan;
	private ArrayList<AutomaticAnswer> answers;
	public AudioPlayerManager audioPlayerManager;
	
	public static void main(String[] args) {
		
		try {
			new FMBot();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	public FMBot() throws LoginException, IllegalArgumentException {
		
		instance = this;
		builder = JDABuilder.createDefault("ODAwNzM3NTE1MDk4ODY1NzA0.YAWe7A.msEKzMYjzkcy6E6MtzjzZvz8PiA");
		builder.setActivity(Activity.listening("Music"));
		builder.setStatus(OnlineStatus.ONLINE);
		
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		
		this.cmdMan = new CommandManager();
		this.answers = new ArrayList<AutomaticAnswer>();
		builder.addEventListeners(new CommandListener());
		builder.addEventListeners(new AnswerListener());
		
		builder.enableCache(CacheFlag.VOICE_STATE);
		builder.enableIntents(
				GatewayIntent.GUILD_MEMBERS,
				GatewayIntent.GUILD_MESSAGES,
				GatewayIntent.GUILD_VOICE_STATES);
		builder.build();
		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);
		
	}
	
	public MessageEmbed createMessage(String titel,String text,int color) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(new Color(color));
		eb.addField(titel, text, false);
		
		return eb.build();
	}
	
	public Message createMessage(String text) {
		MessageBuilder mb = new MessageBuilder();
		
		mb.append("```fix\n "+text+" \n```");
		return mb.build();
	}
	
	public CommandManager getCmdMan() {
		return cmdMan;
	}
	public ArrayList<AutomaticAnswer> getAnswers() {
		return answers;
	}

}
