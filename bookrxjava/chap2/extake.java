package chap2;

import io.reactivex.Observable;

public class extake {
	 public static void main(String[] args) {

	      Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
	      .take(2)
	      .subscribe(s -> System.out.println("RECEIVED: " + s));
	    }
}
