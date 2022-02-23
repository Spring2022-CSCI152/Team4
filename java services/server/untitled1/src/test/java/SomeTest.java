import org.junit.jupiter.api.Test;

class SomeTest {


    @Test
    void itShouldDo10(){
        //given
        int a = 10;
        Some t = new Some();
        t.test(a);
    }

    @Test
    void itShouldDo11(){
        //given
        int a = 11;
        Some t = new Some();
        t.test(a);
    }
    @Test
    void itShouldDoLessThan10(){
        //given
        int a = 8;
        Some t = new Some();
        t.test(a);
    }
}