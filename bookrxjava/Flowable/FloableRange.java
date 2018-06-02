package Flowable;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class FloableRange {
	 public static void main(String[] args) {

         Flowable.range(1, 999_999_999)
                 .map(MyItem::new)
                 .observeOn(Schedulers.io())
                 .subscribe(myItem -> {
                     sleep(50);
                     System.out.println("Received MyItem " + myItem.id);
                 });

         sleep(5000);
     }

     static void sleep(long milliseconds) {
         try {
             Thread.sleep(milliseconds);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

     static final class MyItem {

         final int id;

         MyItem(int id) {
             this.id = id;
             System.out.println("Constructing MyItem " + id);
         }
     }
}
