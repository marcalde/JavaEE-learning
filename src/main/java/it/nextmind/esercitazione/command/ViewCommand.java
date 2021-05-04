package it.nextmind.esercitazione.command;

public class ViewCommand implements CommandInterface {

	private LogOutput receiver;

	public ViewCommand( LogOutput receiver) {

		this.receiver = receiver;

	}

	@Override
	public void execute() {

		receiver.view();

	}

}
