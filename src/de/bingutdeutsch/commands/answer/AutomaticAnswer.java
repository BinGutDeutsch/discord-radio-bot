package de.bingutdeutsch.commands.answer;

public class AutomaticAnswer {

	private String trigger;
	private String reply;
	
	public AutomaticAnswer(String trigger, String reply) {
		setTrigger(trigger);
		setReply(reply);
	}
	
	public String getReply() {
		return reply;
	}public String getTrigger() {
		return trigger;
	}
	public void setReply(String reply) {
		if (reply.length() == 0) {
			return;
		}
		this.reply = reply;
	}
	public void setTrigger(String trigger) {
		if (trigger.length() == 0) {
			return;
		}
		this.trigger = trigger;
	}
}
