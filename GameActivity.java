package project.randd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    GridView gridView;
    Grid grid;

    static final int gridSize = 7;
    int level;

    static final String[] numbersLevel1 = new String[] {
            "4", "", "3", "", "3", "", "2",
            "", "2", "", "", "", "4", "1",
            "3", "", "", "3", "", "", "3",
            "", "", "", "", "", "", "",
            "2", "", "", "8", "", "4", "",
            "", "", "", "", "1", "", "3",
            "", "1", "", "4", "", "1", ""};
    static final String[] numbersLevel2 = new String[] {
            "", "", "", "", "", "", "1",
            "3", "", "5", "", "", "3", "",
            "", "", "", "2", "", "", "",
            "", "", "", "", "", "", "",
            "", "", "", "4", "", "3", "",
            "", "", "", "1", "", "", "",
            "3", "", "6", "", "", "", "3"};
    static final String[] numbersLevel3 = new String[] {
            "4", "", "2", "", "", "", "",
            "", "1", "", "", "3", "", "3",
            "", "", "", "", "", "", "",
            "3", "", "3", "", "6", "", "5",
            "", "", "", "", "", "", "",
            "", "2", "", "", "3", "", "",
            "1", "", "", "2", "", "", "2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle b = getIntent().getExtras();
        ArrayAdapter<String> adapter;
        if (b != null) {
            level = b.getInt("level");
            switch (level) {
                case 1:
                    gridView = (GridView) findViewById(R.id.gridview);

                    adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, numbersLevel1);

                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {

                            grid.onClick(getX(position), getY(position));
                        }
                    });

                    grid = new Grid(numbersLevel1, gridSize, this);
                    break;

                case 2:
                    gridView = (GridView) findViewById(R.id.gridview);

                    adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, numbersLevel2);

                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {

                            grid.onClick(getX(position), getY(position));
                        }
                    });

                    grid = new Grid(numbersLevel2, gridSize, this);
                    break;
                case 3:
                    gridView = (GridView) findViewById(R.id.gridview);

                    adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, numbersLevel3);

                    gridView.setAdapter(adapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {

                            grid.onClick(getX(position), getY(position));
                        }
                    });

                    grid = new Grid(numbersLevel3, gridSize, this);
                    break;
            }
        }
    }

    public void restartLevel(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle b = new Bundle();
        b.putInt("level", level);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void setColor(int x, int y, int color){
        gridView.getChildAt(y*gridSize+x).setBackgroundColor(color);
    }

    public int getX(int position){
        return position % 7;
    }

    public int getY(int position){
        return position / 7;
    }

    public void showToast(String str){
        Toast.makeText(getApplicationContext(),
                str, Toast.LENGTH_SHORT).show();
    }
}