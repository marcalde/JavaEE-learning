package it.nextmind.esercitazione.command;

public class DownloadCommand implements CommandInterface {
	
	private LogOutput receiver;
	
	public DownloadCommand(LogOutput receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		
		receiver.download();
		
	}

}
