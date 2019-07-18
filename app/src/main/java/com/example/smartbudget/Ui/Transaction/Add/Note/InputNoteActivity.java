package com.example.smartbudget.Ui.Transaction.Add.Note;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

public class InputNoteActivity extends AppCompatActivity {

    private static final String TAG = InputNoteActivity.class.getSimpleName();

    private EditText inputNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_note);
        Log.d(TAG, "onCreate: started!!");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputNote = findViewById(R.id.input_note_edt);

        String passedString = getIntent().getStringExtra(Common.EXTRA_PASS_INPUT_NOTE);
        if (passedString != null) {
            inputNote.setText(passedString);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.input_note_save) {
            if (TextUtils.isEmpty(inputNote.getText())) {
                Toast.makeText(this, "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                return true;
            }
            Intent goBackIntent = new Intent();
            goBackIntent.putExtra(Common.EXTRA_INPUT_NOTE, inputNote.getText().toString());
            setResult(RESULT_OK, goBackIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
