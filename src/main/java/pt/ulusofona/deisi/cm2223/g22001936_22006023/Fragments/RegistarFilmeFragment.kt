package pt.ulusofona.deisi.cm2223.g22001936_22006023.Fragments

import android.Manifest
import android.R
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Models.Filme
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Adapters.PhotoAdapter
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Pipocas.Cinemas
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Pipocas.Filmes
import pt.ulusofona.deisi.cm2223.g22001936_22006023.Pipocas.RegistoFilmes
import pt.ulusofona.deisi.cm2223.g22001936_22006023.databinding.FragmentRegistarFilmeBinding
import java.text.SimpleDateFormat
import java.util.*


class RegistarFilmeFragment : Fragment() {

    private val REQUEST_CAMERA_PERMISSION = 1
    private val CAMERA_PERMISSION_CODE = 100
    private lateinit var binding: FragmentRegistarFilmeBinding
    private val TAG = RegistarFilmeFragment::class.java.simpleName
    private lateinit var datePickerDialog : DatePickerDialog
    private lateinit var dataEdit : TextInputEditText
    private lateinit var submitButton : Button
    var calendar = Calendar.getInstance()
    var format : SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    private var photoList: MutableList<Bitmap> = mutableListOf()
    var adapter = PhotoAdapter(photoList)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentRegistarFilmeBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Registar Filme"
        initDatePicker()
        binding.dataLayout.setEndIconOnClickListener { openDatePicker(binding.root) }

        val adapterFilmes: ArrayAdapter<Filme> =
            ArrayAdapter<Filme>(requireContext(), R.layout.select_dialog_item, Filmes.filmes)

        val adapterCinemas: ArrayAdapter<Cinema> =
            ArrayAdapter<Cinema>(requireContext(), R.layout.select_dialog_item, Cinemas.cinemas)

        val actvFilme = binding.autoCompleteTextViewFilmes
        val actvCinema = binding.autoCompleteTextViewCinemas

        actvFilme.threshold = 1
        actvCinema.threshold = 1 //will start working from first character

        actvFilme.setAdapter(adapterFilmes)
        actvCinema.setAdapter(adapterCinemas)
         //setting the adapter data into the AutoCompleteTextView
        
        binding.autoCompleteTextViewFilmes.doOnTextChanged { text, start, before, count ->

            if(Filmes.procurarFilme(text.toString())){
                binding.filmeLayout.error = null
            }else{
                binding.filmeLayout.error = "Filme inexistente por favor escolha um da lista"
            }

        }

        binding.autoCompleteTextViewCinemas.doOnTextChanged { text, start, before, count ->

            if(Cinemas.procurarCinema(text.toString())){
                binding.cinemaLayout.error = null
            }else{
                binding.cinemaLayout.error = "Cinema inexistente por favor escolha um da lista"
            }

        }

        submitButton = binding.submitButton
        submitButton.setOnClickListener { submeter()}
        dataEdit = binding.dataEdit
        dataEdit.setText(getTodaysDate())
        dataEdit.setOnClickListener{ openDatePicker(binding.root)}

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView.adapter = adapter

        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap
                photoList.add(imageBitmap)

                adapter?.notifyDataSetChanged()

            }
        }

        binding.buttonCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
                // A permissão ainda não foi concedida. Solicite a permissão.
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
            } else {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                    resultLauncher.launch(takePictureIntent)
                }
            }

        }

        fun onRequestPermissionsResult(requestCode: Int,
                                                permissions: Array<String>, grantResults: IntArray) {
            when (requestCode) {
                REQUEST_CAMERA_PERMISSION -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                            resultLauncher.launch(takePictureIntent)
                        }
                    } else {
                        Toast.makeText(requireContext(), "A permissão de câmera foi negada", Toast.LENGTH_SHORT).show()
                    }
                    return
                }
            }
        }
        return binding.root
    }


    private fun checkValues(): Boolean{

        return binding.filmeLayout.error == null && binding.autoCompleteTextViewFilmes.text.toString() != "" && binding.autoCompleteTextViewCinemas.text.toString() != "" && binding.cinemaLayout.error == null && binding.ratingBar.rating != 0f && binding.dataEdit.text != null

    }
   private fun submeter(){
        if(checkValues()){
            RegistoFilmes.submit(
                Filmes.pegarFilme(binding.autoCompleteTextViewFilmes.text.toString())!!,
                Cinemas.pegarCinema(binding.autoCompleteTextViewCinemas.text.toString())!!, binding.ratingBar.rating.toInt(), binding.dataEdit.text.toString(),photoList, binding.observacoesEdit.text.toString())
            Toast.makeText(context, "Registo submetido com sucesso!", Toast.LENGTH_LONG).show()
            binding.observacoesEdit.setText("")
            binding.autoCompleteTextViewFilmes.setText("")
            binding.autoCompleteTextViewCinemas.setText("")
            binding.ratingBar.rating = 5f
            photoList.clear()
        }else{
            Toast.makeText(context, "Erro ao submeter verifique se preencheu bem o formulário", Toast.LENGTH_LONG).show()
        }
    }

    private fun getTodaysDate() : String{
        val cal : Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        var month: Int = cal.get(Calendar.MONTH)
        month += 1
        val day : Int = cal.get(Calendar.DAY_OF_MONTH)

        return makeDateString(day,month,year)
    }


    private fun initDatePicker(){
        val dateSetListener : DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener(){ datePicker: DatePicker, year: Int, month: Int, day: Int ->
            val months = month +1
            val date = makeDateString(day,months,year)
            dataEdit.setText(date)
        }
        val cal : Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day : Int = cal.get(Calendar.DAY_OF_MONTH)


        datePickerDialog = DatePickerDialog(requireContext(),dateSetListener,year,month,day)
        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
    }

    private fun makeDateString(day:Int, month:Int, year:Int):String{
        return "$year/${month.toString().padStart(2,'0')}/${day.toString().padStart(2,'0')}"

    }

    private fun openDatePicker(view: View) {
        datePickerDialog.show()
    }

}