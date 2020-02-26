public enum StartingPlayer {
    CIRCLE, CROSS, RANDOM;
    @Override
    public String toString(){
        switch (this){
            case CIRCLE:
                return "circle";
            case CROSS:
                return "cross";
            case RANDOM:
                return "random";
            default:
                return "??";
        }
    }
}
