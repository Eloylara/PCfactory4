package longtabs.app.pcfactory4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_placa, et_cpu, et_tarjeta, et_fuente, et_ram,et_alma, et_gabinete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_placa = (EditText)findViewById(R.id.txt_placa);
        et_cpu = (EditText)findViewById(R.id.txt_cpu);
        et_tarjeta = (EditText)findViewById(R.id.txt_tarjeta);
        et_fuente = (EditText)findViewById(R.id.txt_fuente);
        et_ram = (EditText)findViewById(R.id.txt_ram);
        et_alma = (EditText)findViewById(R.id.txt_alma);
        et_gabinete = (EditText)findViewById(R.id.txt_gabinete);
    }
    public void Registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String placa = et_placa.getText().toString();
        String cpu = et_cpu.getText().toString();
        String tarjeta = et_tarjeta.getText().toString();
        String fuente = et_fuente.getText().toString();
        String ram = et_ram.getText().toString();
        String alma = et_alma.getText().toString();
        String gabinete = et_gabinete.getText().toString();

        if(!codigo.isEmpty() && !placa.isEmpty() && !cpu.isEmpty() && !tarjeta.isEmpty() && !fuente.isEmpty() && !ram.isEmpty() && !alma.isEmpty() && !gabinete.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("placa", placa);
            registro.put("cpu", cpu);
            registro.put("tarjeta", tarjeta);
            registro.put("fuente", fuente);
            registro.put("ram", ram);
            registro.put("alma", alma);
            registro.put("gabinete", gabinete);

            BaseDeDatos.insert("articulos", null, registro);

            BaseDeDatos.close();
            et_codigo.setText("");
            et_placa.setText("");
            et_cpu.setText("");
            et_tarjeta.setText("");
            et_fuente.setText("");
            et_ram.setText("");
            et_alma.setText("");
            et_gabinete.setText("");


            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select placa, cpu, tarjeta, fuente, ram, alma, gabinete from articulos where codigo =" + codigo, null);

            if(fila.moveToFirst()){
                et_placa.setText(fila.getString(0));
                et_cpu.setText(fila.getString(1));
                et_tarjeta.setText(fila.getString(2));
                et_fuente.setText(fila.getString(3));
                et_ram.setText(fila.getString(4));
                et_alma.setText(fila.getString(5));
                et_gabinete.setText(fila.getString(6));
                BaseDeDatabase.close();
            } else {
                Toast.makeText(this,"No existe el artículo", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }

        } else {
            Toast.makeText(this, "Debes introducir el código del artículo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper
                (this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()){

            int cantidad = BaseDatabase.delete("articulos", "codigo=" + codigo, null);
            BaseDatabase.close();

            et_codigo.setText("");
            et_placa.setText("");
            et_cpu.setText("");
            et_tarjeta.setText("");
            et_fuente.setText("");
            et_ram.setText("");
            et_alma.setText("");
            et_gabinete.setText("");

            if(cantidad == 1){
                Toast.makeText(this, "Artículo eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El artículo no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes de introducir el código del artículo", Toast.LENGTH_SHORT).show();
        }
    }

    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String placa = et_placa.getText().toString();
        String cpu = et_cpu.getText().toString();
        String tarjeta = et_tarjeta.getText().toString();
        String fuente = et_fuente.getText().toString();
        String ram = et_ram.getText().toString();
        String alma = et_alma.getText().toString();
        String gabinete = et_gabinete.getText().toString();

        if(!codigo.isEmpty() && !placa.isEmpty() && !cpu.isEmpty() && !tarjeta.isEmpty() && !fuente.isEmpty() && !ram.isEmpty() && !alma.isEmpty() && !gabinete.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("placa", placa);
            registro.put("cpu", cpu);
            registro.put("tarjeta", tarjeta);
            registro.put("fuente", fuente);
            registro.put("ram", ram);
            registro.put("alma", alma);
            registro.put("gabinete", gabinete);

            int cantidad = BaseDatabase.update("articulos", registro, "codigo=" + codigo, null);
            BaseDatabase.close();

            if(cantidad == 1){
                Toast.makeText(this, "Artículo modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El artículo no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

}