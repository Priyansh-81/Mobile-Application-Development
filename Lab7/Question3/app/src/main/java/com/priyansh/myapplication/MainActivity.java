package com.priyansh.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private TextView descriptionTextView;
    private EditText searchEditText;
    private String originalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descriptionTextView = findViewById(R.id.description_text_view);
        searchEditText = findViewById(R.id.search_edit_text);
        originalText = getString(R.string.digital_transformation_description);
        descriptionTextView.setText(originalText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.search_keywords) {
            searchKeywords();
            return true;
        } else if (itemId == R.id.highlight) {
            highlightText();
            return true;
        } else if (itemId == R.id.sort_alphabetically) {
            sortAlphabetically();
            return true;
        } else if (itemId == R.id.sort_by_relevance) {
            sortByRelevance();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchKeywords() {
        String keyword = searchEditText.getText().toString();
        if (keyword.isEmpty()) {
            Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
            return;
        }
        if (originalText.toLowerCase().contains(keyword.toLowerCase())) {
            Toast.makeText(this, "Keyword found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Keyword not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void highlightText() {
        String textToHighlight = searchEditText.getText().toString();
        if (textToHighlight.isEmpty()) {
            Toast.makeText(this, "Please enter text to highlight", Toast.LENGTH_SHORT).show();
            return;
        }

        Spannable spannable = new SpannableString(originalText);
        String normalizedOriginal = Normalizer.normalize(originalText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
        String normalizedHighlight = Normalizer.normalize(textToHighlight, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        int index = normalizedOriginal.indexOf(normalizedHighlight);
        while (index >= 0) {
            spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), index, index + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            index = normalizedOriginal.indexOf(normalizedHighlight, index + 1);
        }
        descriptionTextView.setText(spannable);
    }

    private void sortAlphabetically() {
        String[] words = originalText.split("\\s+");
        Arrays.sort(words);
        StringBuilder sortedText = new StringBuilder();
        for (String word : words) {
            sortedText.append(word).append(" ");
        }
        descriptionTextView.setText(sortedText.toString().trim());
    }

    private void sortByRelevance() {
        final String keyword = searchEditText.getText().toString();
        if (keyword.isEmpty()) {
            Toast.makeText(this, "Please enter a keyword for relevance sorting", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] sentences = originalText.split("\\. ");
        Arrays.sort(sentences, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int count1 = countOccurrences(s1.toLowerCase(), keyword.toLowerCase());
                int count2 = countOccurrences(s2.toLowerCase(), keyword.toLowerCase());
                return Integer.compare(count2, count1);
            }
        });

        StringBuilder sortedText = new StringBuilder();
        for (String sentence : sentences) {
            sortedText.append(sentence).append(". ");
        }
        descriptionTextView.setText(sortedText.toString().trim());
    }

    private int countOccurrences(String text, String keyword) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(keyword, index)) != -1) {
            count++;
            index += keyword.length();
        }
        return count;
    }
}