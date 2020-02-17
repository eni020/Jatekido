package hu.bme.aut.jatekido;

class GameStat {

    String name;
    String time;

    public GameStat(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }
}
