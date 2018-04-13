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
import static Bot.SuperRandom.oRan;
import sx.blah.discord.handle.obj.IChannel;

/**
 *
 * @author FF6EB4
 */
public class Deposit extends Command{
    
    public Deposit(){
        this.category = 2;
        this.signature = new String[]{"!deposit"};
        this.description = "Deposits minerals! Example- !deposit all red 100 blue";
    }
    
    public void execute(String params, long ID){
        IChannel get = Launcher.currentChannel;
        Game.Gate G = Game.Gate.gates.get(get);
        UserData UD = UserData.getUD(ID);
        
        String [] splt = params.split(" ");
        
        try{
        for(int i = 0; i < splt.length; i+=2){
            int index = -1;
            if(splt[i+1].toLowerCase().equals("red")){
                index = 0;
            }
            if(splt[i+1].toLowerCase().equals("green")){
                index = 1;
            }
            if(splt[i+1].toLowerCase().equals("blue")){
                index = 2;
            }
            if(splt[i+1].toLowerCase().equals("yellow")){
                index = 3;
            }
            if(splt[i+1].toLowerCase().equals("purple")){
                index = 4;
            }
            if(splt[i].toLowerCase().equals("all")){
                G.minerals.getData()[index] += UD.minerals.getData()[index];
                UD.minerals.getData()[index] = 0;
                UD.minerals.write();
                G.minerals.write();
                Launcher.send("Deposited all "+splt[i+1]);
            } else {
                int move = Integer.parseInt(splt[i]);
                if(move > UD.minerals.getData()[index]){
                    Launcher.send("You don't have that many minerals!"
                            + "\nYou have: "+G.minerals.getData()[index]+""
                            + "\nYou're trying to deposit: "+move);
                    return;
                }
                if(move < 0){
                    Launcher.send("Feron looks over and sees you reaching your hand into the deposit box...");
                    return;
                }
                else {
                    G.minerals.getData()[index] += move;
                    UD.minerals.getData()[index] -= move;
                    UD.minerals.write();
                    G.minerals.write();
                    Launcher.send("Deposited "+move+" "+splt[i+1]);
                }
            }
        }
        
        } catch (Exception E){
            Launcher.send("Something went wrong depositing! Check for typos!");
        }
    }
}