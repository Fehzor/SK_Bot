/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.Commands.Implementation;

import Bot.Commands.Command;
import Bot.Commands.CommandParser;
import Bot.Launcher;
import static Bot.SuperRandom.oRan;

/**
 *
 * @author FF6EB4
 */
public class Plz extends Command{
    
    public Plz(){
        this.category = -1;
        this.signature = new String[]{"!plz"};
        this.description = "Shrugs!";
    }
    
    public void execute(String params, long ID){
        if(ID == 138897151831834624L){
            if(oRan.nextInt(100)==7){
                Launcher.send("Why don't you just use the /shrug command like seriously I'm tired of implementing this ffffffff");
            } else {
                Launcher.send("¯\\_(ツ)_/¯");
            }
        } else {
            Launcher.send("¯\\_(ツ)_/¯");
        }
    }
}