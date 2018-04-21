/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Commands.Implementation;

import Bot.Commands.Command;
import Bot.Commands.CommandParser;
import Bot.Fields.UserData;
import Bot.Launcher;
import Bot.Settings;
import static Bot.SuperRandom.oRan;

/**
 *
 * @author FF6EB4
 */
public class QuietMode extends Command{
    
    public QuietMode(){
        this.category = -1;
        this.signature = new String[]{"!quiet"};
        this.description = "Toggles quiet";
    }
    
    public void execute(String params, long ID){
        if(ID == 144857966816788482L){
            Settings.quiet.writeData(!Settings.quiet.getData());
            Launcher.send("SENDING TEST MESSAGE");
        } else {
            Launcher.send("Nope.txt");
        }
    }
}