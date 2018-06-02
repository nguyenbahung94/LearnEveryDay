package chap1;

import io.reactivex.Observable;

public class ConnectableObservable {
	   public static void main(String[] args) {

	        io.reactivex.observables.ConnectableObservable<String> source =      
	         Observable.just("Alpha","Beta","Gamma","Delta","Epsilon")
	         .publish();

	         //Set up observer 1
	         source.subscribe(s -> System.out.println("Observer 1: " + s));

	        //Set up observer 2
	        source.map(String::length)
	          .subscribe(i -> System.out.println("Observer 2: " + i));

	        //Fire!
	        source.connect();
	        

	        //Set up observer 2
	        source.map(String::length)
	          .subscribe(i -> System.out.println("Observer 3: " + i));
	      }
}
