public class State {
    private String name;
    private boolean isStart;
    private boolean isFinal;
    private int index;
    private boolean isAccessible;
    private boolean isProductive;


    State(final String name, final boolean isStart, final boolean isFinal){
        this.name = name;
        this.isFinal = isFinal;
        this.isStart = isStart;
        this.isAccessible = false;
        this.isProductive = false;

    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
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

    public void setStart(boolean start) {
        this.isStart = start;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(boolean accessible) {
        isAccessible = accessible;
    }

    public boolean isProductive() {
        return isProductive;
    }

    public void setProductive(boolean productive) {
        isProductive = productive;
    }


    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", isStart=" + isStart +
                ", isFinal=" + isFinal +
                ", index=" + index +
                ", isAccessible=" + isAccessible +
                ", isProductive=" + isProductive +
                '}';
    }
}
