public class Team {
    private String mascotName;
    private int score;
    public Team(String mascotName){
        this.mascotName = mascotName;
        score = 0;
    }
    public String getMascotName(){
        return mascotName;
    }
    public int getScore(){
        return score;
    }
    public void score(){
        score += 2;
    }

    public static void main(String[] args) {
        Team team1 = new Team("Bob");
        Team team2 = new Team("John");
        team1.score();
        team2.score();
        team2.score();
        if(team1.getScore() > team2.getScore()){
            System.out.println(team1.getMascotName());
        }
        else
            System.out.println(team2.getMascotName());
    }

}
