package com.example.leokallo.nopeustesteri;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by leokallo on 22.11.2016.
 */

public class Logiikka {
    private HashMap<Integer, Pallo> pallot;
    private Pallo aktiivinen;
    private boolean onkoPainettu;

    public int getVuoro() {
        return vuoro;
    }

    private int vuoro;
    private int ajastimenViive;

    public Logiikka(HashMap<Integer, Pallo> pPallot) {
        this.pallot = pPallot;
        this.onkoPainettu = false;
        this.vuoro = 0;
        this.ajastimenViive = 1800;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void teePalloAktiiviseksi(Pallo pallo) {
        pallo.aktivoiPallo();
        this.aktiivinen = pallo;
    }

    public Pallo getAktiivinenPallo() {
        return this.aktiivinen;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void deaktivoiPallo(Pallo pallo) {
        pallo.deAktivoiPallo();
        this.aktiivinen = null;
    }

    public Pallo arvoSatunnainenPallo() {
        Random random = new Random();
        Pallo p = null;
        while(true) {
            int i = random.nextInt(4);
            switch(i) {
                case 0: p = pallot.get(R.id.ekaPallo);
                    break;
                case 1: p = pallot.get(R.id.tokaPallo);
                    break;
                case 2: p = pallot.get(R.id.kolmasPallo);
                    break;
                case 3: p = pallot.get(R.id.neljasPallo);
                    break;
            }
            if (p != aktiivinen) {
                break;
            }
        }
        return p;
    }

    public int paivitaAjastimenViive() {
        if (this.vuoro > 0 && this.vuoro % 5 == 0) {
            this.ajastimenViive -= 100;
        }
        return this.ajastimenViive;
    }

    public boolean jatkuukoPeli() {
        if (this.vuoro < 1) {
            this.vuoro++;
            return true;
        } else if (this.onkoPainettu) {
            return true;
        } else {
            return false;
        }
    }

    public void setOnkoPainettu(boolean p) {
        this.onkoPainettu = p;
    }

    public boolean painettuOikeaaPalloa(Pallo p) {
        if (p == this.aktiivinen) {
            this.vuoro++;
            this.onkoPainettu = true;
            return true;
        }
        return false;
    }
}
