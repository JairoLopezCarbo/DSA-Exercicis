package dsa.upc.edu.listapp;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper; // Importante
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import dsa.upc.edu.listapp.api.RetrofitClient;
import dsa.upc.edu.listapp.api.Track;
import dsa.upc.edu.listapp.api.TracksService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private TracksService tracksService;
    private ImageView btnCreate;

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Vincular Vistas
        recyclerView = findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = findViewById(R.id.my_swipe_refresh); // Vinculamos el Swipe
        btnCreate = findViewById(R.id.imageCreate);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Configurar el SwipeRefreshLayout (Recargar al deslizar hacia abajo)
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Llamamos a cargar tracks de nuevo
                loadTracks();
            }
        });

        // 3. Configurar el Swipe-to-Delete (Borrar al deslizar a la derecha)
        setupSwipeToDelete();

        // 4. Botón crear
        btnCreate.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CreateActivity.class);
            i.putExtra("isEdit", false); // Explicitly say this is CREATE mode
            startActivity(i);
        });

        // Carga inicial
        loadTracks();
    }

    private void loadTracks() {
        tracksService = RetrofitClient.getTracksService();

        tracksService.getTracks().enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                // Detener la animación de carga siempre
                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {
                    // ¡OJO! Asignamos a la variable global 'adapter', no creamos una nueva local
                    adapter = new MyAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Could not load Tracks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                // Detener la animación de carga si falla
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Connection error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Configuración del gesto de deslizar
    private void setupSwipeToDelete() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0, // No usamos drag & drop (mover arriba/abajo)
                ItemTouchHelper.RIGHT) { // Solo permitimos swipe a la derecha (puedes añadir LEFT | RIGHT)

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false; // No hacemos nada al mover
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // 1. Obtener la posición del item deslizado
                int position = viewHolder.getAdapterPosition();

                // 2. Obtener el Track asociado (Necesitas implementar 'getTrack' en tu Adapter)
                Track trackToDelete = adapter.getTrack(position);

                // 3. Llamar a la API para borrar
                deleteTrackFromApi(trackToDelete.getId(), position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void deleteTrackFromApi(String id, final int position) {
        tracksService.deleteTrack(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Si la API confirma el borrado, lo quitamos de la lista visualmente
                    adapter.removeTrack(position);
                    Toast.makeText(MainActivity.this, "Track deleted", Toast.LENGTH_SHORT).show();
                } else {
                    // Si falla, avisamos y restauramos la vista (cancelamos el swipe visual)
                    adapter.notifyItemChanged(position);
                    Toast.makeText(MainActivity.this, "Error deleting track", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Si falla la red, restauramos la vista
                adapter.notifyItemChanged(position);
                Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}