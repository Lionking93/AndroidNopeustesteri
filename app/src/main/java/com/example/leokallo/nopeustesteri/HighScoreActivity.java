package com.example.leokallo.nopeustesteri;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HighScoreActivity extends Activity {

    private final String PREFS_NAME = "com.example.leokallo.nopeustesteri.PREFS_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        String[] highScoreArray = getHighScores();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                highScoreArray);
        ListView highScores = (ListView)findViewById(R.id.content_high_score);
        highScores.setAdapter(adapter);
    }

    public String[] getHighScores() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        Set<String> defValues = new HashSet<>();
        defValues.add(getString(R.string.no_scores));
        Set<String> highScores = prefs.getStringSet("highScores",defValues);
        if (highScores.size() < 1) {
            return highScores.toArray(new String[highScores.size()]);
        }
        highScores.remove(getString(R.string.no_scores));
        String[] highScoreArray = highScores.toArray(new String[highScores.size()]);
        String[] sortedHighScores = stringArrayToReverseStringArray(highScoreArray);
        return sortedHighScores;
    }

    public String[] stringArrayToReverseStringArray(String[] stringArray) {
        Integer[] intArray = new Integer[stringArray.length];
        for(int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }
        Arrays.sort(intArray, Collections.<Integer>reverseOrder());
        String[] newStringArray = new String[intArray.length];
        for(int j = 0; j < intArray.length; j++) {
            newStringArray[j] = intArray[j]+"";
        }
        return newStringArray;
    }
}
