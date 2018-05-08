package pl.politechnika.szczesm3.astroweather.planet;

class Sun {
    private static final Sun ourInstance = new Sun();

    static Sun getInstance() {
        return ourInstance;
    }

    private Sun() {
    }
}
