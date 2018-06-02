package chap3;

import java.util.concurrent.ThreadLocalRandom;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

public class exMulticasting2 {
	 public static void main(String[] args) {

		 ConnectableObservable<Integer> threeRandoms = Observable.range(1,3)
	                .map(i -> randomInt()).publish();

	        threeRandoms.subscribe(i -> System.out.println("Observer 1: " + i));
	        threeRandoms.subscribe(i -> System.out.println("Observer 2: " + i));

	        threeRandoms.connect();
	    }

	    public static int randomInt() {
	        return ThreadLocalRandom.current().nextInt(100000);
	    }
}
