package com.example.RhythMix

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.RhythMix.ui.theme.RhythMixTheme
import android.Manifest
import androidx.annotation.RequiresApi
import java.io.File

//import com.example.RhythMix.screens.AudioManager


class MainActivity : ComponentActivity() {



    private var audioFile: File? = null
    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RhythMixTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RhythMixApp(modifier = Modifier)
                }
            }
        }
        requestPermissions()

    }
    private fun requestPermissions(){
        val hasRecordedPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if(!hasRecordedPermission){
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()) { permitted: Boolean ->
                if (!permitted) {
                    Toast.makeText(this, "You need to give permission to record", Toast.LENGTH_LONG)
                        .show()
                }
            }
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  //  RhythMixApp()
}