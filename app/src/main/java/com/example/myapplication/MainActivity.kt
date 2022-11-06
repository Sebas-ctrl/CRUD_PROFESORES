package com.example.myapplication

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nombre = findViewById<EditText>(R.id.txtNombre)
        val apellidos = findViewById<EditText>(R.id.txtApellidos)
        val direccion = findViewById<EditText>(R.id.direccion)
        val telefono = findViewById<EditText>(R.id.numTelefono)
        val correo = findViewById<EditText>(R.id.correo)
        val contraseña = findViewById<EditText>(R.id.contraseña)
        val repetir_contraseña = findViewById<EditText>(R.id.repetir_contraseña)

        val eliminar = findViewById<ImageButton>(R.id.btnEliminar)
        val editar = findViewById<ImageButton>(R.id.btnEditar)
        val guardar = findViewById<ImageButton>(R.id.btnGuardar)
        val buscar = findViewById<ImageButton>(R.id.btnBuscar)

        eliminar.setOnClickListener {
            if(nombre.text.isNotEmpty() || telefono.text.isNotEmpty() || correo.text.isNotEmpty()){
                if(contraseña.text.contentEquals(repetir_contraseña.text)){
                    val admin = SQLiteConnection(this, "cursos", null, 1)
                    val bd = admin.writableDatabase

                    val cursor = bd.rawQuery("select * from docentes " +
                            "where nombre='${nombre.text}' or " +
                            "telefono='${telefono.text}' or " +
                            "correo='${correo.text}'", null)

                    if(cursor.moveToFirst()){
                        val args = arrayOf(nombre.text.toString(), telefono.text.toString(), correo.text.toString())
                        bd.delete("docentes", "nombre=? or telefono=? or correo=?", args)
                        bd.close()

                        nombre.setText("")
                        apellidos.setText("")
                        direccion.setText("")
                        telefono.setText("")
                        correo.setText("")
                        contraseña.setText("")
                        repetir_contraseña.setText("")
                        Toast.makeText(this, "¡El registro se ha eliminado correctamente!", Toast.LENGTH_SHORT).show()
                    }else{
                        nombre.setText("")
                        apellidos.setText("")
                        direccion.setText("")
                        telefono.setText("")
                        correo.setText("")
                        contraseña.setText("")
                        repetir_contraseña.setText("")
                        Toast.makeText(this, "¡No se encontró ningún registro!", Toast.LENGTH_SHORT).show()
                    }

                    cursor.close()


                }else{
                    Toast.makeText(this, "¡Las contraseñas no coinciden!", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "¡Debes llenar el campo de nombre, correo o teléfono!", Toast.LENGTH_SHORT).show()
            }
        }

        editar.setOnClickListener {
            if(nombre.text.isNotEmpty() || telefono.text.isNotEmpty() || correo.text.isNotEmpty()){
                if(contraseña.text.contentEquals(repetir_contraseña.text)){
                    val admin = SQLiteConnection(this, "cursos", null, 1)
                    val bd = admin.writableDatabase
                    val edicion = ContentValues()
                    edicion.put("nombre", nombre.text.toString())
                    edicion.put("apellidos", apellidos.text.toString())
                    edicion.put("direccion", direccion.text.toString())
                    edicion.put("telefono", telefono.text.toString())
                    edicion.put("correo", correo.text.toString())
                    edicion.put("contraseña", contraseña.text.toString())

                    val cursor = bd.rawQuery("select * from docentes " +
                            "where nombre='${nombre.text}' or " +
                            "telefono='${telefono.text}' or " +
                            "correo='${correo.text}'", null)

                    if(cursor.moveToFirst()){
                        val args = arrayOf(nombre.text.toString(), telefono.text.toString(), correo.text.toString())
                        bd.update("docentes", edicion, "nombre=? or telefono=? or correo=?", args)
                        bd.close()

                        nombre.setText("")
                        apellidos.setText("")
                        direccion.setText("")
                        telefono.setText("")
                        correo.setText("")
                        contraseña.setText("")
                        repetir_contraseña.setText("")
                        Toast.makeText(this, "¡Los datos se han modificado correctamente!", Toast.LENGTH_SHORT).show()
                    }else{
                        nombre.setText("")
                        apellidos.setText("")
                        direccion.setText("")
                        telefono.setText("")
                        correo.setText("")
                        contraseña.setText("")
                        repetir_contraseña.setText("")
                        Toast.makeText(this, "¡No se encontró ningún registro!", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(this, "¡Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "¡Debes llenar el campo de nombre, correo o teléfono!", Toast.LENGTH_SHORT).show()
            }
        }

        guardar.setOnClickListener {
            if(nombre.text.isEmpty() || apellidos.text.isEmpty() || direccion.text.isEmpty() || telefono.text.isEmpty() || correo.text.isEmpty() || contraseña.text.isEmpty() || repetir_contraseña.text.isEmpty()){
                Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()
            }else{
                if(contraseña.text.contentEquals(repetir_contraseña.text)){
                    val admin = SQLiteConnection(this, "cursos", null, 1)
                    val bd = admin.writableDatabase
                    val registro = ContentValues()
                    registro.put("nombre", nombre.text.toString())
                    registro.put("apellidos", apellidos.text.toString())
                    registro.put("direccion", direccion.text.toString())
                    registro.put("telefono", telefono.text.toString())
                    registro.put("correo", correo.text.toString())
                    registro.put("contraseña", contraseña.text.toString())

                    bd.insert("docentes", null, registro)
                    bd.close()

                    // Limpiar campos
                    nombre.setText("")
                    apellidos.setText("")
                    direccion.setText("")
                    telefono.setText("")
                    correo.setText("")
                    contraseña.setText("")
                    repetir_contraseña.setText("")
                    Toast.makeText(this, "¡Los datos se han guardado correctamente!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "¡Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        buscar.setOnClickListener {
            if(nombre.text.isNotEmpty() || telefono.text.isNotEmpty() || correo.text.isNotEmpty()){
                if(contraseña.text.contentEquals(repetir_contraseña.text)){
                    val admin = SQLiteConnection(this, "cursos", null, 1)
                    val bd = admin.writableDatabase

                    val cursor = bd.rawQuery("select * from docentes " +
                            "where nombre='${nombre.text}' or " +
                            "telefono='${telefono.text}' or " +
                            "correo='${correo.text}'", null)

                    if(cursor.moveToFirst()){
                        nombre.setText(cursor.getString(1))
                        apellidos.setText(cursor.getString(2))
                        direccion.setText(cursor.getString(3))
                        telefono.setText(cursor.getString(4))
                        correo.setText(cursor.getString(5))
                        contraseña.setText(cursor.getString(6))
                        repetir_contraseña.setText(cursor.getString(6))
                    }else{
                        nombre.setText("")
                        telefono.setText("")
                        correo.setText("")

                        Toast.makeText(this, "¡No se encontró ningún registro!", Toast.LENGTH_SHORT).show()
                    }

                    cursor.close()

                }else{
                    Toast.makeText(this, "¡Las contraseñas no coinciden!", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "¡Debes llenar el campo de nombre, correo o teléfono!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}