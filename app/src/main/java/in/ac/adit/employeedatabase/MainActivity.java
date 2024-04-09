package in.ac.adit.employeedatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText name,email,mobileNo;
    Button add,delete,read,update;
    TextView t1;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.edit2);
        email = findViewById(R.id.edit3);
        mobileNo = findViewById(R.id.edit4);
        add = findViewById(R.id.button1);
        delete = findViewById(R.id.button2);
        update = findViewById(R.id.button3);
        read = findViewById(R.id.button4);
        add.setOnClickListener((View.OnClickListener) this);
        delete.setOnClickListener((View.OnClickListener) this);
        update.setOnClickListener((View.OnClickListener) this);
        read.setOnClickListener((View.OnClickListener) this);
        db = openOrCreateDatabase("EmployeeDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS employee(emailVARCHAR,nameVARCHAR,mobileNoVARCHAR);");
    }
    public void onClick(View view)
    {
// Inserting a record to the Student table
        if(view==add)
        {
             if(mobileNo.getText().toString().trim().length()==0|| name.getText().toString().trim().length()==0|| email.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter all values");
                return;
            }
            db.execSQL("INSERT INTO employee VALUES('"+mobileNo.getText()+"','"+name.getText()+ "','"+email.getText()+"');");
            showMessage("Success", "Record added"); clearText();
        }
        if(view==delete)
        {

            if(mobileNo.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter mobile no"); return;

            }
            Cursor c=db.rawQuery("SELECT * FROM employee WHERE mobileNo='"+mobileNo.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM employee WHERE mobileNo='"+mobileNo.getText()+"'");
                showMessage("Success", "Record Deleted");
            }
            else
            {
                showMessage("Error", "Invalid Rollno");
            }
            clearText();
        }
        if(view==update)
        {

            if(mobileNo.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter mobile no"); return;
            }
            Cursor c=db.rawQuery("SELECT * FROM employee WHERE mobileno='"+mobileNo.getText()+"'", null);
            if(c.moveToFirst()) {
                db.execSQL("UPDATE employee SET name='" + name.getText() + "',email='" + email.getText() +
                        "' WHERE mobileNo='"+mobileNo.getText()+"'"); showMessage("Success", "Record Modified");
            }
            else {
                showMessage("Error", "Invalid mobileno");
            }
            clearText();
        }
        if(view==read) {

            if (mobileNo.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter mobile no");
                return;
            }
            Cursor c = db.rawQuery("SELECT * FROM employee WHERE mobileno='" + mobileNo.getText() + "'", null);
            if (c.moveToFirst()) {
                name.setText(c.getString(1));
                email.setText(c.getString(2));
            } else {
                showMessage("Error", "Invalid mobileno");

                clearText();
            }
        }
    }

    private void clearText() {
        mobileNo.setText("");
            name.setText("");
            email.setText(""); mobileNo.requestFocus();

        }


    private void showMessage(String title, String message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }



