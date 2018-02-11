public class TestaLambda {
    public static void main(String[] args) {
        Button button = new Button();
        button.click(new Action() {
            @Override
            public void execute(Button button) {
                button.disable();
            }
        });

        button.click((Button button1) -> {
            button1.disable();
        });


        button.click(b -> b.disable());

        button.click(Button::disable);
    }
}

class Button {
    private boolean enabled;

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public void click(Action a) {
        a.execute(this);
    }
}

@FunctionalInterface
interface Action {
    void execute(Button button);
}
