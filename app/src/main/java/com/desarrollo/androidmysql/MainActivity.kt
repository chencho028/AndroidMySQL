package com.desarrollo.androidmysql

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class MainActivity : AppCompatActivity() {
    protected lateinit var text:TextView
    protected lateinit var errortext:TextView
    protected lateinit var btn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializamos
        text = findViewById(R.id.txt)
        errortext = findViewById(R.id.txtError)
        btn = findViewById(R.id.btnShow)

        btn.setOnClickListener{
            val task = Task(this)
            task.execute()
        }

    }

    class Task(protected var activity: MainActivity): AsyncTask<Void, Void, Void>() {
        var records:String = ""
        var error:String = ""
        override fun doInBackground(vararg params: Void?): Void? {
            try{
                Class.forName("com.mysql.jdbc.Driver")
                val conn:Connection = DriverManager.getConnection("jdbc:mysql://192.168.1.45:3306/android", "andro", "andro" )
                val st:Statement = conn.createStatement()
                val rs:ResultSet = st.executeQuery("SELECT * FROM test")

                while (rs.next()){
                    records += rs.getString(1) + " " + rs.getString(2) + "\n"
                }
            }catch (e:Exception){
                error = e.toString()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            activity.text.text = records
            if(error != ""){
                activity.errortext.text = error
            }
            super.onPostExecute(result)
        }

    }
}
