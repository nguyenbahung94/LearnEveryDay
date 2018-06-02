package com.example.designpattern.DIPattern.ex1beforeuseDi;

/**
 * Created by nbhung on 3/16/2018.
 */
/*
* Note:
* Class MyApplication có trách nhiệm khởi tạo service email và sau đó sử dụng nó. Điều này dẫn đến sự phụ thuộc trong code.
* Giả sử nếu chúng ta muốn chuyển sang một số service email khác ngon hơn trong tương lai, ta sẽ phải thay đổi code cả trong trong lớp MyApplication.
*  Điều này làm cho ứng dụng khó mở rộng và nếu service email được sử dụng trong nhiều class thì điều đó thậm chí còn mất thời gian và khó hơn nhiều.
*  Hoặc nếu chúng ta muốn mở rộng ứng dụng để nâng cấp tính năng nhắn tin, chẳng hạn như tin nhắn SMS hoặc Facebook thì sẽ cần phải viết lại một application khác cho điều đó.
*  Điều này sẽ bao gồm cả việc code một class mới và thay đổi code phía client class để sử dụng application mới này.
*  Việc tiến hành làm unit test cùng sẽ rất phức tạp vì application đang tạo ra các instance email service một cách trực tiếp. Không có cách nào chúng ta có thể giả lập các objects trong class test
* */
public class MyApplication {
    private EmailService email = new EmailService();

    public void processMessages(String msg, String rec) {
        //do some msg validation, manipulation logic etc
        this.email.sendEmail(msg, rec);
    }
}
