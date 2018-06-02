package chap2;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class exdelay {
	 public static void main(String[] args) {

	        Observable.just("Alpha", "Beta", "Gamma" ,"Delta", "Epsilon")
	          .delay(3, TimeUnit.SECONDS)
	          .subscribe(s -> System.out.println("Received: " + s));

	          sleep(5000);
	     }
	     public static void sleep(long millis) {
	       try {
	         Thread.sleep(millis);
	       } catch (InterruptedException e) {
	         e.printStackTrace();
	       }
	     }
}
