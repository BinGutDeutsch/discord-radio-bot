package de.bingutdeutsch.music;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;


public class TrackScheduler extends AudioEventAdapter{

	public final AudioPlayer player;
	public final BlockingQueue<AudioTrack> queue;
	public boolean repeating = false;
	
	public TrackScheduler(AudioPlayer player) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<AudioTrack>();
	}
	public void queue(AudioTrack track) {
		if (!this.player.startTrack(track, true)) {
			this.queue.offer(track);
		}
	}
	public void nextTrack() {
		this.player.startTrack(this.queue.poll(),false);
		//after setup send message in channel
		//channel.sendMessage(FMBot.instance.createMessage("Now playing",
			//	track.getInfo().title+" by "+track.getInfo().author+ " (Link: "+track.getInfo().uri+")", FMBot.ORANGE)).queue();
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if (endReason.mayStartNext) {
			if (this.repeating) {
				this.player.startTrack(track.makeClone(), false);
				return;
			}
			nextTrack();
			
		}
		super.onTrackEnd(player, track, endReason);
	}
}
