package com.itkweb.hday.similarakka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.itkweb.hday.similarakka.actors.FileReaderActor;
import com.itkweb.hday.similarakka.messages.FileToRead;

public class Test {

    public static void main(String[] args) {
    	
        final ActorSystem system = ActorSystem.create("similarakka");

    	final ActorRef fileActor = system.actorOf(Props.create(FileReaderActor.class), "fileReader");
    	FileToRead ftr = new FileToRead("/home/etaix/development/workspaces/HDay-luna/similarakka/docs/MOLIERE-Le_misanthrope-[Atramenta.net].txt");
    	fileActor.tell(ftr, ActorRef.noSender());

    }

//
//    public static class DispatchActor extends AbstractActor {
//        @Override
//        public PartialFunction<Object, BoxedUnit> receive() {
//        	
//        	
//            return ReceiveBuilder.
//                    match(Greeting.class, message -> System.out.println(message.message)).
//                    build();
//        }
//    }
	
}
