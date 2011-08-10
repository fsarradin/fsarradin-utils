package javafp;

public class TestInline {
    public static void main(String[] args) {
        TestInline holder = new TestInline(1);

        System.out.println(holder.get());
    }

    private int i;

    public TestInline(int i) {
        this.i = i;
    }

    public final int get() {
        return i;
    }

}
