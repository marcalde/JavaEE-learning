package it.nextmind.esercitazione.command;

public class EmailCommand implements CommandInterface{
	
	private LogOutput receiver;

	public EmailCommand( LogOutput receiver) {

		this.receiver = receiver;

	}

	@Override
	public void execute() {

		receiver.view();

	}


}
