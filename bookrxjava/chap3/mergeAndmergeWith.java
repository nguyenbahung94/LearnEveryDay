package chap3;

import io.reactivex.Observable;

public class mergeAndmergeWith {
	 public static void main(String[] args) {

	        Observable<String> source1 =
	          Observable.just("Alpha", "Beta", "Gamma", "Delta", 
	"Epsilon");

	        Observable<String> source2 =
	          Observable.just("Zeta", "Eta", "Theta");

	        Observable.merge(source1, source2)
	          .subscribe(i -> System.out.println("RECEIVED: " + i));
	        
	        
	      System.out.println("///////////////////////////");
	      source1.mergeWith(source2)
	      .subscribe(i -> System.out.println("RECEIVED: " + i));
	     }
}
