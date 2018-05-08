package pl.politechnika.szczesm3.astroweather.planet;

class Moon {
    private static final Moon ourInstance = new Moon();

    static Moon getInstance() {
        return ourInstance;
    }

    private Moon() {
    }
}
