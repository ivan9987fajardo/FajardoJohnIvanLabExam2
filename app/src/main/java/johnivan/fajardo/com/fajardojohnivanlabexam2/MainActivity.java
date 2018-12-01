package johnivan.fajardo.com.fajardojohnivanlabexam2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView avg_exam;
    EditText first,last,ex1,ex2;
    float exam1_int, exam2_int,average;
    String first_name, last_name,exam1_str,exam2_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avg_exam = (TextView)findViewById(R.id.average_exam);
        first = (EditText)findViewById(R.id.fname);
        last = (EditText)findViewById(R.id.lname);
        ex1 = (EditText)findViewById(R.id.exam1);
        ex2 = (EditText)findViewById(R.id.exam2);


        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

    }

    public void avgBtn(View v){
        if( first.getText().toString().length() == 0||last.getText().toString().length() == 0||ex1.getText().toString().length() == 0||ex2.getText().toString().length() == 0 ){
            Toast.makeText(getBaseContext(), "Incomplete Information!",Toast.LENGTH_SHORT).show();
        }
        else {
            first_name = first.getText().toString();
            last_name = last.getText().toString();
            exam1_str = ex1.getText().toString();
            exam2_str = ex2.getText().toString();
            exam1_int = Float.parseFloat(exam1_str);
            exam2_int = Float.parseFloat(exam2_str);
            average = (exam1_int + exam2_int) / 2;
            avg_exam.setText("Your Average is: " + Float.toString(average));
            String body = "First Name: " + first_name + "\n";
            body = body + "Last Name: " + last_name + "\n";
            body = body + "Average: " + average + "\n";
            writeToFile(first_name + last_name, body);
        }

    }

    public  void writeToFile(String fileName, String body)
    {
        FileOutputStream fos = null;

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ICS115/" );

            if (!dir.exists())
            {
                if(!dir.mkdirs()){
                    Log.e("ALERT","could not create the directories");
                }
            }

            final File myFile = new File(dir, fileName + ".txt");

            if (!myFile.exists())
            {
                myFile.createNewFile();
            }

            fos = new FileOutputStream(myFile);

            fos.write(body.getBytes());
            Toast.makeText(getBaseContext(), "Write on SD Card Successful",
                    Toast.LENGTH_SHORT).show();
            fos.close();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
