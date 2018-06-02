package chap2;

import io.reactivex.Observable;

public class exstartWith {
	 public static void main(String[] args) {

	        Observable<String> menu =
	          Observable.just("Coffee", "Tea", "Espresso", "Latte");

	        //print menu
	        menu.startWith("COFFEE SHOP MENU")
	          .subscribe(System.out::println);
	      //print menu
	        menu.startWithArray("COFFEE SHOP MENU","----------------")
	          .subscribe(System.out::println);
	      }
}
