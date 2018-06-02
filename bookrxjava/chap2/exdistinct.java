package chap2;

import io.reactivex.Observable;

public class exdistinct {
	public static void main(String[] args) {

		 Observable.just("Alpha", "Betas", "Gamm", "Delta", "Epsilon")
         .map(String::length)
         .distinct()
         .subscribe(i -> System.out.println("RECEIVED: " + i));
		 
		 Observable.just("Alpha", "Betas", "Gamm", "Delta", "Epsilon")
         .distinct(String::length)
         .subscribe(i -> System.out.println("RECEIVED: " + i));
      }
}
