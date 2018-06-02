package chap2;

import io.reactivex.Observable;

public class exscan {
	 public static void main(String[] args) {

	        Observable.just(5, 3, 7, 10, 2, 14)
	          .scan((accumulator, next) -> accumulator + next)
	          .subscribe(s -> System.out.println("Received: " + s));
	        System.out.println("//////////////////////////");
	        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
	          .scan(0, (total, next) -> total + 1)
	          .subscribe(s -> System.out.println("Received: " + s));

	      }
}
