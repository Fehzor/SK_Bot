package Bot;

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

import Bot.ChatParser.messageHandler;
import Bot.Commands.CommandParser;
import Bot.Fields.UserData;
import static Bot.SuperRandom.oRan;
import Game.Gate;
import Game.Mission;
import Game.Weapons;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import java.awt.Color;
import static java.lang.Thread.yield;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.text.Document;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.RequestBuffer;

public class Launcher implements IListener<MessageReceivedEvent>{

	public static Launcher INSTANCE; // Singleton instance of the bot.
	public static IDiscordClient client; // The instance of the discord client.
        
        //public static final String outputID = "339538527878643723";
        private static boolean slimeCoin = false;
        
        private static ArrayList<MessageReceivedEvent> eventList = new ArrayList<>();
        
        private static ArrayList<MessageTuple> messages = new ArrayList<>();
        private static ArrayList<MessageTuple> toAdd = new ArrayList<>();
        
        public static IChannel currentChannel = null;
        
	public static void main(String[] args) { // Main method
		INSTANCE = login(args[0]); // Creates the bot instance and logs it in.
        }
        
        public static Lock messageLock = new ReentrantLock();
        public static Lock messageLock2 = new ReentrantLock();
        public static Lock handleLock = new ReentrantLock();

	public Launcher(IDiscordClient client) {
            
		this.client = client; // Sets the client instance to the one provided
                
                //Creates a thing that uploads messages in a timely manner.
                new Thread(){
                    public int delay = 100;
                    public void run() {
                        while(true){
                            post();
                            try{
                                Thread.sleep(delay);
                            } catch(Exception E){System.out.println("SLEEP FAIL"+E);};
                        }
                    }
                    
                    public void post(){
                        //System.out.println("POSTING");
                        boolean bump = Launcher.actuallySend();
                        if(bump){
                            delay += 50;
                        } else {
                            if(delay > 100){
                                delay -= 1;
                            }
                        }
                    }
                }.start();
                
                //Creates a thing that uploads messages in a timely manner.
                new Thread(){
                    public int delay = 100;
                    public void run() {
                        while(true){
                            handle();
                            try{
                                Thread.sleep(delay);
                            } catch(Exception E){System.out.println("SLEEP FAIL"+E);};
                        }
                    }
                    
                    public void handle(){
                        Launcher.handle();
                    }
                }.start();
                
                EventDispatcher dispatcher = client.getDispatcher(); // Gets the client's event dispatcher
		dispatcher.registerListener(this);
              
                Weapons.loadAll();
                Mission.startMissions();
	}

	/**
	 * A custom login() method to handle all of the possible exceptions and set the bot instance.
	 */
	public static Launcher login(String token) {
		Launcher bot = null; // Initializing the bot variable

		ClientBuilder builder = new ClientBuilder(); // Creates a new client builder instance
		builder.withToken(token); // Sets the bot token for the client
		try {
			IDiscordClient client = builder.login(); // Builds the IDiscordClient instance and logs it in
			bot = new Launcher(client); // Creating the bot instance
		} catch (DiscordException e) { // Error occurred logging in
			System.err.println("Error occurred while logging in!");
			e.printStackTrace();
		}
                
                
                
		return bot;
	}
        
       public void handle(MessageReceivedEvent event) {
            eventList.add(event);
       }
       
       //Actually handles things
       public static void handle(){
            if(!handleLock.tryLock()){
                return;
            }
            
            if(eventList.size() == 0){
                handleLock.unlock();
                return;
            }
            
            MessageReceivedEvent event = eventList.remove(0);
            IMessage message = event.getMessage(); // Gets the message from the event object NOTE: This is not the content of the message, but the object itself
            IChannel channel = message.getChannel(); // Gets the channel in which this message was sent.
            currentChannel = channel;
            
            UserData UD = UserData.getUD(message.getAuthor());
            if(!Mission.knights.getData().contains(message.getAuthor().getLongID())){
                Gate G;
                try{
                G = Gate.gates.get(currentChannel);

                if(!G.activeUsers.contains(Long.parseLong(UD.ID))){
                    G.activeUsers.add(Long.parseLong(UD.ID));
                }

                if(System.currentTimeMillis() - UD.lastMessage.getData() < 20000){
                    G.tick();
                }

                } catch (NullPointerException NPE){
                    Gate.instantiateGates(currentChannel.getGuild());

                    try{
                        G = Gate.gates.get(currentChannel);

                        if(!G.activeUsers.contains(Long.parseLong(UD.ID))){
                            G.activeUsers.add(Long.parseLong(UD.ID));
                        }

                        if(System.currentTimeMillis() - UD.lastMessage.getData() < 20000){
                            G.tick();
                        }
                    } catch (NullPointerException NPE2){}
                }
            }
            
            
            CommandParser.parseCommand(message.toString(), message.getAuthor().getLongID());
            
            //If they talk it puts them in the right place.
            //IRole get = Area.getArea(UD.location.getData()).role;
            //message.getAuthor().addRole(get);
            
            //This class checks for and increments a person's lols and emoji.
            if(!channel.isPrivate()){
                messageHandler.receive(channel,message);
            }
            
            UD.lastMessage.writeData(System.currentTimeMillis());
            
            System.out.println("PROCESSED = "+message.getAuthor().getName()+": "+message.getContent());
            handleLock.unlock();
            return;
       }
       
       public static void send(String message, IChannel channel){
            toAdd.add(new MessageTuple(message, channel));
       }
       
       public static void send(EmbedBuilder message, IChannel channel){
            EmbedObject EO = message.build();
            toAdd.add(new MessageTuple(EO,channel));
       }
       
       public static void send(String message){
           send(message,currentChannel);
       }
       
       private static boolean actuallySend(){
           if(messageLock.tryLock()){
               //System.out.println("ATTEMPTING TO SEND- "+toAdd.size());
               if(messages.size() == 0){
                   for(int i = toAdd.size()-1; i >= 0; --i){
                       messages.add(toAdd.remove(0));
                   }
                   if(messages.size() == 0){
                       messageLock.unlock();
                       return false;
                   }
               }
               
               //System.out.println("ATTEMPTING TO SEND");
               
               //Combine messages to save rate!
               boolean combine = true;
               while(combine){   
                   try{
                   MessageTuple A = messages.get(0);
                   MessageTuple B = messages.get(1);
                   if(A.addOther(B)){
                       messages.remove(1);
                   } else {
                       combine = false;
                   }
                   } catch (Exception E){combine = false;}
               }
               
               MessageTuple mess = messages.remove(0);
              
               try {
                    mess.send();
                    
                } catch (RateLimitException e) { // RateLimitException thrown. The bot is sending messages too quickly!
                        //System.err.print("Sending messages too quickly!");
                        //e.printStackTrace();
                        messages.add(0,mess);
                        messageLock.unlock();
                        return true;
                } catch (DiscordException e) { // DiscordException thrown. Many possibilities. Use getErrorMessage() to see what went wrong.
                        System.err.print(e.getErrorMessage()); // Print the error message sent by Discord
                        e.printStackTrace();

                } catch (MissingPermissionsException e) { // MissingPermissionsException thrown. The bot doesn't have permission to send the message!
                        System.err.print("Missing permissions for channel!");
                        e.printStackTrace();
                } catch (Exception E){
                    System.err.println("FOUND EXCEPTION "+E);
                }
               messageLock.unlock();
               return false;
           }
           return false;
       }

       
       public static void PM(String message, long ID){
           IUser user = client.fetchUser(ID);
           IChannel get = client.getOrCreatePMChannel(user);
           send(message, get);
       }
}
