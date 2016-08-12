package example.tacademy.sampledata;

/**
 * Created by Tacademy on 2016-08-11.
 */
public class Person {
    //커서에서 가리키는거 확인하려고 인덱스가 0이니까 거기서 -1해서 아이디 -1임
    long id = -1;
    String name;
    int age;
    String phone;
    String address;

    @Override
    public String toString() {
        return name + "(" + age + ")";
    }
}
