package com.example.leokallo.nopeustesteri;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * Created by leokallo on 24.11.2016.
 */

public class Pallo {
    private View vastaavaGrafiikka;
    private Drawable vaaleaVari;
    private Drawable tummaVari;
    private boolean onkoAktiivinen;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Pallo(View pGrafiikka, Drawable pVaaleaVari, Drawable pTummaVari) {
        this.vastaavaGrafiikka = pGrafiikka;
        this.vaaleaVari = pVaaleaVari;
        this.tummaVari = pTummaVari;
        this.onkoAktiivinen = false;
        deAktivoiPallo();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void aktivoiPallo() {
        this.vastaavaGrafiikka.setBackground(vaaleaVari);
        this.onkoAktiivinen = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void deAktivoiPallo() {
        this.vastaavaGrafiikka.setBackground(tummaVari);
        this.onkoAktiivinen = false;
    }

    public boolean onkoAktiivinen() {
        return this.onkoAktiivinen;
    }
}
