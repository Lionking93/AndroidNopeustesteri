package com.example.leokallo.nopeustesteri;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameActivity extends AppCompatActivity implements PeliLoppuiDialogFragment.PeliLoppuiDialogListener {

    private Handler ajastin;
    private Logiikka logiikka;
    private Runnable paivittaja;
    private HashMap<Integer, Pallo> pallot;
    private final String PREFS_NAME = "com.example.leokallo.nopeustesteri.PREFS_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStart() {
        super.onStart();
        TextView scoreView = (TextView)findViewById(R.id.tulos);
        scoreView.setText(getText(R.string.score) + "" + 0);
        this.pallot = alustaPallot();
        this.logiikka = new Logiikka(this.pallot);
        this.ajastin = new Handler();
        this.paivittaja = new Runnable() {
            @Override
            public void run() {
                if (logiikka.jatkuukoPeli()) {
                    paivitaRuutu();
                    ajastin.postDelayed(this, logiikka.paivitaAjastimenViive());
                } else {
                    havisitPelin();
                }
            }
        };
        this.ajastin.postDelayed(this.paivittaja, 1800);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void paivitaRuutu() {
        this.logiikka.setOnkoPainettu(false);
        Pallo p = this.logiikka.arvoSatunnainenPallo();
        if (this.logiikka.getAktiivinenPallo() != null) {
            this.logiikka.deaktivoiPallo(logiikka.getAktiivinenPallo());
        }
        this.logiikka.teePalloAktiiviseksi(p);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.ajastin.removeCallbacks(this.paivittaja);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void merkkaaPallo(View view) {
        Pallo p = this.pallot.get(view.getId());
        if (!this.logiikka.painettuOikeaaPalloa(p)) {
            havisitPelin();
        }
        paivitaTulos();
    }

    public void havisitPelin() {
        talletaTulos();
        this.ajastin.removeCallbacks(this.paivittaja);
        DialogFragment d = new PeliLoppuiDialogFragment();
        d.show(getFragmentManager(), "PeliLoppuiDialogFragment");
    }

    public void paivitaTulos() {
        TextView scoreView = (TextView)findViewById(R.id.tulos);
        int score = this.logiikka.getVuoro()-1;
        scoreView.setText(getText(R.string.score) + "" + score);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private HashMap<Integer,Pallo> alustaPallot() {
        Resources res = getResources();
        HashMap<Integer, Pallo> pallot = new HashMap<>();
        pallot.put(R.id.ekaPallo, new Pallo(
                findViewById(R.id.ekaPallo),
                res.getDrawable(R.drawable.circle_red),
                res.getDrawable(R.drawable.circle_dark_red)));
        pallot.put(R.id.tokaPallo, new Pallo(
                findViewById(R.id.tokaPallo),
                res.getDrawable(R.drawable.circle_green),
                res.getDrawable(R.drawable.circle_dark_green)
        ));
        pallot.put(R.id.kolmasPallo, new Pallo(
                findViewById(R.id.kolmasPallo),
                res.getDrawable(R.drawable.circle_blue),
                res.getDrawable(R.drawable.circle_dark_blue)
        ));
        pallot.put(R.id.neljasPallo, new Pallo(
                findViewById(R.id.neljasPallo),
                res.getDrawable(R.drawable.circle_yellow),
                res.getDrawable(R.drawable.circle_dark_yellow)
        ));
        return pallot;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        onStart();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        super.finish();
    }

    public void talletaTulos() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        Set<String> defValues = new HashSet<>();
        defValues.add(getString(R.string.no_scores));
        Set<String> highScores = prefs.getStringSet("highScores", defValues);
        Set<String> newHighScores = new HashSet<>();
        if (highScores.size() > 5) {
            highScores.remove(poistaPienin(highScores));
        }
        int uusiTulos = this.logiikka.getVuoro()-1;
        newHighScores.add(uusiTulos+"");
        newHighScores.addAll(highScores);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("highScores",newHighScores);
        editor.apply();
    }

    public String poistaPienin(Set<String> pSet) {
        String[] setAsStrings = pSet.toArray(new String[pSet.size()]);
        List<Integer> setAsInts = new ArrayList();
        for (int i = 0; i < setAsStrings.length; i++) {
            setAsInts.add(Integer.parseInt(setAsStrings[i]));
        }
        Collections.sort(setAsInts);
        return setAsInts.get(0)+"";
    }
}
