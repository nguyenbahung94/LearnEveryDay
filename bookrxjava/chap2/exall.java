package chap2;

import io.reactivex.Observable;

public class exall {
	  public static void main(String[] args) {

	        Observable.just(5, 3, 7, 1, 2, 9) 
	          .all(i -> i < 10)
	          .subscribe(s -> System.out.println("Received: " + s));
	     }
}
