package it.nextmind.esercitazione.command;

public class LogOutputInvoker {
	
	private CommandInterface dowloadCommand;
	private CommandInterface emailCommand;
	private CommandInterface viewCommand;
	
	public LogOutputInvoker(CommandInterface dowloadCommand, CommandInterface emailCommand, CommandInterface viewCommand) {
		
		this.dowloadCommand = dowloadCommand;
		this.emailCommand = emailCommand;
		this.viewCommand = viewCommand;
		
	}
	
	public void download() {
		dowloadCommand.execute();
	}
	
	public void view() {
		viewCommand.execute();
	}
	
	public void email() {
		emailCommand.execute();
	}

}
