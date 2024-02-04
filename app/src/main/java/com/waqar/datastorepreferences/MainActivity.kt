package com.waqar.datastorepreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.waqar.datastorepreferences.MyApplication.Companion.clearDataStore
import com.waqar.datastorepreferences.MyApplication.Companion.retrieveData
import com.waqar.datastorepreferences.MyApplication.Companion.saveData
import com.waqar.datastorepreferences.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                saveData(
                    binding.etSaveKey.text.toString(),
                    binding.etSaveValue.text.toString()
                )
            }
        }

        binding.btnRead.setOnClickListener {
            lifecycleScope.launch {
//                Log.e("waqar", "ReAD: ->${binding.etReadkey.text}<-")
                val value = retrieveData(binding.etReadkey.text.toString(), "Didn't found value")
                Log.e("waqar", "onCreate: ->$value<-")
                binding.tvReadValue.text = (value ?: "No value found").toString()

            }
        }

        binding.btnClear.setOnClickListener {
            lifecycleScope.launch {
                clearDataStore()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}