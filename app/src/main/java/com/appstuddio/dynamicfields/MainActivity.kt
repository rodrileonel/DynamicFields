package com.appstuddio.dynamicfields

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appstuddio.dynamicfields.network.request.FieldsRequest
import com.appstuddio.dynamicfields.network.response.FieldResponse
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(ViewModel::class.java) }
    private lateinit var requestMap: LinkedHashMap<String, String>
    private var id = 0
    private lateinit var list:List<FieldResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btGet.setOnClickListener {
            requestMap = LinkedHashMap()
            canvas.removeAllViews()
            viewModel.getData().observe(this, Observer {
                it?.let {
                    id = it.data.id
                    list = it.data.elements
                    for (item in list)
                        when(item.componentType){
                            "text","text_multiline"-> setEditText(item, InputType.TYPE_CLASS_TEXT)
                            "numeric"-> setEditText(item, InputType.TYPE_CLASS_NUMBER)
                            "calendar"-> setEditText(item, InputType.TYPE_CLASS_DATETIME)
                        }
                }
            })
        }
        btSet.setOnClickListener {
            val result = jsonEscape(JSONObject(requestMap as Map<String,String>).toString())
            viewModel.setData(FieldsRequest(FieldsRequest.Elements(result),id))
            tvJSON.text = result
        }
    }

    private fun jsonEscape(str:String):String  {
        return str.replace("\",","\",\n\t")
            .replace("},","},\n\t")
            .replace("{", "{\n\t")
            .replace("\"}", "\"\n}")
    }
    private fun setEditText(item:FieldResponse,type:Int) {
        val editView = EditText(this)
        editView.id = item.ordinal?.toInt()!!
        editView.inputType = type
        editView.hint = item.fieldView
        editView.isFocusable = true
        editView.isClickable = true
        canvas.addView(editView)
        requestMap[item.fieldKey!!] = editView.text.toString()
        editView.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP) {
                requestMap[item.fieldKey] = editView.text.toString()
                true
            } else false
        }
        editView.setOnClickListener {
            validateDate(type,editView,item.fieldKey)
        }
    }

    private fun validateDate(type:Int,editView:EditText,item:String) {
        if(type == InputType.TYPE_CLASS_DATETIME){
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                editView.setText(sdf.format(cal.time).toString())
                requestMap[item] = editView.text.toString()

            }
            DatePickerDialog(this@MainActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
}