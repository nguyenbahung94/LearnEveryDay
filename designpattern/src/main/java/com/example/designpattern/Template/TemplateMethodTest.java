package com.example.designpattern.Template;

/**
 * Created by nbhung on 3/20/2018.
 */

public class TemplateMethodTest {
    public static void main(String[] args) {
        PageTemplate designPattern = new DesignPatternPage();
        designPattern.displayWebPage();

        System.out.println();

        PageTemplate java8Tutorial = new Java8TutorialPage();
        java8Tutorial.displayWebPage();

        System.out.println();

        PageTemplate contact = new ContactPage();
        contact.displayWebPage();
    }

    /*out put
HEADER
NAVIGATION
DESIGN PATTERN CONTENT
FOOTER
///
HEADER
NAVIGATION
JAVA 8 TUTORIAL CONTENT
FOOTER
///

HEADER
ABOUT CONTENT
FOOTE
Như ta thấy, tất cả các page sẽ đều có chung các thành phần trong template đã định nghĩa, chúng chỉ cần implement lại những thành phần mà CHỈ CÓ RIÊNG nó mới quyết định được.
 Vậy nguyên lý của Template Method pattern này là: Đặt tất cả những xử lý chung trong lớp cha, sau đó định nghĩa các hàm abstract và để các lớp con tự định nghĩa lấy.
 Việc này sẽ giảm thiểu sự trùng lặp code và dễ dàng bảo trì về sau khi mỗi lớp con cần có sự thay đổi.
    * */
}
