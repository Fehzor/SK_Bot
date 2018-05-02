/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.weps;

import Bot.Fields.Field;
import Bot.Fields.UserData;
import Bot.Launcher;
import java.util.HashMap;

/**
 *
 * @author FF6EB4
 */
public class Teddy {
    public static Field<HashMap<String,Integer>> pow = new Field<>("TEDDY","POW",new HashMap<>());
    
    public static void increase(UserData UD){
        if(pow.getData().get(UD.ID) == null){
            pow.getData().put(UD.ID,0);
        }
        
        pow.getData().put(UD.ID,pow.getData().get(UD.ID)+1);
        
        pow.write();
    }
    
    public static void check(UserData UD){
        if(pow.getData().get(UD.ID) >= 100 ){
            pow.getData().put(UD.ID,0);
            
            Launcher.send(UD.name+" has found a winterfest present at their feet... a gremlin child would have enjoyed it but alas, it now belongs to "+UD.name+". (see artifacts)");
            UD.artifacts.getData().add("Winterfest Present");
            
            int presents = 0;
            for(String S : UD.artifacts.getData()){
                if(S.equals("Winterfest Present")){
                    presents += 1;
                }
            }
            
            if(presents >= 13){
                for(String S : UD.artifacts.getData()){
                    if(S.equals("Winterfest Present")){
                        UD.artifacts.getData().remove(S);
                    }
                }
                
                Launcher.send("Seeing as to that you have so many, you decide to open the winterfest present... all 13 vanish, and in its place are bits of gremlin money to pay for your sins. +500K cr");
                UD.crowns.append(500000);
            }
            
        }
        pow.write();
        UD.artifacts.write();
    }
}
