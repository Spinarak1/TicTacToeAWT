public enum PlayerType {
    CPU_EASY, CPU_HARD, HUMAN;
    @Override
    public String toString(){
        switch (this){
            case HUMAN:
                return "human";
            case CPU_EASY:
                return "cpu_easy";
            case CPU_HARD:
                return "cpu_hard";
            default:
                return "??";
        }
    }
}
