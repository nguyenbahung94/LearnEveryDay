package chap2;

import java.util.Comparator;

import io.reactivex.Observable;

public class exsorted {
	  public static void main(String[] args) {

	        Observable.just(6, 2, 5, 7, 1, 4, 9, 8, 3)
	          .sorted()
	          .subscribe(System.out::println);
	        
	        System.out.println("//////////////////////");
	        Observable.just(6, 2, 5, 7, 1, 4, 9, 8, 3)
	          .sorted(Comparator.reverseOrder())
	          .subscribe(System.out::println);
	        
	        System.out.println("////////////////////////////");
	        Observable.just("Alpha", "Beta", "Gamma" ,"Delta", "Epsilon")
	          .sorted((x,y) -> Integer.compare(x.length(), y.length()))
	          .subscribe(System.out::println);
	      }
}
