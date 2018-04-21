package Bot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.RequestBuffer;

/**
 *
 * @author FF6EB4
 */
public class MessageTuple {
    public String message;
    public EmbedObject embed;
    public IChannel sendTo;
    public boolean string = true;
    
    public MessageTuple(String mess, IChannel chan){
        this.message = mess;
        this.sendTo = chan;
    }
    
    public MessageTuple(EmbedObject mess, IChannel chan){
        this.embed = mess;
        this.sendTo = chan;
    }
    
    public boolean addOther(MessageTuple other){
        try{
            if(!this.sendTo.equals(other.sendTo)){
                return false;
            } else if(this.message.length() + other.message.length() > 2000){
                return false;
            } else {
                this.message+="\n"+other.message;
                return true;
            }
        } catch (Exception E){return false;}
    }
    
    public MessageTuple split(){
        String text = this.message.substring(1990);
        this.message = this.message.substring(0,1990);
        MessageTuple ret = new MessageTuple(text,this.sendTo);
        return ret;
    }
    
    public void send(){
        if(message != null){
            RequestBuffer.request(() -> sendTo.sendMessage(message));
        }
        if(embed != null){
            RequestBuffer.request(() -> sendTo.sendMessage(embed));
        }
    }
}
