package chap1;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
@SuppressWarnings("unused")
public class demoRxjava1 {
  public static void main(String[] args) {
	  
	  Observable.just("Hi","Hello","Xin chao")
	  .subscribe(x->System.out.println(x));
	  
	  Observable.just("Hi","Hello","Xin chao")
	  .map(s->s.length())
	  .subscribe(x->System.out.println(x));
	  
	  //observable.interval()
	  Observable<Long>sencondIntervals=Observable.interval(1, TimeUnit.SECONDS);
	  sencondIntervals.subscribe(s->System.out.println(s));
	  /* Hold main thread for 5 seconds
      so Observable above has chance to fire */
      sleep(10000l);
  }
  public static void sleep(long millis) {
      try {
        Thread.sleep(millis);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
}
