package dsa.upc.edu.listapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dsa.upc.edu.listapp.api.RetrofitClient;
import dsa.upc.edu.listapp.api.Track;
import dsa.upc.edu.listapp.api.TracksService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        EditText etId = (EditText) findViewById(R.id.etId);
        EditText etTitle = (EditText) findViewById(R.id.etTitle);
        EditText etSinger = (EditText) findViewById(R.id.etSinger);

        Button btnPost = (Button) findViewById(R.id.btnPost);
        Button btnPut = (Button) findViewById(R.id.btnPut);

        // --- LOGIC TO HANDLE EDIT VS CREATE ---

        // Check if we passed the "isEdit" flag from the Adapter
        boolean isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {
            // EDIT MODE
            // 1. Hide POST, Show PUT
            btnPost.setVisibility(View.GONE);
            btnPut.setVisibility(View.VISIBLE);

            // 2. Get data passed from the Adapter
            String id = getIntent().getStringExtra("id");
            String title = getIntent().getStringExtra("title");
            String singer = getIntent().getStringExtra("singer");

            // 3. Fill the text boxes
            etId.setText(id);
            etTitle.setText(title);
            etSinger.setText(singer);

            // 4. Block the ID field so it cannot be changed
            etId.setEnabled(false);
        } else {
            // CREATE MODE
            // 1. Show POST, Hide PUT
            btnPost.setVisibility(View.VISIBLE);
            btnPut.setVisibility(View.GONE);

            // 2. Ensure ID is editable
            etId.setEnabled(true);

            // 3. Clear fields (optional, but good practice)
            etId.setText("");
            etTitle.setText("");
            etSinger.setText("");
        }

        // --------------------------------------

        btnPost.setOnClickListener(v -> {
            TracksService tracksService = RetrofitClient.getTracksService();
            Track track = new Track(etId.getText().toString(), etTitle.getText().toString(), etSinger.getText().toString());
            tracksService.postTrack(track).enqueue(new Callback<Track>() {
                @Override
                public void onResponse(Call<Track> call, Response<Track> response) {
                    if (response.isSuccessful()) { // removed body check to be safer
                        Toast.makeText(CreateActivity.this,
                                "Track created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateActivity.this,
                                "Could not create Track", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<Track> call, Throwable t) {
                    Toast.makeText(CreateActivity.this,
                            "Connection error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            Intent i = new Intent(CreateActivity.this, MainActivity.class);
            startActivity(i);
        });

        btnPut.setOnClickListener(v -> {
            TracksService tracksService = RetrofitClient.getTracksService();
            // In PUT, we use the same ID that is in the disabled text box
            Track track = new Track(etId.getText().toString(), etTitle.getText().toString(), etSinger.getText().toString());

            tracksService.putTrack(track).enqueue(new Callback<Track>() {
                @Override
                public void onResponse(Call<Track> call, Response<Track> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CreateActivity.this,
                                "Track updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CreateActivity.this,
                                "Could not update Track", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<Track> call, Throwable t) {
                    Toast.makeText(CreateActivity.this,
                            "Connection error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            Intent i = new Intent(CreateActivity.this, MainActivity.class);
            startActivity(i);
        });

    }
}