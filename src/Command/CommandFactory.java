package Command;

import Command.extend.PasvCommand;
import Command.extend.PwdCommand;
import Command.extend.SystCommand;

public class CommandFactory {

    public static Command createCommand(String type) {

        type = type.toUpperCase();
        return switch (type) {//增强型switch
            case "USER" -> new UserCommand();
            case "PASS" -> new PassCommand();
            case "LIST" -> new DirCommand();
            case "PORT" -> new PortCommand();
            case "QUIT" -> new QuitCommand();
            case "RETR" -> new RetrCommand();
            case "CWD" -> new CwdCommand();
            case "STOR" -> new StoreCommand();
            case "DELE" -> new DeleCommand();
            case "RNFR" -> new RnfrCommand();
            case "RNTO" -> new RntoCommand();
            case "APPE" -> new AppCommand();
            case "SYST" -> new SystCommand();
            case "PWD" -> new PwdCommand();
            case "PASV" -> new PasvCommand();
            default -> null;
        };

    }
}
