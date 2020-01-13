public class State {
    private String name;
    private boolean isStart;
    private boolean isFinal;

    State(final String name, final boolean isStart, final boolean isFinal){
        this.name = name;
        this.isFinal = isFinal;
        this.isStart = isStart;
    }

    public String getName() {
        return name;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }
}
