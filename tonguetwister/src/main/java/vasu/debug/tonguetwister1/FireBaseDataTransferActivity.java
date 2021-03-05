package vasu.debug.tonguetwister1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseDataTransferActivity extends AppCompatActivity {

    EditText t1;
    EditText t2;
    EditText t3;
    EditText t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_data_transfer);
    }

    public void sendToDatabase(View view) {
        t1 = (EditText)findViewById(R.id.roll);
        t2 = (EditText)findViewById(R.id.course);
        t3 = (EditText)findViewById(R.id.duration);
        t4 = (EditText)findViewById(R.id.name);

        String roll = t1.getText().toString().trim();
        String course = t2.getText().toString().trim();
        String duration = t3.getText().toString().trim();
        String name = t4.getText().toString().trim();

        DataHolder obj = new DataHolder(name,course,duration);

//        //treat whole database as an object and 'db' has root node of firebase
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        //reference from where entry is permitted or data is filled, entry point.
//        DatabaseReference root = db.getReference();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        reference from where entry is permitted
        DatabaseReference node = db.getReference("students");

        node.child(roll).setValue(obj);

        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");



        Toast.makeText(getApplicationContext() ,"data inserted to firebase..",Toast.LENGTH_LONG);
    }
}