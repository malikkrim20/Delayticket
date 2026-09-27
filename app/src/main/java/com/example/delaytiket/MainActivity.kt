package com.example.delaytiket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.CircularProgressIndicator
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                TicketScreen()
            }
        }
    }
}

@Composable
fun TicketScreen() {

    var hargaTiket by rememberSaveable {
        mutableIntStateOf(25000)
    }

    var jumlahTiket by rememberSaveable {
        mutableIntStateOf(1)
    }

    var namaPembeli by rememberSaveable {
        mutableStateOf("")
    }

    var namaError by rememberSaveable {
        mutableStateOf(false)
    }

    var status by rememberSaveable {
        mutableStateOf("Silakan pesan tiket")
    }

    var orderTrigger by rememberSaveable {
        mutableIntStateOf(0)
    }

    var sedangMemproses by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(orderTrigger) {

        if (orderTrigger > 0) {

            sedangMemproses = true
            namaError = false

            status = "Memproses pesanan........."

            delay(5000)

            status = "Tiket telah dipesan"

            sedangMemproses = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9FC))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1976D2),
                            Color(0xFF3F7EF5)
                        )
                    ),
                    shape = RoundedCornerShape(
                        bottomStart = 35.dp,
                        bottomEnd = 35.dp
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 38.dp
                    ),
                verticalArrangement = Arrangement.Center
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Pemesanan Tiket",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 22.dp,
                    bottom = 20.dp
                )
        ) {

            TicketForm(
                hargaTiket = hargaTiket,
                jumlahTiket = jumlahTiket,
                namaPembeli = namaPembeli,
                namaError = namaError,
                status = status,
                sedangMemproses = sedangMemproses,

                onNamaChange = { namaBaru ->

                    namaPembeli = namaBaru

                    namaError = false

                    status = "Silakan pesan tiket"
                },

                onTambah = {

                    jumlahTiket++
                },

                onKurang = {

                    if (jumlahTiket > 1) {
                        jumlahTiket--
                    }
                },

                onPesan = {

                    if (namaPembeli.isBlank()) {

                        namaError = true
                        status = "Nama Masih Kosong"

                    } else {

                        namaError = false
                        orderTrigger++
                    }
                }
            )
        }
    }
}

@Composable
fun TicketForm(
    hargaTiket: Int,
    jumlahTiket: Int,
    namaPembeli: String,
    namaError: Boolean,
    status: String,
    sedangMemproses: Boolean,

    onNamaChange: (String) -> Unit,
    onTambah: () -> Unit,
    onKurang: () -> Unit,
    onPesan: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Nama ",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1D2B53)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = namaPembeli,

            onValueChange = onNamaChange,

            placeholder = {
                Text(
                    text = "Masukkan nama Anda",
                    fontSize = 16.sp
                )
            },

            modifier = Modifier.fillMaxWidth(),

            singleLine = true,

            enabled = !sedangMemproses,

            isError = namaError,

            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),

            keyboardActions = KeyboardActions(
                onDone = {
                    onPesan()
                }
            )
        )


        if (namaError) {

            Text(
                text = "Nama harus diisi!",
                color = Color(0xFFD32F2F),
                fontSize = 14.sp,
                modifier = Modifier.padding(
                    top = 4.dp
                )
            )
        }


        Spacer(modifier = Modifier.height(20.dp))


        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(18.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEDEAF3)
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),

                horizontalAlignment = Alignment.Start
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.size(12.dp))

                    Text(
                        text = "Harga Tiket",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF252C48)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Rp $hargaTiket",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2457D6)
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(18.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEDEAF3)
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.size(12.dp))

                    Text(
                        text = "Jumlah Tiket",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF252C48)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        onClick = onKurang,

                        enabled = !sedangMemproses,

                        modifier = Modifier.size(
                            width = 65.dp,
                            height = 48.dp
                        ),

                        shape = RoundedCornerShape(30.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6950AE)
                        )
                    ) {

                        Text(
                            text = "−",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = jumlahTiket.toString(),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF20243A)
                    )

                    Button(
                        onClick = onTambah,

                        enabled = !sedangMemproses,

                        modifier = Modifier.size(
                            width = 65.dp,
                            height = 48.dp
                        ),

                        shape = RoundedCornerShape(30.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6950AE)
                        )
                    ) {

                        Text(
                            text = "+",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onPesan,

            enabled = !sedangMemproses,

            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),

            shape = RoundedCornerShape(30.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6950AE)
            )
        ) {

            Text(
                text = if (sedangMemproses) {
                    "Memproses..."
                } else {
                    "Pesan Tiket"
                },

                fontSize = 18.sp,

                fontWeight = FontWeight.Bold
            )
        }


        Spacer(modifier = Modifier.height(20.dp))

        StatusCard(
            status = status,
            sedangMemproses = sedangMemproses
        )
    }
}

@Composable
fun StatusCard(
    status: String,
    sedangMemproses: Boolean
) {

    val backgroundColor: Color
    val textColor: Color

    when {

         status == "Tiket telah dipesan" -> {

            backgroundColor = Color(0xFFE5F4E8)
            textColor = Color(0xFF278542)
        }

        status == "Nama Masih Kosong" -> {

            backgroundColor = Color(0xFFFFE7E7)
            textColor = Color(0xFFD32F2F)
        }

        sedangMemproses -> {

            backgroundColor = Color(0xFFE3F0FF)
            textColor = Color(0xFF2876C7)
        }

        else -> {

            backgroundColor = Color(0xFFEDEAF3)
            textColor = Color(0xFF252C48)
        }
    }


    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            when {

                status == "Tiket telah dipesan" -> {

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Pesanan berhasil",
                        tint = textColor,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(
                        modifier = Modifier.size(12.dp)
                    )
                }

                status == "Nama Masih Kosong" -> {

                    Icon(
                        imageVector = Icons.Default.ErrorOutline,
                        contentDescription = "Nama belum diisi",
                        tint = textColor,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(
                        modifier = Modifier.size(12.dp)
                    )
                }

                sedangMemproses -> {

                    CircularProgressIndicator(
                        modifier = Modifier.size(25.dp),

                        color = textColor,

                        strokeWidth = 3.dp
                    )

                    Spacer(
                        modifier = Modifier.size(12.dp)
                    )
                }

                else -> {

                }
            }

            Text(
                text = "Status: $status",

                color = textColor,

                fontSize = 16.sp,

                fontWeight = FontWeight.Bold
            )
        }
    }
}