/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bot.ChatParser;

import Bot.Fields.UserData;
import static Bot.SuperRandom.oRan;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author FF6EB4
 */
public class messageHandler {
    private static messageHandler whatever = new messageHandler();
    private messageHandler(){}
    
    private static ArrayList<String> toAdd = new ArrayList<>();
    private static ArrayList<IMessage> message = new ArrayList<>();
    
    private static final String emojiRegex = "([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])";
    private static final String[] LOLS = new String[]{
        "lol",
        "lolol",
        "lololol",
        "lolololol",
        "lololololol",
        "lolololololol",
        "rofl",
        "lmao",
        "kek",
        "lel",
        "lul",
        "lole",
        "lawl",
        "lmfao",
        "/lol",
        "lolxd",
        "lool",
        "555",
        "lawlz",
        "roflcopter",
        "lols",
        "lolz",
        "luls",
        "lulz",
        "xd"
    };
    
    
    private static ArrayList<Emoji> up = loadUps();
    private static ArrayList<Emoji> loadUps(){
        ArrayList<Emoji> ret = new ArrayList<>();
        ret.add(EmojiManager.getForAlias("up"));
        ret.add(EmojiManager.getForAlias("eggplant"));
        ret.add(EmojiManager.getForAlias("arrow_double_up"));
        ret.add(EmojiManager.getForAlias("sunglasses"));
        ret.add(EmojiManager.getForAlias("upside_down"));
        ret.add(EmojiManager.getForAlias("arrow_up"));
        ret.add(EmojiManager.getForAlias("point_up"));
        ret.add(EmojiManager.getForAlias("point_up_2"));
        ret.add(EmojiManager.getForAlias("arrow_up_small"));
        ret.add(EmojiManager.getForAlias("v"));
        ret.add(EmojiManager.getForAlias("stuck_out_tongue_winking_eye"));
        ret.add(EmojiManager.getForAlias("bacon"));
        ret.add(EmojiManager.getForAlias("bouquet"));
        ret.add(EmojiManager.getForAlias("hot_pepper"));
        ret.add(EmojiManager.getForAlias("pineapple"));
        ret.add(EmojiManager.getForAlias("pizza"));
        ret.add(EmojiManager.getForAlias("microphone"));
        ret.add(EmojiManager.getForAlias("headphones"));
        ret.add(EmojiManager.getForAlias("arrow_upper_right"));
        ret.add(EmojiManager.getForAlias("100"));
        return ret;
    }
    
    private static HashMap<IChannel,IMessage> previous = new HashMap<>();
    private static HashMap<IChannel,IMessage> current = new HashMap<>();
    
    public static void receive(IChannel chan,IMessage mess){
        if(!current.containsKey(chan)){
            previous.put(chan,mess);
            current.put(chan,mess);
            return;
        }
        
        IMessage set = current.get(chan);
        
        //IF IT'S FROM THE SAME PERSON USE THE PREVIOUS MESSAGE!
        IUser a = set.getAuthor();
        IUser b = mess.getAuthor();
        if(a.equals(b)){
            set = previous.get(chan);
        }
        
        IUser get = set.getAuthor();
        UserData UD = UserData.getUD(get);
        
        boolean wasLol = check(mess.toString());
        if(wasLol){
            upvote(set);
            UD.lols.writeData(UD.lols.getData() + 1);
        } else {
            if(!a.equals(b)){
                previous.put(chan,current.get(chan));
                current.put(chan,mess);
                UD.blocks.append(1);
            } else {
                current.put(chan, mess);
            }
        }
        
        //You can't give yourself emoji.
        addEmoji(mess,set,UD);
    }
    
    private static boolean check(String check){
        check = check.toLowerCase();
        String [] parse = check.split(" ");
        for(String s : parse){
            for(String lol : LOLS){
                if(s.equals(lol)){
                    return true;
                }
            }
        }
        return false;
    } 
    
    private static void addEmoji(IMessage mess, IMessage add, UserData UD){
        HashMap<String,Integer> map = UD.emoji.getData();
        
        int twenty = 0;
        
        Matcher matchEmo;
        matchEmo = Pattern.compile(emojiRegex).matcher(mess.toString());
        while (matchEmo.find()) {
            String emoji = matchEmo.group();
            if(map.containsKey(emoji)){
                try{
                    add.addReaction(emoji);
                } catch (Exception E){}
                map.put(emoji,map.get(emoji)+1);
            } else {
                try{
                    add.addReaction(emoji);
                } catch (Exception E){}
                map.put(emoji,1);
            }
            
            try{
                Thread.sleep(1000);
            } catch (Exception E){}
            
            //Only count the first 5 emoji.
            twenty++;
            if(twenty > 19){
                UD.emoji.writeData(map);
                return;
            }
        }
        
        UD.emoji.writeData(map);
    }
    
    
    
    private static void upvote(IMessage mess){
        try{
            Emoji get = up.get(oRan.nextInt(up.size()));
            mess.addReaction(get);
        } catch (Exception E){}
    }
}

//:flag_ac: :flag_bd: :flag_eg: :flag_fi: :flag_gh: :flag_il: :flag_jm: