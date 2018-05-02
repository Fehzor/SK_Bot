/*
 * Copyright (C) 2017 FF6EB4
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package Bot.Commands;


import Bot.Commands.Implementation.*;
import Bot.Launcher;
import java.util.ArrayList;
import java.util.HashMap;
import sx.blah.discord.handle.obj.IChannel;

/**
 *
 * @author FF6EB4
 */
public class CommandParser {
    public static HashMap<String,Command> commandList = loadCommands();
    public static IChannel channel;
    
    public static void parseCommand(String S, long ID){
        String[] split = S.split(" ",2);
        String signature = split[0];
        
        String args = "";
        try{
            args = split[1];
        } catch (Exception E){}
        
        try{
            if(S.charAt(0)=='.'){
                split = S.split(" ",3);
                try{
                    signature = "!"+split[1].toLowerCase();
                    args = split[2];
                } catch (Exception E){}
            }
        } catch (Exception E){}
        
        if(commandList.containsKey(signature)){
            Command C = commandList.get(signature);
            try{
                C.execute(args,ID);
            } catch (Exception E){
                //System.err.println(UD.name+": "+signature+" "+args);
                //LOL.PM("Something went wrong with your command! Use !commands or ask for formatting help!",UD);
            }
        }
    }
    
    private static HashMap<String,Command> loadCommands(){
        HashMap<String,Command> ret = new HashMap<>();
        
        //Hidden commands first
        
        Command coms = new Commands();
        Plz plz = new Plz();
        
        Me stats = new Me();
        Recolor col = new Recolor();
        
        Who who = new Who();
        Gate G = new Gate();
        
        
        ////////////////
        
        addCommand(ret,stats);
        addCommand(ret,col);
        
        addCommand(ret,coms);
        addCommand(ret,plz);
        
        addCommand(ret,who);
        addCommand(ret,G);
        
        //////////////////
        
        addCommand(ret, new Deposit());
        addCommand(ret, new Craft());
        addCommand(ret, new Items());
        addCommand(ret, new Equip());
        addCommand(ret, new Give());
        addCommand(ret, new Embark());
        addCommand(ret, new Missions());
        addCommand(ret, new QuietMode());
        addCommand(ret, new Arsenal());
        addCommand(ret, new getCostumes());
        addCommand(ret, new Draw());
        addCommand(ret, new Top10());
        addCommand(ret, new Artifacts());
        addCommand(ret, new Inspect());
        addCommand(ret, new WorldCore());
        addCommand(ret, new ReMission());
        
        return ret;
    }
    
    private static void addCommand(HashMap<String,Command> ret,Command com){
        for(String s : com.signature){
            ret.put(s,com);
        }
    }
    
    
}
